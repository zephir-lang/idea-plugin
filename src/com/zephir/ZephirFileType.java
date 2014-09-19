package com.zephir;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Nikita Gusakov
 */
public class ZephirFileType extends LanguageFileType {

    public static final LanguageFileType INSTANCE = new ZephirFileType();

    private ZephirFileType() {
        super(ZephirLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Zephir";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Zephir";
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
}
