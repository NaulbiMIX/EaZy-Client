/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.reflect.Field
 */
package net.optifine.reflect;

import java.lang.reflect.Field;
import net.optifine.reflect.IFieldLocator;

public class FieldLocatorFixed
implements IFieldLocator {
    private Field field;

    public FieldLocatorFixed(Field field) {
        this.field = field;
    }

    @Override
    public Field getField() {
        return this.field;
    }
}

