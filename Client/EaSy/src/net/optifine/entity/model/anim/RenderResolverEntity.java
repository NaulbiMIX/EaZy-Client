/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.entity.model.anim;

import net.optifine.entity.model.anim.IRenderResolver;
import net.optifine.entity.model.anim.RenderEntityParameterBool;
import net.optifine.entity.model.anim.RenderEntityParameterFloat;
import net.optifine.expr.IExpression;

public class RenderResolverEntity
implements IRenderResolver {
    @Override
    public IExpression getParameter(String name) {
        RenderEntityParameterBool renderentityparameterbool = RenderEntityParameterBool.parse(name);
        if (renderentityparameterbool != null) {
            return renderentityparameterbool;
        }
        RenderEntityParameterFloat renderentityparameterfloat = RenderEntityParameterFloat.parse(name);
        return renderentityparameterfloat != null ? renderentityparameterfloat : null;
    }
}

