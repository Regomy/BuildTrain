package me.rejomy.buildtrain.command;

import me.rejomy.buildtrain.command.subcommand.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuildTrainCommand extends me.rejomy.buildtrain.command.Command {
    private List<SubCommand> subCommands = new ArrayList<>();
    public BuildTrainCommand() {
        subCommands.add(new ToWorldSubCommand());
        subCommands.add(new StartSubCommand());
        subCommands.add(new SettingSubCommand());
        subCommands.add(new StopSubCommand());
        subCommands.add(new LobbySubCommand());
        subCommands.add(new SpectateSubCommand());

        String subs = subCommands.stream().map(value ->
                value.getClass().getSimpleName().replace("SubCommand", ""))
                .collect(Collectors.joining(", "));

        help = "&a/buildtrain " + subs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendMessage(sender, help);
            return false;
        }

        for(SubCommand sub : subCommands) {
            String name = sub.getClass().getSimpleName().replace("SubCommand", "");

            if(!args[0].equalsIgnoreCase(name)) {
                continue;
            }

            if(sub.onlyPlayer && isConsole(sender)) {
                sendSwap(sender, ErrorType.IS_NOT_PLAYER.error);
                continue;
            }

            String permission = "buildtrain.command." + name;

            if(!hasPermission(sender, permission)) {
                sendSwap(sender, ErrorType.NO_PERMISSION.error, "$permission", permission);
                continue;
            }

            sub.onCommand(sender, command, label, args);
        }

        return false;
    }

    private String help;

}
