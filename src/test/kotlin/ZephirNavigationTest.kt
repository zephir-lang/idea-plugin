// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zephir.ide.navigation.ZephirGotoDeclarationHandler
import com.zephir.lang.core.psi.ZephirClassReference
import com.zephir.lang.core.psi.ZephirComplexId
import com.zephir.lang.core.psi.ZephirTraitDefinition
import java.io.File

class ZephirNavigationTest : BasePlatformTestCase() {
    private val written = mutableListOf<File>()

    override fun tearDown() {
        try {
            written.forEach { it.delete() }
            LocalFileSystem.getInstance().refreshAndFindFileByIoFile(File(project.basePath!!))?.refresh(false, true)
        } finally {
            super.tearDown()
        }
    }

    /** Writes a file under the project base directory, where the navigation scans look for declarations. */
    private fun onDisk(name: String, text: String): VirtualFile {
        val dir = File(project.basePath!!).apply { mkdirs() }
        written += File(dir, name).apply { writeText(text) }
        return LocalFileSystem.getInstance().refreshAndFindFileByIoFile(File(dir, name))!!
    }

    private fun targetsFromUse(file: VirtualFile): Array<PsiElement>? {
        myFixture.configureFromExistingVirtualFile(file)
        val offset = myFixture.file.text.indexOf("use Greets") + "use ".length + 1
        myFixture.editor.caretModel.moveToOffset(offset)
        val element = myFixture.file.findElementAt(offset)
        return ZephirGotoDeclarationHandler().getGotoDeclarationTargets(element, offset, myFixture.editor)
    }

    /** Resolves the class reference of the qualified name at [offset]. */
    private fun resolve(offset: Int): PsiElement? {
        val complexId = PsiTreeUtil.getParentOfType(myFixture.file.findElementAt(offset), ZephirComplexId::class.java)
        return ZephirClassReference(complexId!!).resolve()
    }

    private fun assertIsTheTrait(target: PsiElement?, fileName: String? = null) {
        assertNotNull(target)
        if (fileName != null) assertEquals(fileName, target!!.containingFile.name)
        assertTrue(target!!.parent is ZephirTraitDefinition)
        assertEquals("Greets", (target.parent as ZephirTraitDefinition).name)
    }

    fun testUseGoesToATraitInTheSameFile() {
        val file = onDisk(
            "Same.zep",
            "namespace T;\n\ntrait Greets\n{\n}\n\nclass User\n{\n    use Greets;\n}\n"
        )

        assertIsTheTrait(targetsFromUse(file)!!.single(), "Same.zep")
    }

    fun testUseGoesToATraitInAnotherFile() {
        onDisk("Greets.zep", "namespace T;\n\ntrait Greets\n{\n}\n")
        val user = onDisk("User.zep", "namespace T;\n\nclass User\n{\n    use Greets;\n}\n")

        assertIsTheTrait(targetsFromUse(user)!!.single(), "Greets.zep")
    }

    fun testSecondNameOfAMultiTraitUseResolves() {
        onDisk("Greets.zep", "namespace T;\n\ntrait Greets\n{\n}\n")
        val user = onDisk("User.zep", "namespace T;\n\nclass User\n{\n    use Other, Greets;\n}\n")
        myFixture.configureFromExistingVirtualFile(user)
        val offset = myFixture.file.text.indexOf("Greets") + 1

        assertIsTheTrait(resolve(offset), "Greets.zep")
    }

    fun testReferenceResolvesToATrait() {
        onDisk("Greets.zep", "namespace T;\n\ntrait Greets\n{\n}\n")
        val user = onDisk("User.zep", "namespace T;\n\nclass User\n{\n    use Greets;\n}\n")
        myFixture.configureFromExistingVirtualFile(user)
        val offset = myFixture.file.text.indexOf("Greets") + 1

        assertIsTheTrait(resolve(offset), "Greets.zep")
    }
}
