package com.zephir.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.zephir.ZephirFileType;
import com.zephir.ZephirLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Nikita Gusakov
 */
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
