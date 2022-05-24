package app.iria.extractors.processors

import app.iria.model.Doc

trait PostProcessor {

    def execute( doc : Doc ) : Doc

}
