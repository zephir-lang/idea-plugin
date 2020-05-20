// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.commenter

import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.psi.PsiComment
import com.intellij.psi.tree.IElementType
import com.zephir.lang.core.psi.ZephirTypes

class ZephirCommenter : CodeDocumentationAwareCommenter {
    override fun isDocumentationComment(element: PsiComment?): Boolean {
        return element!!.tokenType === ZephirTypes.CODE_BLOCK
    }

    override fun getLineCommentPrefix() = "//"
    override fun getDocumentationCommentTokenType(): IElementType? = ZephirTypes.COMMENT_BLOCK
    override fun getCommentedBlockCommentPrefix(): String? = null
    override fun getCommentedBlockCommentSuffix(): String? = null
    override fun getLineCommentTokenType(): IElementType? = ZephirTypes.COMMENT
    override fun getBlockCommentTokenType(): IElementType? = ZephirTypes.COMMENT_BLOCK
    override fun getBlockCommentPrefix() = "/*"
    override fun getBlockCommentSuffix() = "*/"
    override fun getDocumentationCommentLinePrefix() = "*"
    override fun getDocumentationCommentPrefix() = "/**"
    override fun getDocumentationCommentSuffix() = "*/"
}
