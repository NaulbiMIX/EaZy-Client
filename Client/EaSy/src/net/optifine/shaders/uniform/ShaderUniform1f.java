/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  org.lwjgl.opengl.ARBShaderObjects
 */
package net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform1f
extends ShaderUniformBase {
    private float[] programValues;
    private static final float VALUE_UNKNOWN = -3.4028235E38f;

    public ShaderUniform1f(String name) {
        super(name);
        this.resetValue();
    }

    public void setValue(float valueNew) {
        int i = this.getProgram();
        float f = this.programValues[i];
        if (valueNew != f) {
            this.programValues[i] = valueNew;
            int j = this.getLocation();
            if (j >= 0) {
                ARBShaderObjects.glUniform1fARB((int)j, (float)valueNew);
                this.checkGLError();
            }
        }
    }

    public float getValue() {
        int i = this.getProgram();
        float f = this.programValues[i];
        return f;
    }

    @Override
    protected void onProgramSet(int program) {
        if (program >= this.programValues.length) {
            float[] afloat = this.programValues;
            float[] afloat1 = new float[program + 10];
            System.arraycopy((Object)afloat, (int)0, (Object)afloat1, (int)0, (int)afloat.length);
            for (int i = afloat.length; i < afloat1.length; ++i) {
                afloat1[i] = -3.4028235E38f;
            }
            this.programValues = afloat1;
        }
    }

    @Override
    protected void resetValue() {
        this.programValues = new float[]{-3.4028235E38f};
    }
}

