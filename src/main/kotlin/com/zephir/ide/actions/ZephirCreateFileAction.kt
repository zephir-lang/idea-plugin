// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.actions.AttributesDefaults
import com.intellij.ide.fileTemplates.ui.CreateFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.psi.PsiDirectory
import com.zephir.lang.core.ZephirFileType
import java.util.*

// TODO(serghei): Put all natural language strings into the resource bundle src/main/resources/.../zephir.properties
// TODO(serghei): Add a note about resource bundle to CONTRIBUTING.md
class ZephirCreateFileAction : CreateFileFromTemplateAction(CAPTION, DESCRIPTION, ZephirFileType.icon) {
    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String = CAPTION

    override fun buildDialog(
        project: Project?,
        directory: PsiDirectory?,
        builder: CreateFileFromTemplateDialog.Builder
    ) {
        // TODO(serghei): Use different icons for class and interface
        // TODO(serghei): setValidator(ZephirNameValidator)
        builder.setTitle(CAPTION)
            .addKind("Class", ZephirFileType.icon, ZEPHIR_KIND_CLASS)
            .addKind("Interface", ZephirFileType.icon, ZEPHIR_KIND_INTERFACE)
    }

    override fun createFileFromTemplate(name: String, template: FileTemplate, dir: PsiDirectory) = try {
        val className = FileUtilRt.getNameWithoutExtension(name)
        val project = dir.project
        val properties = createProperties(project, className)
        CreateFromTemplateDialog(project, dir, template, AttributesDefaults(className).withFixedName(true), properties)
            .create()
            .containingFile
    } catch (e: Exception) {
        LOG.error("Error while creating new file", e)
        null
    }

    private companion object {
        private const val CAPTION = "Zephir File"
        private const val DESCRIPTION = "Create new Zephir file"

        // These constants must match name of internal template stored in JAR resources
        private const val ZEPHIR_KIND_CLASS = "Zephir Class"
        private const val ZEPHIR_KIND_INTERFACE = "Zephir Interface"

        fun createProperties(project: Project, className: String): Properties {
            val properties = FileTemplateManager.getInstance(project).defaultProperties

            // TODO(serghei): Do this better.
            // For example we can transform \Acme\Service to:
            //  - "Acme" as a namespace
            //  - "Service" as a class
            properties += "NAMESPACE" to project.name
            properties += "NAME" to className

            return properties
        }
    }
}
