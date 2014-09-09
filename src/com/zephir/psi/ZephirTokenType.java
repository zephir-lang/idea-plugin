package com.zephir.psi;

import com.intellij.psi.tree.IElementType;
import com.zephir.ZephirLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nikita Gusakov
 */
public class ZephirTokenType extends IElementType {

    public ZephirTokenType(@NotNull @NonNls String debugName) {
        super(debugName, ZephirLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "ZephirTokenType." + super.toString();
    }
}
