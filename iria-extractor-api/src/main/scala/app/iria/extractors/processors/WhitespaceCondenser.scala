package app.iria.extractors.processors

import app.iria.model.Doc

class WhitespaceCondenser extends PostProcessor {
    // TODO - make configurable
    private val whitespaceReplacementPatterns : Seq[ (String, String) ] = {
        Seq( "\t" -> "",
             " {2,}" -> " ",
             " +\n +" -> "",
             "\n{2,}" -> "\n",
             " +\n[a-zA-Z0-9]+" -> " " )
    }

    override def execute( doc : Doc ) : Doc = {
        val condensedBody = cleanWhitespace( doc.normalized.body )
        val condensedTitle = cleanWhitespace( doc.normalized.title )
        val condensedSubject = cleanWhitespace( doc.normalized.subject )
        doc.copy( normalized = doc.normalized.copy( body = condensedBody, title = condensedTitle, subject = condensedSubject ) )
    }

    private def cleanWhitespace( content : String ) : String = {
        if ( content != null && content.nonEmpty ) {
            whitespaceReplacementPatterns.foldLeft( content )( ( content, rule ) => content.replaceAll( rule._1, rule._2 ) ).trim()
        } else content
    }
}
