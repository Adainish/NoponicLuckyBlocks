package gg.oddysian.adenydd.noponicluckyblocks.commands;

import com.pixelmonmod.pixelmon.RandomHelper;
import gg.oddysian.adenydd.noponicluckyblocks.obj.LuckyBlock;
import gg.oddysian.adenydd.noponicluckyblocks.utils.ServerUtils;
import gg.oddysian.adenydd.noponicluckyblocks.commands.permissions.PermissionManager;
import gg.oddysian.adenydd.noponicluckyblocks.commands.permissions.PermissionUtils;
import gg.oddysian.adenydd.noponicluckyblocks.config.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LuckyBlockCommand extends CommandBase {
    @Override
    public String getName() {
        return "noponicluckyblocks";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return Config.getConfig().get().getNode("ServerPrefix").getString() + TextFormatting.RED + "Use: /" + this.getName() + " reload";
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] arguments) throws CommandException {

        String name;
        int amount;

        if(arguments.length == 0)
            getUsage(sender);

        if(arguments.length == 1 && arguments[0].contains("reload")){
            if (PermissionUtils.canUse(PermissionManager.ADMIN_RELOAD, sender)) {
                Config.getConfig().load();
                LuckyBlock.loadLuckyBlocks();
                ServerUtils.send(sender, "&cReloaded All Configs!, Check the console for Errors!");
            } else ServerUtils.send(sender, "&c(&4!&c) &eYou lack the permission node to use this!");
        }
        if(arguments.length == 4 && arguments[0].contains("give")) {
            if (PermissionUtils.canUse(PermissionManager.ADMIN_GIVE, sender)) {
                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(arguments[1]);
                name = arguments[3];
                if (name.equalsIgnoreCase("random")) {
                    name = RandomHelper.getRandomElementFromList(LuckyBlock.luckyBlocklist());
                }
                amount = Integer.parseInt(arguments[2]);
                if (LuckyBlock.isLuckyBlock(name)) {
                    ItemStack stack = LuckyBlock.getLuckyBlock(name).getItem();
                    stack.setCount(amount);
                    target.addItemStackToInventory(stack);
                } else {
                    ServerUtils.send(sender, name + " &cis not a configured Lucky Block!");
                }
            } else ServerUtils.send(sender, "&c(&4!&c) &eYou lack the permission node to use this!");
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("luckyblocks", "nlb");
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList<>();

        if (args.length == 1) {
            possibleArgs.add("reload");
            possibleArgs.add("give");
        }

        if (args.length == 2) {
            possibleArgs.addAll(Arrays.asList(server.getPlayerList().getOnlinePlayerNames()));
        }

        if (args.length == 3) {
            possibleArgs.add("1");
        }

        if (args.length == 4) {
            possibleArgs.addAll(LuckyBlock.luckyBlocklist());
        }

        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }
}
