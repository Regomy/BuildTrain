package me.rejomy.buildtrain.listener;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.util.file.impl.LobbyFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = player.getItemInHand();

        if(item == null) {
            return;
        }

        PlayerData data = Main.getInstance().USERS.get(player);

        if(data.island == null) {
            return;
        }

        if(item.getType() == Material.REDSTONE_COMPARATOR) {
            player.openInventory(data.inventory.getCurrent());
        }
    }

}
