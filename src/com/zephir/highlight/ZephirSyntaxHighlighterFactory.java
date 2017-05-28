/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * Licensed under the MIT License (MIT);
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir.highlight;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZephirSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        return new ZephirSyntaxHighlighter();
    }
}
