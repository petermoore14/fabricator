package fabricator

import org.testng.annotations.{DataProvider, Test}

import scala.collection.mutable

class WordsTestSuite extends BaseTestSuite {

  @Test
  def testCustomConstructor()  {
    val customWords = fabricator.Words("us")
    assert(customWords != null)
  }
  
  @DataProvider(name = "wordsCountDP")
  def wordsCountDP():Array[Array[Any]]= {
    Array(Array("10"),
      Array("100"),
      Array("1000"),
      Array("4000"),
      Array("9500")
    )
  }

  @Test
  def testDefaultWords() {
    val wordsDefaultArray: Array[String] = words.words()
    if (debugEnabled) logger.debug("Getting words array generated with default length ")
    assert(wordsDefaultArray.length == 10)
    val inputSet: mutable.Set[String] = scala.collection.mutable.Set()
    for( i <- wordsDefaultArray.indices by 1) {
      inputSet.add(wordsDefaultArray(i))
    }
    assertResult(10)(inputSet.size)
  }

  @Test(expectedExceptions = Array(classOf[Exception]))
  def testWordsMaximumAmountException(): Unit = {
    words.words(100001)
  }
  
  @Test(dataProvider = "wordsCountDP")
  def testWords(count: String) = {
    if (debugEnabled) logger.debug("Getting words array generated with length = " + count)
    assertResult(words.words(count.toInt).length)(count.toInt)
  }

  @Test
  def testSentenceDefault() = {
    val sentence = words.sentence
    if (debugEnabled) logger.debug("Testing sentence generation. Creating sentence with 10 words length \n" + sentence)
    assertResult(sentence.split(" ").length)(10)
  }

  @Test
  def testSentenceCustomLength() = {
    val sentence = words.sentence(20)
    if (debugEnabled) logger.debug("Testing sentence generation. Creating sentence with 10 words length: \n" + sentence)
    assertResult(sentence.split(" ").length)(20)
  }

  @Test
  def testTextDefaultValue() = {
    val paragraph = words.paragraph
    if (debugEnabled) logger.debug("Testing sentence generation. Creating text with 10 words length: \n" + paragraph)
    assertResult(paragraph.length)(100)
  }

  @Test(dataProvider = "wordsCountDP")
  def testTextCustomValue(length: String) = {
    val paragraph = words.paragraph(length.toInt)
    if (debugEnabled) logger.debug("Testing sentence generation. Creating paragraph with chars length: " + length.toInt + "\n" + paragraph)
    assertResult(paragraph.length())(length.toInt)
  }


}
