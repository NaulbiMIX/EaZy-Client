/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.IRegistry;

public class ModelManager
implements IResourceManagerReloadListener {
    private IRegistry<ModelResourceLocation, IBakedModel> modelRegistry;
    private final TextureMap texMap;
    private final BlockModelShapes modelProvider;
    private IBakedModel defaultModel;

    public ModelManager(TextureMap textures) {
        this.texMap = textures;
        this.modelProvider = new BlockModelShapes(this);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        ModelBakery modelbakery = new ModelBakery(resourceManager, this.texMap, this.modelProvider);
        this.modelRegistry = modelbakery.setupModelRegistry();
        this.defaultModel = this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
        this.modelProvider.reloadModels();
    }

    public IBakedModel getModel(ModelResourceLocation modelLocation) {
        if (modelLocation == null) {
            return this.defaultModel;
        }
        IBakedModel ibakedmodel = this.modelRegistry.getObject(modelLocation);
        return ibakedmodel == null ? this.defaultModel : ibakedmodel;
    }

    public IBakedModel getMissingModel() {
        return this.defaultModel;
    }

    public TextureMap getTextureMap() {
        return this.texMap;
    }

    public BlockModelShapes getBlockModelShapes() {
        return this.modelProvider;
    }
}

