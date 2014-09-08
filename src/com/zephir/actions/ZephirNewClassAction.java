package com.zephir.actions;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.zephir.ZephirIcons;

/**
 * @author Nikita Gusakov
 */
public class ZephirNewClassAction extends CreateFileFromTemplateAction {

    public ZephirNewClassAction() {
        super("Zephir Class", "Create new Zephir class", ZephirIcons.PHALCON);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory psiDirectory, CreateFileFromTemplateDialog.Builder builder) {
        builder.setTitle("New Zephir class");
        builder.addKind("Class", null, "class");
        builder.addKind("Interface", null, "interface");
    }

    @Override
    protected String getActionName(PsiDirectory psiDirectory, String s, String s2) {
        return "Creating new Zephir class";
    }
}
