package app.iria.extractors.processors

import app.iria.model.Doc

class LinkRemover extends PostProcessor {

    // taken from https://urlregex.com/
    private val LINK_REGEX : String = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"

    override def execute( doc : Doc ) : Doc = {
        if ( Option( doc.normalized.body ).nonEmpty ) {
            doc.copy( normalized = doc.normalized.copy( body = doc.normalized.body.replaceAll( LINK_REGEX, "" ) ) )
        } else doc
    }
}
