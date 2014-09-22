package com.zephir.formatting;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Nikita Gusakov
 */
public class ZephirBlock extends AbstractBlock {
    private final CodeStyleSettings settings;

    protected ZephirBlock(ASTNode node, Wrap wrap, Alignment alignment, CodeStyleSettings settings) {
        super(node, wrap, alignment);
        this.settings = settings;
    }

    @Override
    protected List<Block> buildChildren() {
        return null;
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
