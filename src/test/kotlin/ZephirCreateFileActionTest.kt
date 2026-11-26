// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.FileTemplateUtil
import com.intellij.psi.PsiManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zephir.lang.core.psi.ZephirFile
import com.zephir.lang.core.psi.ZephirTraitDefinition
import com.intellij.psi.util.PsiTreeUtil
import java.util.Properties

class ZephirCreateFileActionTest : BasePlatformTestCase() {
    fun testTraitTemplateExists() {
        val template = FileTemplateManager.getInstance(project).getInternalTemplate("Zephir Trait")
        val text = template.getText(Properties().apply { setProperty("NAMESPACE", "Acme"); setProperty("NAME", "Greets") })

        assertTrue(text, text.contains("namespace Acme;"))
        assertTrue(text, text.contains("trait Greets"))
    }

    fun testTraitKindCreatesATraitFile() {
        val dir = myFixture.tempDirFixture.findOrCreateDir("created")
        val psiDir = PsiManager.getInstance(project).findDirectory(dir)!!
        val template = FileTemplateManager.getInstance(project).getInternalTemplate("Zephir Trait")

        val properties = FileTemplateManager.getInstance(project).defaultProperties.apply {
            setProperty("NAMESPACE", "Acme")
            setProperty("NAME", "Greets")
        }

        val file = FileTemplateUtil.createFromTemplate(template, "Greets", properties, psiDir).containingFile

        assertTrue(file is ZephirFile)
        assertEquals("Greets.zep", file.name)
        assertNotNull(PsiTreeUtil.findChildOfType(file, ZephirTraitDefinition::class.java))
        assertEquals("Greets", PsiTreeUtil.findChildOfType(file, ZephirTraitDefinition::class.java)!!.name)
    }
}
