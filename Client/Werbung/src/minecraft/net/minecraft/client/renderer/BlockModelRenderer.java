/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.renderer;

import java.util.BitSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;

public class BlockModelRenderer {
    private static final String __OBFID = "CL_00002518";

    public boolean func_178259_a(IBlockAccess p_178259_1_, IBakedModel p_178259_2_, IBlockState p_178259_3_, BlockPos p_178259_4_, WorldRenderer p_178259_5_) {
        Block var6 = p_178259_3_.getBlock();
        var6.setBlockBoundsBasedOnState(p_178259_1_, p_178259_4_);
        return this.renderBlockModel(p_178259_1_, p_178259_2_, p_178259_3_, p_178259_4_, p_178259_5_, true);
    }

    public boolean renderBlockModel(IBlockAccess p_178267_1_, IBakedModel p_178267_2_, IBlockState p_178267_3_, BlockPos p_178267_4_, WorldRenderer p_178267_5_, boolean p_178267_6_) {
        boolean var7 = Minecraft.isAmbientOcclusionEnabled() && p_178267_3_.getBlock().getLightValue() == 0 && p_178267_2_.isGui3d();
        try {
            Block var8 = p_178267_3_.getBlock();
            return var7 ? this.func_178265_a(p_178267_1_, p_178267_2_, var8, p_178267_4_, p_178267_5_, p_178267_6_) : this.func_178258_b(p_178267_1_, p_178267_2_, var8, p_178267_4_, p_178267_5_, p_178267_6_);
        }
        catch (Throwable var11) {
            CrashReport var9 = CrashReport.makeCrashReport(var11, "Tesselating block model");
            CrashReportCategory var10 = var9.makeCategory("Block model being tesselated");
            CrashReportCategory.addBlockInfo(var10, p_178267_4_, p_178267_3_);
            var10.addCrashSection("Using AO", var7);
            throw new ReportedException(var9);
        }
    }

    public boolean func_178265_a(IBlockAccess p_178265_1_, IBakedModel p_178265_2_, Block p_178265_3_, BlockPos p_178265_4_, WorldRenderer p_178265_5_, boolean p_178265_6_) {
        boolean var7 = false;
        p_178265_5_.func_178963_b(983055);
        float[] var8 = new float[EnumFacing.values().length * 2];
        BitSet var9 = new BitSet(3);
        AmbientOcclusionFace var10 = new AmbientOcclusionFace();
        for (EnumFacing var14 : EnumFacing.values()) {
            List var15 = p_178265_2_.func_177551_a(var14);
            if (var15.isEmpty()) continue;
            BlockPos var16 = p_178265_4_.offset(var14);
            if (p_178265_6_ && !p_178265_3_.shouldSideBeRendered(p_178265_1_, var16, var14)) continue;
            this.func_178263_a(p_178265_1_, p_178265_3_, p_178265_4_, p_178265_5_, var15, var8, var9, var10);
            var7 = true;
        }
        List var17 = p_178265_2_.func_177550_a();
        if (var17.size() > 0) {
            this.func_178263_a(p_178265_1_, p_178265_3_, p_178265_4_, p_178265_5_, var17, var8, var9, var10);
            var7 = true;
        }
        return var7;
    }

    public boolean func_178258_b(IBlockAccess p_178258_1_, IBakedModel p_178258_2_, Block p_178258_3_, BlockPos p_178258_4_, WorldRenderer p_178258_5_, boolean p_178258_6_) {
        boolean var7 = false;
        BitSet var8 = new BitSet(3);
        for (EnumFacing var12 : EnumFacing.values()) {
            List var13 = p_178258_2_.func_177551_a(var12);
            if (var13.isEmpty()) continue;
            BlockPos var14 = p_178258_4_.offset(var12);
            if (p_178258_6_ && !p_178258_3_.shouldSideBeRendered(p_178258_1_, var14, var12)) continue;
            int var15 = p_178258_3_.getMixedBrightnessForBlock(p_178258_1_, var14);
            this.func_178260_a(p_178258_1_, p_178258_3_, p_178258_4_, var12, var15, false, p_178258_5_, var13, var8);
            var7 = true;
        }
        List var16 = p_178258_2_.func_177550_a();
        if (var16.size() > 0) {
            this.func_178260_a(p_178258_1_, p_178258_3_, p_178258_4_, null, -1, true, p_178258_5_, var16, var8);
            var7 = true;
        }
        return var7;
    }

