/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * Licensed under the MIT License (MIT);
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.zephir.ZephirFileType;
import com.zephir.ZephirLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ZephirFile extends PsiFileBase {
    public ZephirFile(@NotNull FileViewProvider fileViewProvider) {
        super(fileViewProvider, ZephirLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ZephirFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Zephir File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
