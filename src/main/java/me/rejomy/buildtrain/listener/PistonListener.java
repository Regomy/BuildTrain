package me.rejomy.buildtrain.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class PistonListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPistonMove(BlockPistonExtendEvent event) {
        World world = event.getBlock().getWorld();

        if(world.getName().equals("bt_world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPistonMove(BlockPistonRetractEvent event) {
        World world = event.getBlock().getWorld();

        if(world.getName().equals("bt_world")) {
            event.setCancelled(true);
        }
    }
}
