/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.util;

import net.minecraft.util.IJsonSerializable;

public class TupleIntJsonSerializable {
    private int integerValue;
    private IJsonSerializable jsonSerializableValue;

    public int getIntegerValue() {
        return this.integerValue;
    }

    public void setIntegerValue(int integerValueIn) {
        this.integerValue = integerValueIn;
    }

    public <T extends IJsonSerializable> T getJsonSerializableValue() {
        return (T)this.jsonSerializableValue;
    }

    public void setJsonSerializableValue(IJsonSerializable jsonSerializableValueIn) {
        this.jsonSerializableValue = jsonSerializableValueIn;
    }
}
