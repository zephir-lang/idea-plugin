// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import com.intellij.testFramework.ParsingTestCase
import com.zephir.lang.core.parser.ZephirParserDefinition

class ZephirParserTest : ParsingTestCase("parser", "zep", ZephirParserDefinition()) {
    fun testHelloWorld() = doTest(true)

    override fun getTestDataPath() = "src/test/testData"
}
