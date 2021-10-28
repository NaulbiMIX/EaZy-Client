/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorField;

public class ModelAdapterBook
extends ModelAdapter {
    public ModelAdapterBook() {
        super(TileEntityEnchantmentTable.class, "book", 0.0f);
    }

    @Override
    public ModelBase makeModel() {
        return new ModelBook();
    }

    @Override
    public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
        if (!(model instanceof ModelBook)) {
            return null;
        }
        ModelBook modelbook = (ModelBook)model;
        if (modelPart.equals((Object)"cover_right")) {
            return modelbook.coverRight;
        }
        if (modelPart.equals((Object)"cover_left")) {
            return modelbook.coverLeft;
        }
        if (modelPart.equals((Object)"pages_right")) {
            return modelbook.pagesRight;
        }
        if (modelPart.equals((Object)"pages_left")) {
            return modelbook.pagesLeft;
        }
        if (modelPart.equals((Object)"flipping_page_right")) {
            return modelbook.flippingPageRight;
        }
        if (modelPart.equals((Object)"flipping_page_left")) {
            return modelbook.flippingPageLeft;
        }
        return modelPart.equals((Object)"book_spine") ? modelbook.bookSpine : null;
    }

    @Override
    public String[] getModelRendererNames() {
        return new String[]{"cover_right", "cover_left", "pages_right", "pages_left", "flipping_page_right", "flipping_page_left", "book_spine"};
    }

    @Override
    public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
        TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
        TileEntityEnchantmentTableRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityEnchantmentTable.class);
        if (!(tileentityspecialrenderer instanceof TileEntityEnchantmentTableRenderer)) {
            return null;
        }
        if (tileentityspecialrenderer.getEntityClass() == null) {
            tileentityspecialrenderer = new TileEntityEnchantmentTableRenderer();
            tileentityspecialrenderer.setRendererDispatcher(tileentityrendererdispatcher);
        }
        if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
            Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
            return null;
        }
        Reflector.setFieldValue(tileentityspecialrenderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
        return tileentityspecialrenderer;
    }
}

