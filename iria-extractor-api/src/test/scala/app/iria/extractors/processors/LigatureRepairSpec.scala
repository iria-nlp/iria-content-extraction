package app.iria.extractors.processors

import app.iria.extractors.ExtractorApiModuleInfo
import app.iria.extractors.utils.Vocabulary
import app.iria.test.ScalatestBase

class LigatureRepairSpec extends ScalatestBase with ExtractorApiModuleInfo {

    val ligatureRepair : LigatureRepair = new LigatureRepair( Vocabulary.default() )

    // fi ligature tests and checking for combinations of the first and second word being not actual words

    "Regex Ligature Text Cleaner" should "fix basic fi ligature with first group not a word" in {
        val original = "the fi lament was too thin"
        val expected = "the filament was too thin"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    "Regex Ligature Text Cleaner" should "fix basic fi ligature with second group not a word" in {
        val original = "Qaddafi 's civil war was brutal"
        val expected = "Qaddafi's civil war was brutal"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    "Regex Ligature Text Cleaner" should "fix basic fi ligature with neither group a word" in {
        val original = "the fi lling was tasty"
        val expected = "the filling was tasty"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    "Regex Ligature Text Cleaner" should "not fix fi ligature with both groups words" in {
        val original = "A Sufi is a Muslim ascetic and mystic"
        val expected = "A Sufi is a Muslim ascetic and mystic"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    // fl ligature test

    "Regex Ligature Text Cleaner" should "fix basic fl ligature" in {
        val original = "the confl ict was bad"
        val expected = "the conflict was bad"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    // ff ligature test

    "Regex Ligature Text Cleaner" should "fix basic ff ligature" in {
        val original = "the target was unaff ected"
        val expected = "the target was unaffected"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    // ffi ligature test

    "Regex Ligature Text Cleaner" should "fix basic ffi ligature" in {
        val original = "the stuff ing was bad"
        val expected = "the stuffing was bad"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    // ffl ligature test
    "Regex Ligature Text Cleaner" should "fix basic ffl ligature" in {
        val original = "every day im shuffl ing"
        val expected = "every day im shuffling"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

    // many ligatures test

    "Regex Ligature Text Cleaner" should "fix all ligatures present" in {
        val original = "If the fi lament was too thin and if Qaddafi 's civil war was brutal, then not only would the fi lling be tasty, but every day im shuffl ing"
        val expected = "If the filament was too thin and if Qaddafi's civil war was brutal, then not only would the filling be tasty, but every day im shuffling"

        val doc = DOC_TEMPLATE.copy( normalized = DOC_TEMPLATE.normalized.copy( body = original ) )

        val actual = ligatureRepair.execute( doc )
        actual.normalized.body shouldBe expected
    }

}
