/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.shaders.uniform;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionFloat;
import net.optifine.shaders.uniform.ShaderParameterFloat;

public class ShaderParameterIndexed
implements IExpressionFloat {
    private ShaderParameterFloat type;
    private int index1;
    private int index2;

    public ShaderParameterIndexed(ShaderParameterFloat type) {
        this(type, 0, 0);
    }

    public ShaderParameterIndexed(ShaderParameterFloat type, int index1) {
        this(type, index1, 0);
    }

    public ShaderParameterIndexed(ShaderParameterFloat type, int index1, int index2) {
        this.type = type;
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public float eval() {
        return this.type.eval(this.index1, this.index2);
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.FLOAT;
    }

    public String toString() {
        if (this.type.getIndexNames1() == null) {
            return "" + (Object)((Object)this.type);
        }
        return this.type.getIndexNames2() == null ? "" + (Object)((Object)this.type) + "." + this.type.getIndexNames1()[this.index1] : "" + (Object)((Object)this.type) + "." + this.type.getIndexNames1()[this.index1] + "." + this.type.getIndexNames2()[this.index2];
    }
}

