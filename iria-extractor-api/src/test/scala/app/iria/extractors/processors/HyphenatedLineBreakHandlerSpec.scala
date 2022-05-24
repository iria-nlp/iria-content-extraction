package app.iria.extractors.processors

import app.iria.extractors.ExtractorApiModuleInfo
import app.iria.extractors.utils.Vocabulary
import app.iria.test.ScalatestBase
import org.scalatest.DoNotDiscover

@DoNotDiscover
class HyphenatedLineBreakHandlerSpec extends ScalatestBase with ExtractorApiModuleInfo {

    private val processor : PostProcessor = new HyphenatedLineBreakHandler( Vocabulary.default() )

    "Hyphenated Line Break Handler" should "repair hyphenated words with a line break" in {
        val original =
            """This is a sent-
              |ence with a hyphenated line break""".stripMargin

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val result = processor.execute( doc )
        result.normalized.body shouldBe "This is a sentence with a hyphenated line break"
    }

    "Hyphenated Line Break Handler" should "repair text with multiple hyphenated line breaks" in {
        val original =
            """This is a sent-
              |ence with multiple hyphen-
              |ated line breaks""".stripMargin

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val result = processor.execute( doc )
        result.normalized.body shouldBe "This is a sentence with multiple hyphenated line breaks"
    }

}
