package app.iria.extractors.processors

import app.iria.extractors.ExtractorApiModuleInfo
import app.iria.test.ScalatestBase
import org.scalatest.DoNotDiscover

@DoNotDiscover
class WhiteSpaceCondenserSpec extends ScalatestBase with ExtractorApiModuleInfo {

    val processor : PostProcessor = new WhitespaceCondenser

    "Whitespace Condenser" should "remove whitespaces from the `norm.body` field" in {
        val body =
            """
              |
              |
              |
              |
              |
              |
              |            Accessibility statementSkip to main content
              |
              |
              |                Search Input
              |
              |""".stripMargin
        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = body ) )

        val result = processor.execute( doc )

        val expected =
            """Accessibility statementSkip to main content
              | Search Input""".stripMargin

        result.normalized.body shouldBe expected
    }

    "Whitespace Condenser" should "properly reconstruct words that are split across lines" in {
        val body =
            """
              | "Here we provide further information about the bat SARS-related coronavi-\nrus"
              |""".stripMargin
        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = body ) )

        val result = processor.execute( doc )

        val expected =
            """Accessibility statementSkip to main content
              | Search Input""".stripMargin

        result.normalized.body shouldBe expected
    }

}
