package edu.holycross.shot.greek
import org.scalatest.FlatSpec


import edu.holycross.shot.mid.orthography._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._

class LGSTokenizationSpec extends FlatSpec {

  val urn = CtsUrn("urn:cts:greekLit:tlg0540.tlg001.omar:1.1")
  val txt = "περὶ πολλοῦ ἂν ποιησαίμην, ὦ ἄνδρες, "
  val cn = CitableNode(urn, txt)

  val numericTick: Character = 'ʹ'

  "The LiteraryGreekString object" should  "implement the MidOrthography trait's tokenCategories function" in {
    val expected = Vector(PunctuationToken, LexicalToken, NumericToken).toSet
    assert (LiteraryGreekString.tokenCategories.toSet == expected)
  }


  it should "implement the MidOrthography trait's tokenizeNode function" in {
    val tokens = LiteraryGreekString.tokenizeNode(cn)
    val expectedTotal = 8
    val expectedLexical = 6
    val expectedPunct = 2
    assert(expectedTotal == tokens.size)

    val punct = tokens.filter(_.tokenCategory.toString == "Some(PunctuationToken)")
    assert(punct.size == expectedPunct)

    val lexical = tokens.filter(_.tokenCategory.toString == "Some(LexicalToken)")
    assert(expectedLexical == lexical.size)
  }

  it should "correctly depunctuate strings using the default LiteraryGreekString definition of punctuation characters" in {
    //val comma = "νῆας τὸν τόπον τῶν νηῶν~  Ἑλλήσποντον δὲ, τὴν μέχρι Σιγείου θάλασσαν⁑"
    val comma = "δὲ,"
    val depunctuatedTokens = LiteraryGreekString.depunctuate(comma)
    assert(depunctuatedTokens.size == 2)
  }

  it should "correctly depunctuate strings using a specified list of punctuation characters" in {
    val tilde = "νηῶν~ "
    val punctList = ".,;~?"
    val depunctuatedTokens = LiteraryGreekString.depunctuate(tilde,punctuationChars = punctList)
    assert(depunctuatedTokens.size == 2)
    assert(depunctuatedTokens(1) == "~")
  }

  it should "correctly identify numeric tokens" in {
    val eleven = s"ia${numericTick}"
    assert(LiteraryGreekString.lexicalCategory(eleven).get == NumericToken)
  }

  it should "correctly identify punctuation tokens using default definition" in {
    assert(LiteraryGreekString.lexicalCategory(",").get == PunctuationToken)
  }

  it should "correctly identify punctuation tokens using specific list of punctuation characters" in {
    assert(LiteraryGreekString.lexicalCategory("~", ".,;?~").get == PunctuationToken)
  }

  it should "correctly identify lexical tokens in ascii form" in {
    val lgs = LiteraryGreekString("ποιησαίμην")
    assert(LiteraryGreekString.lexicalCategory(lgs.ascii).get == LexicalToken)
  }

  it should  "correctly identify lexical tokens in unicode form" in {
    val lgs = LiteraryGreekString("ποιησαίμην")
    assert(LiteraryGreekString.lexicalCategory(lgs.ucode).get == LexicalToken)
  }

  it should "classify invalid strings as lexical categoery None" in {
    val s = "mh=nin2"
    assert(LiteraryGreekString.lexicalCategory(s) == None)
  }


  //it should ""

}
