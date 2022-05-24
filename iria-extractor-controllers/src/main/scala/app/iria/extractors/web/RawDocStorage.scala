package app.iria.extractors.web

import app.iria.extractors.RawInput
import app.iria.model.Doc
import better.files.File

trait RawDocStorage {

    def upload( doc : Doc, raw : RawInput )

}

class LocalFileRawDocStorage( target : File ) extends RawDocStorage {

    override def upload( doc : Doc, raw : RawInput ) : Unit = ???

}
