package me.rejomy.buildtrain.command.subcommand;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.command.SubCommand;
import me.rejomy.buildtrain.island.CreateData;
import me.rejomy.buildtrain.island.IslandData;
import me.rejomy.buildtrain.island.IslandManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class SettingSubCommand extends SubCommand {
    IslandManager manager = Main.getInstance().getIslandManager();

    public SettingSubCommand() {
        super(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sendSwap(sender, ErrorType.ARGS.error, "$current", String.valueOf(args.length),
                    "$need", "2", "$format", "setting value");
            sendMessage(sender, " &f/buildtrain setting - create name, pos1, pos2");
            return false;
        }

        Player player = (Player) sender;

        switch (args[1]) {
            case "create": {
                if (args.length != 3) {
                    sendSwap(sender, ErrorType.ARGS.error, "$current", String.valueOf(args.length),
                            "$need", "3", "$format", "setting value");
                    return false;
                }

                for (IslandData data : manager.islands) {
                    if (data.type.equalsIgnoreCase(args[2])) {
                        sender.sendMessage("Error! Island " + args[2] + " has been exists!");
                        return false;
                    }
                }

                CreateData data = new CreateData();
                data.type = args[2];

                Main.getInstance().USERS.get(sender).create = data;

                sender.sendMessage("Island " + args[2] + " has been created!");
                sender.sendMessage("Use /save " + args[2] + " after confirm all settings!");

                break;
            }

            case "pos1": {
                CreateData data = Main.getInstance().USERS.get(player).create;

                if (data == null) {
                    sendMessage(sender, "&fYou are not have create island! Use /buildtrain create name");
                    return false;
                }

                data.pos1 = player.getLocation();
                sendMessage(sender, "&aPos1 for " + data.type + " has been set!");
                break;
            }
            case "pos2": {
                CreateData data = Main.getInstance().USERS.get(player).create;

                if (data == null) {
                    sendMessage(sender, "&fYou are not have create island! Use /buildtrain create name");
                    return false;
                }

                data.pos2 = player.getLocation();
                sendMessage(sender, "&aPos2 for " + data.type + " has been set!");
                break;
            }

            case "icon": {
                CreateData data = Main.getInstance().USERS.get(player).create;

                if (data == null) {
                    sendMessage(sender, "&fYou are not have create island! Use /buildtrain create name");
                    return false;
                }

                ItemStack hand = player.getItemInHand();

                if(hand == null) {
                    sendMessage(sender, "&cYou are does not have item in your hand!");
                    return false;
                }

                data.icon = hand.getType();
                sendMessage(sender, "&aIcon for " + data.type + " has been set!");
                break;
            }

            case "spawn": {
                CreateData data = Main.getInstance().USERS.get(player).create;

                if(data == null) {
                    sendMessage(sender, "&fYou are not have create island! Use /buildtrain create name");
                    return false;
                }

                data.spawnPlayerLoc = player.getLocation();
                sendMessage(sender, "&aSpawn for " + data.type + " has been set!");

                break;
            }

            case "save": {
                CreateData data = Main.getInstance().USERS.get(player).create;

                if(data == null) {
                    sendMessage(sender, "&fYou are not have create island! Use /buildtrain create name");
                    return false;
                }

                File directory = new File(Main.getInstance().getDataFolder(), "island");
                File island = new File(directory, data.type + ".yml");

                if(!island.exists()) {
                    try {
                        island.createNewFile();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }

                YamlConfiguration config = YamlConfiguration.loadConfiguration(island);

                Location spawn = data.spawnPlayerLoc;
                config.set("spawn.x", spawn.getX());
                config.set("spawn.y", spawn.getY());
                config.set("spawn.z", spawn.getZ());
                config.set("spawn.yaw", spawn.getYaw());
                config.set("spawn.pitch", spawn.getPitch());
                config.set("spawn.world", spawn.getWorld().getName());

                int
                        minX = Math.min(data.pos1.getBlockX(), data.pos2.getBlockX()),
                        minY = Math.min(data.pos1.getBlockY(), data.pos2.getBlockY()),
                        minZ = Math.min(data.pos1.getBlockZ(), data.pos2.getBlockZ()),
                        maxX = Math.max(data.pos1.getBlockX(), data.pos2.getBlockX()),
                        maxY = Math.max(data.pos1.getBlockY(), data.pos2.getBlockY()),
                        maxZ = Math.max(data.pos1.getBlockZ(), data.pos2.getBlockZ());

                config.set("min.x", minX);
                config.set("min.y", minY);
                config.set("min.z", minZ);

                config.set("max.x", maxX);
                config.set("max.y", maxY);
                config.set("max.z", maxZ);

                int line = 0;
                for(int x = minX; x <= maxX; x++) {
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            Block block = new Location(spawn.getWorld(), x, y, z).getBlock();

                            if(block.getType() == Material.AIR) {
                                continue;
                            }

                            config.set("blocks." + line + ".x", x);
                            config.set("blocks." + line + ".y", y);
                            config.set("blocks." + line + ".z", z);

                            config.set("blocks." + line + ".type", block.getType().name());
                            config.set("blocks." + line + ".rotation", block.getData());

                            line++;
                        }
                    }
                }

                config.set("icon", data.icon.name());

                sendMessage(sender, "&aIsland success save!");
                Main.getInstance().USERS.get(player).create = null;

                try {
                    config.save(island);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
            }

        }

        return false;
    }

}