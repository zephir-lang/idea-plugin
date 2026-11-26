// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import com.intellij.icons.AllIcons
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zephir.ide.structure.ZephirStructureViewElement

class ZephirStructureViewTest : BasePlatformTestCase() {
    /** A method ending in `;` shows the abstract icon, one with a body the method icon. */
    fun testAbstractMethodsKeepTheirPresentation() {
        val file = myFixture.configureByText(
            "Shapes.zep",
            """
            namespace Test;

            abstract class Shape
            {
                abstract public function area() -> int;
                public function declared();
                public function describe()
                {
                    return 1;
                }
            }
            """.trimIndent()
        )

        val classElement = ZephirStructureViewElement(file).children.single()
        val icons = classElement.children
            .map { it.presentation }
            .associate { it.presentableText to it.getIcon(false) }

        assertEquals(AllIcons.Nodes.AbstractMethod, icons["area() -> int"])
        assertEquals(AllIcons.Nodes.AbstractMethod, icons["declared()"])
        assertEquals(AllIcons.Nodes.Method, icons["describe()"])
    }

    /** A file lists its traits and top-level functions with their children, and abstract methods stay in file order. */
    fun testTraitsAndFunctionsAppear() {
        val file = myFixture.configureByText(
            "Mixed.zep",
            """
            namespace Test;

            function helper(value) -> int
            {
                return 1;
            }

            trait Greets
            {
                const GREETING = "hello";

                protected name = "world";

                abstract public function label();

                public function greet()
                {
                    return 1;
                }
            }

            class Plain
            {
            }
            """.trimIndent()
        )

        val children = ZephirStructureViewElement(file).children.map { it as ZephirStructureViewElement }
        val byText = children.associateBy { it.presentation.presentableText }

        assertEquals(setOf("Plain", "Greets", "helper(value) -> int"), byText.keys)
        assertEquals(AllIcons.Nodes.Function, byText.getValue("helper(value) -> int").presentation.getIcon(false))
        assertEquals(0, byText.getValue("helper(value) -> int").children.size)

        val trait = byText.getValue("Greets")
        assertEquals(AllIcons.Nodes.AbstractClass, trait.presentation.getIcon(false))
        val members = trait.children.map { (it as ZephirStructureViewElement).presentation }
        assertEquals(
            listOf("GREETING", "name", "label()", "greet()"),
            members.map { it.presentableText }
        )
        assertEquals(AllIcons.Nodes.AbstractMethod, members[2].getIcon(false))
        assertEquals(AllIcons.Nodes.Method, members[3].getIcon(false))
    }
}
