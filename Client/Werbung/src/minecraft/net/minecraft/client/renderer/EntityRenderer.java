/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonSyntaxException
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.ContextCapabilities
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.util.glu.Project
 */
package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class EntityRenderer
implements IResourceManagerReloadListener {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
    public static boolean anaglyphEnable;
    public static int anaglyphField;
    private Minecraft mc;
    private final IResourceManager resourceManager;
    private Random random = new Random();
    private float farPlaneDistance;
    public final ItemRenderer itemRenderer;
    private final MapItemRenderer theMapItemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis = new MouseFilter();
    private MouseFilter mouseFilterYAxis = new MouseFilter();
    private float thirdPersonDistance = 4.0f;
    private float thirdPersonDistanceTemp = 4.0f;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float bossColorModifier;
    private float bossColorModifierPrev;
    private boolean cloudFog;
    private boolean field_175074_C = true;
    private boolean field_175073_D = true;
    private long prevFrameTime = Minecraft.getSystemTime();
    private long renderEndNanoTime;
    private final DynamicTexture lightmapTexture;
    private final int[] lightmapColors;
    private final ResourceLocation locationLightMap;
    private boolean lightmapUpdateNeeded;
    private float torchFlickerX;
    private float field_175075_L;
    private int rainSoundCounter;
    private float[] field_175076_N = new float[1024];
    private float[] field_175077_O = new float[1024];
    private FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
    private float field_175080_Q;
    private float field_175082_R;
    private float field_175081_S;
    private float fogColor2;
    private float fogColor1;
    private int field_175079_V = 0;
    private boolean field_175078_W = false;
    private double cameraZoom = 1.0;
    private double cameraYaw;
    private double cameraPitch;
    private ShaderGroup theShaderGroup;
    private static final ResourceLocation[] shaderResourceLocations;
    public static final int shaderCount;
    private int shaderIndex = shaderCount;
    private boolean field_175083_ad = false;
    private int field_175084_ae = 0;
    private static final String __OBFID = "CL_00000947";

    static {
        shaderResourceLocations = new ResourceLocation[]{new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json")};
        shaderCount = shaderResourceLocations.length;
    }

    public EntityRenderer(Minecraft mcIn, IResourceManager p_i45076_2_) {
        this.mc = mcIn;
        this.resourceManager = p_i45076_2_;
        this.itemRenderer = mcIn.getItemRenderer();
        this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
        this.lightmapTexture = new DynamicTexture(16, 16);
        this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
        for (int var3 = 0; var3 < 32; ++var3) {
            for (int var4 = 0; var4 < 32; ++var4) {
                float var5 = var4 - 16;
                float var6 = var3 - 16;
                float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
                this.field_175076_N[var3 << 5 | var4] = -var6 / var7;
                this.field_175077_O[var3 << 5 | var4] = var5 / var7;
            }
        }
    }

    public boolean isShaderActive() {
        return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
    }

    public void func_175071_c() {
        this.field_175083_ad = !this.field_175083_ad;
    }

    public void func_175066_a(Entity p_175066_1_) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.theShaderGroup = null;
            if (p_175066_1_ instanceof EntityCreeper) {
                this.func_175069_a(new ResourceLocation("shaders/post/creeper.json"));
            } else if (p_175066_1_ instanceof EntitySpider) {
                this.func_175069_a(new ResourceLocation("shaders/post/spider.json"));
            } else if (p_175066_1_ instanceof EntityEnderman) {
                this.func_175069_a(new ResourceLocation("shaders/post/invert.json"));
            }
        }
    }

    public void activateNextShader() {
        if (OpenGlHelper.shadersSupported && this.mc.func_175606_aa() instanceof EntityPlayer) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
            if (this.shaderIndex != shaderCount) {
                this.func_175069_a(shaderResourceLocations[this.shaderIndex]);
            } else {
                this.theShaderGroup = null;
            }
        }
    }

    private void func_175069_a(ResourceLocation p_175069_1_) {
        try {
            this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), p_175069_1_);
            this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.field_175083_ad = true;
        }
        catch (IOException var3) {
            logger.warn("Failed to load shader: " + p_175069_1_, (Throwable)var3);
            this.shaderIndex = shaderCount;
            this.field_175083_ad = false;
        }
        catch (JsonSyntaxException var4) {
            logger.warn("Failed to load shader: " + p_175069_1_, (Throwable)var4);
            this.shaderIndex = shaderCount;
            this.field_175083_ad = false;
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager p_110549_1_) {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        if (this.shaderIndex != shaderCount) {
            this.func_175069_a(shaderResourceLocations[this.shaderIndex]);
        } else {
            this.func_175066_a(this.mc.func_175606_aa());
        }
    }

    public void updateRenderer() {
        float var1;
        float var2;
        if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
        }
        this.updateFovModifierHand();
        this.updateTorchFlicker();
        this.fogColor2 = this.fogColor1;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        if (this.mc.gameSettings.smoothCamera) {
            var1 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            var2 = var1 * var1 * var1 * 8.0f;
            this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05f * var2);
            this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05f * var2);
            this.smoothCamPartialTicks = 0.0f;
            this.smoothCamYaw = 0.0f;
            this.smoothCamPitch = 0.0f;
        } else {
            this.smoothCamFilterX = 0.0f;
            this.smoothCamFilterY = 0.0f;
            this.mouseFilterXAxis.func_180179_a();
            this.mouseFilterYAxis.func_180179_a();
        }
        if (this.mc.func_175606_aa() == null) {
            this.mc.func_175607_a(this.mc.thePlayer);
        }
        var1 = this.mc.theWorld.getLightBrightness(new BlockPos(this.mc.func_175606_aa()));
        var2 = (float)this.mc.gameSettings.renderDistanceChunks / 32.0f;
        float var3 = var1 * (1.0f - var2) + var2;
        this.fogColor1 += (var3 - this.fogColor1) * 0.1f;
        ++this.rendererUpdateCount;
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();
        this.bossColorModifierPrev = this.bossColorModifier;
        if (BossStatus.hasColorModifier) {
            this.bossColorModifier += 0.05f;
            if (this.bossColorModifier > 1.0f) {
                this.bossColorModifier = 1.0f;
            }
            BossStatus.hasColorModifier = false;
        } else if (this.bossColorModifier > 0.0f) {
            this.bossColorModifier -= 0.0125f;
        }
    }

    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }

    public void updateShaderGroupSize(int p_147704_1_, int p_147704_2_) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.createBindFramebuffers(p_147704_1_, p_147704_2_);
            }
            this.mc.renderGlobal.checkOcclusionQueryResult(p_147704_1_, p_147704_2_);
        }
    }

    public void getMouseOver(float p_78473_1_) {
        Entity var2 = this.mc.func_175606_aa();
        if (var2 != null && this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;
            double var3 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = var2.func_174822_a(var3, p_78473_1_);
            double var5 = var3;
            Vec3 var7 = var2.func_174824_e(p_78473_1_);
            if (this.mc.playerController.extendedReach()) {
                var3 = 6.0;
                var5 = 6.0;
            } else {
                if (var3 > 3.0) {
                    var5 = 3.0;
                }
                var3 = var5;
            }
            if (this.mc.objectMouseOver != null) {
                var5 = this.mc.objectMouseOver.hitVec.distanceTo(var7);
            }
            Vec3 var8 = var2.getLook(p_78473_1_);
            Vec3 var9 = var7.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
            this.pointedEntity = null;
            Vec3 var10 = null;
            float var11 = 1.0f;
            List var12 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3).expand(var11, var11, var11));
            double var13 = var5;
            for (int var15 = 0; var15 < var12.size(); ++var15) {
                double var20;
                Entity var16 = (Entity)var12.get(var15);
                if (!var16.canBeCollidedWith()) continue;
                float var17 = var16.getCollisionBorderSize();
                AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
                MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);
                if (var18.isVecInside(var7)) {
                    if (!(0.0 < var13) && var13 != 0.0) continue;
                    this.pointedEntity = var16;
                    var10 = var19 == null ? var7 : var19.hitVec;
                    var13 = 0.0;
                    continue;
                }
                if (var19 == null || !((var20 = var7.distanceTo(var19.hitVec)) < var13) && var13 != 0.0) continue;
                if (var16 == var2.ridingEntity) {
                    if (var13 != 0.0) continue;
                    this.pointedEntity = var16;
                    var10 = var19.hitVec;
                    continue;
                }
                this.pointedEntity = var16;
                var10 = var19.hitVec;
                var13 = var20;
            }
            if (this.pointedEntity != null && (var13 < var5 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var10);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }

    private void updateFovModifierHand() {
        float var1 = 1.0f;
        if (this.mc.func_175606_aa() instanceof AbstractClientPlayer) {
            AbstractClientPlayer var2 = (AbstractClientPlayer)this.mc.func_175606_aa();
            var1 = var2.func_175156_o();
        }
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (var1 - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }

    private float getFOVModifier(float p_78481_1_, boolean p_78481_2_) {
        Block var6;
        if (this.field_175078_W) {
            return 90.0f;
        }
        Entity var3 = this.mc.func_175606_aa();
        float var4 = 70.0f;
        if (p_78481_2_) {
            var4 = this.mc.gameSettings.fovSetting;
            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * p_78481_1_;
        }
        if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getHealth() <= 0.0f) {
            float var5 = (float)((EntityLivingBase)var3).deathTime + p_78481_1_;
            var4 /= (1.0f - 500.0f / (var5 + 500.0f)) * 2.0f + 1.0f;
        }
        if ((var6 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, p_78481_1_)).getMaterial() == Material.water) {
            var4 = var4 * 60.0f / 70.0f;
        }
        return var4;
    }

    private void hurtCameraEffect(float p_78482_1_) {
        if (this.mc.func_175606_aa() instanceof EntityLivingBase) {
            float var4;
            EntityLivingBase var2 = (EntityLivingBase)this.mc.func_175606_aa();
            float var3 = (float)var2.hurtTime - p_78482_1_;
            if (var2.getHealth() <= 0.0f) {
                var4 = (float)var2.deathTime + p_78482_1_;
                GlStateManager.rotate(40.0f - 8000.0f / (var4 + 200.0f), 0.0f, 0.0f, 1.0f);
            }
            if (var3 < 0.0f) {
                return;
            }
            var3 /= (float)var2.maxHurtTime;
            var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927f);
            var4 = var2.attackedAtYaw;
            GlStateManager.rotate(-var4, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-var3 * 14.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(var4, 0.0f, 1.0f, 0.0f);
        }
    }

    private void setupViewBobbing(float p_78475_1_) {
        if (this.mc.func_175606_aa() instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)this.mc.func_175606_aa();
            float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            float var4 = -(var2.distanceWalkedModified + var3 * p_78475_1_);
            float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * p_78475_1_;
            float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * p_78475_1_;
            GlStateManager.translate(MathHelper.sin(var4 * 3.1415927f) * var5 * 0.5f, -Math.abs(MathHelper.cos(var4 * 3.1415927f) * var5), 0.0f);
            GlStateManager.rotate(MathHelper.sin(var4 * 3.1415927f) * var5 * 3.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * 3.1415927f - 0.2f) * var5) * 5.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(var6, 1.0f, 0.0f, 0.0f);
        }
    }

    private void orientCamera(float p_78467_1_) {
        Entity var2 = this.mc.func_175606_aa();
        float var3 = var2.getEyeHeight();
        double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)p_78467_1_;
        double var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)p_78467_1_ + (double)var3;
        double var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)p_78467_1_;
        if (var2 instanceof EntityLivingBase && ((EntityLivingBase)var2).isPlayerSleeping()) {
            var3 = (float)((double)var3 + 1.0);
            GlStateManager.translate(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                BlockPos var27 = new BlockPos(var2);
                IBlockState var11 = this.mc.theWorld.getBlockState(var27);
                Block var29 = var11.getBlock();
                if (var29 == Blocks.bed) {
                    int var30 = ((EnumFacing)((Object)var11.getValue(BlockBed.AGE))).getHorizontalIndex();
                    GlStateManager.rotate(var30 * 90, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0f, 0.0f, -1.0f, 0.0f);
                GlStateManager.rotate(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, -1.0f, 0.0f, 0.0f);
            }
        } else if (this.mc.gameSettings.thirdPersonView > 0) {
            double var10 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * p_78467_1_;
            if (this.mc.gameSettings.debugCamEnable) {
                GlStateManager.translate(0.0f, 0.0f, (float)(-var10));
            } else {
                float var12 = var2.rotationYaw;
                float var13 = var2.rotationPitch;
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    var13 += 180.0f;
                }
                double var14 = (double)(-MathHelper.sin(var12 / 180.0f * 3.1415927f) * MathHelper.cos(var13 / 180.0f * 3.1415927f)) * var10;
                double var16 = (double)(MathHelper.cos(var12 / 180.0f * 3.1415927f) * MathHelper.cos(var13 / 180.0f * 3.1415927f)) * var10;
                double var18 = (double)(-MathHelper.sin(var13 / 180.0f * 3.1415927f)) * var10;
                for (int var20 = 0; var20 < 8; ++var20) {
                    double var25;
                    MovingObjectPosition var24;
                    float var21 = (var20 & 1) * 2 - 1;
                    float var22 = (var20 >> 1 & 1) * 2 - 1;
                    float var23 = (var20 >> 2 & 1) * 2 - 1;
                    if ((var24 = this.mc.theWorld.rayTraceBlocks(new Vec3(var4 + (double)(var21 *= 0.1f), var6 + (double)(var22 *= 0.1f), var8 + (double)(var23 *= 0.1f)), new Vec3(var4 - var14 + (double)var21 + (double)var23, var6 - var18 + (double)var22, var8 - var16 + (double)var23))) == null || !((var25 = var24.hitVec.distanceTo(new Vec3(var4, var6, var8))) < var10)) continue;
                    var10 = var25;
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.rotate(var2.rotationPitch - var13, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(var2.rotationYaw - var12, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, (float)(-var10));
                GlStateManager.rotate(var12 - var2.rotationYaw, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(var13 - var2.rotationPitch, 1.0f, 0.0f, 0.0f);
            }
        } else {
            GlStateManager.translate(0.0f, 0.0f, -0.1f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            GlStateManager.rotate(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, 1.0f, 0.0f, 0.0f);
            if (var2 instanceof EntityAnimal) {
                EntityAnimal var28 = (EntityAnimal)var2;
                GlStateManager.rotate(var28.prevRotationYawHead + (var28.rotationYawHead - var28.prevRotationYawHead) * p_78467_1_ + 180.0f, 0.0f, 1.0f, 0.0f);
            } else {
                GlStateManager.rotate(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        GlStateManager.translate(0.0f, -var3, 0.0f);
        var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)p_78467_1_;
        var6 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)p_78467_1_ + (double)var3;
        var8 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)p_78467_1_;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var6, var8, p_78467_1_);
    }

    private void setupCameraTransform(float p_78479_1_, int p_78479_2_) {
        float var4;
        this.farPlaneDistance = this.mc.gameSettings.renderDistanceChunks * 16;
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        float var3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((float)(-(p_78479_2_ * 2 - 1)) * var3, 0.0f, 0.0f);
        }
        if (this.cameraZoom != 1.0) {
            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0);
        }
        Project.gluPerspective((float)this.getFOVModifier(p_78479_1_, true), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * MathHelper.field_180189_a));
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((float)(p_78479_2_ * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(p_78479_1_);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(p_78479_1_);
        }
        if ((var4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * p_78479_1_) > 0.0f) {
            int var5 = 20;
            if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
                var5 = 7;
            }
            float var6 = 5.0f / (var4 * var4 + 5.0f) - var4 * 0.04f;
            var6 *= var6;
            GlStateManager.rotate(((float)this.rendererUpdateCount + p_78479_1_) * (float)var5, 0.0f, 1.0f, 1.0f);
            GlStateManager.scale(1.0f / var6, 1.0f, 1.0f);
            GlStateManager.rotate(-((float)this.rendererUpdateCount + p_78479_1_) * (float)var5, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(p_78479_1_);
        if (this.field_175078_W) {
            switch (this.field_175079_V) {
                case 0: {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 1: {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 2: {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case 3: {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    break;
                }
                case 4: {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
            }
        }
    }

    private void renderHand(float p_78476_1_, int p_78476_2_) {
        if (!this.field_175078_W) {
            boolean var4;
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            float var3 = 0.07f;
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate((float)(-(p_78476_2_ * 2 - 1)) * var3, 0.0f, 0.0f);
            }
            Project.gluPerspective((float)this.getFOVModifier(p_78476_1_, false), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * 2.0f));
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            if (this.mc.gameSettings.anaglyph) {
                GlStateManager.translate((float)(p_78476_2_ * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            GlStateManager.pushMatrix();
            this.hurtCameraEffect(p_78476_1_);
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(p_78476_1_);
            }
            boolean bl = var4 = this.mc.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.func_175606_aa()).isPlayerSleeping();
            if (!(this.mc.gameSettings.thirdPersonView != 0 || var4 || this.mc.gameSettings.hideGUI || this.mc.playerController.enableEverythingIsScrewedUpMode())) {
                this.func_180436_i();
                this.itemRenderer.renderItemInFirstPerson(p_78476_1_);
                this.func_175072_h();
            }
            GlStateManager.popMatrix();
            if (this.mc.gameSettings.thirdPersonView == 0 && !var4) {
                this.itemRenderer.renderOverlays(p_78476_1_);
                this.hurtCameraEffect(p_78476_1_);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(p_78476_1_);
            }
        }
    }

    public void func_175072_h() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.func_179090_x();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public void func_180436_i() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float var1 = 0.00390625f;
        GlStateManager.scale(var1, var1, var1);
        GlStateManager.translate(8.0f, 8.0f, 8.0f);
        GlStateManager.matrixMode(5888);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10496);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10496);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179098_w();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    private void updateTorchFlicker() {
        this.field_175075_L = (float)((double)this.field_175075_L + (Math.random() - Math.random()) * Math.random() * Math.random());
        this.field_175075_L = (float)((double)this.field_175075_L * 0.9);
        this.torchFlickerX += (this.field_175075_L - this.torchFlickerX) * 1.0f;
        this.lightmapUpdateNeeded = true;
    }

    private void updateLightmap(float p_78472_1_) {
        if (this.lightmapUpdateNeeded) {
            this.mc.mcProfiler.startSection("lightTex");
            WorldClient var2 = this.mc.theWorld;
            if (var2 != null) {
                for (int var3 = 0; var3 < 256; ++var3) {
                    float var16;
                    float var17;
                    float var4 = var2.getSunBrightness(1.0f) * 0.95f + 0.05f;
                    float var5 = var2.provider.getLightBrightnessTable()[var3 / 16] * var4;
                    float var6 = var2.provider.getLightBrightnessTable()[var3 % 16] * (this.torchFlickerX * 0.1f + 1.5f);
                    if (var2.func_175658_ac() > 0) {
                        var5 = var2.provider.getLightBrightnessTable()[var3 / 16];
                    }
                    float var7 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                    float var8 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                    float var11 = var6 * ((var6 * 0.6f + 0.4f) * 0.6f + 0.4f);
                    float var12 = var6 * (var6 * var6 * 0.6f + 0.4f);
                    float var13 = var7 + var6;
                    float var14 = var8 + var11;
                    float var15 = var5 + var12;
                    var13 = var13 * 0.96f + 0.03f;
                    var14 = var14 * 0.96f + 0.03f;
                    var15 = var15 * 0.96f + 0.03f;
                    if (this.bossColorModifier > 0.0f) {
                        var16 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * p_78472_1_;
                        var13 = var13 * (1.0f - var16) + var13 * 0.7f * var16;
                        var14 = var14 * (1.0f - var16) + var14 * 0.6f * var16;
                        var15 = var15 * (1.0f - var16) + var15 * 0.6f * var16;
                    }
                    if (var2.provider.getDimensionId() == 1) {
                        var13 = 0.22f + var6 * 0.75f;
                        var14 = 0.28f + var11 * 0.75f;
                        var15 = 0.25f + var12 * 0.75f;
                    }
                    if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                        var16 = this.func_180438_a(this.mc.thePlayer, p_78472_1_);
                        var17 = 1.0f / var13;
                        if (var17 > 1.0f / var14) {
                            var17 = 1.0f / var14;
                        }
                        if (var17 > 1.0f / var15) {
                            var17 = 1.0f / var15;
                        }
                        var13 = var13 * (1.0f - var16) + var13 * var17 * var16;
                        var14 = var14 * (1.0f - var16) + var14 * var17 * var16;
                        var15 = var15 * (1.0f - var16) + var15 * var17 * var16;
                    }
                    if (var13 > 1.0f) {
                        var13 = 1.0f;
                    }
                    if (var14 > 1.0f) {
                        var14 = 1.0f;
                    }
                    if (var15 > 1.0f) {
                        var15 = 1.0f;
                    }
                    var16 = this.mc.gameSettings.gammaSetting;
                    var17 = 1.0f - var13;
                    float var18 = 1.0f - var14;
                    float var19 = 1.0f - var15;
                    var17 = 1.0f - var17 * var17 * var17 * var17;
                    var18 = 1.0f - var18 * var18 * var18 * var18;
                    var19 = 1.0f - var19 * var19 * var19 * var19;
                    var13 = var13 * (1.0f - var16) + var17 * var16;
                    var14 = var14 * (1.0f - var16) + var18 * var16;
                    var15 = var15 * (1.0f - var16) + var19 * var16;
                    var13 = var13 * 0.96f + 0.03f;
                    var14 = var14 * 0.96f + 0.03f;
                    var15 = var15 * 0.96f + 0.03f;
                    if (var13 > 1.0f) {
                        var13 = 1.0f;
                    }
                    if (var14 > 1.0f) {
                        var14 = 1.0f;
                    }
                    if (var15 > 1.0f) {
                        var15 = 1.0f;
                    }
                    if (var13 < 0.0f) {
                        var13 = 0.0f;
                    }
                    if (var14 < 0.0f) {
                        var14 = 0.0f;
                    }
                    if (var15 < 0.0f) {
                        var15 = 0.0f;
                    }
                    int var20 = 255;
                    int var21 = (int)(var13 * 255.0f);
                    int var22 = (int)(var14 * 255.0f);
                    int var23 = (int)(var15 * 255.0f);
                    this.lightmapColors[var3] = var20 << 24 | var21 << 16 | var22 << 8 | var23;
                }
                this.lightmapTexture.updateDynamicTexture();
                this.lightmapUpdateNeeded = false;
                this.mc.mcProfiler.endSection();
            }
        }
    }

    private float func_180438_a(EntityLivingBase p_180438_1_, float p_180438_2_) {
        int var3 = p_180438_1_.getActivePotionEffect(Potion.nightVision).getDuration();
        return var3 > 200 ? 1.0f : 0.7f + MathHelper.sin(((float)var3 - p_180438_2_) * 3.1415927f * 0.2f) * 0.3f;
    }

    public void updateCameraAndRender(float p_78480_1_) {
        boolean var2 = Display.isActive();
        if (!(var2 || !this.mc.gameSettings.pauseOnLostFocus || this.mc.gameSettings.touchscreen && Mouse.isButtonDown((int)1))) {
            if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu();
            }
        } else {
            this.prevFrameTime = Minecraft.getSystemTime();
        }
        this.mc.mcProfiler.startSection("mouse");
        if (var2 && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
            Mouse.setGrabbed((boolean)false);
            Mouse.setCursorPosition((int)(Display.getWidth() / 2), (int)(Display.getHeight() / 2));
            Mouse.setGrabbed((boolean)true);
        }
        if (this.mc.inGameHasFocus && var2) {
            this.mc.mouseHelper.mouseXYChange();
            float var3 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float var4 = var3 * var3 * var3 * 8.0f;
            float var5 = (float)this.mc.mouseHelper.deltaX * var4;
            float var6 = (float)this.mc.mouseHelper.deltaY * var4;
            int var7 = 1;
            if (this.mc.gameSettings.invertMouse) {
                var7 = -1;
            }
            if (this.mc.gameSettings.smoothCamera) {
                this.smoothCamYaw += var5;
                this.smoothCamPitch += var6;
                float var8 = p_78480_1_ - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = p_78480_1_;
                var5 = this.smoothCamFilterX * var8;
                var6 = this.smoothCamFilterY * var8;
                this.mc.thePlayer.setAngles(var5, var6 * (float)var7);
            } else {
                this.smoothCamYaw = 0.0f;
                this.smoothCamPitch = 0.0f;
                this.mc.thePlayer.setAngles(var5, var6 * (float)var7);
            }
        }
        this.mc.mcProfiler.endSection();
        if (!this.mc.skipRenderWorld) {
            anaglyphEnable = this.mc.gameSettings.anaglyph;
            final ScaledResolution var13 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int var14 = var13.getScaledWidth();
            int var15 = var13.getScaledHeight();
            final int var16 = Mouse.getX() * var14 / this.mc.displayWidth;
            final int var17 = var15 - Mouse.getY() * var15 / this.mc.displayHeight - 1;
            int var18 = this.mc.gameSettings.limitFramerate;
            if (this.mc.theWorld != null) {
                this.mc.mcProfiler.startSection("level");
                int var9 = Math.max(Minecraft.func_175610_ah(), 30);
                this.renderWorld(p_78480_1_, this.renderEndNanoTime + (long)(1000000000 / var9));
                if (OpenGlHelper.shadersSupported) {
                    this.mc.renderGlobal.func_174975_c();
                    if (this.theShaderGroup != null && this.field_175083_ad) {
                        GlStateManager.matrixMode(5890);
                        GlStateManager.pushMatrix();
                        GlStateManager.loadIdentity();
                        this.theShaderGroup.loadShaderGroup(p_78480_1_);
                        GlStateManager.popMatrix();
                    }
                    this.mc.getFramebuffer().bindFramebuffer(true);
                }
                this.renderEndNanoTime = System.nanoTime();
                this.mc.mcProfiler.endStartSection("gui");
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    GlStateManager.alphaFunc(516, 0.1f);
                    this.mc.ingameGUI.func_175180_a(p_78480_1_);
                }
                this.mc.mcProfiler.endSection();
            } else {
                GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
                GlStateManager.matrixMode(5889);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.loadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }
            if (this.mc.currentScreen != null) {
                GlStateManager.clear(256);
                try {
                    this.mc.currentScreen.drawScreen(var16, var17, p_78480_1_);
                }
                catch (Throwable var12) {
                    CrashReport var10 = CrashReport.makeCrashReport(var12, "Rendering screen");
                    CrashReportCategory var11 = var10.makeCategory("Screen render details");
                    var11.addCrashSectionCallable("Screen name", new Callable(){
                        private static final String __OBFID = "CL_00000948";

                        public String call() {
                            return EntityRenderer.access$0((EntityRenderer)EntityRenderer.this).currentScreen.getClass().getCanonicalName();
                        }
                    });
                    var11.addCrashSectionCallable("Mouse location", new Callable(){
                        private static final String __OBFID = "CL_00000950";

                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", var16, var17, Mouse.getX(), Mouse.getY());
                        }
                    });
                    var11.addCrashSectionCallable("Screen size", new Callable(){
                        private static final String __OBFID = "CL_00000951";

                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", var13.getScaledWidth(), var13.getScaledHeight(), EntityRenderer.access$0((EntityRenderer)EntityRenderer.this).displayWidth, EntityRenderer.access$0((EntityRenderer)EntityRenderer.this).displayHeight, var13.getScaleFactor());
                        }
                    });
                    throw new ReportedException(var10);
                }
            }
        }
    }

    public void func_152430_c(float p_152430_1_) {
        this.setupOverlayRendering();
        this.mc.ingameGUI.func_180478_c(new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight));
    }

    private boolean func_175070_n() {
        boolean var2;
        if (!this.field_175073_D) {
            return false;
        }
        Entity var1 = this.mc.func_175606_aa();
        boolean bl = var2 = var1 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;
        if (var2 && !((EntityPlayer)var1).capabilities.allowEdit) {
            ItemStack var3 = ((EntityPlayer)var1).getCurrentEquippedItem();
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                BlockPos var4 = this.mc.objectMouseOver.func_178782_a();
                Block var5 = this.mc.theWorld.getBlockState(var4).getBlock();
                var2 = this.mc.playerController.func_178889_l() == WorldSettings.GameType.SPECTATOR ? var5.hasTileEntity() && this.mc.theWorld.getTileEntity(var4) instanceof IInventory : var3 != null && (var3.canDestroy(var5) || var3.canPlaceOn(var5));
            }
        }
        return var2;
    }

    private void func_175067_i(float p_175067_1_) {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.func_175140_cp() && !this.mc.gameSettings.field_178879_v) {
            Entity var2 = this.mc.func_175606_aa();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glLineWidth((float)1.0f);
            GlStateManager.func_179090_x();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            this.orientCamera(p_175067_1_);
            GlStateManager.translate(0.0f, var2.getEyeHeight(), 0.0f);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), -65536);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), -16776961);
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), -16711936);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.func_179098_w();
            GlStateManager.disableBlend();
        }
    }

    public void renderWorld(float p_78471_1_, long p_78471_2_) {
        this.updateLightmap(p_78471_1_);
        if (this.mc.func_175606_aa() == null) {
            this.mc.func_175607_a(this.mc.thePlayer);
        }
        this.getMouseOver(p_78471_1_);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.5f);
        this.mc.mcProfiler.startSection("center");
        if (this.mc.gameSettings.anaglyph) {
            anaglyphField = 0;
            GlStateManager.colorMask(false, true, true, false);
            this.func_175068_a(0, p_78471_1_, p_78471_2_);
            anaglyphField = 1;
            GlStateManager.colorMask(true, false, false, false);
            this.func_175068_a(1, p_78471_1_, p_78471_2_);
            GlStateManager.colorMask(true, true, true, false);
        } else {
            this.func_175068_a(2, p_78471_1_, p_78471_2_);
        }
        this.mc.mcProfiler.endSection();
    }

    private void func_175068_a(int p_175068_1_, float p_175068_2_, long p_175068_3_) {
        EntityPlayer var16;
        RenderGlobal var5 = this.mc.renderGlobal;
        EffectRenderer var6 = this.mc.effectRenderer;
        boolean var7 = this.func_175070_n();
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("clear");
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        this.updateFogColor(p_175068_2_);
        GlStateManager.clear(16640);
        this.mc.mcProfiler.endStartSection("camera");
        this.setupCameraTransform(p_175068_2_, p_175068_1_);
        ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
        this.mc.mcProfiler.endStartSection("frustum");
        ClippingHelperImpl.getInstance();
        this.mc.mcProfiler.endStartSection("culling");
        Frustrum var8 = new Frustrum();
        Entity var9 = this.mc.func_175606_aa();
        double var10 = var9.lastTickPosX + (var9.posX - var9.lastTickPosX) * (double)p_175068_2_;
        double var12 = var9.lastTickPosY + (var9.posY - var9.lastTickPosY) * (double)p_175068_2_;
        double var14 = var9.lastTickPosZ + (var9.posZ - var9.lastTickPosZ) * (double)p_175068_2_;
        var8.setPosition(var10, var12, var14);
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            this.setupFog(-1, p_175068_2_);
            this.mc.mcProfiler.endStartSection("sky");
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective((float)this.getFOVModifier(p_175068_2_, true), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * 2.0f));
            GlStateManager.matrixMode(5888);
            var5.func_174976_a(p_175068_2_, p_175068_1_);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective((float)this.getFOVModifier(p_175068_2_, true), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * MathHelper.field_180189_a));
            GlStateManager.matrixMode(5888);
        }
        this.setupFog(0, p_175068_2_);
        GlStateManager.shadeModel(7425);
        if (var9.posY + (double)var9.getEyeHeight() < 128.0) {
            this.func_180437_a(var5, p_175068_2_, p_175068_1_);
        }
        this.mc.mcProfiler.endStartSection("prepareterrain");
        this.setupFog(0, p_175068_2_);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        this.mc.mcProfiler.endStartSection("terrain_setup");
        var5.func_174970_a(var9, p_175068_2_, var8, this.field_175084_ae++, this.mc.thePlayer.func_175149_v());
        if (p_175068_1_ == 0 || p_175068_1_ == 2) {
            this.mc.mcProfiler.endStartSection("updatechunks");
            this.mc.renderGlobal.func_174967_a(p_175068_3_);
        }
        this.mc.mcProfiler.endStartSection("terrain");
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        var5.func_174977_a(EnumWorldBlockLayer.SOLID, p_175068_2_, p_175068_1_, var9);
        GlStateManager.enableAlpha();
        var5.func_174977_a(EnumWorldBlockLayer.CUTOUT_MIPPED, p_175068_2_, p_175068_1_, var9);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174936_b(false, false);
        var5.func_174977_a(EnumWorldBlockLayer.CUTOUT, p_175068_2_, p_175068_1_, var9);
        this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).func_174935_a();
        GlStateManager.shadeModel(7424);
        GlStateManager.alphaFunc(516, 0.1f);
        if (!this.field_175078_W) {
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            var5.func_180446_a(var9, var8, p_175068_2_);
            RenderHelper.disableStandardItemLighting();
            this.func_175072_h();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            if (this.mc.objectMouseOver != null && var9.isInsideOfMaterial(Material.water) && var7) {
                var16 = (EntityPlayer)var9;
                GlStateManager.disableAlpha();
                this.mc.mcProfiler.endStartSection("outline");
                var5.drawSelectionBox(var16, this.mc.objectMouseOver, 0, p_175068_2_);
                GlStateManager.enableAlpha();
            }
        }
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        if (var7 && this.mc.objectMouseOver != null && !var9.isInsideOfMaterial(Material.water)) {
            var16 = (EntityPlayer)var9;
            GlStateManager.disableAlpha();
            this.mc.mcProfiler.endStartSection("outline");
            var5.drawSelectionBox(var16, this.mc.objectMouseOver, 0, p_175068_2_);
            GlStateManager.enableAlpha();
        }
        this.mc.mcProfiler.endStartSection("destroyProgress");
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
        var5.func_174981_a(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), var9, p_175068_2_);
        GlStateManager.disableBlend();
        if (!this.field_175078_W) {
            this.func_180436_i();
            this.mc.mcProfiler.endStartSection("litParticles");
            var6.renderLitParticles(var9, p_175068_2_);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0, p_175068_2_);
            this.mc.mcProfiler.endStartSection("particles");
            var6.renderParticles(var9, p_175068_2_);
            this.func_175072_h();
        }
        GlStateManager.depthMask(false);
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("weather");
        this.renderRainSnow(p_175068_2_);
        GlStateManager.depthMask(true);
        var5.func_180449_a(var9, p_175068_2_);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1f);
        this.setupFog(0, p_175068_2_);
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.shadeModel(7425);
        if (this.mc.gameSettings.fancyGraphics) {
            this.mc.mcProfiler.endStartSection("translucent");
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, p_175068_2_, p_175068_1_, var9);
            GlStateManager.disableBlend();
        } else {
            this.mc.mcProfiler.endStartSection("translucent");
            var5.func_174977_a(EnumWorldBlockLayer.TRANSLUCENT, p_175068_2_, p_175068_1_, var9);
        }
        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableFog();
        if (var9.posY + (double)var9.getEyeHeight() >= 128.0) {
            this.mc.mcProfiler.endStartSection("aboveClouds");
            this.func_180437_a(var5, p_175068_2_, p_175068_1_);
        }
        this.mc.mcProfiler.endStartSection("hand");
        if (this.field_175074_C) {
            GlStateManager.clear(256);
            this.renderHand(p_175068_2_, p_175068_1_);
            this.func_175067_i(p_175068_2_);
        }
    }

    private void func_180437_a(RenderGlobal p_180437_1_, float p_180437_2_, int p_180437_3_) {
        if (this.mc.gameSettings.shouldRenderClouds()) {
            this.mc.mcProfiler.endStartSection("clouds");
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective((float)this.getFOVModifier(p_180437_2_, true), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * 4.0f));
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            this.setupFog(0, p_180437_2_);
            p_180437_1_.func_180447_b(p_180437_2_, p_180437_3_);
            GlStateManager.disableFog();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective((float)this.getFOVModifier(p_180437_2_, true), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)(this.farPlaneDistance * MathHelper.field_180189_a));
            GlStateManager.matrixMode(5888);
        }
    }

    private void addRainParticles() {
        float var1 = this.mc.theWorld.getRainStrength(1.0f);
        if (!this.mc.gameSettings.fancyGraphics) {
            var1 /= 2.0f;
        }
        if (var1 != 0.0f) {
            this.random.setSeed((long)this.rendererUpdateCount * 312987231L);
            Entity var2 = this.mc.func_175606_aa();
            WorldClient var3 = this.mc.theWorld;
            BlockPos var4 = new BlockPos(var2);
            int var5 = 10;
            double var6 = 0.0;
            double var8 = 0.0;
            double var10 = 0.0;
            int var12 = 0;
            int var13 = (int)(100.0f * var1 * var1);
            if (this.mc.gameSettings.particleSetting == 1) {
                var13 >>= 1;
            } else if (this.mc.gameSettings.particleSetting == 2) {
                var13 = 0;
            }
            for (int var14 = 0; var14 < var13; ++var14) {
                BlockPos var15 = var3.func_175725_q(var4.add(this.random.nextInt(var5) - this.random.nextInt(var5), 0, this.random.nextInt(var5) - this.random.nextInt(var5)));
                BiomeGenBase var16 = var3.getBiomeGenForCoords(var15);
                BlockPos var17 = var15.offsetDown();
                Block var18 = var3.getBlockState(var17).getBlock();
                if (var15.getY() > var4.getY() + var5 || var15.getY() < var4.getY() - var5 || !var16.canSpawnLightningBolt() || !(var16.func_180626_a(var15) >= 0.15f)) continue;
                float var19 = this.random.nextFloat();
                float var20 = this.random.nextFloat();
                if (var18.getMaterial() == Material.lava) {
                    this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (float)var15.getX() + var19, (double)((float)var15.getY() + 0.1f) - var18.getBlockBoundsMinY(), (double)((float)var15.getZ() + var20), 0.0, 0.0, 0.0, new int[0]);
                    continue;
                }
                if (var18.getMaterial() == Material.air) continue;
                var18.setBlockBoundsBasedOnState(var3, var17);
                if (this.random.nextInt(++var12) == 0) {
                    var6 = (float)var17.getX() + var19;
                    var8 = (double)((float)var17.getY() + 0.1f) + var18.getBlockBoundsMaxY() - 1.0;
                    var10 = (float)var17.getZ() + var20;
                }
                this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, (float)var17.getX() + var19, (double)((float)var17.getY() + 0.1f) + var18.getBlockBoundsMaxY(), (double)((float)var17.getZ() + var20), 0.0, 0.0, 0.0, new int[0]);
            }
            if (var12 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
                this.rainSoundCounter = 0;
                if (var8 > (double)(var4.getY() + 1) && var3.func_175725_q(var4).getY() > MathHelper.floor_float(var4.getY())) {
                    this.mc.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.1f, 0.5f, false);
                } else {
                    this.mc.theWorld.playSound(var6, var8, var10, "ambient.weather.rain", 0.2f, 1.0f, false);
                }
            }
        }
    }

    protected void renderRainSnow(float p_78474_1_) {
        float var2 = this.mc.theWorld.getRainStrength(p_78474_1_);
        if (var2 > 0.0f) {
            this.func_180436_i();
            Entity var3 = this.mc.func_175606_aa();
            WorldClient var4 = this.mc.theWorld;
            int var5 = MathHelper.floor_double(var3.posX);
            int var6 = MathHelper.floor_double(var3.posY);
            int var7 = MathHelper.floor_double(var3.posZ);
            Tessellator var8 = Tessellator.getInstance();
            WorldRenderer var9 = var8.getWorldRenderer();
            GlStateManager.disableCull();
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1f);
            double var10 = var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * (double)p_78474_1_;
            double var12 = var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)p_78474_1_;
            double var14 = var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * (double)p_78474_1_;
            int var16 = MathHelper.floor_double(var12);
            int var17 = 5;
            if (this.mc.gameSettings.fancyGraphics) {
                var17 = 10;
            }
            int var18 = -1;
            float var19 = (float)this.rendererUpdateCount + p_78474_1_;
            if (this.mc.gameSettings.fancyGraphics) {
                var17 = 10;
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            for (int var20 = var7 - var17; var20 <= var7 + var17; ++var20) {
                for (int var21 = var5 - var17; var21 <= var5 + var17; ++var21) {
                    float var33;
                    double var36;
                    int var22 = (var20 - var7 + 16) * 32 + var21 - var5 + 16;
                    float var23 = this.field_175076_N[var22] * 0.5f;
                    float var24 = this.field_175077_O[var22] * 0.5f;
                    BlockPos var25 = new BlockPos(var21, 0, var20);
                    BiomeGenBase var26 = var4.getBiomeGenForCoords(var25);
                    if (!var26.canSpawnLightningBolt() && !var26.getEnableSnow()) continue;
                    int var27 = var4.func_175725_q(var25).getY();
                    int var28 = var6 - var17;
                    int var29 = var6 + var17;
                    if (var28 < var27) {
                        var28 = var27;
                    }
                    if (var29 < var27) {
                        var29 = var27;
                    }
                    float var30 = 1.0f;
                    int var31 = var27;
                    if (var27 < var16) {
                        var31 = var16;
                    }
                    if (var28 == var29) continue;
                    this.random.setSeed(var21 * var21 * 3121 + var21 * 45238971 ^ var20 * var20 * 418711 + var20 * 13761);
                    float var32 = var26.func_180626_a(new BlockPos(var21, var28, var20));
                    if (var4.getWorldChunkManager().getTemperatureAtHeight(var32, var27) >= 0.15f) {
                        if (var18 != 0) {
                            if (var18 >= 0) {
                                var8.draw();
                            }
                            var18 = 0;
                            this.mc.getTextureManager().bindTexture(locationRainPng);
                            var9.startDrawingQuads();
                        }
                        var33 = ((float)(this.rendererUpdateCount + var21 * var21 * 3121 + var21 * 45238971 + var20 * var20 * 418711 + var20 * 13761 & 31) + p_78474_1_) / 32.0f * (3.0f + this.random.nextFloat());
                        double var34 = (double)((float)var21 + 0.5f) - var3.posX;
                        var36 = (double)((float)var20 + 0.5f) - var3.posZ;
                        float var38 = MathHelper.sqrt_double(var34 * var34 + var36 * var36) / (float)var17;
                        float var39 = 1.0f;
                        var9.func_178963_b(var4.getCombinedLight(new BlockPos(var21, var31, var20), 0));
                        var9.func_178960_a(var39, var39, var39, ((1.0f - var38 * var38) * 0.5f + 0.5f) * var2);
                        var9.setTranslation(-var10 * 1.0, -var12 * 1.0, -var14 * 1.0);
                        var9.addVertexWithUV((double)((float)var21 - var23) + 0.5, var28, (double)((float)var20 - var24) + 0.5, 0.0f * var30, (float)var28 * var30 / 4.0f + var33 * var30);
                        var9.addVertexWithUV((double)((float)var21 + var23) + 0.5, var28, (double)((float)var20 + var24) + 0.5, 1.0f * var30, (float)var28 * var30 / 4.0f + var33 * var30);
                        var9.addVertexWithUV((double)((float)var21 + var23) + 0.5, var29, (double)((float)var20 + var24) + 0.5, 1.0f * var30, (float)var29 * var30 / 4.0f + var33 * var30);
                        var9.addVertexWithUV((double)((float)var21 - var23) + 0.5, var29, (double)((float)var20 - var24) + 0.5, 0.0f * var30, (float)var29 * var30 / 4.0f + var33 * var30);
                        var9.setTranslation(0.0, 0.0, 0.0);
                        continue;
                    }
                    if (var18 != 1) {
                        if (var18 >= 0) {
                            var8.draw();
                        }
                        var18 = 1;
                        this.mc.getTextureManager().bindTexture(locationSnowPng);
                        var9.startDrawingQuads();
                    }
                    var33 = ((float)(this.rendererUpdateCount & 511) + p_78474_1_) / 512.0f;
                    float var42 = this.random.nextFloat() + var19 * 0.01f * (float)this.random.nextGaussian();
                    float var35 = this.random.nextFloat() + var19 * (float)this.random.nextGaussian() * 0.001f;
                    var36 = (double)((float)var21 + 0.5f) - var3.posX;
                    double var43 = (double)((float)var20 + 0.5f) - var3.posZ;
                    float var40 = MathHelper.sqrt_double(var36 * var36 + var43 * var43) / (float)var17;
                    float var41 = 1.0f;
                    var9.func_178963_b((var4.getCombinedLight(new BlockPos(var21, var31, var20), 0) * 3 + 15728880) / 4);
                    var9.func_178960_a(var41, var41, var41, ((1.0f - var40 * var40) * 0.3f + 0.5f) * var2);
                    var9.setTranslation(-var10 * 1.0, -var12 * 1.0, -var14 * 1.0);
                    var9.addVertexWithUV((double)((float)var21 - var23) + 0.5, var28, (double)((float)var20 - var24) + 0.5, 0.0f * var30 + var42, (float)var28 * var30 / 4.0f + var33 * var30 + var35);
                    var9.addVertexWithUV((double)((float)var21 + var23) + 0.5, var28, (double)((float)var20 + var24) + 0.5, 1.0f * var30 + var42, (float)var28 * var30 / 4.0f + var33 * var30 + var35);
                    var9.addVertexWithUV((double)((float)var21 + var23) + 0.5, var29, (double)((float)var20 + var24) + 0.5, 1.0f * var30 + var42, (float)var29 * var30 / 4.0f + var33 * var30 + var35);
                    var9.addVertexWithUV((double)((float)var21 - var23) + 0.5, var29, (double)((float)var20 - var24) + 0.5, 0.0f * var30 + var42, (float)var29 * var30 / 4.0f + var33 * var30 + var35);
                    var9.setTranslation(0.0, 0.0, 0.0);
                }
            }
            if (var18 >= 0) {
                var8.draw();
            }
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1f);
            this.func_175072_h();
        }
    }

    public void setupOverlayRendering() {
        ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
    }

    private void updateFogColor(float p_78466_1_) {
        float var17;
        float var11;
        float var13;
        WorldClient var2 = this.mc.theWorld;
        Entity var3 = this.mc.func_175606_aa();
        float var4 = 0.25f + 0.75f * (float)this.mc.gameSettings.renderDistanceChunks / 32.0f;
        var4 = 1.0f - (float)Math.pow(var4, 0.25);
        Vec3 var5 = var2.getSkyColor(this.mc.func_175606_aa(), p_78466_1_);
        float var6 = (float)var5.xCoord;
        float var7 = (float)var5.yCoord;
        float var8 = (float)var5.zCoord;
        Vec3 var9 = var2.getFogColor(p_78466_1_);
        this.field_175080_Q = (float)var9.xCoord;
        this.field_175082_R = (float)var9.yCoord;
        this.field_175081_S = (float)var9.zCoord;
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            float[] var14;
            double var10 = -1.0;
            Vec3 var12 = MathHelper.sin(var2.getCelestialAngleRadians(p_78466_1_)) > 0.0f ? new Vec3(var10, 0.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
            var13 = (float)var3.getLook(p_78466_1_).dotProduct(var12);
            if (var13 < 0.0f) {
                var13 = 0.0f;
            }
            if (var13 > 0.0f && (var14 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(p_78466_1_), p_78466_1_)) != null) {
                this.field_175080_Q = this.field_175080_Q * (1.0f - (var13 *= var14[3])) + var14[0] * var13;
                this.field_175082_R = this.field_175082_R * (1.0f - var13) + var14[1] * var13;
                this.field_175081_S = this.field_175081_S * (1.0f - var13) + var14[2] * var13;
            }
        }
        this.field_175080_Q += (var6 - this.field_175080_Q) * var4;
        this.field_175082_R += (var7 - this.field_175082_R) * var4;
        this.field_175081_S += (var8 - this.field_175081_S) * var4;
        float var19 = var2.getRainStrength(p_78466_1_);
        if (var19 > 0.0f) {
            var11 = 1.0f - var19 * 0.5f;
            float var20 = 1.0f - var19 * 0.4f;
            this.field_175080_Q *= var11;
            this.field_175082_R *= var11;
            this.field_175081_S *= var20;
        }
        if ((var11 = var2.getWeightedThunderStrength(p_78466_1_)) > 0.0f) {
            float var20 = 1.0f - var11 * 0.5f;
            this.field_175080_Q *= var20;
            this.field_175082_R *= var20;
            this.field_175081_S *= var20;
        }
        Block var21 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, p_78466_1_);
        if (this.cloudFog) {
            Vec3 var22 = var2.getCloudColour(p_78466_1_);
            this.field_175080_Q = (float)var22.xCoord;
            this.field_175082_R = (float)var22.yCoord;
            this.field_175081_S = (float)var22.zCoord;
        } else if (var21.getMaterial() == Material.water) {
            var13 = (float)EnchantmentHelper.func_180319_a(var3) * 0.2f;
            if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
                var13 = var13 * 0.3f + 0.6f;
            }
            this.field_175080_Q = 0.02f + var13;
            this.field_175082_R = 0.02f + var13;
            this.field_175081_S = 0.2f + var13;
        } else if (var21.getMaterial() == Material.lava) {
            this.field_175080_Q = 0.6f;
            this.field_175082_R = 0.1f;
            this.field_175081_S = 0.0f;
        }
        var13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * p_78466_1_;
        this.field_175080_Q *= var13;
        this.field_175082_R *= var13;
        this.field_175081_S *= var13;
        double var23 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)p_78466_1_) * var2.provider.getVoidFogYFactor();
        if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
            int var16 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
            var23 = var16 < 20 ? (var23 *= (double)(1.0f - (float)var16 / 20.0f)) : 0.0;
        }
        if (var23 < 1.0) {
            if (var23 < 0.0) {
                var23 = 0.0;
            }
            var23 *= var23;
            this.field_175080_Q = (float)((double)this.field_175080_Q * var23);
            this.field_175082_R = (float)((double)this.field_175082_R * var23);
            this.field_175081_S = (float)((double)this.field_175081_S * var23);
        }
        if (this.bossColorModifier > 0.0f) {
            float var24 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * p_78466_1_;
            this.field_175080_Q = this.field_175080_Q * (1.0f - var24) + this.field_175080_Q * 0.7f * var24;
            this.field_175082_R = this.field_175082_R * (1.0f - var24) + this.field_175082_R * 0.6f * var24;
            this.field_175081_S = this.field_175081_S * (1.0f - var24) + this.field_175081_S * 0.6f * var24;
        }
        if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.nightVision)) {
            float var24 = this.func_180438_a((EntityLivingBase)var3, p_78466_1_);
            var17 = 1.0f / this.field_175080_Q;
            if (var17 > 1.0f / this.field_175082_R) {
                var17 = 1.0f / this.field_175082_R;
            }
            if (var17 > 1.0f / this.field_175081_S) {
                var17 = 1.0f / this.field_175081_S;
            }
            this.field_175080_Q = this.field_175080_Q * (1.0f - var24) + this.field_175080_Q * var17 * var24;
            this.field_175082_R = this.field_175082_R * (1.0f - var24) + this.field_175082_R * var17 * var24;
            this.field_175081_S = this.field_175081_S * (1.0f - var24) + this.field_175081_S * var17 * var24;
        }
        if (this.mc.gameSettings.anaglyph) {
            float var24 = (this.field_175080_Q * 30.0f + this.field_175082_R * 59.0f + this.field_175081_S * 11.0f) / 100.0f;
            var17 = (this.field_175080_Q * 30.0f + this.field_175082_R * 70.0f) / 100.0f;
            float var18 = (this.field_175080_Q * 30.0f + this.field_175081_S * 70.0f) / 100.0f;
            this.field_175080_Q = var24;
            this.field_175082_R = var17;
            this.field_175081_S = var18;
        }
        GlStateManager.clearColor(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 0.0f);
    }

    private void setupFog(int p_78468_1_, float p_78468_2_) {
        Entity var3 = this.mc.func_175606_aa();
        boolean var4 = false;
        if (var3 instanceof EntityPlayer) {
            var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
        }
        GL11.glFog((int)2918, (FloatBuffer)this.setFogColorBuffer(this.field_175080_Q, this.field_175082_R, this.field_175081_S, 1.0f));
        GL11.glNormal3f((float)0.0f, (float)-1.0f, (float)0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Block var5 = ActiveRenderInfo.func_180786_a(this.mc.theWorld, var3, p_78468_2_);
        if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.blindness)) {
            float var6 = 5.0f;
            int var7 = ((EntityLivingBase)var3).getActivePotionEffect(Potion.blindness).getDuration();
            if (var7 < 20) {
                var6 = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - (float)var7 / 20.0f);
            }
            GlStateManager.setFog(9729);
            if (p_78468_1_ == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(var6 * 0.8f);
            } else {
                GlStateManager.setFogStart(var6 * 0.25f);
                GlStateManager.setFogEnd(var6);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi((int)34138, (int)34139);
            }
        } else if (this.cloudFog) {
            GlStateManager.setFog(2048);
            GlStateManager.setFogDensity(0.1f);
        } else if (var5.getMaterial() == Material.water) {
            GlStateManager.setFog(2048);
            if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).isPotionActive(Potion.waterBreathing)) {
                GlStateManager.setFogDensity(0.01f);
            } else {
                GlStateManager.setFogDensity(0.1f - (float)EnchantmentHelper.func_180319_a(var3) * 0.03f);
            }
        } else if (var5.getMaterial() == Material.lava) {
            GlStateManager.setFog(2048);
            GlStateManager.setFogDensity(2.0f);
        } else {
            float var6 = this.farPlaneDistance;
            GlStateManager.setFog(9729);
            if (p_78468_1_ == -1) {
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(var6);
            } else {
                GlStateManager.setFogStart(var6 * 0.75f);
                GlStateManager.setFogEnd(var6);
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi((int)34138, (int)34139);
            }
            if (this.mc.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
                GlStateManager.setFogStart(var6 * 0.05f);
                GlStateManager.setFogEnd(Math.min(var6, 192.0f) * 0.5f);
            }
        }
        GlStateManager.enableColorMaterial();
        GlStateManager.enableFog();
        GlStateManager.colorMaterial(1028, 4608);
    }

    private FloatBuffer setFogColorBuffer(float p_78469_1_, float p_78469_2_, float p_78469_3_, float p_78469_4_) {
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(p_78469_1_).put(p_78469_2_).put(p_78469_3_).put(p_78469_4_);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }

    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }

    static /* synthetic */ Minecraft access$0(EntityRenderer entityRenderer) {
        return entityRenderer.mc;
    }

}

