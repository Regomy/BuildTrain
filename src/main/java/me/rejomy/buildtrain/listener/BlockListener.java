package me.rejomy.buildtrain.listener;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.island.Island;
import me.rejomy.buildtrain.util.ColorUtil;
import me.rejomy.buildtrain.util.PlayerUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void on(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerData data = Main.getInstance().USERS.get(player);
        Island island = data.island;

        if (island == null) {
            return;
        } else if(data.spectator) {
            event.setCancelled(true);
            return;
        }

        if(data.oneStack) {
            int slot = player.getInventory().getHeldItemSlot();
            ItemStack item = player.getInventory().getItem(slot);

            if(item.getAmount() + 1 <= item.getType().getMaxStackSize()) {
                item.setAmount(item.getAmount() + 1);
            }

            //player.getInventory().setItem(slot, item);
        }

        if (island.placedBlocks.isEmpty()) {

            data.taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(),
                    () -> data.currentTime += 100, 2, 2);
        }

        island.placedBlocks.add(event.getBlock());
    }

    @EventHandler(ignoreCancelled = true)
    public void on(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.hasBlock()) {
            return;
        }

        Player player = event.getPlayer();
        Island island = Main.getInstance().USERS.get(player).island;

        if (island == null) {
            return;
        }

        ItemStack inHand = player.getInventory().getItemInHand();

        if (inHand != null && inHand.getType() == Material.REDSTONE_COMPARATOR) {
            event.setCancelled(true);
            Main.getInstance().playerUtil
                    .sendMessage(player, Main.getInstance().getConfig().getString("message.cant place"));
        }
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            Block block = event.getBlock();
            // Проверяем, что это блок, который вы хотите обрабатывать
            if (fallingBlock.getWorld().getName().equals("bt_world")) {
                // Удаляем блок после падения через 1 тик (20 мс)
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> block.setType(Material.AIR), 1);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void on(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Island island = Main.getInstance().USERS.get(player).island;

        if (island == null) {
            return;
        }

        event.setCancelled(true);
    }
}
