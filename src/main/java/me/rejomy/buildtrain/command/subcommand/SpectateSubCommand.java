package me.rejomy.buildtrain.command.subcommand;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.command.SubCommand;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.util.file.impl.LobbyFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateSubCommand extends SubCommand {
    private LobbyFile file = (LobbyFile) Main.getInstance().getFileManager().getFileByName("spawn");
    World world = Bukkit.getWorld("bt_world");
    public SpectateSubCommand() {
        super(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        PlayerData data = Main.getInstance().USERS.get(player);

        if(!data.spectator) {
            Bukkit.getScheduler().cancelTask(data.taskId);

            for (Player people : world.getPlayers()) {
                if (people == player) {
                    continue;
                }

                people.hidePlayer(player);
            }
        } else {
            for (Player people : Bukkit.getOnlinePlayers()) {
                if (people == player) {
                    continue;
                }

                people.showPlayer(player);
            }

            player.teleport(data.island.spawnPlayerLoc);
        }

        String action = data.spectator? "quit" : "join";
        sendMessage(sender, Main.getInstance().getConfig().getString("message.spectator." + action));
        Main.getInstance().playerUtil.playSound(player, "sound.spectator." + action, Main.INSTANCE.getConfig());

        player.setAllowFlight(!data.spectator);
        player.setFlying(!data.spectator);

        data.spectator = !data.spectator;

        return false;
    }
}
