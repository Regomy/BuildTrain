package me.rejomy.buildtrain.listener;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        if(event.getFrom().getWorld().getName().equalsIgnoreCase("bt_world")
            && event.getFrom().getWorld() != event.getTo().getWorld())
        {
            Player player = event.getPlayer();
            PlayerData data = Main.getInstance().USERS.get(player);

            if(data.spectator) {
                player.setFlying(false);
                player.setAllowFlight(false);

                for(Player people : Bukkit.getOnlinePlayers()) {
                    people.showPlayer(player);
                }
            }

            Main.getInstance().getDataManager().remove(player);
        }
    }

}
