package app.iria.extractors.processors

import app.iria.extractors.utils.Vocabulary
import app.iria.model.Doc

import scala.util.matching.Regex

class LigatureRepair( vocab : Vocabulary ) extends PostProcessor {

    private val ligatureRegex : Regex = raw"\b(\w*(fi|fl|ff|ffi|ffl))[\s]+([\w']+)".r

    override def execute( doc : Doc ) : Doc = {
        val title = repairLigatureErrors( doc.normalized.title )
        val body = repairLigatureErrors( doc.normalized.body )
        val subject = repairLigatureErrors( doc.normalized.subject )

        doc.copy( normalized = doc.normalized.copy( title = title, body = body, subject = subject ) )
    }

    private def repairLigatureErrors( text : String ) : String = {
        if ( text != null && text.nonEmpty ) {
            ligatureRegex.replaceAllIn( text, m => {
                if ( !vocab.contains( m.group( 1 ) ) || !vocab.contains( m.group( 3 ) ) ) m.group( 1 ) + m.group( 3 ) else m.matched
            } )
        } else null
    }
}
