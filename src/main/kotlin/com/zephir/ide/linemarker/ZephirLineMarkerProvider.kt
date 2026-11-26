// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.linemarker

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.DumbService
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.ZephirFileType
import com.zephir.lang.core.psi.*

/**
 * Adds gutter icons to class, interface and trait declarations showing the number of subclasses, implementations
 * or users.
 */
class ZephirLineMarkerProvider : LineMarkerProvider, DumbAware {

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? = null

    override fun collectSlowLineMarkers(
        elements: List<PsiElement>,
        result: MutableCollection<in LineMarkerInfo<*>>
    ) {
        for (element in elements) {
            when (element) {
                is ZephirClassDefinition -> collectSubtypes(element, result)
                is ZephirInterfaceDefinition -> collectSubtypes(element, result)
                is ZephirTraitDefinition -> collectTraitUsers(element, result)
            }
        }
    }

    private fun collectTraitUsers(element: ZephirTraitDefinition, result: MutableCollection<in LineMarkerInfo<*>>) {
        val project = element.project
        if (DumbService.isDumb(project)) return

        val name = element.name ?: return
        val anchor = element.id?.let { PsiTreeUtil.getDeepestFirst(it) } ?: return
        val psiManager = PsiManager.getInstance(project)

        val users = mutableListOf<PsiElement>()
        for (vFile in FileTypeIndex.getFiles(ZephirFileType, GlobalSearchScope.projectScope(project))) {
            val psiFile = psiManager.findFile(vFile) as? ZephirFile ?: continue
            for (use in PsiTreeUtil.findChildrenOfType(psiFile, ZephirTraitUseStatement::class.java)) {
                val names = use.complexIdList?.complexIdList.orEmpty().map { it.idList.lastOrNull()?.text }
                if (name !in names) continue
                val owner = PsiTreeUtil.getParentOfType(use, ZephirClassDefinition::class.java, ZephirTraitDefinition::class.java)
                if (owner != null && owner != element && owner !in users) users.add(owner)
            }
        }

        if (users.isEmpty()) return

        result.add(
            NavigationGutterIconBuilder.create(AllIcons.Gutter.ImplementedMethod)
                .setTargets(users)
                .setPopupTitle("Used By")
                .setTooltipText("${users.size} class(es) or trait(s) using this trait")
                .createLineMarkerInfo(anchor)
        )
    }

    private fun collectSubtypes(element: PsiElement, result: MutableCollection<in LineMarkerInfo<*>>) {
        val name = (element as? ZephirNamedElement)?.name ?: return
        val project = element.project
        val scope = GlobalSearchScope.projectScope(project)

        val subtypes = mutableListOf<PsiElement>()

        for (vFile in FileTypeIndex.getFiles(ZephirFileType, scope)) {
            val psiFile = PsiManager.getInstance(project).findFile(vFile) as? ZephirFile
                ?: continue

            for (cls in PsiTreeUtil.findChildrenOfType(psiFile, ZephirClassDefinition::class.java)) {
                if (cls == element) continue
                // extends ClassName
                val superName = cls.superClass?.complexId?.idList?.lastOrNull()?.text
                if (superName == name) {
                    subtypes.add(cls)
                    continue
                }
                // implements InterfaceName
                val implNames = cls.implementsList?.complexIdList?.complexIdList
                    ?.map { it.idList.lastOrNull()?.text }
                if (implNames != null && name in implNames) {
                    subtypes.add(cls)
                }
            }

            for (iface in PsiTreeUtil.findChildrenOfType(psiFile, ZephirInterfaceDefinition::class.java)) {
                if (iface == element) continue
                // interface extends InterfaceName
                val superNames = iface.superInterfaces?.complexIdList?.complexIdList
                    ?.map { it.idList.lastOrNull()?.text }
                if (superNames != null && name in superNames) {
                    subtypes.add(iface)
                }
            }
        }

        if (subtypes.isEmpty()) return

        val anchor = when (element) {
            is ZephirClassDefinition -> element.id
            is ZephirInterfaceDefinition -> element.id
            else -> return
        }?.let { PsiTreeUtil.getDeepestFirst(it) } ?: return

        val icon = if (element is ZephirInterfaceDefinition)
            AllIcons.Gutter.ImplementedMethod
        else
            AllIcons.Gutter.OverridenMethod

        result.add(
            NavigationGutterIconBuilder.create(icon)
                .setTargets(subtypes)
                .setPopupTitle(if (element is ZephirInterfaceDefinition) "Implementations" else "Subclasses")
                .setTooltipText("${subtypes.size} ${if (element is ZephirInterfaceDefinition) "implementation(s)" else "subclass(es)"}")
                .createLineMarkerInfo(anchor)
        )
    }
}
