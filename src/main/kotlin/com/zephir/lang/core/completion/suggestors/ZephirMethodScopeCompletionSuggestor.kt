// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion.suggestors

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.zephir.lang.core.completion.ZephirCompletionPriority
import com.zephir.lang.core.completion.ZephirCompletionSuggestor
import com.zephir.lang.core.psi.*
import java.util.*

object ZephirMethodScopeCompletionSuggestor : ZephirCompletionSuggestor {
    private const val MAX_SYNTAX_TREE_DEEP = 256

    private val keywords = arrayOf(
        "let", "echo", "const", "if", "else", "elseif", "switch", "case", "default",
        "do", "while", "for", "loop", "reverse", "break", "continue", "in", "new", "return",
        "require", "clone", "empty", "typeof", "instanceof", "likely", "unlikely", "fetch",
        "isset", "unset", "throw", "try", "catch"
    )

    private val typeHints = arrayOf(
        "var", "array", "object", "callable", "resource", "int", "integer", "uint", "long", "ulong",
        "double", "float", "string", "char", "uchar"
    )

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        val psiElement = parameters.originalPosition ?: return

        val methodDefinition = (getPsiByCurrentPos(psiElement, "method") ?: return) as ZephirMethodDefinition
        val classBody = (getPsiByCurrentPos(psiElement, "class") ?: return) as ZephirClassBody
        getPsiByCurrentPos(psiElement, "method_body") ?: return

        processMethodArguments(methodDefinition, result)
        processClassMembers(classBody, result)
        processClassConstants(classBody, result)
        processClassMethods(classBody, result)

        // process keywords
        for (keyword in keywords) {
            result.addElement(LookupElementBuilder.create(keyword))
        }

        // process type hints
        for (typeHint in typeHints) {
            result.addElement(LookupElementBuilder.create(typeHint))
        }
    }

    private fun processMethodArguments(methodDefinition: ZephirMethodDefinition, result: CompletionResultSet) {
        val methodArgs = LinkedList<ZephirArgument>()
        for (args in methodDefinition.argumentsList) {
            methodArgs.addAll(args.argumentList)
        }

        for (arg in methodArgs) {
            val completionElement = arg.id?.text?.let {
                LookupElementBuilder
                    .create(it, arg.id!!.text)
                    .withTypeText(if (arg.type != null) arg.type!!.text else "", true)
                    .withBoldness(true)
                    .withLookupString(arg.id!!.text)
                    .withTailText(
                        if (arg.defaultValue != null) " " + arg.defaultValue!!.text else "",
                        true
                    )
            }
            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, ZephirCompletionPriority.METHOD_SCOPE_PRIORITY)
            )
        }
    }

    private fun processClassMembers(classBody: ZephirClassBody, result: CompletionResultSet) {
        for (propDef in classBody.propertyDefinitionList) {
            val completionElement = LookupElementBuilder
                .create(propDef.id.text, "this->" + propDef.id.text)
                .withTypeText(propDef.visibility.text, true)
                .withLookupString(propDef.id.text)
                .withTailText(
                    if (propDef.defaultValue != null) " " + propDef.defaultValue!!.text else "",
                    true
                )

            result.addElement(
                PrioritizedLookupElement.withPriority(
                    completionElement,
                    ZephirCompletionPriority.CLASS_PROPERTY_PRIORITY
                )
            )
        }
    }

    private fun processClassConstants(classBody: ZephirClassBody, result: CompletionResultSet) {
        for (constDef in classBody.constantDefinitionList) {
            val completionElement = LookupElementBuilder
                .create(constDef.id.text, "self::" + constDef.id.text)
                .withTypeText(constDef.defaultValue!!.text)
                .withLookupString(constDef.id.text)

            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, ZephirCompletionPriority.CLASS_CONSTS_PRIORITY)
            )
        }
    }

    private fun processClassMethods(classBody: ZephirClassBody, result: CompletionResultSet) {
        for (abstractDef in classBody.abstractMethodDefinitionList) {
            val interDef = abstractDef.interfaceMethodDefinition

            val completionElement = LookupElementBuilder
                .create(interDef.id.text, "this->" + interDef.id.text.toString() + "()")
                .withTypeText(if (interDef.returnType != null) interDef.returnType!!.text else "")
                .withLookupString(interDef.id.text)

            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, ZephirCompletionPriority.CLASS_METHOD_PRIORITY)
            )
        }
    }

    private fun getPsiByCurrentPos(psiElement: PsiElement, objectType: String): PsiElement? {
        var parent = psiElement.parent

        if (parent == null || parent is ZephirFile) {
            return null
        }

        var findLimitCounter = 0

        do {
            parent = parent.parent
            if (parent == null || parent is ZephirFile) {
                return null
            } else if (objectType == "method" && parent is ZephirMethodDefinition) {
                return parent
            } else if (objectType == "class" && parent is ZephirClassBody) {
                return parent
            } else if (objectType == "method_body" && parent is ZephirMethodBody) {
                return parent
            }
            ++findLimitCounter
        } while (findLimitCounter < MAX_SYNTAX_TREE_DEEP) // to avoid possible infinite cycles

        return null
    }
}
