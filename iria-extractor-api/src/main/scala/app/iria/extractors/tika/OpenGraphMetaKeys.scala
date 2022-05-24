package app.iria.extractors.tika

import org.apache.tika.metadata.Property

object OpenGraphMetaKeys {

    private val NAMESPACE : String = "og"
    private val DELIMITER : String = ":"
    private val OPEN_GRAPH_PREFIX : String = s"${NAMESPACE}${DELIMITER}"


    val SITE_NAME : Property = Property.internalText( OPEN_GRAPH_PREFIX + "site_name" )
    val URL : Property = Property.internalText( OPEN_GRAPH_PREFIX + "url" )

}
