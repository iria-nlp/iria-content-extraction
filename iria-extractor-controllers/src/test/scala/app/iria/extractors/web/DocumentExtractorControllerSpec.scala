package app.iria.extractors.web

import app.iria.extractors.processors.PostProcessor
import app.iria.extractors.web.test.ExtractorControllerModuleInfo
import app.iria.extractors.{DocumentExtractor, Extractor}
import app.iria.test.ScalatestBase
import org.scalatest.{BeforeAndAfterEach, DoNotDiscover}
import org.scalatra.test.scalatest.ScalatraFlatSpec

@DoNotDiscover
class DocumentExtractorControllerSpec extends ScalatestBase with ScalatraFlatSpec with ExtractorControllerModuleInfo with BeforeAndAfterEach {

    private val extractor1 : Extractor = mock[ Extractor ]
    private val extractor2 : Extractor = mock[ Extractor ]

    private val notification1 : Notification = mock[ Notification ]
    private val notification2 : Notification = mock[ Notification ]

    private val processor1 : PostProcessor = mock[ PostProcessor ]
    private val processor2 : PostProcessor = mock[ PostProcessor ]

    private val mocks = Seq( extractor1, extractor2, notification1, notification2, processor1, processor2 )

    private val controller : DocumentUploadController = {
        val extractor = new DocumentExtractor( Set( extractor1, extractor2 ), Seq( processor1, processor2 ) )
        new DocumentUploadController( extractor, null, Set( notification1, notification2 ) )
    }

    addServlet( controller, "/upload" )


    override def beforeEach( ) : Unit = {
        reset( mocks : _* )
        when( extractor1.name ).thenReturn( "extractor-1" )
    }

    "Extractor Controller" should "successfully extract a document and send notifications" in {
        // when the first extractor accepts the input
        when( extractor1.accepts( *, * ) ).thenReturn( true )
        when( extractor2.accepts( *, * ) ).thenReturn( false )
        when( extractor1.extract( * ) ).thenReturn( DOC_TEMPLATE )

        when( processor1.execute( * ) ).thenReturn( DOC_TEMPLATE )
        when( processor2.execute( * ) ).thenReturn( DOC_TEMPLATE )

        submitMultipart( method = "POST", path = "/upload", files = Array( ("file", ( TEST_FILES / "TestDoc.pdf" ).toJava) ) ) {
            verify( processor1, times( 1 ) ).execute( * )
            verify( processor2, times( 1 ) ).execute( * )

            verify( notification1, times( 1 ) ).send( * )
            verify( notification2, times( 1 ) ).send( * )

            response.status shouldBe 200
            response.body shouldBe DOC_TEMPLATE.id
        }
    }

    override def header = null
}
