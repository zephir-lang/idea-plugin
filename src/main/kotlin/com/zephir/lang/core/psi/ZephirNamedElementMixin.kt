// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

abstract class ZephirNamedElementMixin(node: ASTNode) : ASTWrapperPsiElement(node), ZephirNamedElement {
    override fun getName(): String? = nameIdentifier?.text

    override fun getNameIdentifier(): PsiElement? = PsiTreeUtil.getChildOfType(this, ZephirId::class.java)

    override fun setName(name: String): PsiElement {
        val id = nameIdentifier ?: return this
        val newId = ZephirElementFactory.createId(project, name)
        id.replace(newId)
        return this
    }
}
