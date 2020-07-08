import com.intellij.testFramework.ParsingTestCase
import com.zephir.lang.core.parser.ZephirParserDefinition

class ZephirParserTest : ParsingTestCase("parser", "zep", ZephirParserDefinition()) {
    fun testHelloWorld() = doTest(true)

    override fun getTestDataPath() = "src/test/testData"
}
