/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.config;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.src.Config;
import net.optifine.config.Matches;

public class MatchBlock {
    private int blockId = -1;
    private int[] metadatas = null;

    public MatchBlock(int blockId) {
        this.blockId = blockId;
    }

    public MatchBlock(int blockId, int metadata) {
        this.blockId = blockId;
        if (metadata >= 0 && metadata <= 15) {
            this.metadatas = new int[]{metadata};
        }
    }

    public MatchBlock(int blockId, int[] metadatas) {
        this.blockId = blockId;
        this.metadatas = metadatas;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int[] getMetadatas() {
        return this.metadatas;
    }

    public boolean matches(BlockStateBase blockState) {
        if (blockState.getBlockId() != this.blockId) {
            return false;
        }
        return Matches.metadata(blockState.getMetadata(), this.metadatas);
    }

    public boolean matches(int id, int metadata) {
        if (id != this.blockId) {
            return false;
        }
        return Matches.metadata(metadata, this.metadatas);
    }

    public void addMetadata(int metadata) {
        if (this.metadatas != null && metadata >= 0 && metadata <= 15) {
            for (int i = 0; i < this.metadatas.length; ++i) {
                if (this.metadatas[i] != metadata) continue;
                return;
            }
            this.metadatas = Config.addIntToArray(this.metadatas, metadata);
        }
    }

    public String toString() {
        return "" + this.blockId + ":" + Config.arrayToString(this.metadatas);
    }
}

