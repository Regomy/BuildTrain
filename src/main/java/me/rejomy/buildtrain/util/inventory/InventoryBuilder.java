package me.rejomy.buildtrain.util.inventory;

import me.rejomy.buildtrain.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

import static me.rejomy.buildtrain.util.ColorUtil.toColor;

public abstract class InventoryBuilder implements Listener {
    public InventoryBuilder() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }
    public final List<Inventory> inventories = new ArrayList<>();
    public int currentIndex;
    private Inventory current;
    public Inventory getCurrent() {
        if(current == null) {
            if(inventories.isEmpty()) {
                return null;
            }
            current = inventories.get(0);
            currentIndex = 0;
        }

        return current;
    }
    public abstract void fill();
    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) {
            return;
        }

        Inventory eInv = event.getInventory();

        for(Inventory inventory : inventories) {
            if(eInv.getName().equals(inventory.getName())) {
                onClick((Player) event.getWhoClicked(), eInv, event);
                event.setCancelled(true);
                break;
            }
        }
    }
    public abstract void onClick(Player player, Inventory inventory, InventoryClickEvent event);
    public void create(String name, int size) {
        inventories.add(Bukkit.createInventory(null, size, toColor(name)));
    }
    public Inventory get(int index) {
        if(inventories.size() <= index) {
            throw new IndexOutOfBoundsException("Index=" + index + " size=" + inventories.size());
        }

        current = inventories.get(index);
        currentIndex = index;
        return current;
    }
    public Inventory next() {
        ++currentIndex;

        if(inventories.size() <= currentIndex) {
            currentIndex = 0;
            current = inventories.get(0);
        } else {
            current = inventories.get(currentIndex);
        }

        return current;
    }
}
