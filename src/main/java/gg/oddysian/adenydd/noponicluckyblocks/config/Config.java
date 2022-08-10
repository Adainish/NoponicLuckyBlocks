package gg.oddysian.adenydd.noponicluckyblocks.config;

import lombok.SneakyThrows;

import java.util.Arrays;

public class Config extends Configurable {
    private static Config config;

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void setup() {
        super.setup();
    }

    public void load() {
        super.load();
    }
    @SneakyThrows
    public void populate() {
        this.get().getNode("ServerPrefix").setValue("§7[§6Lucky Blocks§7]").setComment("Server Prefix");
        this.get().getNode("LuckyBlocks", "example", "Display").setValue("&eLucky Block").setComment("Item Display Name");
        this.get().getNode("LuckyBlocks", "example", "Lore").setValue(Arrays.asList("&cI'm a lucky block!", "&eI contain either something good or bad!")).setComment("Item Lore");
        this.get().getNode("LuckyBlocks", "example", "ItemString").setValue("minecraft:gold_block").setComment("ItemString we're using");
        this.get().getNode("LuckyBlocks", "example", "EnabledRewards").setValue(Arrays.asList("example", "example2")).setComment("Enabled Rewards");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example", "Chance").setValue(1).setComment("chance of the reward appearing");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example", "Message").setValue("&cOh! It seems you got a jar of dirt! ...., Without the jar").setComment("Message send when this reward drops");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example", "SendMessage").setValue(true).setComment("Toggle the message send");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example", "Commands").setValue(Arrays.asList("give @pl minecraft:dirt 1", "message @pl hi")).setComment("Commands executed for this reward");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example2", "Chance").setValue(1).setComment("chance of the reward appearing");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example2", "Message").setValue("&cOh! It seems you got a jar of dirt! ...., Without the jar").setComment("Message send when this reward drops");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example2", "SendMessage").setValue(true).setComment("Toggle the message send");
        this.get().getNode("LuckyBlocks", "example", "Rewards", "example2", "Commands").setValue(Arrays.asList("give @pl minecraft:dirt 1", "message @pl hi")).setComment("Commands executed for this reward");
    }

    public String getConfigName() {
        return "LuckyBlocks.conf";
    }

    public Config() {
    }
}
