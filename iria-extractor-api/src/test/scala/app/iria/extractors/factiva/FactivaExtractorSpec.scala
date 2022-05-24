package app.iria.extractors.factiva

import app.iria.extractors.{Extractor, ExtractorApiModuleInfo, RawInput}
import app.iria.test.ScalatestBase
import org.scalatest.DoNotDiscover

@DoNotDiscover
class FactivaExtractorSpec extends ScalatestBase with ExtractorApiModuleInfo {

    private val extractor : Extractor = new FactivaExtractor()

    "Factiva Extractor" should "accept a `text/plain` document with a valid Factiva JSON" in {
        val contentType = "text/plain"
        val factiva = ( FACTIVAS / "factiva-01.json" ).loadBytes
        extractor.accepts( contentType, factiva ) shouldBe true
    }

    "Factiva Extractor" should "accept a `text/json` document with a valid Factiva JSON" in {
        val contentType = "text/json"
        val factiva = ( FACTIVAS / "factiva-01.json" ).loadBytes
        extractor.accepts( contentType, factiva ) shouldBe true
    }

    "Factiva Extractor" should "accept a `application/json` document with a valid Factiva JSON" in {
        val contentType = "application/json"
        val factiva = ( FACTIVAS / "factiva-01.json" ).loadBytes
        extractor.accepts( contentType, factiva ) shouldBe true
    }

    "Factiva Extractor" should "reject input if it does not validate against the Factiva schema" in {
        val contentType = "application/json"
        val factiva = """{"name":"michael"}""".getBytes
        extractor.accepts( contentType, factiva ) shouldBe false
    }

    "Factiva Extractor" should "reject any unaccepted formats" in {
        val contentType = "application/pdf"
        val factiva = null
        extractor.accepts( contentType, factiva ) shouldBe false
    }

    "Factiva Extractor" should "extract a Factiva document" in {
        val raw = RawInput( name = "", ( FACTIVAS / "factiva-01.json" ).loadBytes )
        val result = extractor.extract( raw )

        result.id shouldBe "11462bbdd2ed1e179f5c0a364131a55c"
        result.normalized.body shouldBe "body"
        result.normalized.author shouldBe "Michael Reynolds"
        result.metadata.publisher shouldBe "publisher"
    }

}
