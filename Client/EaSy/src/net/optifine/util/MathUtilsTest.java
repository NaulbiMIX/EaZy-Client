/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Enum
 *  java.lang.Exception
 *  java.lang.Float
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package net.optifine.util;

import java.io.PrintStream;
import net.minecraft.util.MathHelper;
import net.optifine.util.MathUtils;

public class MathUtilsTest {
    public static void main(String[] args) throws Exception {
        OPER[] amathutilstest$oper = OPER.values();
        for (int i = 0; i < amathutilstest$oper.length; ++i) {
            OPER mathutilstest$oper = amathutilstest$oper[i];
            MathUtilsTest.dbg("******** " + (Object)((Object)mathutilstest$oper) + " ***********");
            MathUtilsTest.test(mathutilstest$oper, false);
        }
    }

    private static void test(OPER oper, boolean fast) {
        double d0;
        double d1;
        MathHelper.fastMath = fast;
        switch (oper) {
            case SIN: 
            case COS: {
                d0 = -MathHelper.PI;
                d1 = MathHelper.PI;
                break;
            }
            case ASIN: 
            case ACOS: {
                d0 = -1.0;
                d1 = 1.0;
                break;
            }
            default: {
                return;
            }
        }
        int i = 10;
        for (int j = 0; j <= i; ++j) {
            float f;
            float f1;
            double d2 = d0 + (double)j * (d1 - d0) / (double)i;
            switch (oper) {
                case SIN: {
                    f = (float)Math.sin((double)d2);
                    f1 = MathHelper.sin((float)d2);
                    break;
                }
                case COS: {
                    f = (float)Math.cos((double)d2);
                    f1 = MathHelper.cos((float)d2);
                    break;
                }
                case ASIN: {
                    f = (float)Math.asin((double)d2);
                    f1 = MathUtils.asin((float)d2);
                    break;
                }
                case ACOS: {
                    f = (float)Math.acos((double)d2);
                    f1 = MathUtils.acos((float)d2);
                    break;
                }
                default: {
                    return;
                }
            }
            MathUtilsTest.dbg(String.format((String)"%.2f, Math: %f, Helper: %f, diff: %f", (Object[])new Object[]{d2, Float.valueOf((float)f), Float.valueOf((float)f1), Float.valueOf((float)Math.abs((float)(f - f1)))}));
        }
    }

    public static void dbg(String str) {
        System.out.println(str);
    }

    private static enum OPER {
        SIN,
        COS,
        ASIN,
        ACOS;
        
    }

}

