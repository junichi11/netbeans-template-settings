/*
 * The MIT License
 *
 * Copyright 2015 junichi11.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.junichi11.netbeans.modules.template.settings.ui.customizers;

import com.junichi11.netbeans.modules.template.settings.preferences.TemplateSettingsPreferences;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Lookup;

/**
 *
 * @author junichi11
 */
public class TemplateSettingsCustomizerProvider implements ProjectCustomizer.CompositeCategoryProvider {

    private static final String CATEGORY_NAME = "Template Settings";
    private TemplateSettingsCustomizerPanel panel;

    @ProjectCustomizer.CompositeCategoryProvider.Registrations({
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-ant-freeform", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-apisupport-project", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-apisupport-project-suite", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-apisupport-project-suite-jnlp", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-apisupport-project-suite-osgi", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-apisupport-project-suite-package", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-autoproject", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-j2ee-clientproject", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-j2ee-earproject", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-j2ee-ejbjarproject", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-java-j2seproject", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-maven", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-php-project", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-web-project", position = 6000),
        @ProjectCustomizer.CompositeCategoryProvider.Registration(projectType = "org-netbeans-modules-web.clientproject", position = 6000)
    })
    public static TemplateSettingsCustomizerProvider createTemplateSettings() {
        return new TemplateSettingsCustomizerProvider();
    }

    private TemplateSettingsCustomizerProvider() {
    }

    @Override
    public ProjectCustomizer.Category createCategory(Lookup context) {
        return Category.create(CATEGORY_NAME, CATEGORY_NAME, null);
    }

    @Override
    public JComponent createComponent(ProjectCustomizer.Category category, Lookup context) {
        final Project project = context.lookup(Project.class);
        category.setOkButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings(project);
            }
        });
        loadSettings(project);
        return getPanel();
    }

    private TemplateSettingsCustomizerPanel getPanel() {
        if (panel == null) {
            panel = new TemplateSettingsCustomizerPanel();
        }
        return panel;
    }

    private void loadSettings(Project project) {
        getPanel().setSettings(TemplateSettingsPreferences.getSettings(project));
        getPanel().setSettingsEnabled(TemplateSettingsPreferences.isEnabled(project));
    }

    private void saveSettings(Project project) {
        TemplateSettingsPreferences.setSettings(project, getPanel().getSettings());
        TemplateSettingsPreferences.setEnabled(project, getPanel().isSettingsEnabled());
    }

}
