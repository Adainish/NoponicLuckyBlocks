package gg.oddysian.adenydd.noponicluckyblocks.obj;

import com.google.common.reflect.TypeToken;
import gg.oddysian.adenydd.noponicluckyblocks.NoponicLuckyBlocks;
import gg.oddysian.adenydd.noponicluckyblocks.config.Config;
import gg.oddysian.adenydd.noponicluckyblocks.utils.ItemBuilder;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LuckyBlock {
    public static List<LuckyBlocks> luckyBlocks = new ArrayList<>();

    public static void loadLuckyBlocks() {
        luckyBlocks.clear();
        CommentedConfigurationNode rootNode = Config.getConfig().get().getNode("LuckyBlocks");
        Map nodeMap = rootNode.getChildrenMap();

        for (Object nodeObject : nodeMap.keySet()) {
            if (nodeObject == null) {
                NoponicLuckyBlocks.log.info(nodeObject + "LUCKYBLOCK NULL");
            } else {
                String node = nodeObject.toString();
                if (node == null) {
                    NoponicLuckyBlocks.log.info(node + "LUCKY BLOCK NULL");
                } else {
                    NoponicLuckyBlocks.log.info(node + "NEW LUCKY BLOCK ADDED");
                    luckyBlocks.add(new LuckyBlocks(node));
                }
            }
        }

    }
    public static List<String> luckyBlocklist() {
        return luckyBlocks.stream().map(commanditem -> commanditem.key).collect(Collectors.toList());
    }

    public static boolean isLuckyBlock(String name) {
        Iterator var1 = luckyBlocks.iterator();

        LuckyBlocks item;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            item = (LuckyBlocks) var1.next();
        } while (!item.key.equalsIgnoreCase(name));

        return true;
    }

    public static LuckyBlocks getLuckyBlock(String key) {
        Iterator var1 = luckyBlocks.iterator();

        LuckyBlocks item;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            item = (LuckyBlocks) var1.next();
        } while (!item.key.equalsIgnoreCase(key));

        return item;
    }

    public static class LuckyBlocks {

        public String key;
        public String display;
        public String itemString = "minecraft:gold_block";
        public List<String> commands;
        public String permission = "DEFAULT IMAGE";
        public List<String> Lore;

        public LuckyBlocks(String key) {
            this.key = key;
            this.itemString = Config.getConfig().get().getNode("LuckyBlocks", key, "ItemString").getString();
            this.permission = Config.getConfig().get().getNode("LuckyBlocks", key, "Permission").getString();
            this.display =  Config.getConfig().get().getNode("LuckyBlocks", key, "Display").getString();
            try {
                this.Lore = Config.getConfig().get().getNode("LuckyBlocks", key, "Lore").getList(TypeToken.of(String.class));
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        }

        public String getFormattedName() {
            return this.display.replaceAll("&", "§");
        }

        public List<String> getFormattedText() {
            List<String> formattedInfo = new ArrayList<>();
            for (String s: this.Lore) {
                formattedInfo.add(s.replaceAll("&", "§"));
            }
            return formattedInfo;
        }
        public ItemStack getItem() {
            String itemID = this.itemString.split(":")[0] + ":" + this.itemString.split(":")[1];
            net.minecraft.item.ItemStack mcItem = (new ItemBuilder(new net.minecraft.item.ItemStack(Item.getByNameOrId(itemID), 1))).setLore(getFormattedText().toArray(new String[0])).setName(this.getFormattedName()).build();
            NBTTagCompound tag = mcItem.hasTagCompound() ? mcItem.getTagCompound() : new NBTTagCompound();
            tag.setBoolean("LuckyBlock", true);
            tag.setString("LuckyBlockType", key);
            if (itemID.toLowerCase().contains("potion")) {
                String potionType = this.itemString.split(":")[2];
                tag.setString("Potion", potionType.toLowerCase().replaceAll(" ", "_"));
                tag.setInteger("HideFlags", 63);
            }
            mcItem.setTagCompound(tag);
            mcItem.setStackDisplayName(this.getFormattedName());
            return mcItem;
        }
    }
}
