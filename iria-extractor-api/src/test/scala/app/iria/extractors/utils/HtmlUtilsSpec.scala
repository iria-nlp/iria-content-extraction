package app.iria.extractors.utils

import app.iria.extractors.ExtractorApiModuleInfo
import app.iria.test.ScalatestBase
import org.scalatest.DoNotDiscover

@DoNotDiscover
class HtmlUtilsSpec extends ScalatestBase with ExtractorApiModuleInfo {

    "HTML Utils" should "detect an empty HTML document" in {
        val content = """<html></html>"""
        HtmlUtils.isHtml( content ) shouldBe true
    }

    "HTML Utils" should "detect HTML content from a real file" in {
        val content = ( TEST_RESOURCES / "files" / "html" / "Algospeak is changing our language in real time - The Washington Post.html" ).contentAsString
        HtmlUtils.isHtml( content ) shouldBe true
    }

    "HTML Utils" should "ignore content that contains HTML tag names, but is not an HTML document" in {
        val content = "This text contains an <html> element tag, but is not HTML"
        HtmlUtils.isHtml( content ) shouldBe false
    }

}
