package app.iria.extractors.processors

import app.iria.extractors.ExtractorApiModuleInfo
import app.iria.test.ScalatestBase
import org.scalatest.DoNotDiscover

@DoNotDiscover
class LinkRemoverSpec extends ScalatestBase with ExtractorApiModuleInfo {

    private val remover : LinkRemover = new LinkRemover

    "Link Remover" should "remove just the links from a block of text" in {
        val text =
            """1/19/22, 10:53 AM Lorum Ipsum | by Michael Reynolds | Lorum Ipsum\nhttps://abcd.com/deep-learning-may-not-be-the-silver-bullet-for-all-nlp-tasks-just-yet-7e83405b8359\nhttps://abcd.com/search\nhttps://abcd.com/m/signin?operation=register&redirect=https%3A%2F%2Fabcd.com%2Fnew-story&source=--------------------------new_post_sidenav-------------- lorum ipsum""".stripMargin

        val result = remover.execute( DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = text ) ) )

        result.normalized.body shouldBe """1/19/22, 10:53 AM Lorum Ipsum | by Michael Reynolds | Lorum Ipsum\n\n\n lorum ipsum"""
    }

}
