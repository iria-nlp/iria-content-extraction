package app.iria.extractors.web

import app.iria.extractors.{DocumentExtractor, RawInput}
import app.iria.utils.ExceptionDecorator.exceptionText
import org.scalatra.servlet.{FileUploadSupport, MultipartConfig}
import org.scalatra.{InternalServerError, Ok, ScalatraServlet}

import scala.util.{Failure, Success}

class DocumentUploadController( extractor : DocumentExtractor,
                                rawDocStorage : RawDocStorage,
                                notifications : Set[ Notification ] ) extends ScalatraServlet with FileUploadSupport {

    val STREAM_THRESHOLD_SIZE : Int = 100 * 1000 * 1000
    configureMultipartHandling( MultipartConfig( fileSizeThreshold = Some( STREAM_THRESHOLD_SIZE ) ) )

    post( "/" ) {
        fileParams.get( "file" ) match {
            case Some( file ) => {
                val raw = RawInput( file.name, file.get )
                extractor.extractDoc( raw ) match {
                    case Success( doc ) => {
                        notifications.foreach( _.send( doc ) )
                        Ok( doc.id )
                    }
                    case Failure( e ) => {
                        InternalServerError( s" ${file.name} - ${exceptionText( e )}" )
                    }
                }
            }
            case None => null
        }
    }
}
