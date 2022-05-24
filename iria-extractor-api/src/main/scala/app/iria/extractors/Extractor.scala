package app.iria.extractors

import app.iria.model.Doc

trait Extractor {

    val name : String

    def extract( raw : RawInput ) : Doc

    def accepts( contentType : String, content : Array[ Byte ] ) : Boolean

    protected def ignoreAdditionalTypes( contentType : String ) : String = {
        contentType.split( "\\+" )( 0 )
    }
}
