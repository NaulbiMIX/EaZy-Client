/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.optifine.expr;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IParameters;

public class Parameters
implements IParameters {
    private ExpressionType[] parameterTypes;

    public Parameters(ExpressionType[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public ExpressionType[] getParameterTypes(IExpression[] params) {
        return this.parameterTypes;
    }
}

