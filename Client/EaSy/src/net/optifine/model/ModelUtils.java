/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package net.optifine.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;

public class ModelUtils {
    public static void dbgModel(IBakedModel model) {
        if (model != null) {
            Config.dbg("Model: " + model + ", ao: " + model.isAmbientOcclusion() + ", gui3d: " + model.isGui3d() + ", builtIn: " + model.isBuiltInRenderer() + ", particle: " + model.getParticleTexture());
            EnumFacing[] aenumfacing = EnumFacing.VALUES;
            for (int i = 0; i < aenumfacing.length; ++i) {
                EnumFacing enumfacing = aenumfacing[i];
                List<BakedQuad> list = model.getFaceQuads(enumfacing);
                ModelUtils.dbgQuads(enumfacing.getName(), list, "  ");
            }
            List<BakedQuad> list1 = model.getGeneralQuads();
            ModelUtils.dbgQuads("General", list1, "  ");
        }
    }

    private static void dbgQuads(String name, List quads, String prefix) {
        for (Object o : quads) {
            BakedQuad bakedquad = (BakedQuad)o;
            ModelUtils.dbgQuad(name, bakedquad, prefix);
        }
    }

    public static void dbgQuad(String name, BakedQuad quad, String prefix) {
        Config.dbg(prefix + "Quad: " + quad.getClass().getName() + ", type: " + name + ", face: " + quad.getFace() + ", tint: " + quad.getTintIndex() + ", sprite: " + quad.getSprite());
        ModelUtils.dbgVertexData(quad.getVertexData(), "  " + prefix);
    }

    public static void dbgVertexData(int[] vd, String prefix) {
        int i = vd.length / 4;
        Config.dbg(prefix + "Length: " + vd.length + ", step: " + i);
        for (int j = 0; j < 4; ++j) {
            int k = j * i;
            float f = Float.intBitsToFloat((int)vd[k + 0]);
            float f1 = Float.intBitsToFloat((int)vd[k + 1]);
            float f2 = Float.intBitsToFloat((int)vd[k + 2]);
            int l = vd[k + 3];
            float f3 = Float.intBitsToFloat((int)vd[k + 4]);
            float f4 = Float.intBitsToFloat((int)vd[k + 5]);
            Config.dbg(prefix + j + " xyz: " + f + "," + f1 + "," + f2 + " col: " + l + " u,v: " + f3 + "," + f4);
        }
    }

    public static IBakedModel duplicateModel(IBakedModel model) {
        List list = ModelUtils.duplicateQuadList(model.getGeneralQuads());
        EnumFacing[] aenumfacing = EnumFacing.VALUES;
        ArrayList list1 = new ArrayList();
        for (int i = 0; i < aenumfacing.length; ++i) {
            EnumFacing enumfacing = aenumfacing[i];
            List<BakedQuad> list2 = model.getFaceQuads(enumfacing);
            List list3 = ModelUtils.duplicateQuadList(list2);
            list1.add((Object)list3);
        }
        SimpleBakedModel simplebakedmodel = new SimpleBakedModel((List<BakedQuad>)list, (List<List<BakedQuad>>)list1, model.isAmbientOcclusion(), model.isGui3d(), model.getParticleTexture(), model.getItemCameraTransforms());
        return simplebakedmodel;
    }

    public static List duplicateQuadList(List list) {
        ArrayList list2 = new ArrayList();
        for (Object o : list) {
            BakedQuad bakedquad = (BakedQuad)o;
            BakedQuad bakedquad1 = ModelUtils.duplicateQuad(bakedquad);
            list2.add((Object)bakedquad1);
        }
        return list2;
    }

    public static BakedQuad duplicateQuad(BakedQuad quad) {
        BakedQuad bakedquad = new BakedQuad((int[])quad.getVertexData().clone(), quad.getTintIndex(), quad.getFace(), quad.getSprite());
        return bakedquad;
    }
}

