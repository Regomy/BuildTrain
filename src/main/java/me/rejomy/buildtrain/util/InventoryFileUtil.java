package me.rejomy.buildtrain.util;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.util.file.FileBuilder;
import me.rejomy.buildtrain.util.inventory.InventoryBuilder;
import me.rejomy.buildtrain.util.inventory.implement.IslandInventory;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class InventoryFileUtil {
    private static IslandInventory islandInventory;

    static {
        try {
            islandInventory = new IslandInventory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean runCommands(Player player, int slot, FileBuilder file) {
        boolean executed = false;
        for(String path : file.getYaml().getConfigurationSection("items").getKeys(false)) {
            int itemSlot = file.getYaml().getInt("items." + path + ".slot");

            if(slot != itemSlot) {
                continue;
            }

            executed = true;

            for(String command : file.getYaml().getStringList("items." + path + ".command")) {
                if(command.contains("[CLOSE]")) {
                    player.closeInventory();
                } else if (command.contains("[SOUND] ")) {
                    String name = command.replace("[SOUND] ", "");

                    if (name.isEmpty() || name.contains("NONE")) continue;

                    try {
                        Sound sound = Sound.valueOf(name);
                        player.playSound(player.getLocation(), sound, 2f, 2f);
                    } catch (IllegalArgumentException exception) {
                        Main.getInstance().getLogger().info("Sound " + name + " not found!");
                        Main.getInstance().getLogger().info("Server version: " + Bukkit.getVersion());
                    }
                } else if(command.contains("[PLAYER] ")) {
                    Bukkit.dispatchCommand(player, command.replace("[PLAYER] ", ""));
                } else if(command.contains("[OPEN] ")) {
                    String inventory = command.replace("[OPEN] ", "");

                    if(inventory.equalsIgnoreCase("BLOCK")) {
                        player.openInventory(Main.getInstance().getBlockInventory().get(0));
                    } else if(inventory.equalsIgnoreCase("ISLAND")) {
                        player.openInventory(islandInventory.getCurrent());
                    } else if(inventory.equalsIgnoreCase("SETTING")) {
                        PlayerData data = Main.getInstance().USERS.get(player);
                        player.openInventory(data.inventory.getCurrent());
                    }
                } else if(command.contains("[ACTION] ")) {
                    String inventory = command.replace("[ACTION] ", "");

                    if(inventory.equalsIgnoreCase("ONESTACK")) {
                        PlayerData data = Main.getInstance().USERS.get(player);
                        data.oneStack = !data.oneStack;
                    }
                }
            }
        }
        return executed;
    }

    public static boolean runCommands(Player player, int slot, FileBuilder file, InventoryBuilder builder) {
        boolean executed = false;

        for(String path : file.getYaml().getConfigurationSection("items").getKeys(false)) {
            int itemSlot = file.getYaml().getInt("items." + path + ".slot");

            if(slot != itemSlot) {
                continue;
            }

            executed = true;

            for(String command : file.getYaml().getStringList("items." + path + ".command")) {
                if(command.contains("[CLOSE]")) {
                    player.closeInventory();
                } else if (command.contains("[SOUND] ")) {
                    String name = command.replace("[SOUND] ", "");

                    Main.getInstance().playerUtil.playSound(player, name);
                } else if(command.contains("[PLAYER] ")) {
                    Bukkit.dispatchCommand(player, command.replace("[PLAYER] ", ""));
                } else if(command.contains("[OPEN] ")) {
                    String inventory = command.replace("[OPEN] ", "");

                    if(inventory.equalsIgnoreCase("BLOCK")) {
                        player.openInventory(Main.getInstance().getBlockInventory().get(0));
                    } else if(inventory.equalsIgnoreCase("ISLAND")) {
                        player.openInventory(islandInventory.getCurrent());
                    } else if(inventory.equalsIgnoreCase("SETTING")) {
                        PlayerData data = Main.getInstance().USERS.get(player);
                        player.openInventory(data.inventory.getCurrent());
                    } else if(inventory.equalsIgnoreCase("NEXT-PAGE")) {
                        player.openInventory(builder.next());
                    } else if(inventory.equalsIgnoreCase("BACK-PAGE")) {
                        if(builder.currentIndex != 0) {
                            player.openInventory(builder.get(builder.currentIndex - 1));
                        }
                    }
                }
            }
        }
        return executed;
    }
}
