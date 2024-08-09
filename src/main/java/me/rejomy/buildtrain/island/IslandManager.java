package me.rejomy.buildtrain.island;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.util.Block;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IslandManager {
    private int count = 0;
    private List<Integer> removeCounts = new ArrayList<>();
    public List<IslandData> islands = new ArrayList<>();

    public Island spawn(String type) {

        IslandData data = islands.stream().filter(islandData -> islandData.type.equals(type))
                .findFirst().orElse(null);

        if(data == null) {
            Main.getInstance().disablePlugin("Type " + type + " not found!");
            return null;
        }

        Island island;

        if(removeCounts.isEmpty()) {
            island = new Island(data, count);

            count++;
        } else {
            island = new Island(data, removeCounts.remove(0));
        }

        return island;
    }

    public void remove(Island island) {
        for(Block block : island.blocks) {
            block.location.getBlock().setType(Material.AIR);
        }

        for(org.bukkit.block.Block block : island.placedBlocks) {
            block.getLocation().getBlock().setType(Material.AIR);
        }

        for(byte i = 0; i < 5; i++) {
            Location location = island.spawnPlayerLoc.clone();

            location.add(-2 + i, -1, 20);

            location.getBlock().setType(Material.AIR);
        }

        removeCounts.add(island.count);
    }

    public void fill() {
        File islandFile = Main.getInstance().getFileManager().getFileByPath("island");

        if(islandFile.listFiles() == null || islandFile.listFiles().length == 0) {
            Main.getInstance().getLogger().severe("Type " + islandFile.getPath() + " is not found!");
            return;
        }

        for(File island : islandFile.listFiles()) {
            IslandData data = new IslandData();
            YamlConfiguration config = YamlConfiguration.loadConfiguration(island);

            data.spawnPlayerLoc = new Location(
                    Bukkit.getWorld(config.getString("spawn.world")),
                    config.getDouble("spawn.x"),
                    config.getDouble("spawn.y"),
                    config.getDouble("spawn.z"),
                    Float.parseFloat(config.getString("spawn.yaw")),
                    Float.parseFloat(config.getString("spawn.pitch"))
            );

            data.minX = config.getInt("min.x");
            data.minY = config.getInt("min.y");
            data.minZ = config.getInt("min.z");

            data.maxX = config.getInt("max.x");
            data.maxY = config.getInt("max.y");
            data.maxZ = config.getInt("max.z");

            data.type = island.getName().replace(".yml", "");

            for(String blockLine : config.getConfigurationSection("blocks").getKeys(false)) {
                Block block = new Block();

                block.location = new Location(
                        Bukkit.getWorld(config.getString("spawn.world")),
                        config.getInt("blocks." + blockLine + ".x"),
                        config.getInt("blocks." + blockLine + ".y"),
                        config.getInt("blocks." + blockLine + ".z")
                );

                block.type = Material.valueOf(config.getString("blocks." + blockLine + ".type"));
                block.rotation = (byte) config.getInt("blocks." + blockLine + ".rotation");

                data.blocks.add(block);
            }

            Material icon;

            if(config.get("icon") == null) {
                icon = Material.STONE;
            } else {
                try {
                    icon = Material.valueOf(config.getString("icon"));
                } catch (IllegalArgumentException exception) {
                    icon = Material.STONE;
                }
            }

            data.icon = icon;

            islands.add(data);
        }
    }

}
