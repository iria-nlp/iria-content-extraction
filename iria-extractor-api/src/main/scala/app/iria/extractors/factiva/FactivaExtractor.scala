package app.iria.extractors.factiva

import app.iria.extractors.ExtractorUtils.formatByline
import app.iria.extractors.{Extractor, RawInput}
import app.iria.model.{Doc, Metadata, Normalized, Source}
import app.iria.utils.IdGenerator
import app.iria.utils.temporal.OffsetDateTimes
import better.files.Resource
import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.{JSONObject, JSONTokener}

import java.time.OffsetDateTime

class FactivaExtractor extends Extractor {

    override val name : String = "factiva-extractor"

    private val schema : Schema = {
        val schemaJson = new JSONObject( new JSONTokener( Resource.getAsStream( "factiva/schema.json" ) ) )
        SchemaLoader.load( schemaJson )
    }

    private val acceptedTypes : Set[ String ] = Set( "application/json", "text/json", "text/plain" )

    override def extract( raw : RawInput ) : Doc = {
        val factiva : Factiva = FactivaJsonFormat.fromFactivaJson( raw.content )
        toDoc( raw, factiva )
    }

    override def accepts( contentType : String, content : Array[ Byte ] ) : Boolean = {
        if ( acceptedTypes.contains( ignoreAdditionalTypes( contentType ) ) ) {
            try {
                schema.validate( new JSONObject( new String( content ) ) )
                return true
            }
            catch {
                case e : Throwable => return false
            }
        } else false
    }

    private def toDoc( raw : RawInput, factiva : Factiva ) : Doc = {
        val docId = IdGenerator.newId( raw.content )
        val source : Source = Source( url = null, contentType = "application/json", cachedUrl = "", properties = Map() )
        val meta : Metadata = Metadata( creator = factiva.sourceName,
                                        publisher = factiva.publisherName,
                                        retrievedDate = convertFactivaTimeValue( factiva.ingestionDatetime ),
                                        createdDate = convertFactivaTimeValue( factiva.publicationDatetime ),
                                        modifiedDate = convertFactivaTimeValue( factiva.modificationDateTime ) )

        val norm : Normalized = Normalized( title = factiva.title, author = formatByline( factiva.byline ), subject = null, body = factiva.body, tags = Set(), genre = "news_article" )

        Doc( id = docId, source = source, normalized = norm, metadata = meta )
    }

    private def convertFactivaTimeValue( value : String ) : OffsetDateTime = {
        if ( value != null && value.nonEmpty ) OffsetDateTimes.fromEpochSecond( value.toLong )
        else null
    }
}
