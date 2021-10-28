/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Random
 */
package net.minecraft.world.gen.structure;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureMineshaftStart
extends StructureStart {
    public StructureMineshaftStart() {
    }

    public StructureMineshaftStart(World worldIn, Random rand, int chunkX, int chunkZ) {
        super(chunkX, chunkZ);
        StructureMineshaftPieces.Room structuremineshaftpieces$room = new StructureMineshaftPieces.Room(0, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
        this.components.add((Object)structuremineshaftpieces$room);
        structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, (List<StructureComponent>)this.components, rand);
        this.updateBoundingBox();
        this.markAvailableHeight(worldIn, rand, 10);
    }
}

