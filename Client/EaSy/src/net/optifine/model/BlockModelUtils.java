/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  org.lwjgl.util.vector.Vector3f
 */
package net.optifine.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.src.Config;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.optifine.model.ModelUtils;
import org.lwjgl.util.vector.Vector3f;

public class BlockModelUtils {
    private static final float VERTEX_COORD_ACCURACY = 1.0E-6f;

    public static IBakedModel makeModelCube(String spriteName, int tintIndex) {
        TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(spriteName);
        return BlockModelUtils.makeModelCube(textureatlassprite, tintIndex);
    }

    public static IBakedModel makeModelCube(TextureAtlasSprite sprite, int tintIndex) {
        ArrayList list = new ArrayList();
        EnumFacing[] aenumfacing = EnumFacing.VALUES;
        ArrayList list1 = new ArrayList();
        for (int i = 0; i < aenumfacing.length; ++i) {
            EnumFacing enumfacing = aenumfacing[i];
            ArrayList list2 = new ArrayList();
            list2.add((Object)BlockModelUtils.makeBakedQuad(enumfacing, sprite, tintIndex));
            list1.add((Object)list2);
        }
        SimpleBakedModel ibakedmodel = new SimpleBakedModel((List<BakedQuad>)list, (List<List<BakedQuad>>)list1, true, true, sprite, ItemCameraTransforms.DEFAULT);
        return ibakedmodel;
    }

    public static IBakedModel joinModelsCube(IBakedModel modelBase, IBakedModel modelAdd) {
        ArrayList list = new ArrayList();
        list.addAll(modelBase.getGeneralQuads());
        list.addAll(modelAdd.getGeneralQuads());
        EnumFacing[] aenumfacing = EnumFacing.VALUES;
        ArrayList list1 = new ArrayList();
        for (int i = 0; i < aenumfacing.length; ++i) {
            EnumFacing enumfacing = aenumfacing[i];
            ArrayList list2 = new ArrayList();
            list2.addAll(modelBase.getFaceQuads(enumfacing));
            list2.addAll(modelAdd.getFaceQuads(enumfacing));
            list1.add((Object)list2);
        }
        boolean flag = modelBase.isAmbientOcclusion();
        boolean flag1 = modelBase.isBuiltInRenderer();
        TextureAtlasSprite textureatlassprite = modelBase.getParticleTexture();
        ItemCameraTransforms itemcameratransforms = modelBase.getItemCameraTransforms();
        SimpleBakedModel ibakedmodel = new SimpleBakedModel((List<BakedQuad>)list, (List<List<BakedQuad>>)list1, flag, flag1, textureatlassprite, itemcameratransforms);
        return ibakedmodel;
    }

    public static BakedQuad makeBakedQuad(EnumFacing facing, TextureAtlasSprite sprite, int tintIndex) {
        Vector3f vector3f = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f vector3f1 = new Vector3f(16.0f, 16.0f, 16.0f);
        BlockFaceUV blockfaceuv = new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0);
        BlockPartFace blockpartface = new BlockPartFace(facing, tintIndex, "#" + facing.getName(), blockfaceuv);
        ModelRotation modelrotation = ModelRotation.X0_Y0;
        BlockPartRotation blockpartrotation = null;
        boolean flag = false;
        boolean flag1 = true;
        FaceBakery facebakery = new FaceBakery();
        BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, sprite, facing, modelrotation, blockpartrotation, flag, flag1);
        return bakedquad;
    }

    public static IBakedModel makeModel(String modelName, String spriteOldName, String spriteNewName) {
        TextureMap texturemap = Config.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(spriteOldName);
        TextureAtlasSprite textureatlassprite1 = texturemap.getSpriteSafe(spriteNewName);
        return BlockModelUtils.makeModel(modelName, textureatlassprite, textureatlassprite1);
    }

    public static IBakedModel makeModel(String modelName, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
        if (spriteOld != null && spriteNew != null) {
            ModelManager modelmanager = Config.getModelManager();
            if (modelmanager == null) {
                return null;
            }
            ModelResourceLocation modelresourcelocation = new ModelResourceLocation(modelName, "normal");
            IBakedModel ibakedmodel = modelmanager.getModel(modelresourcelocation);
            if (ibakedmodel != null && ibakedmodel != modelmanager.getMissingModel()) {
                IBakedModel ibakedmodel1 = ModelUtils.duplicateModel(ibakedmodel);
                EnumFacing[] aenumfacing = EnumFacing.VALUES;
                for (int i = 0; i < aenumfacing.length; ++i) {
                    EnumFacing enumfacing = aenumfacing[i];
                    List<BakedQuad> list = ibakedmodel1.getFaceQuads(enumfacing);
                    BlockModelUtils.replaceTexture(list, spriteOld, spriteNew);
                }
                List<BakedQuad> list1 = ibakedmodel1.getGeneralQuads();
                BlockModelUtils.replaceTexture(list1, spriteOld, spriteNew);
                return ibakedmodel1;
            }
            return null;
        }
        return null;
    }

    private static void replaceTexture(List<BakedQuad> quads, TextureAtlasSprite spriteOld, TextureAtlasSprite spriteNew) {
        ArrayList list = new ArrayList();
        for (BakedQuad bakedquad : quads) {
            if (bakedquad.getSprite() == spriteOld) {
                bakedquad = new BreakingFour(bakedquad, spriteNew);
            }
            list.add((Object)bakedquad);
        }
        quads.clear();
        quads.addAll((Collection)list);
    }

    public static void snapVertexPosition(Vector3f pos) {
        pos.setX(BlockModelUtils.snapVertexCoord(pos.getX()));
        pos.setY(BlockModelUtils.snapVertexCoord(pos.getY()));
        pos.setZ(BlockModelUtils.snapVertexCoord(pos.getZ()));
    }

    private static float snapVertexCoord(float x) {
        if (x > -1.0E-6f && x < 1.0E-6f) {
            return 0.0f;
        }
        return x > 0.999999f && x < 1.000001f ? 1.0f : x;
    }

    public static AxisAlignedBB getOffsetBoundingBox(AxisAlignedBB aabb, Block.EnumOffsetType offsetType, BlockPos pos) {
        int i = pos.getX();
        int j = pos.getZ();
        long k = (long)(i * 3129871) ^ (long)j * 116129781L;
        k = k * k * 42317861L + k * 11L;
        double d0 = ((double)((float)(k >> 16 & 15L) / 15.0f) - 0.5) * 0.5;
        double d1 = ((double)((float)(k >> 24 & 15L) / 15.0f) - 0.5) * 0.5;
        double d2 = 0.0;
        if (offsetType == Block.EnumOffsetType.XYZ) {
            d2 = ((double)((float)(k >> 20 & 15L) / 15.0f) - 1.0) * 0.2;
        }
        return aabb.offset(d0, d2, d1);
    }
}

