package me.rejomy.buildtrain.command.subcommand;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.command.SubCommand;
import me.rejomy.buildtrain.util.file.impl.LobbyFile;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbySubCommand extends SubCommand {
    private LobbyFile file = (LobbyFile) Main.getInstance().getFileManager().getFileByName("spawn");
    public LobbySubCommand() {
        super(true);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sendSwap(sender, ErrorType.ARGS.error, "$current", String.valueOf(args.length),
                    "$need", "1", "$format", "lobby");
            sendMessage(sender, " &f/buildtrain lobby");
            return false;
        }

        Player player = (Player) sender;

        if(player.isOp()) {
            Location location = player.getLocation();

            file.getYaml().set("world", location.getWorld().getName());
            file.getYaml().set("x", location.getX());
            file.getYaml().set("y", location.getYaw());
            file.getYaml().set("z", location.getZ());
            file.getYaml().set("yaw", location.getYaw());
            file.getYaml().set("pitch", location.getPitch());

            file.loadValues();

            sender.sendMessage("Spawn has been set!");
        }

        return false;
    }
}
