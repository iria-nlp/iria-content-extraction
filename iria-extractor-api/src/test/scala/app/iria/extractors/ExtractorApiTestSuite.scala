package app.iria.extractors

import app.iria.extractors.factiva.FactivaExtractorSpec
import app.iria.extractors.tika.TikaExtractorSpec
import app.iria.model.{Doc, Metadata, Normalized, Source}
import app.iria.test.ModuleInfo
import app.iria.utils.temporal.OffsetDateTimes
import better.files.File
import org.scalatest.{BeforeAndAfterAll, Suites}


trait ExtractorApiModuleInfo extends ModuleInfo {

    override protected val MODULE_NAME : String = "iria-extractor-api"

    val PDFS : File = ( TEST_RESOURCES / "files" / "pdf" )
    val HTMLS : File = ( TEST_RESOURCES / "files" / "html" )
    val FACTIVAS : File = ( TEST_RESOURCES / "files" / "factiva" )
    val ACCEPTANCE : File = ( TEST_RESOURCES / "files" / "acceptance" )


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

class ExtractorApiTestSuite
  extends Suites( new FactivaExtractorSpec, new DocumentExtractorSpec, new TikaExtractorSpec )
    with ExtractorApiModuleInfo
    with BeforeAndAfterAll {

    override def beforeAll( ) : Unit = {
        if ( TARGET.exists ) TARGET.delete( swallowIOExceptions = true )
        TARGET.createDirectory()
    }

    override def afterAll( ) : Unit = {
        TARGET.delete( swallowIOExceptions = true )
    }
}
