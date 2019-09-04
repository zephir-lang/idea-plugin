/**
 * Copyright (c) 2014-2019 Zephir Team
 *
 * This source file is subject the MIT license, that is bundled with
 * this package in the file LICENSE, and is available through the
 * world-wide-web at the following url:
 *
 * https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
 */

package com.zephir.lang;

import com.intellij.lang.CodeDocumentationAwareCommenter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.tree.IElementType;
import com.zephir.psi.ZephirTypes;
import org.jetbrains.annotations.Nullable;

public class ZephirCommenter implements CodeDocumentationAwareCommenter {
    @Nullable
    @Override
    public String getLineCommentPrefix() {
        return "//";
    }

    @Nullable
    @Override
    public String getBlockCommentPrefix() {
        return "/*";
    }

    @Nullable
    @Override
    public String getBlockCommentSuffix() {
        return "*/";
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    @Nullable
    @Override
    public String getCommentedBlockCommentSuffix() {
        return null;
    }

    @Nullable
    @Override
    public IElementType getLineCommentTokenType() {
        return ZephirTypes.COMMENT;
    }

    @Nullable
    @Override
    public IElementType getBlockCommentTokenType() {
        return ZephirTypes.COMMENT_BLOCK;
    }

    @Nullable
    @Override
    public IElementType getDocumentationCommentTokenType() {
        return ZephirTypes.COMMENT_BLOCK;
    }

    @Nullable
    @Override
    public String getDocumentationCommentPrefix() {
        return "/**";
    }

    @Nullable
    @Override
    public String getDocumentationCommentLinePrefix() {
        return "*";
    }

    @Nullable
    @Override
    public String getDocumentationCommentSuffix() {
        return "*/";
    }

    @Override
    public boolean isDocumentationComment(PsiComment element) {
        return element.getTokenType() == ZephirTypes.CODE_BLOCK;
    }
}
