package app.iria.extractors.tika

import app.iria.extractors.tika.TikaUtils._
import app.iria.extractors.{Extractor, RawInput}
import app.iria.model.{Doc, Metadata, Normalized, Source}
import app.iria.utils.IdGenerator
import app.iria.utils.temporal.{OffsetDateTimes, TemporalParser}
import org.apache.tika.metadata.{Property, Metadata => TikaMetadata, TikaCoreProperties => TikaMetaKeys}
import org.apache.tika.parser.{AutoDetectParser, ParseContext, Parser}
import org.apache.tika.sax.BodyContentHandler

import java.io.ByteArrayInputStream
import java.time.OffsetDateTime

// TODO - dedicated handlers for common metadata classes (ie: open graph, dublin core, etc...)
class TikaExtractor( dateParser : TemporalParser = TemporalParser.default() ) extends Extractor {

    override val name : String = "tika-extractor"

    private val parseContext : ParseContext = new ParseContext()
    private val parser : Parser = new AutoDetectParser()

    private val acceptedTypes : Set[ String ] = Set( "text/html", "application/html", "application/pdf", "application/xhtml", "application/octet-stream" )

    override def extract( raw : RawInput ) : Doc = {
        val id = IdGenerator.newId( raw.content )

        val bytes = new ByteArrayInputStream( raw.content )
        val body = new BodyContentHandler( -1 )
        val meta = new TikaMetadata()

        parser.parse( bytes, body, meta, parseContext )

        val source = extractSource( raw, meta )
        val normalized = extractNormalized( body, meta )
        val metadata = extractMetadata( raw, meta )
        Doc( id, source, normalized, metadata )
    }

    override def accepts( contentType : String, content : Array[ Byte ] ) : Boolean = {
        acceptedTypes.contains( ignoreAdditionalTypes( contentType ) )
    }

    private def extractSource( rawData : RawInput, metadata : TikaMetadata ) : Source = {
        val contentType : String = {
            if ( rawData.contentType.isEmpty ) {
                extractProperty( Seq( TikaMetaKeys.FORMAT ), "(?i)(.*content-type.*)", metadata ) match {
                    case null => null
                    case content => content.split( ";" )( 0 )
                }
            }
            else rawData.contentType.get
        }

        val url : String = {
            Option( extractProperty( Seq( TikaMetaKeys.SOURCE, OpenGraphMetaKeys.URL ), fallbackRegex = "(?i).*url.*", metadata = metadata ) ) match {
                case Some( value ) => value
                case None => null
            }
        }
        Source( url = url, contentType = contentType, cachedUrl = null, toMap( metadata ) )
    }

    private def extractNormalized( body : BodyContentHandler, metadata : TikaMetadata ) : Normalized = {
        val title : String = extractProperty( Seq( TikaMetaKeys.TITLE ), "(?i)(.*title.*)", metadata )
        val author : String = {
            val byAuthor = extractProperty( fallbackRegex = "(?i)(.*author.*)", metadata = metadata )
            val byByline = extractProperty( fallbackRegex = "(?i)(by(\\s?)l(ine)?)", metadata = metadata )
            if ( byByline != null && byByline.nonEmpty ) byByline
            else if ( byAuthor != null && byAuthor.nonEmpty ) byAuthor
            else extractProperty( Seq( TikaMetaKeys.CREATOR ), "(?i)(.*creator.*)", metadata )
        }

        Normalized( title = title, author = author, body = body.toString.trim(), subject = null, tags = Set(), genre = null ) // TODO genre prediction
    }


    private def extractMetadata( raw : RawInput, metadata : TikaMetadata ) : Metadata = {
        val created : OffsetDateTime = extractDateProperty( Seq( TikaMetaKeys.CREATED ), "(?i)((.*created)|(.*published).*(date|time).*)", metadata )
        val modified : OffsetDateTime = extractDateProperty( Seq( TikaMetaKeys.MODIFIED ), "(?i)((.*modified.*).*(date|time).*)", metadata )
        val publisher = extractProperty( Seq( TikaMetaKeys.PUBLISHER, OpenGraphMetaKeys.SITE_NAME ), "(?i).*publisher.*", metadata )
        val creator = extractProperty( Seq( TikaMetaKeys.CREATOR ), "(?i)(.*creator.*)", metadata )

        Metadata( retrievedDate = raw.timestamp, createdDate = created, modifiedDate = modified, creator = creator, publisher = publisher )
    }

    private def extractProperty( preferredSourceFields : Seq[ Property ] = Seq(), fallbackRegex : String, metadata : TikaMetadata ) : String = {
        val preferredTikaProperties : Seq[ String ] = {
            preferredSourceFields.flatMap( prop => {
                Option( metadata.get( prop ) ) match {
                    case Some( prop ) => Option( prop )
                    case None => None
                }
            } )
        }

        if ( preferredTikaProperties.nonEmpty ) preferredTikaProperties.head
        else {
            searchByKey( fallbackRegex, toMap( metadata ) )
              .flatMap( prop => Option( prop._2 ) )
              .headOption.orNull
        }
    }

    private def extractDateProperty( tikaProps : Seq[ Property ], searchRegex : String, metadata : TikaMetadata ) : OffsetDateTime = {
        val dateStr : Option[ String ] = {
            tikaProps
              .flatMap( prop => Option( metadata.get( prop ) ) )
              .headOption match {
                case result@Some( _ ) => result
                case None => {
                    val candidates = searchByKey( searchRegex, toMap( metadata ) )
                    if ( candidates.nonEmpty ) Some( candidates.head._2 )
                    else None
                }
            }
        }

        dateStr match {
            case Some( value ) => {
                dateParser.get( value ) match {
                    case Some( value ) => {
                        value match {
                            case Left( offsetDateTime ) => offsetDateTime
                            case Right( localDate ) => OffsetDateTimes.midnightOf( localDate.getMonthValue, localDate.getDayOfMonth, localDate.getYear )
                        }
                    }
                    case None => null
                }
            }
            case None => null
        }

    }
}
