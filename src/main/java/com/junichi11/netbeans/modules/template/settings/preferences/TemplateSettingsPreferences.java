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
package com.junichi11.netbeans.modules.template.settings.preferences;

import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;

/**
 *
 * @author junichi11
 */
public final class TemplateSettingsPreferences {

    private static final String SETTINGS = "settings"; // NOI18N
    private static final String ENABLED = "enabled"; // NOI18N

    private static final String DEFAULT_SETTINGS
            = "# If you enable settings in this project, your settings will be used when you create a new template.\n" // NOI18N
            + "\n" // NOI18N
            + "# uncomment the next line and specify your user name to be used in new templates\n" // NOI18N
            + "#user=your user name\n"; // NOI18N

    private TemplateSettingsPreferences() {
    }

    public static boolean isEnabled(Project project) {
        return getPreferences(project, false).getBoolean(ENABLED, false);
    }

    public static void setEnabled(Project project, boolean isEnabled) {
        getPreferences(project, false).putBoolean(ENABLED, isEnabled);
    }

    public static String getSettings(Project project) {
        return getPreferences(project, false).get(SETTINGS, DEFAULT_SETTINGS); // NOI18N
    }

    public static void setSettings(Project project, String properties) {
        getPreferences(project, false).put(SETTINGS, properties);
    }

    private static Preferences getPreferences(Project project) {
        return getPreferences(project, true);
    }

    private static Preferences getPreferences(Project project, boolean isShared) {
        return ProjectUtils.getPreferences(project, TemplateSettingsPreferences.class, isShared);
    }
}
