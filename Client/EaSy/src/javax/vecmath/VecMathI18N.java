/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.MissingResourceException
 *  java.util.ResourceBundle
 */
package javax.vecmath;

import java.io.PrintStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

class VecMathI18N {
    VecMathI18N() {
    }

    static String getString(String key) {
        String s;
        try {
            s = ResourceBundle.getBundle((String)"javax.vecmath.ExceptionStrings").getString(key);
        }
        catch (MissingResourceException e) {
            System.err.println("VecMathI18N: Error looking up: " + key);
            s = key;
        }
        return s;
    }
}

