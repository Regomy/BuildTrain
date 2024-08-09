package me.rejomy.buildtrain.util.inventory.implement;

import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.util.ColorUtil;
import me.rejomy.buildtrain.util.InventoryFileUtil;
import me.rejomy.buildtrain.util.ItemBuilder;
import me.rejomy.buildtrain.util.file.impl.SettingInventoryFile;
import me.rejomy.buildtrain.util.inventory.InventoryBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

import static me.rejomy.buildtrain.util.ColorUtil.toColor;

public class SettingInventory extends InventoryBuilder {
    private final static SettingInventoryFile SETTING = new SettingInventoryFile();
    public Material CURRENT_BLOCK;
    private PlayerData data;
    public SettingInventory(PlayerData data) {
        this.data = data;
        CURRENT_BLOCK = Material.STONE;
        create((String) SETTING.VALUES.get("name"), 54);
        fill();
    }

    @Override
    public void fill() {
        for(String path : SETTING.getYaml().getConfigurationSection("items").getKeys(false)) {
            setItemFromConfig("items." + path);
        }
    }

    @Override
    public void onClick(Player player, Inventory inventory, InventoryClickEvent event) {
        InventoryFileUtil.runCommands(player, event.getSlot(), SETTING);
    }

    private void setItemFromConfig(String path) {
        Material material;

        if(SETTING.VALUES.get(path + ".type") != null) {
            material = Material.valueOf((String) SETTING.VALUES.get(path + ".type"));
        } else {
            material = CURRENT_BLOCK;
        }

        ItemBuilder builder = new ItemBuilder(material);

        builder.setName(toColor((String) SETTING.VALUES.get(path + ".name")));
        List<String> lore = (List<String>) SETTING.VALUES.get(path + ".lore");
        lore.replaceAll(ColorUtil::toColor);
        builder.meta.setLore(lore);
        builder.setMeta();
        getCurrent().setItem((Integer) SETTING.VALUES.get(path + ".slot"), builder.item);
    }
}
