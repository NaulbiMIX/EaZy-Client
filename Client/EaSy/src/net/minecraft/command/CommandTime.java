/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommandTime
extends CommandBase {
    @Override
    public String getCommandName() {
        return "time";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.time.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 1) {
            if (args[0].equals((Object)"set")) {
                int l = args[1].equals((Object)"day") ? 1000 : (args[1].equals((Object)"night") ? 13000 : CommandTime.parseInt(args[1], 0));
                this.setTime(sender, l);
                CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.set", l);
                return;
            }
            if (args[0].equals((Object)"add")) {
                int k = CommandTime.parseInt(args[1], 0);
                this.addTime(sender, k);
                CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.added", k);
                return;
            }
            if (args[0].equals((Object)"query")) {
                if (args[1].equals((Object)"daytime")) {
                    int j = (int)(sender.getEntityWorld().getWorldTime() % Integer.MAX_VALUE);
                    sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, j);
                    CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.query", j);
                    return;
                }
                if (args[1].equals((Object)"gametime")) {
                    int i = (int)(sender.getEntityWorld().getTotalWorldTime() % Integer.MAX_VALUE);
                    sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
                    CommandTime.notifyOperators(sender, (ICommand)this, "commands.time.query", i);
                    return;
                }
            }
        }
        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return CommandTime.getListOfStringsMatchingLastWord(args, "set", "add", "query");
        }
        if (args.length == 2 && args[0].equals((Object)"set")) {
            return CommandTime.getListOfStringsMatchingLastWord(args, "day", "night");
        }
        return args.length == 2 && args[0].equals((Object)"query") ? CommandTime.getListOfStringsMatchingLastWord(args, "daytime", "gametime") : null;
    }

    protected void setTime(ICommandSender sender, int time) {
        for (int i = 0; i < MinecraftServer.getServer().worldServers.length; ++i) {
            MinecraftServer.getServer().worldServers[i].setWorldTime(time);
        }
    }

    protected void addTime(ICommandSender sender, int time) {
        for (int i = 0; i < MinecraftServer.getServer().worldServers.length; ++i) {
            WorldServer worldserver = MinecraftServer.getServer().worldServers[i];
            worldserver.setWorldTime(worldserver.getWorldTime() + (long)time);
        }
    }
}

