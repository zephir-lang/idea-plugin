// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.zephir.lang.core.psi.*

class ZephirStructureViewModel(file: ZephirFile, editor: Editor?) :
    StructureViewModelBase(file, editor, ZephirStructureViewElement(file)),
    StructureViewModel.ElementInfoProvider {

    override fun getSorters(): Array<Sorter> = arrayOf(Sorter.ALPHA_SORTER)

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement) = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean =
        element.value is ZephirMethodDefinition ||
        element.value is ZephirAbstractMethodDefinition ||
        element.value is ZephirInterfaceMethodDefinition ||
        element.value is ZephirPropertyDefinition ||
        element.value is ZephirConstantDefinition
}
