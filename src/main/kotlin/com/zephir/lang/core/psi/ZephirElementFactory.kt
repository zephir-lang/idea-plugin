// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.ZephirFileType

/** Factory for creating Zephir PSI elements programmatically, used by rename refactoring. */
object ZephirElementFactory {
    /** Creates a [ZephirId] node with the given [name] by parsing a minimal dummy Zephir file. */
    fun createId(project: Project, name: String): ZephirId {
        val file = PsiFileFactory.getInstance(project)
            .createFileFromText("dummy.zep", ZephirFileType, "namespace Dummy;\nclass $name {}") as ZephirFile
        return PsiTreeUtil.findChildOfType(file, ZephirId::class.java)!!
    }

    /** Creates a bare `IDENTIFIER` leaf element with the given [name], used by [ZephirIdMixin.setName]. */
    fun createIdentifier(project: Project, name: String): PsiElement {
        return createId(project, name).identifier
    }
}
