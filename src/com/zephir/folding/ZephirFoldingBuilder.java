/**
 * Copyright (c) 2014-2019 Zephir Team
 *
 * This source file is subject the MIT license, that is bundled with
 * this package in the file LICENSE, and is available through the
 * world-wide-web at the following url:
 *
 * https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
 */

package com.zephir.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.CustomFoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ZephirFoldingBuilder extends CustomFoldingBuilder {
    @Override
    protected void buildLanguageFoldRegions(@NotNull List<FoldingDescriptor> foldingDescriptors,
                                            @NotNull PsiElement psiElement,
                                            @NotNull Document document,
                                            final boolean quick) {

    }

    @Override
    protected String getLanguagePlaceholderText(@NotNull ASTNode astNode, @NotNull TextRange textRange) {
        return null;
    }

    @Override
    protected boolean isRegionCollapsedByDefault(@NotNull ASTNode astNode) {
        return false;
    }
}
