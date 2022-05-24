package app.iria.extractors.processors

import app.iria.extractors.utils.Vocabulary
import app.iria.model.Doc

import scala.util.matching.Regex

class HyphenatedLineBreakHandler( vocab : Vocabulary ) extends PostProcessor {

    private val hyphenatedLineBreakPattern : Regex = raw"([a-zA-Z]+)-\n([a-zA-Z]+)".r

    override def execute( doc : Doc ) : Doc = {
        val body = repairHyphenatedWords( doc.normalized.body )
        val title = repairHyphenatedWords( doc.normalized.title )

        doc.copy( normalized = doc.normalized.copy( body = body, title = title ) )
    }

    private def repairHyphenatedWords( text : String ) : String = {
        if ( text != null && text.nonEmpty ) {
            hyphenatedLineBreakPattern.replaceAllIn( text, m => {
                val repaired = m.group( 0 ).replaceAll( "-\n", "" )
                if ( vocab.contains( repaired ) ) repaired
                else text
            } )

        } else null
    }
}
