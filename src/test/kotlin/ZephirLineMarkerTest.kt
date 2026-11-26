// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ZephirLineMarkerTest : BasePlatformTestCase() {
    fun testTraitUsedByTwoClassesShowsAMarker() {
        myFixture.addFileToProject("First.zep", "namespace T;\n\nclass First\n{\n    use Greets;\n}\n")
        myFixture.addFileToProject("Trait.zep", "namespace T;\n\ntrait Other\n{\n    use Greets, Third;\n}\n")
        myFixture.configureByText("Greets.zep", "namespace T;\n\ntrait Greets\n{\n}\n")

        val gutters = myFixture.findAllGutters().filter { it.tooltipText?.contains("using this trait") == true }

        assertEquals(1, gutters.size)
        assertEquals("2 class(es) or trait(s) using this trait", gutters.single().tooltipText)
    }

    fun testUnusedTraitHasNoMarker() {
        myFixture.configureByText("Greets.zep", "namespace T;\n\ntrait Greets\n{\n}\n")

        assertTrue(myFixture.findAllGutters().none { it.tooltipText?.contains("using this trait") == true })
    }

    fun testClassWithASubclassStillShowsAMarker() {
        myFixture.addFileToProject("Child.zep", "namespace T;\n\nclass Child extends Base\n{\n}\n")
        myFixture.configureByText("Base.zep", "namespace T;\n\nclass Base\n{\n}\n")

        assertEquals(listOf("1 subclass(es)"), myFixture.findAllGutters().mapNotNull { it.tooltipText })
    }
}
