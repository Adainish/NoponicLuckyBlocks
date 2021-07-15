package gg.oddysian.adenydd.noponicluckyblocks.utils;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.List;

public class ServerUtils {
    private static final MinecraftServer SERVER = FMLCommonHandler.instance().getMinecraftServerInstance();

    public static void send(ICommandSender sender, String message) {
        sender.sendMessage(new TextComponentString((message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")));
    }
    public static void runCommand(String cmd) {
        SERVER.getCommandManager().executeCommand(SERVER, cmd);
    }

    public static MinecraftServer getInstance() {
        return SERVER;
    }

    public static String getFormattedDisplayName(String s) {
        return s.replaceAll("&", "§");
    }

    public static List<String> getFormattedText(List<String> unformatted) {
        List<String> formattedInfo = new ArrayList<>();
        for (String s:unformatted) {
            formattedInfo.add(s.replaceAll("&", "§"));
        }
        return formattedInfo;
    }

}
