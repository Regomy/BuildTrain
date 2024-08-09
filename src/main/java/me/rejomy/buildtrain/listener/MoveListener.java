package me.rejomy.buildtrain.listener;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.island.Island;
import me.rejomy.buildtrain.island.IslandManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        PlayerData data = Main.getInstance().USERS.get(player);

        if(data.spectator) {
            return;
        }

        Island island = data.island;

        if (island != null) {

            Location to = event.getTo();

            if(to.getZ() > island.maxZ + 20 || to.getZ() < island.minZ
                || to.getX() > island.maxX || to.getX() < island.minX
                    || island.minY > to.getY()) {

                Main.getInstance().playerUtil.reset(player);
                Main.getInstance().playerUtil.playSound(player, "sound.fail", Main.getInstance().getConfig());
            } else if(island.spawnPlayerLoc.getBlockZ() + 20 == to.getBlockZ()
                    && Math.abs(player.getLocation().getY() - island.spawnPlayerLoc.getY()) < 1.5) {
                Main.getInstance().playerUtil.finish(player);
            }

        }
    }
}
