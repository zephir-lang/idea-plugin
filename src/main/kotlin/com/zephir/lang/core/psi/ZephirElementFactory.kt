// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.ZephirFileType

object ZephirElementFactory {
    fun createId(project: Project, name: String): ZephirId {
        val file = PsiFileFactory.getInstance(project)
            .createFileFromText("dummy.zep", ZephirFileType, "namespace Dummy;\nclass $name {}") as ZephirFile
        return PsiTreeUtil.findChildOfType(file, ZephirId::class.java)!!
    }
}
