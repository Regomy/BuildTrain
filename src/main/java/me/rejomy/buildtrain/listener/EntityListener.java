package me.rejomy.buildtrain.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EntityListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onSpawn(EntitySpawnEvent event) {
        World world = event.getEntity().getWorld();

        if(world.getName().equals("bt_world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onHunger(FoodLevelChangeEvent event) {
        World world = event.getEntity().getWorld();

        if(world.getName().equals("bt_world")) {
            event.setCancelled(true);
        }
    }
}
