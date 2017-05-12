package com.zephir.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.zephir.psi.ZephirArgument;
import com.zephir.psi.ZephirArguments;
import com.zephir.psi.ZephirFile;
import com.zephir.psi.ZephirMethodDefinition;
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

        ZephirMethodDefinition methodDefinition = getMethodByCurrentPos(psiElement);
        if (methodDefinition == null) {
            return;
        }

        List<ZephirArgument> methodArgs = new LinkedList();
        for(ZephirArguments args : methodDefinition.getArgumentsList()) {
            methodArgs.addAll(args.getArgumentList());
        }

        for (ZephirArgument arg : methodArgs) {
            LookupElementBuilder completionElement = LookupElementBuilder
                //empty space to pull up arguments of method
                .create(arg.getId().getText(), " " + arg.getId().getText())
                .withTypeText(arg.getType().getText(), true)
                .withBoldness(true)
                .withLookupString(arg.getId().getText())
                .withTailText(
                    arg.getDefaultValue() != null ? " " + arg.getDefaultValue().getText() : "",
                    true
                );

            result.addElement(completionElement);
        }

        for (String keyword : this.keywords) {
            result.addElement(LookupElementBuilder.create(keyword));
        }

        for (String typeHint : this.typeHints) {
            result.addElement(LookupElementBuilder.create(typeHint));
        }
    }

    private ZephirMethodDefinition getMethodByCurrentPos(PsiElement psiElement) {
        PsiElement parent = psiElement.getParent();

        if((parent instanceof ZephirFile)) {
            return null;
        }

        int findLimitCounter = 0;
        do{
            parent = parent.getParent();
            if((parent instanceof ZephirFile)) {
                return null;
            } else if (parent instanceof ZephirMethodDefinition) {
                return (ZephirMethodDefinition)parent;
            }
            ++findLimitCounter;
        } while (findLimitCounter < MAX_SYNTAX_TREE_DEEP); // to avoid possible infinite cycles

        return null;
    }

}
