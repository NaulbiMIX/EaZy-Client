/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  java.awt.Dimension
 *  java.awt.image.BufferedImage
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.TreeSet
 *  java.util.concurrent.Callable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.optifine.BetterGrass;
import net.optifine.ConnectedTextures;
import net.optifine.CustomItems;
import net.optifine.EmissiveTextures;
import net.optifine.SmartAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.reflect.ReflectorMethod;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.ShadersTex;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap
extends AbstractTexture
implements ITickableTextureObject {
    private static final boolean ENABLE_SKIP = Boolean.parseBoolean((String)System.getProperty((String)"fml.skipFirstTextureLoad", (String)"true"));
    private static final Logger logger = LogManager.getLogger();
    public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
    public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
    private final List<TextureAtlasSprite> listAnimatedSprites = Lists.newArrayList();
    private final Map<String, TextureAtlasSprite> mapRegisteredSprites = Maps.newHashMap();
    private final Map<String, TextureAtlasSprite> mapUploadedSprites = Maps.newHashMap();
    private final String basePath;
    private final IIconCreator iconCreator;
    private int mipmapLevels;
    private final TextureAtlasSprite missingImage = new TextureAtlasSprite("missingno");
    private boolean skipFirst = false;
    private TextureAtlasSprite[] iconGrid = null;
    private int iconGridSize = -1;
    private int iconGridCountX = -1;
    private int iconGridCountY = -1;
    private double iconGridSizeU = -1.0;
    private double iconGridSizeV = -1.0;
    private CounterInt counterIndexInMap = new CounterInt(0);
    public int atlasWidth = 0;
    public int atlasHeight = 0;
    private int countAnimationsActive;
    private int frameCountAnimations;

    public TextureMap(String p_i46099_1_) {
        this(p_i46099_1_, null);
    }

    public TextureMap(String p_i5_1_, boolean p_i5_2_) {
        this(p_i5_1_, null, p_i5_2_);
    }

    public TextureMap(String p_i46100_1_, IIconCreator iconCreatorIn) {
        this(p_i46100_1_, iconCreatorIn, false);
    }

    public TextureMap(String p_i6_1_, IIconCreator p_i6_2_, boolean p_i6_3_) {
        this.basePath = p_i6_1_;
        this.iconCreator = p_i6_2_;
        this.skipFirst = p_i6_3_ && ENABLE_SKIP;
    }

    private void initMissingImage() {
        int i = this.getMinSpriteSize();
        int[] aint = this.getMissingImageData(i);
        this.missingImage.setIconWidth(i);
        this.missingImage.setIconHeight(i);
        int[][] aint1 = new int[this.mipmapLevels + 1][];
        aint1[0] = aint;
        this.missingImage.setFramesTextureData((List<int[][]>)Lists.newArrayList((Object[])new int[][][]{aint1}));
        this.missingImage.setIndexInMap(this.counterIndexInMap.nextValue());
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        ShadersTex.resManager = resourceManager;
        if (this.iconCreator != null) {
            this.loadSprites(resourceManager, this.iconCreator);
        }
    }

    public void loadSprites(IResourceManager resourceManager, IIconCreator p_174943_2_) {
        this.mapRegisteredSprites.clear();
        this.counterIndexInMap.reset();
        p_174943_2_.registerSprites(this);
        if (this.mipmapLevels >= 4) {
            this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
            Config.log("Mipmap levels: " + this.mipmapLevels);
        }
        this.initMissingImage();
        this.deleteGlTexture();
        this.loadTextureAtlas(resourceManager);
    }

    public void loadTextureAtlas(IResourceManager resourceManager) {
        int j2;
        int j;
        int k2;
        ShadersTex.resManager = resourceManager;
        Config.dbg("Multitexture: " + Config.isMultiTexture());
        if (Config.isMultiTexture()) {
            for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values()) {
                textureatlassprite.deleteSpriteTexture();
            }
        }
        ConnectedTextures.updateIcons(this);
        CustomItems.updateIcons(this);
        BetterGrass.updateIcons(this);
        int i2 = TextureUtils.getGLMaximumTextureSize();
        Stitcher stitcher = new Stitcher(i2, i2, true, 0, this.mipmapLevels);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        int i = Integer.MAX_VALUE;
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, this);
        this.iconGridSize = j = this.getMinSpriteSize();
        int k = 1 << this.mipmapLevels;
        int l = 0;
        int i1 = 0;
        for (Map.Entry entry : this.mapRegisteredSprites.entrySet()) {
            if (this.skipFirst) break;
            TextureAtlasSprite textureatlassprite3 = (TextureAtlasSprite)entry.getValue();
            ResourceLocation resourcelocation1 = new ResourceLocation(textureatlassprite3.getIconName());
            ResourceLocation resourcelocation2 = this.completeResourceLocation(resourcelocation1, 0);
            textureatlassprite3.updateIndexInMap(this.counterIndexInMap);
            if (textureatlassprite3.hasCustomLoader(resourceManager, resourcelocation1)) {
                if (!textureatlassprite3.load(resourceManager, resourcelocation1)) {
                    i = Math.min((int)i, (int)Math.min((int)textureatlassprite3.getIconWidth(), (int)textureatlassprite3.getIconHeight()));
                    stitcher.addSprite(textureatlassprite3);
                    Config.detail("Custom loader (skipped): " + textureatlassprite3);
                    ++i1;
                }
                Config.detail("Custom loader: " + textureatlassprite3);
                ++l;
                continue;
            }
            try {
                TextureMetadataSection texturemetadatasection;
                IResource iresource = resourceManager.getResource(resourcelocation2);
                BufferedImage[] abufferedimage = new BufferedImage[1 + this.mipmapLevels];
                abufferedimage[0] = TextureUtil.readBufferedImage(iresource.getInputStream());
                int k3 = abufferedimage[0].getWidth();
                int l3 = abufferedimage[0].getHeight();
                if (k3 < 1 || l3 < 1) {
                    Config.warn("Invalid sprite size: " + textureatlassprite3);
                    continue;
                }
                if (k3 < j || this.mipmapLevels > 0) {
                    int i4;
                    int n = i4 = this.mipmapLevels > 0 ? TextureUtils.scaleToGrid(k3, j) : TextureUtils.scaleToMin(k3, j);
                    if (i4 != k3) {
                        if (!TextureUtils.isPowerOfTwo(k3)) {
                            Config.log("Scaled non power of 2: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
                        } else {
                            Config.log("Scaled too small texture: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
                        }
                        int j1 = l3 * i4 / k3;
                        abufferedimage[0] = TextureUtils.scaleImage(abufferedimage[0], i4);
                    }
                }
                if ((texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture")) != null) {
                    List<Integer> list1 = texturemetadatasection.getListMipmaps();
                    if (!list1.isEmpty()) {
                        int k1 = abufferedimage[0].getWidth();
                        int l1 = abufferedimage[0].getHeight();
                        if (MathHelper.roundUpToPowerOfTwo(k1) != k1 || MathHelper.roundUpToPowerOfTwo(l1) != l1) {
                            throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                        }
                    }
                    Iterator k1 = list1.iterator();
                    while (k1.hasNext()) {
                        int j4 = (Integer)k1.next();
                        if (j4 <= 0 || j4 >= abufferedimage.length - 1 || abufferedimage[j4] != null) continue;
                        ResourceLocation resourcelocation = this.completeResourceLocation(resourcelocation1, j4);
                        try {
                            abufferedimage[j4] = TextureUtil.readBufferedImage(resourceManager.getResource(resourcelocation).getInputStream());
                        }
                        catch (IOException ioexception) {
                            logger.error("Unable to load miplevel {} from: {}", new Object[]{j4, resourcelocation, ioexception});
                        }
                    }
                }
                AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
                textureatlassprite3.loadSprite(abufferedimage, animationmetadatasection);
            }
            catch (RuntimeException runtimeexception) {
                logger.error("Unable to parse metadata from " + resourcelocation2, (Throwable)runtimeexception);
                ReflectorForge.FMLClientHandler_trackBrokenTexture(resourcelocation2, runtimeexception.getMessage());
                continue;
            }
            catch (IOException ioexception1) {
                logger.error("Using missing texture, unable to load " + resourcelocation2 + ", " + ioexception1.getClass().getName());
                ReflectorForge.FMLClientHandler_trackMissingTexture(resourcelocation2);
                continue;
            }
            i = Math.min((int)i, (int)Math.min((int)textureatlassprite3.getIconWidth(), (int)textureatlassprite3.getIconHeight()));
            int j3 = Math.min((int)Integer.lowestOneBit((int)textureatlassprite3.getIconWidth()), (int)Integer.lowestOneBit((int)textureatlassprite3.getIconHeight()));
            if (j3 < k) {
                logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[]{resourcelocation2, textureatlassprite3.getIconWidth(), textureatlassprite3.getIconHeight(), MathHelper.calculateLogBaseTwo(k), MathHelper.calculateLogBaseTwo(j3)});
                k = j3;
            }
            stitcher.addSprite(textureatlassprite3);
        }
        if (l > 0) {
            Config.dbg("Custom loader sprites: " + l);
        }
        if (i1 > 0) {
            Config.dbg("Custom loader sprites (skipped): " + i1);
        }
        if ((k2 = MathHelper.calculateLogBaseTwo(j2 = Math.min((int)i, (int)k))) < 0) {
            k2 = 0;
        }
        if (k2 < this.mipmapLevels) {
            logger.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.basePath, this.mipmapLevels, k2, j2});
            this.mipmapLevels = k2;
        }
        for (final TextureAtlasSprite textureatlassprite1 : this.mapRegisteredSprites.values()) {
            if (this.skipFirst) break;
            try {
                textureatlassprite1.generateMipmaps(this.mipmapLevels);
            }
            catch (Throwable throwable1) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Applying mipmap");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Sprite being mipmapped");
                crashreportcategory.addCrashSectionCallable("Sprite name", new Callable<String>(){

                    public String call() throws Exception {
                        return textureatlassprite1.getIconName();
                    }
                });
                crashreportcategory.addCrashSectionCallable("Sprite size", new Callable<String>(){

                    public String call() throws Exception {
                        return textureatlassprite1.getIconWidth() + " x " + textureatlassprite1.getIconHeight();
                    }
                });
                crashreportcategory.addCrashSectionCallable("Sprite frames", new Callable<String>(){

                    public String call() throws Exception {
                        return textureatlassprite1.getFrameCount() + " frames";
                    }
                });
                crashreportcategory.addCrashSection("Mipmap levels", this.mipmapLevels);
                throw new ReportedException(crashreport);
            }
        }
        this.missingImage.generateMipmaps(this.mipmapLevels);
        stitcher.addSprite(this.missingImage);
        this.skipFirst = false;
        stitcher.doStitch();
        logger.info("Created: {}x{} {}-atlas", new Object[]{stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), this.basePath});
        if (Config.isShaders()) {
            ShadersTex.allocateTextureMap(this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), stitcher, this);
        } else {
            TextureUtil.allocateTextureImpl(this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        }
        HashMap map = Maps.newHashMap(this.mapRegisteredSprites);
        for (TextureAtlasSprite textureatlassprite2 : stitcher.getStichSlots()) {
            if (Config.isShaders()) {
                ShadersTex.setIconName(ShadersTex.setSprite(textureatlassprite2).getIconName());
            }
            String s = textureatlassprite2.getIconName();
            map.remove((Object)s);
            this.mapUploadedSprites.put((Object)s, (Object)textureatlassprite2);
            try {
                if (Config.isShaders()) {
                    ShadersTex.uploadTexSubForLoadAtlas(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
                } else {
                    TextureUtil.uploadTextureMipmap(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
                }
            }
            catch (Throwable throwable) {
                CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
                CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Texture being stitched together");
                crashreportcategory1.addCrashSection("Atlas path", this.basePath);
                crashreportcategory1.addCrashSection("Sprite", textureatlassprite2);
                throw new ReportedException(crashreport1);
            }
            if (!textureatlassprite2.hasAnimationMetadata()) continue;
            textureatlassprite2.setAnimationIndex(this.listAnimatedSprites.size());
            this.listAnimatedSprites.add((Object)textureatlassprite2);
        }
        for (TextureAtlasSprite textureatlassprite4 : map.values()) {
            textureatlassprite4.copyFrom(this.missingImage);
        }
        Config.log("Animated sprites: " + this.listAnimatedSprites.size());
        if (Config.isMultiTexture()) {
            int l2 = stitcher.getCurrentWidth();
            int i3 = stitcher.getCurrentHeight();
            for (TextureAtlasSprite textureatlassprite5 : stitcher.getStichSlots()) {
                textureatlassprite5.sheetWidth = l2;
                textureatlassprite5.sheetHeight = i3;
                textureatlassprite5.mipmapLevels = this.mipmapLevels;
                TextureAtlasSprite textureatlassprite6 = textureatlassprite5.spriteSingle;
                if (textureatlassprite6 == null) continue;
                if (textureatlassprite6.getIconWidth() <= 0) {
                    textureatlassprite6.setIconWidth(textureatlassprite5.getIconWidth());
                    textureatlassprite6.setIconHeight(textureatlassprite5.getIconHeight());
                    textureatlassprite6.initSprite(textureatlassprite5.getIconWidth(), textureatlassprite5.getIconHeight(), 0, 0, false);
                    textureatlassprite6.clearFramesTextureData();
                    List<int[][]> list = textureatlassprite5.getFramesTextureData();
                    textureatlassprite6.setFramesTextureData(list);
                    textureatlassprite6.setAnimationMetadata(textureatlassprite5.getAnimationMetadata());
                }
                textureatlassprite6.sheetWidth = l2;
                textureatlassprite6.sheetHeight = i3;
                textureatlassprite6.mipmapLevels = this.mipmapLevels;
                textureatlassprite6.setAnimationIndex(textureatlassprite5.getAnimationIndex());
                textureatlassprite5.bindSpriteTexture();
                boolean flag1 = false;
                boolean flag = true;
                try {
                    TextureUtil.uploadTextureMipmap(textureatlassprite6.getFrameTextureData(0), textureatlassprite6.getIconWidth(), textureatlassprite6.getIconHeight(), textureatlassprite6.getOriginX(), textureatlassprite6.getOriginY(), flag1, flag);
                }
                catch (Exception exception) {
                    Config.dbg("Error uploading sprite single: " + textureatlassprite6 + ", parent: " + textureatlassprite5);
                    exception.printStackTrace();
                }
            }
            Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
        }
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
        this.updateIconGrid(stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        if (Config.equals(System.getProperty((String)"saveTextureMap"), "true")) {
            Config.dbg("Exporting texture map: " + this.basePath);
            TextureUtils.saveGlTexture("debug/" + this.basePath.replaceAll("/", "_"), this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        }
    }

    public ResourceLocation completeResourceLocation(ResourceLocation p_completeResourceLocation_1_) {
        return this.completeResourceLocation(p_completeResourceLocation_1_, 0);
    }

    public ResourceLocation completeResourceLocation(ResourceLocation location, int p_147634_2_) {
        if (this.isAbsoluteLocation(location)) {
            return new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + ".png");
        }
        return p_147634_2_ == 0 ? new ResourceLocation(location.getResourceDomain(), String.format((String)"%s/%s%s", (Object[])new Object[]{this.basePath, location.getResourcePath(), ".png"})) : new ResourceLocation(location.getResourceDomain(), String.format((String)"%s/mipmaps/%s.%d%s", (Object[])new Object[]{this.basePath, location.getResourcePath(), p_147634_2_, ".png"}));
    }

    public TextureAtlasSprite getAtlasSprite(String iconName) {
        TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.mapUploadedSprites.get((Object)iconName);
        if (textureatlassprite == null) {
            textureatlassprite = this.missingImage;
        }
        return textureatlassprite;
    }

    public void updateAnimations() {
        int j;
        if (Config.isShaders()) {
            ShadersTex.updatingTex = this.getMultiTexID();
        }
        boolean flag = false;
        boolean flag1 = false;
        TextureUtil.bindTexture(this.getGlTextureId());
        int i = 0;
        for (TextureAtlasSprite textureatlassprite : this.listAnimatedSprites) {
            if (!this.isTerrainAnimationActive(textureatlassprite)) continue;
            textureatlassprite.updateAnimation();
            if (textureatlassprite.isAnimationActive()) {
                ++i;
            }
            if (textureatlassprite.spriteNormal != null) {
                flag = true;
            }
            if (textureatlassprite.spriteSpecular == null) continue;
            flag1 = true;
        }
        if (Config.isMultiTexture()) {
            for (TextureAtlasSprite textureatlassprite2 : this.listAnimatedSprites) {
                TextureAtlasSprite textureatlassprite1;
                if (!this.isTerrainAnimationActive(textureatlassprite2) || (textureatlassprite1 = textureatlassprite2.spriteSingle) == null) continue;
                if (textureatlassprite2 == TextureUtils.iconClock || textureatlassprite2 == TextureUtils.iconCompass) {
                    textureatlassprite1.frameCounter = textureatlassprite2.frameCounter;
                }
                textureatlassprite2.bindSpriteTexture();
                textureatlassprite1.updateAnimation();
                if (!textureatlassprite1.isAnimationActive()) continue;
                ++i;
            }
            TextureUtil.bindTexture(this.getGlTextureId());
        }
        if (Config.isShaders()) {
            if (flag) {
                TextureUtil.bindTexture(this.getMultiTexID().norm);
                for (TextureAtlasSprite textureatlassprite3 : this.listAnimatedSprites) {
                    if (textureatlassprite3.spriteNormal == null || !this.isTerrainAnimationActive(textureatlassprite3)) continue;
                    if (textureatlassprite3 == TextureUtils.iconClock || textureatlassprite3 == TextureUtils.iconCompass) {
                        textureatlassprite3.spriteNormal.frameCounter = textureatlassprite3.frameCounter;
                    }
                    textureatlassprite3.spriteNormal.updateAnimation();
                    if (!textureatlassprite3.spriteNormal.isAnimationActive()) continue;
                    ++i;
                }
            }
            if (flag1) {
                TextureUtil.bindTexture(this.getMultiTexID().spec);
                for (TextureAtlasSprite textureatlassprite4 : this.listAnimatedSprites) {
                    if (textureatlassprite4.spriteSpecular == null || !this.isTerrainAnimationActive(textureatlassprite4)) continue;
                    if (textureatlassprite4 == TextureUtils.iconClock || textureatlassprite4 == TextureUtils.iconCompass) {
                        textureatlassprite4.spriteNormal.frameCounter = textureatlassprite4.frameCounter;
                    }
                    textureatlassprite4.spriteSpecular.updateAnimation();
                    if (!textureatlassprite4.spriteSpecular.isAnimationActive()) continue;
                    ++i;
                }
            }
            if (flag || flag1) {
                TextureUtil.bindTexture(this.getGlTextureId());
            }
        }
        if ((j = Config.getMinecraft().entityRenderer.frameCount) != this.frameCountAnimations) {
            this.countAnimationsActive = i;
            this.frameCountAnimations = j;
        }
        if (SmartAnimations.isActive()) {
            SmartAnimations.resetSpritesRendered();
        }
        if (Config.isShaders()) {
            ShadersTex.updatingTex = null;
        }
    }

    public TextureAtlasSprite registerSprite(ResourceLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }
        TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.mapRegisteredSprites.get((Object)location.toString());
        if (textureatlassprite == null) {
            textureatlassprite = TextureAtlasSprite.makeAtlasSprite(location);
            this.mapRegisteredSprites.put((Object)location.toString(), (Object)textureatlassprite);
            textureatlassprite.updateIndexInMap(this.counterIndexInMap);
            if (Config.isEmissiveTextures()) {
                this.checkEmissive(location, textureatlassprite);
            }
        }
        return textureatlassprite;
    }

    @Override
    public void tick() {
        this.updateAnimations();
    }

    public void setMipmapLevels(int mipmapLevelsIn) {
        this.mipmapLevels = mipmapLevelsIn;
    }

    public TextureAtlasSprite getMissingSprite() {
        return this.missingImage;
    }

    public TextureAtlasSprite getTextureExtry(String p_getTextureExtry_1_) {
        return (TextureAtlasSprite)this.mapRegisteredSprites.get((Object)p_getTextureExtry_1_);
    }

    public boolean setTextureEntry(String p_setTextureEntry_1_, TextureAtlasSprite p_setTextureEntry_2_) {
        if (!this.mapRegisteredSprites.containsKey((Object)p_setTextureEntry_1_)) {
            this.mapRegisteredSprites.put((Object)p_setTextureEntry_1_, (Object)p_setTextureEntry_2_);
            p_setTextureEntry_2_.updateIndexInMap(this.counterIndexInMap);
            return true;
        }
        return false;
    }

    public boolean setTextureEntry(TextureAtlasSprite p_setTextureEntry_1_) {
        return this.setTextureEntry(p_setTextureEntry_1_.getIconName(), p_setTextureEntry_1_);
    }

    public String getBasePath() {
        return this.basePath;
    }

    public int getMipmapLevels() {
        return this.mipmapLevels;
    }

    private boolean isAbsoluteLocation(ResourceLocation p_isAbsoluteLocation_1_) {
        String s = p_isAbsoluteLocation_1_.getResourcePath();
        return this.isAbsoluteLocationPath(s);
    }

    private boolean isAbsoluteLocationPath(String p_isAbsoluteLocationPath_1_) {
        String s = p_isAbsoluteLocationPath_1_.toLowerCase();
        return s.startsWith("mcpatcher/") || s.startsWith("optifine/");
    }

    public TextureAtlasSprite getSpriteSafe(String p_getSpriteSafe_1_) {
        ResourceLocation resourcelocation = new ResourceLocation(p_getSpriteSafe_1_);
        return (TextureAtlasSprite)this.mapRegisteredSprites.get((Object)resourcelocation.toString());
    }

    public TextureAtlasSprite getRegisteredSprite(ResourceLocation p_getRegisteredSprite_1_) {
        return (TextureAtlasSprite)this.mapRegisteredSprites.get((Object)p_getRegisteredSprite_1_.toString());
    }

    private boolean isTerrainAnimationActive(TextureAtlasSprite p_isTerrainAnimationActive_1_) {
        if (p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterFlow) {
            if (p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaFlow) {
                if (p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer0 && p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer1) {
                    if (p_isTerrainAnimationActive_1_ == TextureUtils.iconPortal) {
                        return Config.isAnimatedPortal();
                    }
                    return p_isTerrainAnimationActive_1_ != TextureUtils.iconClock && p_isTerrainAnimationActive_1_ != TextureUtils.iconCompass ? Config.isAnimatedTerrain() : true;
                }
                return Config.isAnimatedFire();
            }
            return Config.isAnimatedLava();
        }
        return Config.isAnimatedWater();
    }

    public int getCountRegisteredSprites() {
        return this.counterIndexInMap.getValue();
    }

    private int detectMaxMipmapLevel(Map p_detectMaxMipmapLevel_1_, IResourceManager p_detectMaxMipmapLevel_2_) {
        int j;
        int i = this.detectMinimumSpriteSize(p_detectMaxMipmapLevel_1_, p_detectMaxMipmapLevel_2_, 20);
        if (i < 16) {
            i = 16;
        }
        if ((i = MathHelper.roundUpToPowerOfTwo(i)) > 16) {
            Config.log("Sprite size: " + i);
        }
        if ((j = MathHelper.calculateLogBaseTwo(i)) < 4) {
            j = 4;
        }
        return j;
    }

    private int detectMinimumSpriteSize(Map p_detectMinimumSpriteSize_1_, IResourceManager p_detectMinimumSpriteSize_2_, int p_detectMinimumSpriteSize_3_) {
        HashMap map = new HashMap();
        for (Object ff_Object : p_detectMinimumSpriteSize_1_.entrySet()) {
            Map.Entry entry = (Map.Entry)ff_Object;
            TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)entry.getValue();
            ResourceLocation resourcelocation = new ResourceLocation(textureatlassprite.getIconName());
            ResourceLocation resourcelocation1 = this.completeResourceLocation(resourcelocation);
            if (textureatlassprite.hasCustomLoader(p_detectMinimumSpriteSize_2_, resourcelocation)) continue;
            try {
                Dimension dimension;
                InputStream inputstream;
                IResource iresource = p_detectMinimumSpriteSize_2_.getResource(resourcelocation1);
                if (iresource == null || (inputstream = iresource.getInputStream()) == null || (dimension = TextureUtils.getImageSize(inputstream, "png")) == null) continue;
                int i = dimension.width;
                int j = MathHelper.roundUpToPowerOfTwo(i);
                if (!map.containsKey((Object)j)) {
                    map.put((Object)j, (Object)1);
                    continue;
                }
                int k = (Integer)map.get((Object)j);
                map.put((Object)j, (Object)(k + 1));
            }
            catch (Exception iresource) {}
        }
        int l = 0;
        Set set = map.keySet();
        TreeSet set1 = new TreeSet((Collection)set);
        for (Object o : set1) {
            int j1 = (Integer)o;
            int l1 = (Integer)map.get((Object)j1);
            l += l1;
        }
        int i1 = 16;
        int k1 = 0;
        int i2 = l * p_detectMinimumSpriteSize_3_ / 100;
        for (Object o : set1) {
            int j2 = (Integer)o;
            int k2 = (Integer)map.get((Object)j2);
            k1 += k2;
            if (j2 > i1) {
                i1 = j2;
            }
            if (k1 <= i2) continue;
            return i1;
        }
        return i1;
    }

    private int getMinSpriteSize() {
        int i = 1 << this.mipmapLevels;
        if (i < 8) {
            i = 8;
        }
        return i;
    }

    private int[] getMissingImageData(int p_getMissingImageData_1_) {
        BufferedImage bufferedimage = new BufferedImage(16, 16, 2);
        bufferedimage.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
        BufferedImage bufferedimage1 = TextureUtils.scaleImage(bufferedimage, p_getMissingImageData_1_);
        int[] aint = new int[p_getMissingImageData_1_ * p_getMissingImageData_1_];
        bufferedimage1.getRGB(0, 0, p_getMissingImageData_1_, p_getMissingImageData_1_, aint, 0, p_getMissingImageData_1_);
        return aint;
    }

    public boolean isTextureBound() {
        int j;
        int i = GlStateManager.getBoundTexture();
        return i == (j = this.getGlTextureId());
    }

    private void updateIconGrid(int p_updateIconGrid_1_, int p_updateIconGrid_2_) {
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGrid = null;
        if (this.iconGridSize > 0) {
            this.iconGridCountX = p_updateIconGrid_1_ / this.iconGridSize;
            this.iconGridCountY = p_updateIconGrid_2_ / this.iconGridSize;
            this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
            this.iconGridSizeU = 1.0 / (double)this.iconGridCountX;
            this.iconGridSizeV = 1.0 / (double)this.iconGridCountY;
            for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values()) {
                double d0 = 0.5 / (double)p_updateIconGrid_1_;
                double d1 = 0.5 / (double)p_updateIconGrid_2_;
                double d2 = (double)Math.min((float)textureatlassprite.getMinU(), (float)textureatlassprite.getMaxU()) + d0;
                double d3 = (double)Math.min((float)textureatlassprite.getMinV(), (float)textureatlassprite.getMaxV()) + d1;
                double d4 = (double)Math.max((float)textureatlassprite.getMinU(), (float)textureatlassprite.getMaxU()) - d0;
                double d5 = (double)Math.max((float)textureatlassprite.getMinV(), (float)textureatlassprite.getMaxV()) - d1;
                int i = (int)(d2 / this.iconGridSizeU);
                int j = (int)(d3 / this.iconGridSizeV);
                int k = (int)(d4 / this.iconGridSizeU);
                int l = (int)(d5 / this.iconGridSizeV);
                for (int i1 = i; i1 <= k; ++i1) {
                    if (i1 >= 0 && i1 < this.iconGridCountX) {
                        for (int j1 = j; j1 <= l; ++j1) {
                            if (j1 >= 0 && j1 < this.iconGridCountX) {
                                int k1 = j1 * this.iconGridCountX + i1;
                                this.iconGrid[k1] = textureatlassprite;
                                continue;
                            }
                            Config.warn("Invalid grid V: " + j1 + ", icon: " + textureatlassprite.getIconName());
                        }
                        continue;
                    }
                    Config.warn("Invalid grid U: " + i1 + ", icon: " + textureatlassprite.getIconName());
                }
            }
        }
    }

    public TextureAtlasSprite getIconByUV(double p_getIconByUV_1_, double p_getIconByUV_3_) {
        if (this.iconGrid == null) {
            return null;
        }
        int j = (int)(p_getIconByUV_3_ / this.iconGridSizeV);
        int i = (int)(p_getIconByUV_1_ / this.iconGridSizeU);
        int k = j * this.iconGridCountX + i;
        return k >= 0 && k <= this.iconGrid.length ? this.iconGrid[k] : null;
    }

    private void checkEmissive(ResourceLocation p_checkEmissive_1_, TextureAtlasSprite p_checkEmissive_2_) {
        ResourceLocation resourcelocation;
        ResourceLocation resourcelocation1;
        String s = EmissiveTextures.getSuffixEmissive();
        if (s != null && !p_checkEmissive_1_.getResourcePath().endsWith(s) && Config.hasResource(resourcelocation1 = this.completeResourceLocation(resourcelocation = new ResourceLocation(p_checkEmissive_1_.getResourceDomain(), p_checkEmissive_1_.getResourcePath() + s)))) {
            TextureAtlasSprite textureatlassprite = this.registerSprite(resourcelocation);
            textureatlassprite.isEmissive = true;
            p_checkEmissive_2_.spriteEmissive = textureatlassprite;
        }
    }

    public int getCountAnimations() {
        return this.listAnimatedSprites.size();
    }

    public int getCountAnimationsActive() {
        return this.countAnimationsActive;
    }

}
