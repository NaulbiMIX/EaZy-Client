/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 */
package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CommandReplaceItem
extends CommandBase {
    private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();

    @Override
    public String getCommandName() {
        return "replaceitem";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.replaceitem.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        int i;
        boolean flag;
        Item item;
        if (args.length < 1) {
            throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
        }
        if (args[0].equals((Object)"entity")) {
            flag = false;
        } else {
            if (!args[0].equals((Object)"block")) {
                throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
            }
            flag = true;
        }
        if (flag) {
            if (args.length < 6) {
                throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
            }
            i = 4;
        } else {
            if (args.length < 4) {
                throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
            }
            i = 2;
        }
        int j = this.getSlotForShortcut(args[i++]);
        try {
            item = CommandReplaceItem.getItemByText(sender, args[i]);
        }
        catch (NumberInvalidException numberinvalidexception) {
            if (Block.getBlockFromName(args[i]) != Blocks.air) {
                throw numberinvalidexception;
            }
            item = null;
        }
        int k = args.length > ++i ? CommandReplaceItem.parseInt(args[i++], 1, 64) : 1;
        int l = args.length > i ? CommandReplaceItem.parseInt(args[i++]) : 0;
        ItemStack itemstack = new ItemStack(item, k, l);
        if (args.length > i) {
            String s = CommandReplaceItem.getChatComponentFromNthArg(sender, args, i).getUnformattedText();
            try {
                itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
            }
            catch (NBTException nbtexception) {
                throw new CommandException("commands.replaceitem.tagError", nbtexception.getMessage());
            }
        }
        if (itemstack.getItem() == null) {
            itemstack = null;
        }
        if (flag) {
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            BlockPos blockpos = CommandReplaceItem.parseBlockPos(sender, args, 1, false);
            World world = sender.getEntityWorld();
            TileEntity tileentity = world.getTileEntity(blockpos);
            if (tileentity == null || !(tileentity instanceof IInventory)) {
                throw new CommandException("commands.replaceitem.noContainer", blockpos.getX(), blockpos.getY(), blockpos.getZ());
            }
            IInventory iinventory = (IInventory)((Object)tileentity);
            if (j >= 0 && j < iinventory.getSizeInventory()) {
                iinventory.setInventorySlotContents(j, itemstack);
            }
        } else {
            Entity entity = CommandReplaceItem.getEntity(sender, args[1]);
            sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
            }
            if (!entity.replaceItemInInventory(j, itemstack)) {
                throw new CommandException("commands.replaceitem.failed", j, k, itemstack == null ? "Air" : itemstack.getChatComponent());
            }
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
            }
        }
        sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
        CommandReplaceItem.notifyOperators(sender, (ICommand)this, "commands.replaceitem.success", j, k, itemstack == null ? "Air" : itemstack.getChatComponent());
    }

    private int getSlotForShortcut(String shortcut) throws CommandException {
        if (!SHORTCUTS.containsKey((Object)shortcut)) {
            throw new CommandException("commands.generic.parameter.invalid", shortcut);
        }
        return (Integer)SHORTCUTS.get((Object)shortcut);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return CommandReplaceItem.getListOfStringsMatchingLastWord(args, "entity", "block");
        }
        if (args.length == 2 && args[0].equals((Object)"entity")) {
            return CommandReplaceItem.getListOfStringsMatchingLastWord(args, this.getUsernames());
        }
        if (args.length >= 2 && args.length <= 4 && args[0].equals((Object)"block")) {
            return CommandReplaceItem.func_175771_a(args, 1, pos);
        }
        if (!(args.length == 3 && args[0].equals((Object)"entity") || args.length == 5 && args[0].equals((Object)"block"))) {
            return !(args.length == 4 && args[0].equals((Object)"entity") || args.length == 6 && args[0].equals((Object)"block")) ? null : CommandReplaceItem.getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys());
        }
        return CommandReplaceItem.getListOfStringsMatchingLastWord(args, SHORTCUTS.keySet());
    }

    protected String[] getUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return args.length > 0 && args[0].equals((Object)"entity") && index == 1;
    }

    static {
        for (int i = 0; i < 54; ++i) {
            SHORTCUTS.put((Object)("slot.container." + i), (Object)i);
        }
        for (int j = 0; j < 9; ++j) {
            SHORTCUTS.put((Object)("slot.hotbar." + j), (Object)j);
        }
        for (int k = 0; k < 27; ++k) {
            SHORTCUTS.put((Object)("slot.inventory." + k), (Object)(9 + k));
        }
        for (int l = 0; l < 27; ++l) {
            SHORTCUTS.put((Object)("slot.enderchest." + l), (Object)(200 + l));
        }
        for (int i1 = 0; i1 < 8; ++i1) {
            SHORTCUTS.put((Object)("slot.villager." + i1), (Object)(300 + i1));
        }
        for (int j1 = 0; j1 < 15; ++j1) {
            SHORTCUTS.put((Object)("slot.horse." + j1), (Object)(500 + j1));
        }
        SHORTCUTS.put((Object)"slot.weapon", (Object)99);
        SHORTCUTS.put((Object)"slot.armor.head", (Object)103);
        SHORTCUTS.put((Object)"slot.armor.chest", (Object)102);
        SHORTCUTS.put((Object)"slot.armor.legs", (Object)101);
        SHORTCUTS.put((Object)"slot.armor.feet", (Object)100);
        SHORTCUTS.put((Object)"slot.horse.saddle", (Object)400);
        SHORTCUTS.put((Object)"slot.horse.armor", (Object)401);
        SHORTCUTS.put((Object)"slot.horse.chest", (Object)499);
    }
}

