package me.rejomy.buildtrain.command.subcommand;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.command.SubCommand;
import me.rejomy.buildtrain.island.Island;
import me.rejomy.buildtrain.util.file.impl.LobbyFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopSubCommand extends SubCommand {
    private final LobbyFile file = (LobbyFile) Main.getInstance().getFileManager().getFileByName("spawn");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean senderIsTarget = false;
        Player player;

        if (isConsole(sender)) {
            if (args.length != 2) {
                sendSwap(sender, ErrorType.ARGS.error, "$current", String.valueOf(args.length),
                        "$need", "2", "$format", "stop name");
                return false;
            }

            player = Bukkit.getPlayer(args[1]);

            if (player == null) {
                sendMessage(sender, "&7Player " + args[1] + "&c not found!");
                return false;
            }
        } else {
            player = (Player) sender;
            senderIsTarget = true;
        }

        Island island = Main.getInstance().USERS.get(player).island;

        if (island == null) {
            if (senderIsTarget) {
                sendMessage(sender, "You should be played an arena for do it.");
            } else {
                sendMessage(sender, "&cPlayer " + player.getName() + " not train his builder skill.");
            }

            return false;
        }

        player.getInventory().clear();
        player.teleport(file.spawn);

        sendMessage(sender, Main.getInstance().getConfig().getString("message.remove"));

        Main.getInstance().getDataManager().remove(player);
        Main.getInstance().playerUtil.playSound(player, "sound.exit", Main.INSTANCE.getConfig());

        return false;
    }
}
