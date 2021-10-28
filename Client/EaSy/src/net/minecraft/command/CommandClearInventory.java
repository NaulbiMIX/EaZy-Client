/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Set
 */
package net.minecraft.command;

import java.util.List;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;

public class CommandClearInventory
extends CommandBase {
    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.clear.usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP entityplayermp = args.length == 0 ? CommandClearInventory.getCommandSenderAsPlayer(sender) : CommandClearInventory.getPlayer(sender, args[0]);
        Item item = args.length >= 2 ? CommandClearInventory.getItemByText(sender, args[1]) : null;
        int i = args.length >= 3 ? CommandClearInventory.parseInt(args[2], -1) : -1;
        int j = args.length >= 4 ? CommandClearInventory.parseInt(args[3], -1) : -1;
        NBTTagCompound nbttagcompound = null;
        if (args.length >= 5) {
            try {
                nbttagcompound = JsonToNBT.getTagFromJson(CommandClearInventory.buildString(args, 4));
            }
            catch (NBTException nbtexception) {
                throw new CommandException("commands.clear.tagError", nbtexception.getMessage());
            }
        }
        if (args.length >= 2 && item == null) {
            throw new CommandException("commands.clear.failure", entityplayermp.getName());
        }
        int k = entityplayermp.inventory.clearMatchingItems(item, i, j, nbttagcompound);
        entityplayermp.inventoryContainer.detectAndSendChanges();
        if (!entityplayermp.capabilities.isCreativeMode) {
            entityplayermp.updateHeldItem();
        }
        sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
        if (k == 0) {
            throw new CommandException("commands.clear.failure", entityplayermp.getName());
        }
        if (j == 0) {
            sender.addChatMessage(new ChatComponentTranslation("commands.clear.testing", entityplayermp.getName(), k));
        } else {
            CommandClearInventory.notifyOperators(sender, (ICommand)this, "commands.clear.success", entityplayermp.getName(), k);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return CommandClearInventory.getListOfStringsMatchingLastWord(args, this.func_147209_d());
        }
        return args.length == 2 ? CommandClearInventory.getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys()) : null;
    }

    protected String[] func_147209_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }
}

