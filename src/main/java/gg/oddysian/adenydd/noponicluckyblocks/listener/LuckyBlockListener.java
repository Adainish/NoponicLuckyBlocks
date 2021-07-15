package gg.oddysian.adenydd.noponicluckyblocks.listener;

import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.RandomHelper;
import gg.oddysian.adenydd.noponicluckyblocks.config.Config;
import gg.oddysian.adenydd.noponicluckyblocks.obj.LuckyBlock;
import gg.oddysian.adenydd.noponicluckyblocks.utils.ServerUtils;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class LuckyBlockListener {
    public LuckyBlockListener() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLuckyBlockConsumption(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled())
            return;

        List<String> entrylist = new ArrayList<>();

        EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();

        if (event.getHand().equals(EnumHand.MAIN_HAND) && event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().getBoolean("LuckyBlock")) {
            String key = event.getItemStack().getTagCompound().getString("LuckyBlockType");
            LuckyBlock.LuckyBlocks luckyBlock = LuckyBlock.getLuckyBlock(key);
            if(!event.getItemStack().getDisplayName().equals(ServerUtils.getFormattedDisplayName(luckyBlock.display)))
                return;

            try {
                for (String string : Config.getConfig().get().getNode("LuckyBlocks", key, "EnabledRewards").getList(TypeToken.of(String.class))) {
                    int addtimes = Config.getConfig().get().getNode("LuckyBlocks", key, "Rewards", string, "Chance").getInt();
                    for (int i = 0; i < addtimes; i++) {
                        entrylist.add(string);
                    }
                }
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }

            List<String> cmds = new ArrayList<>();
            String reward = RandomHelper.getRandomElementFromList(entrylist);
            try {
                cmds = Config.getConfig().get().getNode("LuckyBlocks", key, "Rewards", reward, "Commands").getList(TypeToken.of(String.class));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }

            if(Config.getConfig().get().getNode("LuckyBlocks", key, "Rewards", reward, "SendMessage").getBoolean())
                ServerUtils.send(player, Config.getConfig().get().getNode("LuckyBlocks", key, "Rewards", reward, "Message").getString());

            for (String a: cmds) {
                ServerUtils.runCommand(a.replaceAll("@pl", player.getName()));
            }

            event.getItemStack().shrink(1);

            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onItemPlaceAttempt(PlayerInteractEvent.RightClickBlock event) {
        if(event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().getBoolean("LuckyBlock"))
            event.setCanceled(true);
    }
}
