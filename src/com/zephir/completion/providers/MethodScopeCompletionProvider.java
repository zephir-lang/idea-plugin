// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.zephir.completion.Priority;
import com.zephir.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class MethodScopeCompletionProvider extends CompletionProvider<CompletionParameters> {

    private static int MAX_SYNTAX_TREE_DEEP = 256;

    private String[] keywords = new String[] {
        "let", "echo", "const", "if", "else", "elseif", "switch", "case", "default",
        "do", "while", "for", "loop", "reverse", "break", "continue", "in", "new", "return",
        "require", "clone", "empty", "typeof", "instanceof", "likely", "unlikely", "fetch",
        "isset", "unset", "throw", "try", "catch"
    };

    private String[] typeHints = new String[] {
        "var", "array", "object", "callable", "resource", "int", "integer", "uint", "long", "ulong",
        "double", "float", "string", "char", "uchar"
    };


    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  ProcessingContext context,
                                  @NotNull CompletionResultSet result) {

        PsiElement psiElement = parameters.getOriginalPosition();
        if(psiElement == null) {
            return;
        }

        ZephirMethodDefinition methodDefinition = (ZephirMethodDefinition)getPsiByCurrentPos(psiElement, "method");
        if (methodDefinition == null) {
            return;
        }

        ZephirClassBody classBody = (ZephirClassBody)getPsiByCurrentPos(psiElement, "class");
        if (classBody == null) {
            return;
        }

        ZephirMethodBody methodBody = (ZephirMethodBody)getPsiByCurrentPos(psiElement, "method_body");
        if (methodBody == null) {
            return;
        }

        processMethodArguments(methodDefinition, result);
        processClassMembers(classBody, result);
        processClassConstants(classBody, result);
        processClassMethods(classBody, result);

        //process keywords
        for (String keyword : this.keywords) {
            result.addElement(LookupElementBuilder.create(keyword));
        }

        for (String typeHint : this.typeHints) {
            result.addElement(LookupElementBuilder.create(typeHint));
        }
    }


    private void processClassMembers(ZephirClassBody classBody, @NotNull CompletionResultSet result) {

        for(ZephirPropertyDefinition propDef : classBody.getPropertyDefinitionList()) {
            LookupElementBuilder completionElement = LookupElementBuilder
                .create(propDef.getId().getText(), "this->" + propDef.getId().getText())
                .withTypeText(propDef.getVisibility().getText(), true)
                .withLookupString(propDef.getId().getText())
                .withTailText(
                    propDef.getDefaultValue() != null ? " " + propDef.getDefaultValue().getText() : "",
                    true
                );

            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, Priority.CLASS_PROPERTY_PRIORITY)
            );
        }
    }

    private void processClassConstants(ZephirClassBody classBody, @NotNull CompletionResultSet result) {

        for(ZephirConstantDefinition constDef : classBody.getConstantDefinitionList()) {
            LookupElementBuilder completionElement = LookupElementBuilder
                .create(constDef.getId().getText(), "self::" + constDef.getId().getText())
                .withTypeText(constDef.getDefaultValue().getText())
                .withLookupString(constDef.getId().getText());

            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, Priority.CLASS_CONSTS_PRIORITY)
            );
        }
    }

    private void processClassMethods(ZephirClassBody classBody, @NotNull CompletionResultSet result) {

        for(ZephirAbstractMethodDefinition abstractDef : classBody.getAbstractMethodDefinitionList()) {
            ZephirInterfaceMethodDefinition interDef = abstractDef.getInterfaceMethodDefinition();

            LookupElementBuilder completionElement = LookupElementBuilder
                .create(interDef.getId().getText(), "this->" + interDef.getId().getText() + "()")
                .withTypeText(interDef.getReturnType() != null ? interDef.getReturnType().getText() : "")
                .withLookupString(interDef.getId().getText());

            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, Priority.CLASS_METHOD_PRIORITY)
            );
        }

        for(ZephirMethodDefinition methodDef : classBody.getMethodDefinitionList()) {

            LookupElementBuilder completionElement = LookupElementBuilder
                .create(methodDef.getId().getText(), "this->" + methodDef.getId().getText() + "()")
                .withTypeText(methodDef.getReturnType() != null ? methodDef.getReturnType().getText() : "")
                .withLookupString(methodDef.getId().getText());

            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, Priority.CLASS_METHOD_PRIORITY)
            );
        }

    }

    private void processMethodArguments(ZephirMethodDefinition methodDefinition, @NotNull CompletionResultSet result) {

        List<ZephirArgument> methodArgs = new LinkedList();
        for(ZephirArguments args : methodDefinition.getArgumentsList()) {
            methodArgs.addAll(args.getArgumentList());
        }

        for (ZephirArgument arg : methodArgs) {
            LookupElementBuilder completionElement = LookupElementBuilder
                .create(arg.getId().getText(), arg.getId().getText())
                .withTypeText(arg.getType() != null ? arg.getType().getText() : "", true)
                .withBoldness(true)
                .withLookupString(arg.getId().getText())
                .withTailText(
                    arg.getDefaultValue() != null ? " " + arg.getDefaultValue().getText() : "",
                    true
                );

            result.addElement(
                PrioritizedLookupElement.withPriority(completionElement, Priority.METHOD_SCOPE_PRIORITY)
            );
        }

    }

    private PsiElement getPsiByCurrentPos(PsiElement psiElement, String objectType) {
        PsiElement parent = psiElement.getParent();

        if (parent == null || parent instanceof ZephirFile) {
            return null;
        }

        int findLimitCounter = 0;
        do {
            parent = parent.getParent();
            if (parent == null || parent instanceof ZephirFile) {
                return null;
            } else if (objectType.equals("method") && parent instanceof ZephirMethodDefinition) {
                return parent;
            } else if (objectType.equals("class") && parent instanceof ZephirClassBody) {
                return parent;
            } else if (objectType.equals("method_body") && parent instanceof ZephirMethodBody) {
                return parent;
            }
            ++findLimitCounter;
        } while (findLimitCounter < MAX_SYNTAX_TREE_DEEP); // to avoid possible infinite cycles

        return null;
    }

}
