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
package com.junichi11.netbeans.modules.template.settings;

import com.junichi11.netbeans.modules.template.settings.preferences.TemplateSettingsPreferences;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.loaders.CreateFromTemplateAttributesProvider;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author junichi11
 */
@ServiceProvider(service = CreateFromTemplateAttributesProvider.class)
public class ProjectTemplateAttributesProvider implements CreateFromTemplateAttributesProvider {

    private static final Logger LOGGER = Logger.getLogger(ProjectTemplateAttributesProvider.class.getName());

    @Override
    public Map<String, ?> attributesFor(DataObject template, DataFolder target, String name) {
        FileObject primaryFile = target.getPrimaryFile();
        if (primaryFile == null) {
            return Collections.emptyMap();
        }
        Project project = FileOwnerQuery.getOwner(primaryFile);
        if (project == null || !TemplateSettingsPreferences.isEnabled(project)) {
            return Collections.emptyMap();
        }

        // get project properties
        String settings = TemplateSettingsPreferences.getSettings(project);
        if (settings == null) {
            return Collections.emptyMap();
        }
        HashMap<String, String> attributes = new HashMap<>();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(settings.getBytes("UTF-8"))) { // NOI18N
            Properties properties = new Properties();
            properties.load(inputStream);
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                attributes.put(key, value);
            }
        } catch (UnsupportedEncodingException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
        return attributes;
    }

}
