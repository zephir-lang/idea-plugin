package com.zephir;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.Nullable;

/**
 * @author Nikita Gusakov
 */
public class ZephirLanguage extends Language {
    public static final Language INSTANCE = new ZephirLanguage();

    private ZephirLanguage() {
        super("Zephir");
    }

    @Nullable
    @Override
    public LanguageFileType getAssociatedFileType() {
        return ZephirFileType.INSTANCE;
    }
}
