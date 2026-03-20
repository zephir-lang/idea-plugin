// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.structure

import com.intellij.icons.AllIcons
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.ide.icons.ZephirIcons
import com.zephir.lang.core.psi.*
import javax.swing.Icon

class ZephirStructureViewElement(private val element: PsiElement) : StructureViewTreeElement {

    override fun getValue() = element

    override fun navigate(requestFocus: Boolean) { (element as? NavigatablePsiElement)?.navigate(requestFocus) }
    override fun canNavigate() = (element as? NavigatablePsiElement)?.canNavigate() ?: false
    override fun canNavigateToSource() = (element as? NavigatablePsiElement)?.canNavigateToSource() ?: false

    override fun getPresentation(): ItemPresentation = object : ItemPresentation {
        override fun getPresentableText(): String? = when (element) {
            is ZephirFile -> element.name
            is ZephirClassDefinition -> element.id.text
            is ZephirInterfaceDefinition -> element.id.text
            is ZephirMethodDefinition -> buildMethodText(
                element.id.text,
                element.argumentsList,
                element.returnType
            )
            is ZephirAbstractMethodDefinition -> buildMethodText(
                element.interfaceMethodDefinition.id.text,
                element.interfaceMethodDefinition.argumentsList,
                element.interfaceMethodDefinition.returnType
            )
            is ZephirInterfaceMethodDefinition -> buildMethodText(
                element.id.text,
                element.argumentsList,
                element.returnType
            )
            is ZephirPropertyDefinition -> element.id.text
            is ZephirConstantDefinition -> element.id.text
            else -> element.text
        }

        override fun getIcon(unused: Boolean): Icon? = when (element) {
            is ZephirFile -> ZephirIcons.FILE
            is ZephirClassDefinition -> AllIcons.Nodes.Class
            is ZephirInterfaceDefinition -> AllIcons.Nodes.Interface
            is ZephirMethodDefinition -> if (element.methodModifiersList.any { it.text == "abstract" })
                AllIcons.Nodes.AbstractMethod else AllIcons.Nodes.Method
            is ZephirAbstractMethodDefinition -> AllIcons.Nodes.AbstractMethod
            is ZephirInterfaceMethodDefinition -> AllIcons.Nodes.Method
            is ZephirPropertyDefinition -> AllIcons.Nodes.Field
            is ZephirConstantDefinition -> AllIcons.Nodes.Constant
            else -> null
        }
    }

    override fun getChildren(): Array<TreeElement> = when (element) {
        is ZephirFile -> {
            val classes = PsiTreeUtil.getChildrenOfTypeAsList(element, ZephirClassDefinition::class.java)
            val ifaces = PsiTreeUtil.getChildrenOfTypeAsList(element, ZephirInterfaceDefinition::class.java)
            (classes + ifaces)
                .map { ZephirStructureViewElement(it) }
                .toTypedArray()
        }
        is ZephirClassDefinition -> element.classBody?.let { body ->
            (body.constantDefinitionList +
             body.propertyDefinitionList +
             body.abstractMethodDefinitionList +
             body.methodDefinitionList)
                .map { ZephirStructureViewElement(it) }
                .toTypedArray()
        } ?: emptyArray()
        is ZephirInterfaceDefinition -> element.interfaceBody?.let { body ->
            (body.constantDefinitionList + body.interfaceMethodDefinitionList)
                .map { ZephirStructureViewElement(it) }
                .toTypedArray()
        } ?: emptyArray()
        else -> emptyArray()
    }

    private fun buildMethodText(
        name: String,
        argsList: List<ZephirArguments>,
        returnType: ZephirReturnType?
    ): String {
        val args = argsList.flatMap { it.argumentList }.joinToString(", ") { arg ->
            buildString {
                arg.type?.text?.let { append("$it ") }
                append(arg.id?.text ?: arg.phpReserved?.text ?: "")
            }
        }
        val ret = returnType?.typeList?.joinToString(" | ") { it.text }?.let { " -> $it" } ?: ""
        return "$name($args)$ret"
    }
}
