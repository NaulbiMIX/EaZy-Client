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
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty
extends CommandBase {
    @Override
    public String getCommandName() {
        return "difficulty";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.difficulty.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 0) {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
        EnumDifficulty enumdifficulty = this.getDifficultyFromCommand(args[0]);
        MinecraftServer.getServer().setDifficultyForAllWorlds(enumdifficulty);
        CommandDifficulty.notifyOperators(sender, (ICommand)this, "commands.difficulty.success", new ChatComponentTranslation(enumdifficulty.getDifficultyResourceKey(), new Object[0]));
    }

    protected EnumDifficulty getDifficultyFromCommand(String p_180531_1_) throws CommandException, NumberInvalidException {
        if (!p_180531_1_.equalsIgnoreCase("peaceful") && !p_180531_1_.equalsIgnoreCase("p")) {
            if (!p_180531_1_.equalsIgnoreCase("easy") && !p_180531_1_.equalsIgnoreCase("e")) {
                if (!p_180531_1_.equalsIgnoreCase("normal") && !p_180531_1_.equalsIgnoreCase("n")) {
                    return !p_180531_1_.equalsIgnoreCase("hard") && !p_180531_1_.equalsIgnoreCase("h") ? EnumDifficulty.getDifficultyEnum(CommandDifficulty.parseInt(p_180531_1_, 0, 3)) : EnumDifficulty.HARD;
                }
                return EnumDifficulty.NORMAL;
            }
            return EnumDifficulty.EASY;
        }
        return EnumDifficulty.PEACEFUL;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? CommandDifficulty.getListOfStringsMatchingLastWord(args, "peaceful", "easy", "normal", "hard") : null;
    }
}

