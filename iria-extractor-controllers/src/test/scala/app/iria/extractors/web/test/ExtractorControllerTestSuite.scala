package app.iria.extractors.web.test

import app.iria.extractors.web.DocumentExtractorControllerSpec
import app.iria.model.{Doc, Metadata, Normalized, Source}
import app.iria.test.ModuleInfo
import app.iria.utils.temporal.OffsetDateTimes
import better.files.File
import org.scalatest.Suites

trait ExtractorControllerModuleInfo extends ModuleInfo {

    override protected val MODULE_NAME : String = "iria-extractor-controllers"

    val TEST_FILES : File = TEST_RESOURCES / "files"

    val DOC_TEMPLATE : Doc = {
        val metadata : Metadata = {
            Metadata( creator = "michael",
                      publisher = "Intellij IDEA",
                      OffsetDateTimes.midnightOf( 6, 30, 1988 ),
                      OffsetDateTimes.midnightOf( 6, 30, 1988 ),
                      OffsetDateTimes.midnightOf( 6, 30, 1988 ) )
        }
        val source : Source = Source( "http://iria.app/docs/123.pdf", "application/html", "s3://bucket/1.html", Map( "property" -> "value" ) )
        val norm : Normalized = Normalized( title = "Test Document", author = "Michael", body = "this is a test document", subject = "TEST", tags = Set( "tag1", "tag2" ), genre = "news_article" )

        Doc( id = "1", source, norm, metadata )
    }
}

class ExtractorControllerTestSuite extends Suites( new DocumentExtractorControllerSpec )
