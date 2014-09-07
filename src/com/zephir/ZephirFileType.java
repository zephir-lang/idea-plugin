package com.zephir;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Nikita Gusakov
 */
public class ZephirFileType implements FileType {

    public static final ZephirFileType INSTANCE = new ZephirFileType();

    @NotNull
    @Override
    public String getName() {
        return "Zephir";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "The Zephir language";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "zep";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return ZephirIcons.PHALCON;
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, byte[] bytes) {
        return CharsetToolkit.UTF8;
    }
}