    private void func_178263_a(IBlockAccess p_178263_1_, Block p_178263_2_, BlockPos p_178263_3_, WorldRenderer p_178263_4_, List p_178263_5_, float[] p_178263_6_, BitSet p_178263_7_, AmbientOcclusionFace p_178263_8_) {
        double var9 = p_178263_3_.getX();
        double var11 = p_178263_3_.getY();
        double var13 = p_178263_3_.getZ();
        Block.EnumOffsetType var15 = p_178263_2_.getOffsetType();
        if (var15 != Block.EnumOffsetType.NONE) {
            long var16 = MathHelper.func_180186_a(p_178263_3_);
            var9 += ((double)((float)(var16 >> 16 & 15L) / 15.0f) - 0.5) * 0.5;
            var13 += ((double)((float)(var16 >> 24 & 15L) / 15.0f) - 0.5) * 0.5;
            if (var15 == Block.EnumOffsetType.XYZ) {
                var11 += ((double)((float)(var16 >> 20 & 15L) / 15.0f) - 1.0) * 0.2;
            }
        }
        for (BakedQuad var17 : p_178263_5_) {
            this.func_178261_a(p_178263_2_, var17.func_178209_a(), var17.getFace(), p_178263_6_, p_178263_7_);
            p_178263_8_.func_178204_a(p_178263_1_, p_178263_2_, p_178263_3_, var17.getFace(), p_178263_6_, p_178263_7_);
            p_178263_4_.func_178981_a(var17.func_178209_a());
            p_178263_4_.func_178962_a(p_178263_8_.field_178207_c[0], p_178263_8_.field_178207_c[1], p_178263_8_.field_178207_c[2], p_178263_8_.field_178207_c[3]);
            if (var17.func_178212_b()) {
                int var18 = p_178263_2_.colorMultiplier(p_178263_1_, p_178263_3_, var17.func_178211_c());
                if (EntityRenderer.anaglyphEnable) {
                    var18 = TextureUtil.func_177054_c(var18);
                }
                float var19 = (float)(var18 >> 16 & 255) / 255.0f;
                float var20 = (float)(var18 >> 8 & 255) / 255.0f;
                float var21 = (float)(var18 & 255) / 255.0f;
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[0] * var19, p_178263_8_.field_178206_b[0] * var20, p_178263_8_.field_178206_b[0] * var21, 4);
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[1] * var19, p_178263_8_.field_178206_b[1] * var20, p_178263_8_.field_178206_b[1] * var21, 3);
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[2] * var19, p_178263_8_.field_178206_b[2] * var20, p_178263_8_.field_178206_b[2] * var21, 2);
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[3] * var19, p_178263_8_.field_178206_b[3] * var20, p_178263_8_.field_178206_b[3] * var21, 1);
            } else {
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[0], p_178263_8_.field_178206_b[0], p_178263_8_.field_178206_b[0], 4);
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[1], p_178263_8_.field_178206_b[1], p_178263_8_.field_178206_b[1], 3);
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[2], p_178263_8_.field_178206_b[2], p_178263_8_.field_178206_b[2], 2);
                p_178263_4_.func_178978_a(p_178263_8_.field_178206_b[3], p_178263_8_.field_178206_b[3], p_178263_8_.field_178206_b[3], 1);
            }
            p_178263_4_.func_178987_a(var9, var11, var13);
        }
    }

    private void func_178261_a(Block p_178261_1_, int[] p_178261_2_, EnumFacing p_178261_3_, float[] p_178261_4_, BitSet p_178261_5_) {
        float var13;
        float var6 = 32.0f;
        float var7 = 32.0f;
        float var8 = 32.0f;
        float var9 = -32.0f;
        float var10 = -32.0f;
        float var11 = -32.0f;
        for (int var12 = 0; var12 < 4; ++var12) {
            var13 = Float.intBitsToFloat(p_178261_2_[var12 * 7]);
            float var14 = Float.intBitsToFloat(p_178261_2_[var12 * 7 + 1]);
            float var15 = Float.intBitsToFloat(p_178261_2_[var12 * 7 + 2]);
            var6 = Math.min(var6, var13);
            var7 = Math.min(var7, var14);
            var8 = Math.min(var8, var15);
            var9 = Math.max(var9, var13);
            var10 = Math.max(var10, var14);
            var11 = Math.max(var11, var15);
        }
        if (p_178261_4_ != null) {
            p_178261_4_[EnumFacing.WEST.getIndex()] = var6;
            p_178261_4_[EnumFacing.EAST.getIndex()] = var9;
            p_178261_4_[EnumFacing.DOWN.getIndex()] = var7;
            p_178261_4_[EnumFacing.UP.getIndex()] = var10;
            p_178261_4_[EnumFacing.NORTH.getIndex()] = var8;
            p_178261_4_[EnumFacing.SOUTH.getIndex()] = var11;
            p_178261_4_[EnumFacing.WEST.getIndex() + EnumFacing.values().length] = 1.0f - var6;
            p_178261_4_[EnumFacing.EAST.getIndex() + EnumFacing.values().length] = 1.0f - var9;
            p_178261_4_[EnumFacing.DOWN.getIndex() + EnumFacing.values().length] = 1.0f - var7;
            p_178261_4_[EnumFacing.UP.getIndex() + EnumFacing.values().length] = 1.0f - var10;
            p_178261_4_[EnumFacing.NORTH.getIndex() + EnumFacing.values().length] = 1.0f - var8;
            p_178261_4_[EnumFacing.SOUTH.getIndex() + EnumFacing.values().length] = 1.0f - var11;
        }
        float var16 = 1.0E-4f;
        var13 = 0.9999f;
        switch (SwitchEnumFacing.field_178290_a[p_178261_3_.ordinal()]) {
            case 1: {
                p_178261_5_.set(1, var6 >= 1.0E-4f || var8 >= 1.0E-4f || var9 <= 0.9999f || var11 <= 0.9999f);
                p_178261_5_.set(0, (var7 < 1.0E-4f || p_178261_1_.isFullCube()) && var7 == var10);
                break;
            }
            case 2: {
                p_178261_5_.set(1, var6 >= 1.0E-4f || var8 >= 1.0E-4f || var9 <= 0.9999f || var11 <= 0.9999f);
                p_178261_5_.set(0, (var10 > 0.9999f || p_178261_1_.isFullCube()) && var7 == var10);
                break;
            }
            case 3: {
                p_178261_5_.set(1, var6 >= 1.0E-4f || var7 >= 1.0E-4f || var9 <= 0.9999f || var10 <= 0.9999f);
                p_178261_5_.set(0, (var8 < 1.0E-4f || p_178261_1_.isFullCube()) && var8 == var11);
                break;
            }
            case 4: {
                p_178261_5_.set(1, var6 >= 1.0E-4f || var7 >= 1.0E-4f || var9 <= 0.9999f || var10 <= 0.9999f);
                p_178261_5_.set(0, (var11 > 0.9999f || p_178261_1_.isFullCube()) && var8 == var11);
                break;
            }
            case 5: {
                p_178261_5_.set(1, var7 >= 1.0E-4f || var8 >= 1.0E-4f || var10 <= 0.9999f || var11 <= 0.9999f);
                p_178261_5_.set(0, (var6 < 1.0E-4f || p_178261_1_.isFullCube()) && var6 == var9);
                break;
            }
            case 6: {
                p_178261_5_.set(1, var7 >= 1.0E-4f || var8 >= 1.0E-4f || var10 <= 0.9999f || var11 <= 0.9999f);
                p_178261_5_.set(0, (var9 > 0.9999f || p_178261_1_.isFullCube()) && var6 == var9);
            }
        }
    }

    private void func_178260_a(IBlockAccess p_178260_1_, Block p_178260_2_, BlockPos p_178260_3_, EnumFacing p_178260_4_, int p_178260_5_, boolean p_178260_6_, WorldRenderer p_178260_7_, List p_178260_8_, BitSet p_178260_9_) {
        double var10 = p_178260_3_.getX();
        double var12 = p_178260_3_.getY();
        double var14 = p_178260_3_.getZ();
        Block.EnumOffsetType var16 = p_178260_2_.getOffsetType();
        if (var16 != Block.EnumOffsetType.NONE) {
            int var17 = p_178260_3_.getX();
            int var18 = p_178260_3_.getZ();
            long var19 = (long)(var17 * 3129871) ^ (long)var18 * 116129781L;
            var19 = var19 * var19 * 42317861L + var19 * 11L;
            var10 += ((double)((float)(var19 >> 16 & 15L) / 15.0f) - 0.5) * 0.5;
            var14 += ((double)((float)(var19 >> 24 & 15L) / 15.0f) - 0.5) * 0.5;
            if (var16 == Block.EnumOffsetType.XYZ) {
                var12 += ((double)((float)(var19 >> 20 & 15L) / 15.0f) - 1.0) * 0.2;
            }
        }
        for (BakedQuad var24 : p_178260_8_) {
            if (p_178260_6_) {
                this.func_178261_a(p_178260_2_, var24.func_178209_a(), var24.getFace(), null, p_178260_9_);
                p_178260_5_ = p_178260_9_.get(0) ? p_178260_2_.getMixedBrightnessForBlock(p_178260_1_, p_178260_3_.offset(var24.getFace())) : p_178260_2_.getMixedBrightnessForBlock(p_178260_1_, p_178260_3_);
            }
            p_178260_7_.func_178981_a(var24.func_178209_a());
            p_178260_7_.func_178962_a(p_178260_5_, p_178260_5_, p_178260_5_, p_178260_5_);
            if (var24.func_178212_b()) {
                int var25 = p_178260_2_.colorMultiplier(p_178260_1_, p_178260_3_, var24.func_178211_c());
                if (EntityRenderer.anaglyphEnable) {
                    var25 = TextureUtil.func_177054_c(var25);
                }
                float var20 = (float)(var25 >> 16 & 255) / 255.0f;
                float var21 = (float)(var25 >> 8 & 255) / 255.0f;
                float var22 = (float)(var25 & 255) / 255.0f;
                p_178260_7_.func_178978_a(var20, var21, var22, 4);
                p_178260_7_.func_178978_a(var20, var21, var22, 3);
                p_178260_7_.func_178978_a(var20, var21, var22, 2);
                p_178260_7_.func_178978_a(var20, var21, var22, 1);
            }
            p_178260_7_.func_178987_a(var10, var12, var14);
        }
    }

    public void func_178262_a(IBakedModel p_178262_1_, float p_178262_2_, float p_178262_3_, float p_178262_4_, float p_178262_5_) {
        for (EnumFacing var9 : EnumFacing.values()) {
            this.func_178264_a(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, p_178262_1_.func_177551_a(var9));
        }
        this.func_178264_a(p_178262_2_, p_178262_3_, p_178262_4_, p_178262_5_, p_178262_1_.func_177550_a());
    }

    public void func_178266_a(IBakedModel p_178266_1_, IBlockState p_178266_2_, float p_178266_3_, boolean p_178266_4_) {
        Block var5 = p_178266_2_.getBlock();
        var5.setBlockBoundsForItemRender();
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        int var6 = var5.getRenderColor(var5.getStateForEntityRender(p_178266_2_));
        if (EntityRenderer.anaglyphEnable) {
            var6 = TextureUtil.func_177054_c(var6);
        }
        float var7 = (float)(var6 >> 16 & 255) / 255.0f;
        float var8 = (float)(var6 >> 8 & 255) / 255.0f;
        float var9 = (float)(var6 & 255) / 255.0f;
        if (!p_178266_4_) {
            GlStateManager.color(p_178266_3_, p_178266_3_, p_178266_3_, 1.0f);
        }
        this.func_178262_a(p_178266_1_, p_178266_3_, var7, var8, var9);
    }

    private void func_178264_a(float p_178264_1_, float p_178264_2_, float p_178264_3_, float p_178264_4_, List p_178264_5_) {
        Tessellator var6 = Tessellator.getInstance();
        WorldRenderer var7 = var6.getWorldRenderer();
        for (BakedQuad var9 : p_178264_5_) {
            var7.startDrawingQuads();
            var7.setVertexFormat(DefaultVertexFormats.field_176599_b);
            var7.func_178981_a(var9.func_178209_a());
            if (var9.func_178212_b()) {
                var7.func_178990_f(p_178264_2_ * p_178264_1_, p_178264_3_ * p_178264_1_, p_178264_4_ * p_178264_1_);
            } else {
                var7.func_178990_f(p_178264_1_, p_178264_1_, p_178264_1_);
            }
            Vec3i var10 = var9.getFace().getDirectionVec();
            var7.func_178975_e(var10.getX(), var10.getY(), var10.getZ());
            var6.draw();
        }
    }

    class AmbientOcclusionFace {
        private final float[] field_178206_b = new float[4];
        private final int[] field_178207_c = new int[4];
        private static final String __OBFID = "CL_00002515";

        AmbientOcclusionFace() {
        }

        public void func_178204_a(IBlockAccess p_178204_1_, Block p_178204_2_, BlockPos p_178204_3_, EnumFacing p_178204_4_, float[] p_178204_5_, BitSet p_178204_6_) {
            float var27;
            int var31;
            float var26;
            int var30;
            float var25;
            float var28;
            int var32;
            int var29;
            BlockPos var33;
            BlockPos var7 = p_178204_6_.get(0) ? p_178204_3_.offset(p_178204_4_) : p_178204_3_;
            EnumNeighborInfo var8 = EnumNeighborInfo.func_178273_a(p_178204_4_);
            BlockPos var9 = var7.offset(var8.field_178276_g[0]);
            BlockPos var10 = var7.offset(var8.field_178276_g[1]);
            BlockPos var11 = var7.offset(var8.field_178276_g[2]);
            BlockPos var12 = var7.offset(var8.field_178276_g[3]);
            int var13 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var9);
            int var14 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var10);
            int var15 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var11);
            int var16 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var12);
            float var17 = p_178204_1_.getBlockState(var9).getBlock().getAmbientOcclusionLightValue();
            float var18 = p_178204_1_.getBlockState(var10).getBlock().getAmbientOcclusionLightValue();
            float var19 = p_178204_1_.getBlockState(var11).getBlock().getAmbientOcclusionLightValue();
            float var20 = p_178204_1_.getBlockState(var12).getBlock().getAmbientOcclusionLightValue();
            boolean var21 = p_178204_1_.getBlockState(var9.offset(p_178204_4_)).getBlock().isTranslucent();
            boolean var22 = p_178204_1_.getBlockState(var10.offset(p_178204_4_)).getBlock().isTranslucent();
            boolean var23 = p_178204_1_.getBlockState(var11.offset(p_178204_4_)).getBlock().isTranslucent();
            boolean var24 = p_178204_1_.getBlockState(var12.offset(p_178204_4_)).getBlock().isTranslucent();
            if (!var23 && !var21) {
                var25 = var17;
                var29 = var13;
            } else {
                var33 = var9.offset(var8.field_178276_g[2]);
                var25 = p_178204_1_.getBlockState(var33).getBlock().getAmbientOcclusionLightValue();
                var29 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var33);
            }
            if (!var24 && !var21) {
                var26 = var17;
                var30 = var13;
            } else {
                var33 = var9.offset(var8.field_178276_g[3]);
                var26 = p_178204_1_.getBlockState(var33).getBlock().getAmbientOcclusionLightValue();
                var30 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var33);
            }
            if (!var23 && !var22) {
                var27 = var18;
                var31 = var14;
            } else {
                var33 = var10.offset(var8.field_178276_g[2]);
                var27 = p_178204_1_.getBlockState(var33).getBlock().getAmbientOcclusionLightValue();
                var31 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var33);
            }
            if (!var24 && !var22) {
                var28 = var18;
                var32 = var14;
            } else {
                var33 = var10.offset(var8.field_178276_g[3]);
                var28 = p_178204_1_.getBlockState(var33).getBlock().getAmbientOcclusionLightValue();
                var32 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, var33);
            }
            int var60 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, p_178204_3_);
            if (p_178204_6_.get(0) || !p_178204_1_.getBlockState(p_178204_3_.offset(p_178204_4_)).getBlock().isOpaqueCube()) {
                var60 = p_178204_2_.getMixedBrightnessForBlock(p_178204_1_, p_178204_3_.offset(p_178204_4_));
            }
            float var34 = p_178204_6_.get(0) ? p_178204_1_.getBlockState(var7).getBlock().getAmbientOcclusionLightValue() : p_178204_1_.getBlockState(p_178204_3_).getBlock().getAmbientOcclusionLightValue();
            VertexTranslations var35 = VertexTranslations.func_178184_a(p_178204_4_);
            if (p_178204_6_.get(1) && var8.field_178289_i) {
                float var36 = (var20 + var17 + var26 + var34) * 0.25f;
                float var37 = (var19 + var17 + var25 + var34) * 0.25f;
                float var38 = (var19 + var18 + var27 + var34) * 0.25f;
                float var39 = (var20 + var18 + var28 + var34) * 0.25f;
                float var40 = p_178204_5_[var8.field_178286_j[0].field_178229_m] * p_178204_5_[var8.field_178286_j[1].field_178229_m];
                float var41 = p_178204_5_[var8.field_178286_j[2].field_178229_m] * p_178204_5_[var8.field_178286_j[3].field_178229_m];
                float var42 = p_178204_5_[var8.field_178286_j[4].field_178229_m] * p_178204_5_[var8.field_178286_j[5].field_178229_m];
                float var43 = p_178204_5_[var8.field_178286_j[6].field_178229_m] * p_178204_5_[var8.field_178286_j[7].field_178229_m];
                float var44 = p_178204_5_[var8.field_178287_k[0].field_178229_m] * p_178204_5_[var8.field_178287_k[1].field_178229_m];
                float var45 = p_178204_5_[var8.field_178287_k[2].field_178229_m] * p_178204_5_[var8.field_178287_k[3].field_178229_m];
                float var46 = p_178204_5_[var8.field_178287_k[4].field_178229_m] * p_178204_5_[var8.field_178287_k[5].field_178229_m];
                float var47 = p_178204_5_[var8.field_178287_k[6].field_178229_m] * p_178204_5_[var8.field_178287_k[7].field_178229_m];
                float var48 = p_178204_5_[var8.field_178284_l[0].field_178229_m] * p_178204_5_[var8.field_178284_l[1].field_178229_m];
                float var49 = p_178204_5_[var8.field_178284_l[2].field_178229_m] * p_178204_5_[var8.field_178284_l[3].field_178229_m];
                float var50 = p_178204_5_[var8.field_178284_l[4].field_178229_m] * p_178204_5_[var8.field_178284_l[5].field_178229_m];
                float var51 = p_178204_5_[var8.field_178284_l[6].field_178229_m] * p_178204_5_[var8.field_178284_l[7].field_178229_m];
                float var52 = p_178204_5_[var8.field_178285_m[0].field_178229_m] * p_178204_5_[var8.field_178285_m[1].field_178229_m];
                float var53 = p_178204_5_[var8.field_178285_m[2].field_178229_m] * p_178204_5_[var8.field_178285_m[3].field_178229_m];
                float var54 = p_178204_5_[var8.field_178285_m[4].field_178229_m] * p_178204_5_[var8.field_178285_m[5].field_178229_m];
                float var55 = p_178204_5_[var8.field_178285_m[6].field_178229_m] * p_178204_5_[var8.field_178285_m[7].field_178229_m];
                this.field_178206_b[VertexTranslations.access$2((VertexTranslations)var35)] = var36 * var40 + var37 * var41 + var38 * var42 + var39 * var43;
                this.field_178206_b[VertexTranslations.access$3((VertexTranslations)var35)] = var36 * var44 + var37 * var45 + var38 * var46 + var39 * var47;
                this.field_178206_b[VertexTranslations.access$4((VertexTranslations)var35)] = var36 * var48 + var37 * var49 + var38 * var50 + var39 * var51;
                this.field_178206_b[VertexTranslations.access$5((VertexTranslations)var35)] = var36 * var52 + var37 * var53 + var38 * var54 + var39 * var55;
                int var56 = this.getAoBrightness(var16, var13, var30, var60);
                int var57 = this.getAoBrightness(var15, var13, var29, var60);
                int var58 = this.getAoBrightness(var15, var14, var31, var60);
                int var59 = this.getAoBrightness(var16, var14, var32, var60);
                this.field_178207_c[VertexTranslations.access$2((VertexTranslations)var35)] = this.func_178203_a(var56, var57, var58, var59, var40, var41, var42, var43);
                this.field_178207_c[VertexTranslations.access$3((VertexTranslations)var35)] = this.func_178203_a(var56, var57, var58, var59, var44, var45, var46, var47);
                this.field_178207_c[VertexTranslations.access$4((VertexTranslations)var35)] = this.func_178203_a(var56, var57, var58, var59, var48, var49, var50, var51);
                this.field_178207_c[VertexTranslations.access$5((VertexTranslations)var35)] = this.func_178203_a(var56, var57, var58, var59, var52, var53, var54, var55);
            } else {
                float var36 = (var20 + var17 + var26 + var34) * 0.25f;
                float var37 = (var19 + var17 + var25 + var34) * 0.25f;
                float var38 = (var19 + var18 + var27 + var34) * 0.25f;
                float var39 = (var20 + var18 + var28 + var34) * 0.25f;
                this.field_178207_c[VertexTranslations.access$2((VertexTranslations)var35)] = this.getAoBrightness(var16, var13, var30, var60);
                this.field_178207_c[VertexTranslations.access$3((VertexTranslations)var35)] = this.getAoBrightness(var15, var13, var29, var60);
                this.field_178207_c[VertexTranslations.access$4((VertexTranslations)var35)] = this.getAoBrightness(var15, var14, var31, var60);
                this.field_178207_c[VertexTranslations.access$5((VertexTranslations)var35)] = this.getAoBrightness(var16, var14, var32, var60);
                this.field_178206_b[VertexTranslations.access$2((VertexTranslations)var35)] = var36;
                this.field_178206_b[VertexTranslations.access$3((VertexTranslations)var35)] = var37;
                this.field_178206_b[VertexTranslations.access$4((VertexTranslations)var35)] = var38;
                this.field_178206_b[VertexTranslations.access$5((VertexTranslations)var35)] = var39;
            }
        }

        private int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_) {
            if (p_147778_1_ == 0) {
                p_147778_1_ = p_147778_4_;
            }
            if (p_147778_2_ == 0) {
                p_147778_2_ = p_147778_4_;
            }
            if (p_147778_3_ == 0) {
                p_147778_3_ = p_147778_4_;
            }
            return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 16711935;
        }

        private int func_178203_a(int p_178203_1_, int p_178203_2_, int p_178203_3_, int p_178203_4_, float p_178203_5_, float p_178203_6_, float p_178203_7_, float p_178203_8_) {
            int var9 = (int)((float)(p_178203_1_ >> 16 & 255) * p_178203_5_ + (float)(p_178203_2_ >> 16 & 255) * p_178203_6_ + (float)(p_178203_3_ >> 16 & 255) * p_178203_7_ + (float)(p_178203_4_ >> 16 & 255) * p_178203_8_) & 255;
            int var10 = (int)((float)(p_178203_1_ & 255) * p_178203_5_ + (float)(p_178203_2_ & 255) * p_178203_6_ + (float)(p_178203_3_ & 255) * p_178203_7_ + (float)(p_178203_4_ & 255) * p_178203_8_) & 255;
            return var9 << 16 | var10;
        }
    }

    public static enum EnumNeighborInfo {
        DOWN("DOWN", 0, new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.5f, false, new Orientation[0], new Orientation[0], new Orientation[0], new Orientation[0]),
        UP("UP", 1, new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH}, 1.0f, false, new Orientation[0], new Orientation[0], new Orientation[0], new Orientation[0]),
        NORTH("NORTH", 2, new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST}, 0.8f, true, new Orientation[]{Orientation.UP, Orientation.FLIP_WEST, Orientation.UP, Orientation.WEST, Orientation.FLIP_UP, Orientation.WEST, Orientation.FLIP_UP, Orientation.FLIP_WEST}, new Orientation[]{Orientation.UP, Orientation.FLIP_EAST, Orientation.UP, Orientation.EAST, Orientation.FLIP_UP, Orientation.EAST, Orientation.FLIP_UP, Orientation.FLIP_EAST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_EAST, Orientation.DOWN, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.FLIP_EAST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_WEST, Orientation.DOWN, Orientation.WEST, Orientation.FLIP_DOWN, Orientation.WEST, Orientation.FLIP_DOWN, Orientation.FLIP_WEST}),
        SOUTH("SOUTH", 3, new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP}, 0.8f, true, new Orientation[]{Orientation.UP, Orientation.FLIP_WEST, Orientation.FLIP_UP, Orientation.FLIP_WEST, Orientation.FLIP_UP, Orientation.WEST, Orientation.UP, Orientation.WEST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_WEST, Orientation.FLIP_DOWN, Orientation.FLIP_WEST, Orientation.FLIP_DOWN, Orientation.WEST, Orientation.DOWN, Orientation.WEST}, new Orientation[]{Orientation.DOWN, Orientation.FLIP_EAST, Orientation.FLIP_DOWN, Orientation.FLIP_EAST, Orientation.FLIP_DOWN, Orientation.EAST, Orientation.DOWN, Orientation.EAST}, new Orientation[]{Orientation.UP, Orientation.FLIP_EAST, Orientation.FLIP_UP, Orientation.FLIP_EAST, Orientation.FLIP_UP, Orientation.EAST, Orientation.UP, Orientation.EAST}),
        WEST("WEST", 4, new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6f, true, new Orientation[]{Orientation.UP, Orientation.SOUTH, Orientation.UP, Orientation.FLIP_SOUTH, Orientation.FLIP_UP, Orientation.FLIP_SOUTH, Orientation.FLIP_UP, Orientation.SOUTH}, new Orientation[]{Orientation.UP, Orientation.NORTH, Orientation.UP, Orientation.FLIP_NORTH, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.FLIP_UP, Orientation.NORTH}, new Orientation[]{Orientation.DOWN, Orientation.NORTH, Orientation.DOWN, Orientation.FLIP_NORTH, Orientation.FLIP_DOWN, Orientation.FLIP_NORTH, Orientation.FLIP_DOWN, Orientation.NORTH}, new Orientation[]{Orientation.DOWN, Orientation.SOUTH, Orientation.DOWN, Orientation.FLIP_SOUTH, Orientation.FLIP_DOWN, Orientation.FLIP_SOUTH, Orientation.FLIP_DOWN, Orientation.SOUTH}),
        EAST("EAST", 5, new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH}, 0.6f, true, new Orientation[]{Orientation.FLIP_DOWN, Orientation.SOUTH, Orientation.FLIP_DOWN, Orientation.FLIP_SOUTH, Orientation.DOWN, Orientation.FLIP_SOUTH, Orientation.DOWN, Orientation.SOUTH}, new Orientation[]{Orientation.FLIP_DOWN, Orientation.NORTH, Orientation.FLIP_DOWN, Orientation.FLIP_NORTH, Orientation.DOWN, Orientation.FLIP_NORTH, Orientation.DOWN, Orientation.NORTH}, new Orientation[]{Orientation.FLIP_UP, Orientation.NORTH, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.UP, Orientation.FLIP_NORTH, Orientation.UP, Orientation.NORTH}, new Orientation[]{Orientation.FLIP_UP, Orientation.SOUTH, Orientation.FLIP_UP, Orientation.FLIP_SOUTH, Orientation.UP, Orientation.FLIP_SOUTH, Orientation.UP, Orientation.SOUTH});
        
        protected final EnumFacing[] field_178276_g;
        protected final float field_178288_h;
        protected final boolean field_178289_i;
        protected final Orientation[] field_178286_j;
        protected final Orientation[] field_178287_k;
        protected final Orientation[] field_178284_l;
        protected final Orientation[] field_178285_m;
        private static final EnumNeighborInfo[] field_178282_n;
        private static final EnumNeighborInfo[] $VALUES;
        private static final String __OBFID = "CL_00002516";

        static {
            field_178282_n = new EnumNeighborInfo[6];
            $VALUES = new EnumNeighborInfo[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
            EnumNeighborInfo.field_178282_n[EnumFacing.DOWN.getIndex()] = DOWN;
            EnumNeighborInfo.field_178282_n[EnumFacing.UP.getIndex()] = UP;
            EnumNeighborInfo.field_178282_n[EnumFacing.NORTH.getIndex()] = NORTH;
            EnumNeighborInfo.field_178282_n[EnumFacing.SOUTH.getIndex()] = SOUTH;
            EnumNeighborInfo.field_178282_n[EnumFacing.WEST.getIndex()] = WEST;
            EnumNeighborInfo.field_178282_n[EnumFacing.EAST.getIndex()] = EAST;
        }

        private EnumNeighborInfo(String p_i46236_1_, int p_i46236_2_, EnumFacing[] p_i46236_3_, float p_i46236_4_, boolean p_i46236_5_, Orientation[] p_i46236_6_, Orientation[] p_i46236_7_, Orientation[] p_i46236_8_, Orientation[] p_i46236_9_) {
            this.field_178276_g = p_i46236_3_;
            this.field_178288_h = p_i46236_4_;
            this.field_178289_i = p_i46236_5_;
            this.field_178286_j = p_i46236_6_;
            this.field_178287_k = p_i46236_7_;
            this.field_178284_l = p_i46236_8_;
            this.field_178285_m = p_i46236_9_;
        }

        public static EnumNeighborInfo func_178273_a(EnumFacing p_178273_0_) {
            return field_178282_n[p_178273_0_.getIndex()];
        }
    }

    public static enum Orientation {
        DOWN("DOWN", 0, EnumFacing.DOWN, false),
        UP("UP", 1, EnumFacing.UP, false),
        NORTH("NORTH", 2, EnumFacing.NORTH, false),
        SOUTH("SOUTH", 3, EnumFacing.SOUTH, false),
        WEST("WEST", 4, EnumFacing.WEST, false),
        EAST("EAST", 5, EnumFacing.EAST, false),
        FLIP_DOWN("FLIP_DOWN", 6, EnumFacing.DOWN, true),
        FLIP_UP("FLIP_UP", 7, EnumFacing.UP, true),
        FLIP_NORTH("FLIP_NORTH", 8, EnumFacing.NORTH, true),
        FLIP_SOUTH("FLIP_SOUTH", 9, EnumFacing.SOUTH, true),
        FLIP_WEST("FLIP_WEST", 10, EnumFacing.WEST, true),
        FLIP_EAST("FLIP_EAST", 11, EnumFacing.EAST, true);
        
        protected final int field_178229_m;
        private static final Orientation[] $VALUES;
        private static final String __OBFID = "CL_00002513";

        static {
            $VALUES = new Orientation[]{DOWN, UP, NORTH, SOUTH, WEST, EAST, FLIP_DOWN, FLIP_UP, FLIP_NORTH, FLIP_SOUTH, FLIP_WEST, FLIP_EAST};
        }

        private Orientation(String p_i46233_1_, int p_i46233_2_, EnumFacing p_i46233_3_, boolean p_i46233_4_) {
            this.field_178229_m = p_i46233_3_.getIndex() + (p_i46233_4_ ? EnumFacing.values().length : 0);
        }
    }

    static final class SwitchEnumFacing {
        static final int[] field_178290_a = new int[EnumFacing.values().length];
        private static final String __OBFID = "CL_00002517";

        static {
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                SwitchEnumFacing.field_178290_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        SwitchEnumFacing() {
        }
    }

    static enum VertexTranslations {
        DOWN("DOWN", 0, 0, 1, 2, 3),
        UP("UP", 1, 2, 3, 0, 1),
        NORTH("NORTH", 2, 3, 0, 1, 2),
        SOUTH("SOUTH", 3, 0, 1, 2, 3),
        WEST("WEST", 4, 3, 0, 1, 2),
        EAST("EAST", 5, 1, 2, 3, 0);
        
        private final int field_178191_g;
        private final int field_178200_h;
        private final int field_178201_i;
        private final int field_178198_j;
        private static final VertexTranslations[] field_178199_k;
        private static final VertexTranslations[] $VALUES;
        private static final String __OBFID = "CL_00002514";

        static {
            field_178199_k = new VertexTranslations[6];
            $VALUES = new VertexTranslations[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
            VertexTranslations.field_178199_k[EnumFacing.DOWN.getIndex()] = DOWN;
            VertexTranslations.field_178199_k[EnumFacing.UP.getIndex()] = UP;
            VertexTranslations.field_178199_k[EnumFacing.NORTH.getIndex()] = NORTH;
            VertexTranslations.field_178199_k[EnumFacing.SOUTH.getIndex()] = SOUTH;
            VertexTranslations.field_178199_k[EnumFacing.WEST.getIndex()] = WEST;
            VertexTranslations.field_178199_k[EnumFacing.EAST.getIndex()] = EAST;
        }

        private VertexTranslations(String p_i46234_1_, int p_i46234_2_, int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_) {
            this.field_178191_g = p_i46234_3_;
            this.field_178200_h = p_i46234_4_;
            this.field_178201_i = p_i46234_5_;
            this.field_178198_j = p_i46234_6_;
        }

        public static VertexTranslations func_178184_a(EnumFacing p_178184_0_) {
            return field_178199_k[p_178184_0_.getIndex()];
        }

        static /* synthetic */ int access$2(VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178191_g;
        }

        static /* synthetic */ int access$3(VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178200_h;
        }

        static /* synthetic */ int access$4(VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178201_i;
        }

        static /* synthetic */ int access$5(VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178198_j;
        }
    }

}

