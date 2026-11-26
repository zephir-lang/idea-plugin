// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.io.File

class ZephirCompletionTest : BasePlatformTestCase() {
    private val written = mutableListOf<File>()

    override fun tearDown() {
        try {
            written.forEach { it.delete() }
            LocalFileSystem.getInstance().refreshAndFindFileByIoFile(File(project.basePath!!))?.refresh(false, true)
        } finally {
            super.tearDown()
        }
    }

    private fun complete(text: String): List<String> {
        myFixture.configureByText("Completion.zep", text)
        myFixture.completeBasic()
        return myFixture.lookupElementStrings.orEmpty()
    }

    /** The lookup items, or the words that completion inserted itself when only one item matched. */
    private fun offered(text: String, vararg words: String): List<String> {
        val items = complete(text)
        if (items.isNotEmpty()) return items
        val document = myFixture.editor.document.text
        return words.filter { document.contains(it) && !text.contains(it) }
    }

    fun testClassKeywordsInClassBody() {
        val items = complete("namespace T;\n\nclass A\n{\n    <caret>\n}\n")

        for (keyword in listOf("readonly", "internal", "deprecated", "const", "use", "public", "function")) {
            assertContainsElements(items, keyword)
        }
    }

    fun testClassKeywordsInTraitBody() {
        val items = complete("namespace T;\n\ntrait A\n{\n    <caret>\n}\n")

        assertContainsElements(items, "readonly", "internal", "use", "const")
    }

    fun testClassKeywordsAreNotOfferedAtFileLevel() {
        val items = complete("namespace T;\n\n<caret>\n")

        assertDoesntContain(items, "readonly", "public", "protected", "private", "const", "extends", "implements")
        assertContainsElements(items, "trait", "function", "class", "interface")
    }

    fun testFileKeywordsAreNotOfferedInAClassBody() {
        val items = complete("namespace T;\n\nclass A\n{\n    <caret>\n}\n")

        assertDoesntContain(items, "trait", "namespace", "interface")
    }

    fun testExtendsAndImplementsAfterAClassName() {
        assertContainsElements(complete("namespace T;\n\nclass Foo e<caret> {\n}\n"), "extends", "implements")
    }

    fun testImplementsAfterASuperClass() {
        assertContainsElements(offered("namespace T;\n\nclass Foo extends Bar i<caret> {\n}\n", "implements"), "implements")
    }

    fun testExtendsAfterAnInterfaceName() {
        val items = offered("namespace T;\n\ninterface Foo e<caret> {\n}\n", "extends", "implements")

        assertContainsElements(items, "extends")
        assertDoesntContain(items, "implements")
    }

    fun testATraitHeaderTakesNeitherKeyword() {
        assertDoesntContain(complete("namespace T;\n\ntrait Foo e<caret> {\n}\n"), "extends", "implements")
    }

    fun testMethodKeywords() {
        val items = complete("namespace T;\n\nclass A\n{\n    public function run()\n    {\n        <caret>\n    }\n}\n")

        assertContainsElements(items, "yield", "require_once", "let", "return")
    }

    fun testTraitMembersInATraitMethod() {
        val items = complete(
            """
            namespace T;

            trait Greets
            {
                protected name = "world";

                public function label() { return 1; }

                public function greet()
                {
                    <caret>
                }
            }
            """.trimIndent()
        )

        assertContainsElements(items, "this->name", "this->label()")
    }

    fun testDestructuredVariables() {
        val items = complete(
            """
            namespace T;

            class A
            {
                public function run(list)
                {
                    let [first, , third] = list;
                    <caret>
                }
            }
            """.trimIndent()
        )

        assertContainsElements(items, "first", "third")
    }

    fun testTraitNamesAreOfferedFromTheProjectScan() {
        val dir = File(project.basePath!!).apply { mkdirs() }
        written += File(dir, "Greets.zep").apply { writeText("namespace T;\n\ntrait Greets\n{\n}\n") }
        written += File(dir, "Plain.zep").apply { writeText("namespace T;\n\nclass Plain\n{\n}\n") }
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(dir)!!.refresh(false, true)

        myFixture.configureByText("User.zep", "namespace T;\n\nclass User\n{\n    use <caret>\n}\n")
        myFixture.completeBasic()
        val items = myFixture.lookupElementStrings.orEmpty()

        assertContainsElements(items, "Greets", "Plain")
    }

    fun testMemberAccessForAParameterWithASingleClassType() {
        val dir = File(project.basePath!!).apply { mkdirs() }
        written += File(dir, "Bee.zep").apply {
            writeText("namespace T;\n\nclass Bee\n{\n    public function buzz() { return 1; }\n\n    public hive;\n}\n")
        }
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(dir)!!.refresh(false, true)

        val items = offered(
            "namespace T;\n\nclass A\n{\n    public function run(<Bee> b)\n    {\n        b->bu<caret>;\n    }\n}\n",
            "buzz"
        )

        assertContainsElements(items, "buzz")
    }

    fun testNoMemberAccessForAUnionTypedParameter() {
        val dir = File(project.basePath!!).apply { mkdirs() }
        written += File(dir, "Bee.zep").apply {
            writeText("namespace T;\n\nclass Bee\n{\n    public function buzz() { return 1; }\n}\n")
        }
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(dir)!!.refresh(false, true)

        val items = offered(
            "namespace T;\n\nclass A\n{\n    public function run(<Bee>|null b)\n    {\n        b->bu<caret>;\n    }\n}\n",
            "buzz"
        )

        assertDoesntContain(items, "buzz")
    }
}
