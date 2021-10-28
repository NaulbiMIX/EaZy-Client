/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.shaders.config;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpressionBool;
import net.optifine.shaders.config.ShaderOptionSwitch;

public class ExpressionShaderOptionSwitch
implements IExpressionBool {
    private ShaderOptionSwitch shaderOption;

    public ExpressionShaderOptionSwitch(ShaderOptionSwitch shaderOption) {
        this.shaderOption = shaderOption;
    }

    @Override
    public boolean eval() {
        return ShaderOptionSwitch.isTrue(this.shaderOption.getValue());
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.BOOL;
    }

    public String toString() {
        return "" + this.shaderOption;
    }
}

