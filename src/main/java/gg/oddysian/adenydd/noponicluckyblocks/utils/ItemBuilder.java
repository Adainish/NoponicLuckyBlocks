package gg.oddysian.adenydd.noponicluckyblocks.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ItemBuilder {
    private ItemStack stack;

    public ItemBuilder(Item item, int amount) {
        this.stack = new ItemStack(item, amount);
    }

    public ItemBuilder(Item item, String itemName, int amount) {
        this.stack = new ItemStack(item, amount);
        this.stack.setStackDisplayName(itemName);
    }

    public ItemBuilder(ItemStack stack) {
        this.stack = stack;
    }

    public ItemBuilder(Item item, String itemName, int amount, short meta) {
        this.stack = new ItemStack(item, amount, meta);
        this.stack.setStackDisplayName(itemName);
    }

    public ItemBuilder() {
    }

    public ItemBuilder(Item item) {
        this(item, 1);
    }

    public ItemBuilder(Block block) {
        this(Item.getItemFromBlock(block), 1);
    }

    public ItemBuilder setStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.stack.setStackDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... loreArray) {
        NBTTagCompound nbt = this.stack.hasTagCompound() ? this.stack.getTagCompound() : new NBTTagCompound();
        NBTTagList lore = new NBTTagList();
        String[] var4 = loreArray;
        int var5 = loreArray.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String line = var4[var6];
            lore.appendTag(new NBTTagString(line));
        }

        NBTTagCompound display = nbt.hasKey("display") ? nbt.getCompoundTag("display") : new NBTTagCompound();
        display.setTag("Lore", lore);
        nbt.setTag("display", display);
        this.stack.setTagCompound(nbt);
        return this;
    }

    public ItemStack build() {
        return this.stack;
    }

    public ItemBuilder setEnchanted() {
        this.stack.addEnchantment(Enchantments.FORTUNE, 1);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("HideFlags", 4);
        this.stack.setTagCompound(nbt);
        return this;
    }
}
