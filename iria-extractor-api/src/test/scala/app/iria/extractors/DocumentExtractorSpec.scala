package app.iria.extractors

import app.iria.extractors.factiva.FactivaExtractor
import app.iria.extractors.tika.TikaExtractor
import app.iria.test.ScalatestBase
import app.iria.utils.temporal.OffsetDateTimes
import better.files.File
import org.scalatest.DoNotDiscover

@DoNotDiscover
class DocumentExtractorSpec extends ScalatestBase with ExtractorApiModuleInfo {

    private val extractor : DocumentExtractor = new DocumentExtractor( Set( new TikaExtractor, new FactivaExtractor ) )

    //    "Document Extractor" should "extract all documents" in {
    //        val files = {
    //            PDFS.list ++ ( HTMLS ).list
    //        }
    //
    //        files.foreach( f => {
    //            val rawInput = RawInput( "asdf", f.loadBytes )
    //            val response = extractor.extractDoc( rawInput )
    //            ( TARGET / s"${response.get.id}.json" ).writeText( DocumentJsonFormat.toDocJson( response.get ) )
    //        } )
    //    }

    "Document Extractor" should "extract a scientific PDF document" in {
        val pdf : File = ( PDFS / "Rule Based Information Extraction.pdf" )

        val input = RawInput( "asdf", pdf.loadBytes )
        val extracted = extractor.extractDoc( input )

        Thread.sleep( 500 )
        extracted.isSuccess shouldBe true
        extracted.get.normalized.title shouldBe "Rule-Based Information Extraction is Dead! Long Live Rule-Based Information Extraction Systems!"
        extracted.get.metadata.createdDate shouldBe OffsetDateTimes.fromIsoString( "2013-10-09T16:58:39.000Z" )
        extracted.get.source.properties.nonEmpty shouldBe true
    }

    "Document Extractor" should "extract an HTML news article" in {
        val html : File = HTMLS / "Researchers Find Another Clue in the Dyatlov Pass Mystery - The New York Times.html"

        val input = RawInput( "asdf", html.loadBytes )
        val extracted = extractor.extractDoc( input )

        Thread.sleep( 100 )
        extracted.isSuccess shouldBe true
        extracted.get.normalized.title shouldBe "Researchers Find Another Clue in the Dyatlov Pass Mystery - The New York Times"
        extracted.get.metadata.modifiedDate shouldBe OffsetDateTimes.fromIsoString( "2022-04-01T16:01:43.000Z" )
        extracted.get.source.properties.nonEmpty shouldBe true
    }

    "Document Extractor" should "extract a Factiva document" in {
        val html : File = FACTIVAS / "factiva-01.json"

        val input = RawInput( "asdf", html.loadBytes )
        val extracted = extractor.extractDoc( input )

        extracted.isSuccess shouldBe true
        extracted.get.normalized.title shouldBe "title"
    }

}
