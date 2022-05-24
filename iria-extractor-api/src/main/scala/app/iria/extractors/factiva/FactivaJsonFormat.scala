package app.iria.extractors.factiva

import app.iria.serialization.{DeserializationException, JacksonSerialization}
import app.iria.utils.ExceptionDecorator._

import scala.util.{Failure, Success}

object FactivaJsonFormat extends JacksonSerialization {

    def fromFactivaJson( json : Array[ Byte ] ) : Factiva = {
        unmarshalTo( new String( json ), classOf[ Factiva ] ) match {
            case Success( factiva ) => factiva
            case Failure( e ) => throw new DeserializationException( s"failed to deserialize `factiva` ${e.logFormat}", new String( json ), e )
        }
    }

}
