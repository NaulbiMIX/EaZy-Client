/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 */
package org.objectweb.asm;

import java.util.Arrays;
import org.objectweb.asm.Handle;

public final class ConstantDynamic {
    private final String name;
    private final String descriptor;
    private final Handle bootstrapMethod;
    private final Object[] bootstrapMethodArguments;

    public /* varargs */ ConstantDynamic(String name, String descriptor, Handle bootstrapMethod, Object ... bootstrapMethodArguments) {
        this.name = name;
        this.descriptor = descriptor;
        this.bootstrapMethod = bootstrapMethod;
        this.bootstrapMethodArguments = bootstrapMethodArguments;
    }

    public String getName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public Handle getBootstrapMethod() {
        return this.bootstrapMethod;
    }

    public int getBootstrapMethodArgumentCount() {
        return this.bootstrapMethodArguments.length;
    }

    public Object getBootstrapMethodArgument(int index) {
        return this.bootstrapMethodArguments[index];
    }

    Object[] getBootstrapMethodArgumentsUnsafe() {
        return this.bootstrapMethodArguments;
    }

    public int getSize() {
        char firstCharOfDescriptor = this.descriptor.charAt(0);
        return firstCharOfDescriptor == 'J' || firstCharOfDescriptor == 'D' ? 2 : 1;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ConstantDynamic)) {
            return false;
        }
        ConstantDynamic constantDynamic = (ConstantDynamic)object;
        return this.name.equals((Object)constantDynamic.name) && this.descriptor.equals((Object)constantDynamic.descriptor) && this.bootstrapMethod.equals(constantDynamic.bootstrapMethod) && Arrays.equals((Object[])this.bootstrapMethodArguments, (Object[])constantDynamic.bootstrapMethodArguments);
    }

    public int hashCode() {
        return this.name.hashCode() ^ Integer.rotateLeft((int)this.descriptor.hashCode(), (int)8) ^ Integer.rotateLeft((int)this.bootstrapMethod.hashCode(), (int)16) ^ Integer.rotateLeft((int)Arrays.hashCode((Object[])this.bootstrapMethodArguments), (int)24);
    }

    public String toString() {
        return this.name + " : " + this.descriptor + ' ' + this.bootstrapMethod + ' ' + Arrays.toString((Object[])this.bootstrapMethodArguments);
    }
}

