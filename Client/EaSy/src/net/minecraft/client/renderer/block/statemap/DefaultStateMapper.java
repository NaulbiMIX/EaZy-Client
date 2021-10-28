/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;

public class DefaultStateMapper
extends StateMapperBase {
    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock()), this.getPropertyString((Map<IProperty, Comparable>)state.getProperties()));
    }
}

