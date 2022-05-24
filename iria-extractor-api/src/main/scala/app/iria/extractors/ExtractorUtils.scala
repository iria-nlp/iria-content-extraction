package app.iria.extractors

object ExtractorUtils {

    def formatByline( byline : String ) : String = {
        if ( byline != null && byline.nonEmpty ) byline.split( "(?i)by" ).last.trim()
        else null
    }

}
