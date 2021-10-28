/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  com.google.common.base.Functions
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.primitives.Doubles
 */
package net.minecraft.command;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.IAdminCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;

public abstract class CommandBase
implements ICommand {
    private static IAdminCommand theAdmin;
    private static final String __OBFID = "CL_00001739";

    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public List getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    public static int parseInt(String input) throws NumberInvalidException {
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", input);
        }
    }

    public static int parseInt(String input, int min) throws NumberInvalidException {
        return CommandBase.parseInt(input, min, Integer.MAX_VALUE);
    }

    public static int parseInt(String input, int min, int max) throws NumberInvalidException {
        int var3 = CommandBase.parseInt(input);
        if (var3 < min) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", var3, min);
        }
        if (var3 > max) {
            throw new NumberInvalidException("commands.generic.num.tooBig", var3, max);
        }
        return var3;
    }

    public static long parseLong(String input) throws NumberInvalidException {
        try {
            return Long.parseLong(input);
        }
        catch (NumberFormatException var2) {
            throw new NumberInvalidException("commands.generic.num.invalid", input);
        }
    }

    public static long parseLong(String input, long min, long max) throws NumberInvalidException {
        long var5 = CommandBase.parseLong(input);
        if (var5 < min) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", var5, min);
        }
        if (var5 > max) {
            throw new NumberInvalidException("commands.generic.num.tooBig", var5, max);
        }
        return var5;
    }

    public static BlockPos func_175757_a(ICommandSender sender, String[] args, int p_175757_2_, boolean p_175757_3_) throws NumberInvalidException {
        BlockPos var4 = sender.getPosition();
        return new BlockPos(CommandBase.func_175769_b(var4.getX(), args[p_175757_2_], -30000000, 30000000, p_175757_3_), CommandBase.func_175769_b(var4.getY(), args[p_175757_2_ + 1], 0, 256, false), CommandBase.func_175769_b(var4.getZ(), args[p_175757_2_ + 2], -30000000, 30000000, p_175757_3_));
    }

    public static double parseDouble(String input) throws NumberInvalidException {
        try {
            double var1 = Double.parseDouble(input);
            if (!Doubles.isFinite((double)var1)) {
                throw new NumberInvalidException("commands.generic.num.invalid", input);
            }
            return var1;
        }
        catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", input);
        }
    }

    public static double parseDouble(String input, double min) throws NumberInvalidException {
        return CommandBase.parseDouble(input, min, Double.MAX_VALUE);
    }

    public static double parseDouble(String input, double min, double max) throws NumberInvalidException {
        double var5 = CommandBase.parseDouble(input);
        if (var5 < min) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", var5, min);
        }
        if (var5 > max) {
            throw new NumberInvalidException("commands.generic.double.tooBig", var5, max);
        }
        return var5;
    }

    public static boolean parseBoolean(String input) throws CommandException {
        if (!input.equals("true") && !input.equals("1")) {
            if (!input.equals("false") && !input.equals("0")) {
                throw new CommandException("commands.generic.boolean.invalid", input);
            }
            return false;
        }
        return true;
    }

    public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException {
        if (sender instanceof EntityPlayerMP) {
            return (EntityPlayerMP)sender;
        }
        throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
    }

    public static EntityPlayerMP getPlayer(ICommandSender sender, String username) throws PlayerNotFoundException {
        EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(sender, username);
        if (var2 == null) {
            try {
                var2 = MinecraftServer.getServer().getConfigurationManager().func_177451_a(UUID.fromString(username));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        if (var2 == null) {
            var2 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username);
        }
        if (var2 == null) {
            throw new PlayerNotFoundException();
        }
        return var2;
    }

    public static Entity func_175768_b(ICommandSender p_175768_0_, String p_175768_1_) throws EntityNotFoundException {
        return CommandBase.func_175759_a(p_175768_0_, p_175768_1_, Entity.class);
    }

    public static Entity func_175759_a(ICommandSender p_175759_0_, String p_175759_1_, Class p_175759_2_) throws EntityNotFoundException {
        Entity var3 = PlayerSelector.func_179652_a(p_175759_0_, p_175759_1_, p_175759_2_);
        MinecraftServer var4 = MinecraftServer.getServer();
        if (var3 == null) {
            var3 = var4.getConfigurationManager().getPlayerByUsername(p_175759_1_);
        }
        if (var3 == null) {
            try {
                UUID var5 = UUID.fromString(p_175759_1_);
                var3 = var4.getEntityFromUuid(var5);
                if (var3 == null) {
                    var3 = var4.getConfigurationManager().func_177451_a(var5);
                }
            }
            catch (IllegalArgumentException var6) {
                throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[0]);
            }
        }
        if (var3 != null && p_175759_2_.isAssignableFrom(var3.getClass())) {
            return var3;
        }
        throw new EntityNotFoundException();
    }

    public static List func_175763_c(ICommandSender p_175763_0_, String p_175763_1_) throws EntityNotFoundException {
        return PlayerSelector.hasArguments(p_175763_1_) ? PlayerSelector.func_179656_b(p_175763_0_, p_175763_1_, Entity.class) : Lists.newArrayList((Object[])new Entity[]{CommandBase.func_175768_b(p_175763_0_, p_175763_1_)});
    }

    public static String getPlayerName(ICommandSender sender, String query) throws PlayerNotFoundException {
        try {
            return CommandBase.getPlayer(sender, query).getName();
        }
        catch (PlayerNotFoundException var3) {
            if (PlayerSelector.hasArguments(query)) {
                throw var3;
            }
            return query;
        }
    }

    public static String func_175758_e(ICommandSender p_175758_0_, String p_175758_1_) throws EntityNotFoundException {
        try {
            return CommandBase.getPlayer(p_175758_0_, p_175758_1_).getName();
        }
        catch (PlayerNotFoundException var5) {
            try {
                return CommandBase.func_175768_b(p_175758_0_, p_175758_1_).getUniqueID().toString();
            }
            catch (EntityNotFoundException var4) {
                if (PlayerSelector.hasArguments(p_175758_1_)) {
                    throw var4;
                }
                return p_175758_1_;
            }
        }
    }

    public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int p_147178_2_) throws CommandException {
        return CommandBase.getChatComponentFromNthArg(sender, args, p_147178_2_, false);
    }

    public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index, boolean p_147176_3_) throws PlayerNotFoundException {
        ChatComponentText var4 = new ChatComponentText("");
        for (int var5 = index; var5 < args.length; ++var5) {
            if (var5 > index) {
                var4.appendText(" ");
            }
            IChatComponent var6 = new ChatComponentText(args[var5]);
            if (p_147176_3_) {
                IChatComponent var7 = PlayerSelector.func_150869_b(sender, args[var5]);
                if (var7 == null) {
                    if (PlayerSelector.hasArguments(args[var5])) {
                        throw new PlayerNotFoundException();
                    }
                } else {
                    var6 = var7;
                }
            }
            var4.appendSibling(var6);
        }
        return var4;
    }

    public static String func_180529_a(String[] p_180529_0_, int p_180529_1_) {
        StringBuilder var2 = new StringBuilder();
        for (int var3 = p_180529_1_; var3 < p_180529_0_.length; ++var3) {
            if (var3 > p_180529_1_) {
                var2.append(" ");
            }
            String var4 = p_180529_0_[var3];
            var2.append(var4);
        }
        return var2.toString();
    }

    public static CoordinateArg func_175770_a(double p_175770_0_, String p_175770_2_, boolean p_175770_3_) throws NumberInvalidException {
        return CommandBase.func_175767_a(p_175770_0_, p_175770_2_, -30000000, 30000000, p_175770_3_);
    }

    public static CoordinateArg func_175767_a(double p_175767_0_, String p_175767_2_, int p_175767_3_, int p_175767_4_, boolean p_175767_5_) throws NumberInvalidException {
        boolean var6 = p_175767_2_.startsWith("~");
        if (var6 && Double.isNaN(p_175767_0_)) {
            throw new NumberInvalidException("commands.generic.num.invalid", p_175767_0_);
        }
        double var7 = 0.0;
        if (!var6 || p_175767_2_.length() > 1) {
            boolean var9 = p_175767_2_.contains(".");
            if (var6) {
                p_175767_2_ = p_175767_2_.substring(1);
            }
            var7 += CommandBase.parseDouble(p_175767_2_);
            if (!var9 && !var6 && p_175767_5_) {
                var7 += 0.5;
            }
        }
        if (p_175767_3_ != 0 || p_175767_4_ != 0) {
            if (var7 < (double)p_175767_3_) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", var7, p_175767_3_);
            }
            if (var7 > (double)p_175767_4_) {
                throw new NumberInvalidException("commands.generic.double.tooBig", var7, p_175767_4_);
            }
        }
        return new CoordinateArg(var7 + (var6 ? p_175767_0_ : 0.0), var7, var6);
    }

    public static double func_175761_b(double p_175761_0_, String p_175761_2_, boolean p_175761_3_) throws NumberInvalidException {
        return CommandBase.func_175769_b(p_175761_0_, p_175761_2_, -30000000, 30000000, p_175761_3_);
    }

    public static double func_175769_b(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException {
        double var7;
        boolean var6 = input.startsWith("~");
        if (var6 && Double.isNaN(base)) {
            throw new NumberInvalidException("commands.generic.num.invalid", base);
        }
        double d = var7 = var6 ? base : 0.0;
        if (!var6 || input.length() > 1) {
            boolean var9 = input.contains(".");
            if (var6) {
                input = input.substring(1);
            }
            var7 += CommandBase.parseDouble(input);
            if (!var9 && !var6 && centerBlock) {
                var7 += 0.5;
            }
        }
        if (min != 0 || max != 0) {
            if (var7 < (double)min) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", var7, min);
            }
            if (var7 > (double)max) {
                throw new NumberInvalidException("commands.generic.double.tooBig", var7, max);
            }
        }
        return var7;
    }

    public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException {
        ResourceLocation var2 = new ResourceLocation(id);
        Item var3 = (Item)Item.itemRegistry.getObject(var2);
        if (var3 == null) {
            throw new NumberInvalidException("commands.give.notFound", var2);
        }
        return var3;
    }

    public static Block getBlockByText(ICommandSender sender, String id) throws NumberInvalidException {
        ResourceLocation var2 = new ResourceLocation(id);
        if (!Block.blockRegistry.containsKey(var2)) {
            throw new NumberInvalidException("commands.give.notFound", var2);
        }
        Block var3 = (Block)Block.blockRegistry.getObject(var2);
        if (var3 == null) {
            throw new NumberInvalidException("commands.give.notFound", var2);
        }
        return var3;
    }

    public static String joinNiceString(Object[] elements) {
        StringBuilder var1 = new StringBuilder();
        for (int var2 = 0; var2 < elements.length; ++var2) {
            String var3 = elements[var2].toString();
            if (var2 > 0) {
                if (var2 == elements.length - 1) {
                    var1.append(" and ");
                } else {
                    var1.append(", ");
                }
            }
            var1.append(var3);
        }
        return var1.toString();
    }

    public static IChatComponent join(List components) {
        ChatComponentText var1 = new ChatComponentText("");
        for (int var2 = 0; var2 < components.size(); ++var2) {
            if (var2 > 0) {
                if (var2 == components.size() - 1) {
                    var1.appendText(" and ");
                } else if (var2 > 0) {
                    var1.appendText(", ");
                }
            }
            var1.appendSibling((IChatComponent)components.get(var2));
        }
        return var1;
    }

    public static String joinNiceStringFromCollection(Collection strings) {
        return CommandBase.joinNiceString(strings.toArray(new String[strings.size()]));
    }

    public static List func_175771_a(String[] p_175771_0_, int p_175771_1_, BlockPos p_175771_2_) {
        String var3;
        if (p_175771_2_ == null) {
            return null;
        }
        if (p_175771_0_.length - 1 == p_175771_1_) {
            var3 = Integer.toString(p_175771_2_.getX());
        } else if (p_175771_0_.length - 1 == p_175771_1_ + 1) {
            var3 = Integer.toString(p_175771_2_.getY());
        } else {
            if (p_175771_0_.length - 1 != p_175771_1_ + 2) {
                return null;
            }
            var3 = Integer.toString(p_175771_2_.getZ());
        }
        return Lists.newArrayList((Object[])new String[]{var3});
    }

    public static boolean doesStringStartWith(String original, String region) {
        return region.regionMatches(true, 0, original, 0, original.length());
    }

    public static List getListOfStringsMatchingLastWord(String[] args, String ... possibilities) {
        return CommandBase.func_175762_a(args, Arrays.asList(possibilities));
    }

    public static List func_175762_a(String[] p_175762_0_, Collection p_175762_1_) {
        String var2 = p_175762_0_[p_175762_0_.length - 1];
        ArrayList var3 = Lists.newArrayList();
        if (!p_175762_1_.isEmpty()) {
            for (String var5 : Iterables.transform((Iterable)p_175762_1_, (Function)Functions.toStringFunction())) {
                if (!CommandBase.doesStringStartWith(var2, var5)) continue;
                var3.add(var5);
            }
            if (var3.isEmpty()) {
                for (Object var6 : p_175762_1_) {
                    if (!(var6 instanceof ResourceLocation) || !CommandBase.doesStringStartWith(var2, ((ResourceLocation)var6).getResourcePath())) continue;
                    var3.add(String.valueOf(var6));
                }
            }
        }
        return var3;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    public static void notifyOperators(ICommandSender sender, ICommand command, String msgFormat, Object ... msgParams) {
        CommandBase.notifyOperators(sender, command, 0, msgFormat, msgParams);
    }

    public static void notifyOperators(ICommandSender sender, ICommand command, int p_152374_2_, String msgFormat, Object ... msgParams) {
        if (theAdmin != null) {
            theAdmin.notifyOperators(sender, command, p_152374_2_, msgFormat, msgParams);
        }
    }

    public static void setAdminCommander(IAdminCommand command) {
        theAdmin = command;
    }

    public int compareTo(ICommand p_compareTo_1_) {
        return this.getCommandName().compareTo(p_compareTo_1_.getCommandName());
    }

    public int compareTo(Object p_compareTo_1_) {
        return this.compareTo((ICommand)p_compareTo_1_);
    }

    public static class CoordinateArg {
        private final double field_179633_a;
        private final double field_179631_b;
        private final boolean field_179632_c;
        private static final String __OBFID = "CL_00002365";

        protected CoordinateArg(double p_i46051_1_, double p_i46051_3_, boolean p_i46051_5_) {
            this.field_179633_a = p_i46051_1_;
            this.field_179631_b = p_i46051_3_;
            this.field_179632_c = p_i46051_5_;
        }

        public double func_179628_a() {
            return this.field_179633_a;
        }

        public double func_179629_b() {
            return this.field_179631_b;
        }

        public boolean func_179630_c() {
            return this.field_179632_c;
        }
    }

}
