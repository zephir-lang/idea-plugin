package com.zephir.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.zephir.ZephirLanguage;
import com.zephir.psi.ZephirScalarType;
import com.zephir.psi.ZephirTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nikita Gusakov
 */
public class ZephirKeywordCompletionContributor extends CompletionContributor {
    public ZephirKeywordCompletionContributor() {
        extend(CompletionType.BASIC,
                // @todo: should be defined just just outside of class/interface
                PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
                new ZephirKeywordCompletionProvider(new String[]{
                        "namespace", "use", "as", "interface", "class", "extends", "implements", "final", "abstract"
                })
        );
        extend(CompletionType.BASIC,
                // @todo: should be defined just for method body
                PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
                new ZephirKeywordCompletionProvider(new String[]{
                        "let", "echo", "const", "if", "else", "elseif", "switch", "case", "default",
                        "do", "while", "for", "loop", "reverse", "break", "continue", "in", "new", "return",
                        "require", "clone", "empty", "typeof", "instanceof", "likely", "unlikely", "fetch",
                        "isset", "unset", "throw", "try", "catch"
                })
        );
        extend(CompletionType.BASIC,
                // @todo: should be defined just for type-hints
                PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
                new ZephirKeywordCompletionProvider(new String[]{
                        "var", "array", "object", "callable", "resource",
                        "int", "integer", "uint", "long", "ulong",
                        "double", "float",
                        "string", "char", "uchar"
                })
        );
    }

    private class ZephirKeywordCompletionProvider extends CompletionProvider<CompletionParameters> {
        private final String[] keywords;

        public ZephirKeywordCompletionProvider(String[] keywords) {
            this.keywords = keywords;
        }

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
            for (String keyword : this.keywords) {
                result.addElement(LookupElementBuilder.create(keyword));
            }
        }
    }
}
