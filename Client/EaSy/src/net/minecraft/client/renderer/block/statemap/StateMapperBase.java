/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 */
package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.resources.model.ModelResourceLocation;

public abstract class StateMapperBase
implements IStateMapper {
    protected Map<IBlockState, ModelResourceLocation> mapStateModelLocations = Maps.newLinkedHashMap();

    public String getPropertyString(Map<IProperty, Comparable> p_178131_1_) {
        StringBuilder stringbuilder = new StringBuilder();
        for (Map.Entry entry : p_178131_1_.entrySet()) {
            if (stringbuilder.length() != 0) {
                stringbuilder.append(",");
            }
            IProperty iproperty = (IProperty)entry.getKey();
            Comparable comparable = (Comparable)entry.getValue();
            stringbuilder.append(iproperty.getName());
            stringbuilder.append("=");
            stringbuilder.append(iproperty.getName(comparable));
        }
        if (stringbuilder.length() == 0) {
            stringbuilder.append("normal");
        }
        return stringbuilder.toString();
    }

    @Override
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
        for (IBlockState iblockstate : blockIn.getBlockState().getValidStates()) {
            this.mapStateModelLocations.put((Object)iblockstate, (Object)this.getModelResourceLocation(iblockstate));
        }
        return this.mapStateModelLocations;
    }

    protected abstract ModelResourceLocation getModelResourceLocation(IBlockState var1);
}

