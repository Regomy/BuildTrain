package me.rejomy.buildtrain.command.subcommand;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class ToWorldSubCommand extends SubCommand {
    World world = Bukkit.getWorld("bt_world");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;

        if(isConsole(sender)) {
            if(args.length != 2) {
                sendSwap(sender, ErrorType.ARGS.error, "$current", String.valueOf(args.length),
                        "$need", "2", "$format", "toworld name");
                return false;
            }

            player = Bukkit.getPlayer(args[1]);

            if(player == null) {
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

            sendMessage(sender, "Player " + player.getName() + " success teleported to " + world.getName());
        } else {
            player = (Player) sender;
        }

        player.teleport(new Location(world, 0, 128, 0));
        sendMessage(player, "&aYou are success teleported to world " + world.getName() + "!");

        return false;
    }
}
