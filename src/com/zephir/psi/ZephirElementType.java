package com.zephir.psi;

import com.intellij.psi.tree.IElementType;
import com.zephir.ZephirLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nikita Gusakov
 */
public class ZephirElementType extends IElementType {
    public ZephirElementType(@NotNull @NonNls String debugName) {
        super(debugName, ZephirLanguage.INSTANCE);
    }
}
