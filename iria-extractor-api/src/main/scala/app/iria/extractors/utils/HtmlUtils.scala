package app.iria.extractors.utils

import java.util.regex.Pattern

// liberally adapted from https://github.com/dbennett455/DetectHtml/blob/master/src/org/github/DetectHtml.java
object HtmlUtils {

    private val tagStart : String = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>"
    private val tagEnd : String = "\\</\\w+\\>"
    private val selfClosedTag : String = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>"
    private val htmlEntity : String = "&[a-zA-Z][a-zA-Z0-9]+;"

    private val pattern : Pattern = Pattern.compile( "(" + tagStart + ".*" + tagEnd + ")|(" + selfClosedTag + ")|(" + htmlEntity + ")", Pattern.DOTALL )

    def isHtml( content : String ) : Boolean = {
        if ( content != null && content.nonEmpty ) pattern.matcher( content ).find()
        else false
    }

}
