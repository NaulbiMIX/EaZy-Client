/*
 * Decompiled with CFR 0.145.
 */
package com.sun.jna;

public class FromNativeContext {
    private Class<?> type;

    FromNativeContext(Class<?> javaType) {
        this.type = javaType;
    }

    public Class<?> getTargetType() {
        return this.type;
    }
}

