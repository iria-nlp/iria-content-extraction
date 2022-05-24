package app.iria.extractors

import app.iria.extractors.processors.PostProcessor
import app.iria.extractors.utils.HtmlUtils
import app.iria.model.Doc
import org.apache.tika.detect.{DefaultDetector, Detector}
import org.apache.tika.io.TikaInputStream
import org.apache.tika.metadata.{Metadata => TikaMetadata}
import org.slf4j.{Logger, LoggerFactory}

import scala.util.{Failure, Success, Try}

class DocumentExtractor( extractors : Set[ Extractor ],
                         postprocessors : Seq[ PostProcessor ] = Seq() ) {

    private val LOG : Logger = LoggerFactory.getLogger( getClass )

    private val detector : Detector = new DefaultDetector

    def extractDoc( input : RawInput ) : Try[ Doc ] = {
        try {
            // TODO - implement preprocessor behavior
            val updatedInput : RawInput = {
                if ( input.contentType.isEmpty || input.contentType.get == "application/octet-stream" ) {
                    val contentType = detectContentType( input.content )
                    input.copy( contentType = Some( contentType ) )
                } else input
            }

            val extractorCandidates = extractors.filter( _.accepts( updatedInput.contentType.get, input.content ) )
            if ( extractorCandidates.size == 1 ) {
                LOG.info( s"using ${extractorCandidates.head.name} for ${updatedInput.name}" )
                val doc : Doc = {
                    postprocessors
                      .foldLeft( extractorCandidates.head.extract( updatedInput ) )( ( doc, processor ) => processor.execute( doc ) )
                }
                Success( doc )
            }
            else Failure( new Exception( s"ambiguous content type: ${updatedInput.contentType.get}, cannot determine the correct extractor to use..." ) )
        } catch {
            case e : Throwable => {
                e.printStackTrace()
                Failure( e )
            } //TODO -- better exception handling lol
        }
    }

    private def detectContentType( content : Array[ Byte ] ) : String = {
        detector.detect( TikaInputStream.get( content ), new TikaMetadata() ).toString match {
            case "application/octet-stream" => {
                if ( HtmlUtils.isHtml( new String( content ) ) ) "text/html"
                else "application/octet-stream"
            }
            case other => other
        }
    }

}
