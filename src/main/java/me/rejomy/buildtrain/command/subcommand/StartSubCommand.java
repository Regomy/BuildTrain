package me.rejomy.buildtrain.command.subcommand;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.command.SubCommand;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.island.Island;
import me.rejomy.buildtrain.island.IslandData;
import me.rejomy.buildtrain.island.IslandManager;
import me.rejomy.buildtrain.util.PlayerUtil;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StartSubCommand extends SubCommand {
    World world = Bukkit.getWorld("bt_world");
    Random random = new Random();
    IslandManager manager = Main.getInstance().getIslandManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;

        if (isConsole(sender)) {
            if (args.length != 2) {
                sendSwap(sender, ErrorType.ARGS.error, "$current", String.valueOf(args.length),
                        "$need", "2", "$format", "start name");
                return false;
            }

            player = Bukkit.getPlayer(args[1]);

            if (player == null) {
                sendMessage(sender, "&7Player " + args[1] + "&c not found!");
                return false;
            }

            if (world == null) {
                String worlds = Bukkit.getWorlds().stream().map(World::getName)
                        .collect(Collectors.joining(", "));

                Main.getInstance().getLogger().severe("World `bt_world` is not found" +
                        "\nLoaded worlds:" + worlds);

                Bukkit.getPluginManager().disablePlugin(Main.getInstance());
                return false;
            }

            sendMessage(sender, "Player " + player.getName() + " success started to " + world.getName());
        } else {
            player = (Player) sender;
        }

        List<IslandData> islandTypes = Main.getInstance().getIslandManager().islands;

        if(islandTypes.isEmpty()) {
            sendMessage(sender, "Islands not found on island folder!");
            return false;
        }

        PlayerData data = Main.getInstance().USERS.get(player);
        String islandType = data.islandType;

        if(data.island != null) {
            sendMessage(sender, Main.getInstance().getConfig().getString("message.already have"));
            Main.getInstance().playerUtil.playSound(player, "sound.deny", Main.INSTANCE.getConfig());
            return false;
        }

        Island island = data.island =
                manager.spawn(
                    islandType.isEmpty()? manager.islands.get(random.nextInt(
                            manager.islands.size()
                    )).type : islandType
                );

        player.teleport(island.spawnPlayerLoc);
        player.getInventory().clear();
        PlayerUtil.clearPotions(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);

        player.getInventory().addItem(new ItemStack(data.inventory.CURRENT_BLOCK, 128));

        player.getInventory().setItem(8, new ItemStack(Material.REDSTONE_COMPARATOR, 1));

        sendMessage(sender, Main.getInstance().getConfig().getString("message.teleport"));
        Main.getInstance().playerUtil.playSound(player, "sound.start", Main.INSTANCE.getConfig());

        return false;
    }

}