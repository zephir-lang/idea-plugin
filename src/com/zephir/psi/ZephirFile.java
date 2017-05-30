/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * This source file is subject the MIT license, that is bundled with
 * this package in the file LICENSE, and is available through the
 * world-wide-web at the following url:
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
