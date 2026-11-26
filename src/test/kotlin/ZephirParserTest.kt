// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import com.intellij.lang.LanguageBraceMatching
import com.intellij.testFramework.ParsingTestCase
import com.zephir.ide.typing.ZephirBraceMatcher
import com.zephir.lang.core.ZephirLanguage
import com.zephir.lang.core.parser.ZephirParserDefinition

class ZephirParserTest : ParsingTestCase("parser", "zep", ZephirParserDefinition()) {
    /** The parser's error recovery balances the brace pairs the editor registers, so the tests register them too. */
    override fun setUp() {
        super.setUp()
        addExplicitExtension(LanguageBraceMatching.INSTANCE, ZephirLanguage, ZephirBraceMatcher())
    }

    fun testHelloWorld() = doTest(true)

    fun testIssue12() = doTest(true)

    fun testDoWhileLoop() = doTest(true)

    fun testMixedType() = doTest(true)

    fun testStaticSelfReturn() = doTest(true)

    fun testVariadic() = doTest(true)

    fun testTraits() = doTest(true)

    fun testTopLevel() = doTest(true)

    fun testCaseInsensitiveKeywords() = doTest(true)

    fun testMemberModifiers() = doTest(true)

    fun testTypedProperties() = doTest(true)

    fun testPropertyShortcuts() = doTest(true)

    fun testUnionTypes() = doTest(true)

    fun testParameterForms() = doTest(true)

    fun testAttributes() = doTest(true)

    fun testConstExpressions() = doTest(true)

    fun testAttributeErrors() = doTest(true)

    fun testAttributeErrorsComma() = doTest(true)

    fun testParameterNullableError() = doTest(true)

    fun testClosureAttributeError() = doTest(true)

    fun testVoidPropertyError() = doTest(true)

    fun testVoidParameterError() = doTest(true)

    fun testVoidUnionError() = doTest(true)

    fun testResourcePropertyError() = doTest(true)

    fun testResourceParameterError() = doTest(true)

    fun testVoidReturnUnionError() = doTest(true)

    fun testResourceReturnError() = doTest(true)

    fun testResourceUnionPropertyError() = doTest(true)

    fun testResourceUnionParameterError() = doTest(true)

    fun testResourceUnionReturnError() = doTest(true)

    fun testYield() = doTest(true)

    fun testLetForms() = doTest(true)

    fun testClosures() = doTest(true)

    fun testNamedArguments() = doTest(true)

    fun testClassConstantAccess() = doTest(true)

    fun testNewAndInstanceof() = doTest(true)

    fun testOperators() = doTest(true)

    fun testArrowFnError() = doTest(true)

    fun testArrowParenError() = doTest(true)

    fun testYieldExpressionError() = doTest(true)

    fun testNestedDestructureError() = doTest(true)

    fun testNamedArgumentFunctionError() = doTest(true)

    fun testRecoveryTopLevel() = doTest(true)

    fun testRecoveryMembers() = doTest(true)

    fun testRecoveryStatements() = doTest(true)

    fun testEmptyStatements() = doTest(true)

    fun testChainedCalls() = doTest(true)

    fun testDanglingMemberModifierError() = doTest(true)

    fun testDanglingMemberAttributeError() = doTest(true)

    fun testDanglingClassModifierError() = doTest(true)

    fun testDanglingAttributeError() = doTest(true)

    fun testResourceCollectionReturnError() = doTest(true)

    override fun getTestDataPath() = "src/test/testData"
}
