package me.rejomy.buildtrain.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ItemBuilder {
    public final ItemStack item;
    public final ItemMeta meta;
    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
    }
    public ItemBuilder(ItemStack item) {
        this.item = item;
        meta = item.getItemMeta();
    }
    public void setMeta() {
        item.setItemMeta(meta);
    }
    public void setName(String name) {
        meta.setDisplayName(name);
        setMeta();
    }
}
