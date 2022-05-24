package app.iria.extractors.tika

import app.iria.extractors.{Extractor, ExtractorApiModuleInfo, RawInput}
import app.iria.test.ScalatestBase
import app.iria.utils.temporal.OffsetDateTimes
import org.scalatest.DoNotDiscover

@DoNotDiscover
class TikaExtractorSpec extends ScalatestBase with ExtractorApiModuleInfo {

    private val extractor : Extractor = new TikaExtractor()

    "Tika Extractor" should "extract a PDF with Tika preferred metadata property names" in {
        val file = ( PDFS / "all-tika-preferred-props.pdf" ).loadBytes
        val input = RawInput( "new-file.pdf", file, None )

        val document = extractor.extract( input )

        document.source.contentType shouldBe "application/pdf"
        document.normalized.body shouldBe "This document has all of the metadata properties that Tika prefers to use."
        document.normalized.author shouldBe "Michael Reynolds"
        document.metadata.publisher shouldBe "Michael Reynolds LLC"
        document.source.contentType shouldBe "application/pdf"
        document.metadata.createdDate shouldBe OffsetDateTimes.fromIsoString( "2022-05-23T19:33:00Z" )
    }

    "Tika Extractor" should "extract a PDF with non-standard metadata property names" in {
        val file = ( PDFS / "non-tika-custom-props.pdf" ).loadBytes
        val input = RawInput( "new-file.pdf", file, None )

        val document = extractor.extract( input )

        document.normalized.body shouldBe "This document requires the extractor to search the property names for values."
        document.source.contentType shouldBe "application/pdf"
        document.normalized.author shouldBe "Michael Reynolds"
        document.metadata.publisher shouldBe "Michael Reynolds LLC"
        document.source.contentType shouldBe "application/pdf"
    }

    "Tika Extractor" should "extract an HTML file with Tika preferred metadata values" in {
        val file = ( HTMLS / "Algospeak is changing our language in real time - The Washington Post.html" ).loadBytes
        val input = RawInput( "new-file.pdf", file, None )

        val document = extractor.extract( input )

        document.normalized.body.nonEmpty shouldBe true
        document.source.contentType shouldBe "text/html"
        document.normalized.author shouldBe "Taylor Lorenz"
        document.metadata.publisher shouldBe "Washington Post"
    }

    //TODO - more tests, obviously

}
