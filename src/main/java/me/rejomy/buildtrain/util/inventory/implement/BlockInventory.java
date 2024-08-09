package me.rejomy.buildtrain.util.inventory.implement;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.util.ColorUtil;
import me.rejomy.buildtrain.util.InventoryFileUtil;
import me.rejomy.buildtrain.util.ItemBuilder;
import me.rejomy.buildtrain.util.file.impl.BlockInventoryFile;
import me.rejomy.buildtrain.util.inventory.InventoryBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static me.rejomy.buildtrain.util.ColorUtil.toColor;

public class BlockInventory extends InventoryBuilder {
    private static final BlockInventoryFile BLOCK_FILE = new BlockInventoryFile();
    public BlockInventory() {
        fill();
    }
    @Override
    public void fill() {
        List<Material> materials = Arrays.stream(Material.values()).filter(material ->
                material.isSolid() && material.isBlock() &&
                        material != Material.BURNING_FURNACE &&
                        material != Material.SOIL
        ).collect(Collectors.toList());

        int items = BLOCK_FILE.getYaml().getConfigurationSection("items").getKeys(false).size();

        int needInventories = (materials.size() + items) / 54 + 1;

        if(needInventories > 1) {
            for(byte a = 0; a < needInventories; a++) {
                create((String) BLOCK_FILE.VALUES.get("name"), 54);
            }
        }

        int count = 0, offset = 0;

        materials.removeIf(material -> {
            getCurrent().setItem(0, new ItemStack(material));

            boolean remove = getCurrent().getItem(0) == null;

            getCurrent().setItem(0, null);

            return remove;
        });

        slotLoop: for(int slot = 0; slot < materials.size(); slot++) {
            if(slot + offset == 0) {
                for (String path : BLOCK_FILE.getYaml().getConfigurationSection("items").getKeys(false)) {
                    setItemFromConfig("items." + path);
                }
            } else if((slot + offset) % 54 == 0) {
                next();
                ++count;

                for(String path : BLOCK_FILE.getYaml().getConfigurationSection("items").getKeys(false)) {
                    setItemFromConfig("items." + path);
                }
            }

            for(String path : BLOCK_FILE.getYaml().getConfigurationSection("items").getKeys(false)) {
                int itemSlot = BLOCK_FILE.getYaml().getInt("items." + path + ".slot");

                if(slot - count * 54 + offset == itemSlot) {
                    slot--;
                    offset++;
                    continue slotLoop;
                }
            }

            ItemStack item = new ItemStack(materials.get(slot));

            getCurrent().setItem(slot - count * 54 + offset, item);
        }

    }

    @Override
    public void onClick(Player player, Inventory inventory, InventoryClickEvent event) {
        if(InventoryFileUtil.runCommands(player, event.getSlot(), BLOCK_FILE, this)) {
            return;
        }

        PlayerData data = Main.getInstance().USERS.get(player);
        Inventory playerInventory = player.getInventory();

        if(data.island != null) {
            for(byte slot = 0; slot < playerInventory.getSize(); slot++) {
                ItemStack item = playerInventory.getItem(slot);
                if(item != null && item.getType() == data.inventory.CURRENT_BLOCK) {
                    playerInventory.setItem(slot, new ItemStack(event.getCurrentItem().getType(), 64));
                }
            }
        }

        data.inventory.CURRENT_BLOCK = event.getCurrentItem().getType();
        data.inventory.fill();
    }

    private void setItemFromConfig(String path) {
        Material material = Material.valueOf((String) BLOCK_FILE.VALUES.get(path + ".type"));

        ItemBuilder builder = new ItemBuilder(material);

        builder.setName(toColor((String) BLOCK_FILE.VALUES.get(path + ".name")));
        List<String> lore = (List<String>) BLOCK_FILE.VALUES.get(path + ".lore");
        lore.replaceAll(ColorUtil::toColor);
        builder.meta.setLore(lore);
        builder.setMeta();
        getCurrent().setItem((Integer) BLOCK_FILE.VALUES.get(path + ".slot"), builder.item);
    }
}
