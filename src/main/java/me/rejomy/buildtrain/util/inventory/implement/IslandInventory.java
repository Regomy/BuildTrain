package me.rejomy.buildtrain.util.inventory.implement;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.DataManager;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.island.IslandData;
import me.rejomy.buildtrain.island.IslandManager;
import me.rejomy.buildtrain.util.ColorUtil;
import me.rejomy.buildtrain.util.InventoryFileUtil;
import me.rejomy.buildtrain.util.ItemBuilder;
import me.rejomy.buildtrain.util.file.impl.IslandInventoryFile;
import me.rejomy.buildtrain.util.inventory.InventoryBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

import static me.rejomy.buildtrain.util.ColorUtil.toColor;

public class IslandInventory extends InventoryBuilder {
    private static final IslandInventoryFile FILE = new IslandInventoryFile();
    public IslandInventory() {
        fill();
    }
    @Override
    public void fill() {
        create((String) FILE.VALUES.get("name"), 54);
        IslandManager manager = Main.INSTANCE.getIslandManager();

        for(byte index = 0; index < manager.islands.size(); index++) {
            IslandData data = manager.islands.get(index);

            ItemBuilder builder = new ItemBuilder(data.icon);

            builder.setName(ColorUtil.toColor("&e&l" + data.type));

            getCurrent().setItem(index, builder.item);
        }

        for(String path : FILE.getYaml().getConfigurationSection("items").getKeys(false)) {
            setItemFromConfig("items." + path);
        }
    }

    @Override
    public void onClick(Player player, Inventory inventory, InventoryClickEvent event) {
        if(InventoryFileUtil.runCommands(player, event.getSlot(), FILE)) {
            return;
        }

        PlayerData data = Main.getInstance().USERS.get(player);

        if(event.getCurrentItem().getItemMeta().getDisplayName().contains(data.islandType)) {
            player.sendMessage(ColorUtil.toColor(Main.getInstance().getConfig().getString("message.same-island")));
            Main.getInstance().playerUtil.playSound(player, Main.getInstance().getConfig().getString("sound.deny"));

            return;
        }

        player.chat("/bt stop");
        player.chat("/bt start");
    }

    private void setItemFromConfig(String path) {
        Material material = Material.valueOf((String) FILE.VALUES.get(path + ".type"));

        ItemBuilder builder = new ItemBuilder(material);

        builder.setName(toColor((String) FILE.VALUES.get(path + ".name")));
        List<String> lore = (List<String>) FILE.VALUES.get(path + ".lore");
        lore.replaceAll(ColorUtil::toColor);
        builder.meta.setLore(lore);
        builder.setMeta();
        getCurrent().setItem((Integer) FILE.VALUES.get(path + ".slot"), builder.item);
    }
}
