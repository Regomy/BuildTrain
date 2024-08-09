package me.rejomy.buildtrain.command;

import me.rejomy.buildtrain.util.ColorUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor {
    private final String BORDER = ColorUtil.toColor("&8          &m-------");
    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(BORDER);
        sender.sendMessage(" " + ColorUtil.toColor(message));
        sender.sendMessage(BORDER);
    }

    public void sendSwap(CommandSender sender, String message, String... replace) {
        if(replace.length > 1) {
            for(int i = 1; i < replace.length; i+=2) {
                message = message.replace(replace[i - 1], replace[i]);
            }
        }

        sendMessage(sender, message);
    }

    public boolean isConsole(CommandSender sender) {
        return sender instanceof ConsoleCommandSender;
    }

    public boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    public boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission);
    }

    public enum ErrorType {
        IS_NOT_PLAYER("&cThis command is only available to the player!"),
        NO_PERMISSION("&cThis command is required the permission $permission"),
        ARGS("&7Args is incorrect &c$current/$need&c! &7Use &a/buildtrain $format");

        public String error;
        ErrorType(String error) {
            this.error = error;
        }
    }

}
