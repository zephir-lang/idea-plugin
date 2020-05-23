// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.actions

import com.intellij.icons.AllIcons
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.fileTemplates.actions.AttributesDefaults
import com.intellij.ide.fileTemplates.ui.CreateFromTemplateDialog
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.util.IncorrectOperationException
import com.zephir.lang.core.ZephirFileType
import java.util.*

// TODO(serghei): Put all natural language strings into the resource bundle src/main/resources/.../zephir.properties
// TODO(serghei): Add a note about resource bundle to CONTRIBUTING.md
class ZephirCreateFileAction : CreateFileFromTemplateAction(
    "Zephir File/Class",
    "Creates new Zephir file or class",
    ZephirFileType.icon
) {
    override fun buildDialog(
        project: Project,
        directory: PsiDirectory,
        builder: CreateFileFromTemplateDialog.Builder
    ) {
        // TODO(serghei): setValidator(ZephirNameValidator)
        builder.setTitle("New Zephir File/Class")
            .addKind("File", ZephirFileType.icon, "Zephir File")
            .addKind("Class", AllIcons.Nodes.Class, "Zephir Class")
            .addKind("Interface", AllIcons.Nodes.Interface, "Zephir Interface")
    }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String =
        "Zephir File/Class"

    override fun createFileFromTemplate(name: String, template: FileTemplate, dir: PsiDirectory): PsiFile? {
        val project = dir.project
        val className = FileUtilRt.getNameWithoutExtension(name)
        val properties = createProperties(project, className)

        val element = try {
            CreateFromTemplateDialog(
                project,
                dir,
                template,
                AttributesDefaults(className).withFixedName(true),
                properties
            ).create()
        } catch (e: IncorrectOperationException) {
            LOG.error("Error while creating new file", e)
            throw e
        } catch (e: Exception) {
            LOG.error("Error while creating new file", e)
            null
        }

        return element?.containingFile
    }

    private companion object {
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
