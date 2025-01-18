package me.rejomy.buildtrain.util;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.DataManager;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

public class PlayerUtil {
    public void playSound(Player player, String path, MemoryConfiguration config) {
        if(config.get(path) == null) {
            return;
        }

        String name = config.getString(path);

        playSound(player, name);
    }

    public void playSound(Player player, String soundName) {
        if(soundName.equalsIgnoreCase("none")) {
            return;
        }

        try {
            Sound sound = Sound.valueOf(soundName);

            player.playSound(player.getLocation(), sound, 2f, 2f);
        } catch (IllegalArgumentException exception) {
            Main.getInstance().getLogger().info("Sound " + soundName + " not found!");
            Main.getInstance().getLogger().info("Server version: " + Bukkit.getVersion());
        }

    }

    public void sendMessage(Player player, String message) {
        if(message.isEmpty()) {
            return;
        }

        player.sendMessage(ColorUtil.toColor(message));
    }

    public void reset(Player player) {
        PlayerData data = Main.getInstance().USERS.get(player);
        Bukkit.getScheduler().cancelTask(data.taskId);
        data.currentTime = 0;

        Island island = data.island;
        island.reset();

        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(data.inventory.CURRENT_BLOCK, 128));
        player.getInventory().setItem(8, new ItemStack(Material.REDSTONE_COMPARATOR, 1));

        player.teleport(island.spawnPlayerLoc);
        player.setNoDamageTicks(10);
    }

    public void finish(Player player) {
        PlayerData data = Main.getInstance().USERS.get(player);

        Bukkit.getScheduler().cancelTask(data.taskId);

        double timeSeconds = ((int) ((data.currentTime / 1000.0) * 100)) / 100.0;

        sendMessage(player, Main.getInstance().getConfig().getString("message.finish")
                .replace("$time", String.valueOf(timeSeconds)));

        playSound(player, "sound.finished", Main.getInstance().getConfig());

        if (timeSeconds < data.sessionBestTime) {
            data.sessionBestTime = timeSeconds;

            // Handle for top
            if (!Main.getInstance().getDataManager().bestForSession.contains(data)) {
                Main.getInstance().getDataManager().bestForSession.add(data);
            }

            Main.getInstance().getDataManager().bestForSession = Main.getInstance().getDataManager().bestForSession.stream()
                    .sorted(Comparator.comparingDouble(userData -> userData.sessionBestTime)).collect(Collectors.toList());
            // Handle for top - close
        }

        if(data.currentTime < data.bestTime) {
            double oldRecord = ((int) ((data.bestTime / 1000.0) * 100)) / 100.0;

            sendMessage(player, Main.getInstance().getConfig().getString("message.beat-record")
                    .replace("$lastRecord", String.valueOf(oldRecord))
                    .replace("$record", String.valueOf(timeSeconds)));

            data.bestTime = data.currentTime;
            data.currentTime = 0;
        }

        reset(player);
    }

    public static void clearPotions(Player player) {
        for(PotionEffect effect : new ArrayList<>(player.getActivePotionEffects())) {
            player.removePotionEffect(effect.getType());
        }
    }
}
