package app.iria.extractors.tika

import org.apache.tika.metadata.{Metadata => TikaMetadata}

object TikaUtils {

    def searchByKey( expression : String, props : Map[ String, String ] ) : Map[ String, String ] = {
        props
          .keys
          .filter( key => key.matches( expression ) )
          .map( key => (key, props( key )) )
          .toMap
    }

    def toMap( metadata : TikaMetadata ) : Map[ String, String ] = {
        metadata
          .names()
          .map( key => (key, metadata.get( key )) )
          .toMap
    }

}
