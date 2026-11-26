// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zephir.ide.documentation.ZephirDocumentationProvider
import com.zephir.lang.core.psi.ZephirClassDefinition
import com.zephir.lang.core.psi.ZephirFunctionDefinition
import com.zephir.lang.core.psi.ZephirMethodDefinition
import com.zephir.lang.core.psi.ZephirTraitDefinition

class ZephirDocumentationTest : BasePlatformTestCase() {
    private fun doc(text: String, type: Class<out PsiElement>): String? {
        val file = myFixture.configureByText("Doc.zep", text)
        val target = PsiTreeUtil.findChildOfType(file, type)!!
        return ZephirDocumentationProvider().generateDoc(target, null)
    }

    fun testTraitSignature() {
        val html = doc(
            """
            namespace Test;

            /** Greets people. */
            trait Greets
            {
            }
            """.trimIndent(),
            ZephirTraitDefinition::class.java
        )!!

        assertTrue(html, html.contains("<b>trait</b> Greets"))
        assertTrue(html, html.contains("Greets people."))
    }

    fun testFunctionSignature() {
        val html = doc(
            """
            /** Doubles a value. */
            function twice(int value) -> int
            {
                return value;
            }
            """.trimIndent(),
            ZephirFunctionDefinition::class.java
        )!!

        assertTrue(html, html.contains("<b>fun</b> twice(int value) -> int"))
        assertTrue(html, html.contains("Doubles a value."))
    }

    fun testDocblockBeforeAttributes() {
        val html = doc(
            """
            namespace Test;

            /** Documented first. */
            #[Marker]
            #[Other(1)]
            class Tagged
            {
            }
            """.trimIndent(),
            ZephirClassDefinition::class.java
        )!!

        assertTrue(html, html.contains("Documented first."))
    }

    fun testDocblockAfterAttributes() {
        val html = doc(
            """
            namespace Test;

            class Tagged
            {
                #[Marker]
                /** Documented last. */
                public function run() { return 1; }
            }
            """.trimIndent(),
            ZephirMethodDefinition::class.java
        )!!

        assertTrue(html, html.contains("Documented last."))
    }

    fun testAbstractMethodIsMarkedAbstract() {
        val html = doc(
            """
            class Shape
            {
                /** Area. */
                abstract public function area();
            }
            """.trimIndent(),
            ZephirMethodDefinition::class.java
        )!!

        assertTrue(html, html.contains("<b>abstract fun</b> area()"))
    }

    fun testDocblockOfAnEarlierMemberIsNotBorrowed() {
        val file = myFixture.configureByText(
            "Doc.zep",
            """
            class Tagged
            {
                /** A */
                public a;

                #[X]
                public function b() { return 1; }
            }
            """.trimIndent()
        )
        val method = PsiTreeUtil.findChildOfType(file, ZephirMethodDefinition::class.java)!!

        assertNull(ZephirDocumentationProvider().generateDoc(method, null))
    }

    fun testNoDocblockMeansNoDocumentation() {
        assertNull(doc("trait Bare\n{\n}\n", ZephirTraitDefinition::class.java))
    }
}
