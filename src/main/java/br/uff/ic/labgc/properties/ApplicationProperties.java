/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.ic.labgc.properties;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class looks for properties in the application settings file and return
 * their values.
 *
 * @author Cristiano
 */
public class ApplicationProperties {

    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle(IPropertiesConstants.PROPERTIES_FILE_NAME,
                Locale.getDefault(), ClassLoader.getSystemClassLoader());
    }

    public static String getPropertyValue(String propertyKey) {
        return bundle.getString(propertyKey);
    }
    
}
