package me.rejomy.buildtrain.island;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.util.Block;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Island extends IslandData {
    public int count;
    public List<org.bukkit.block.Block> placedBlocks = new ArrayList<>();

    public Island(IslandData data, int count) {
        this.count = count;

        spawnPlayerLoc = data.spawnPlayerLoc.clone();

        // The multiplier value is need for islands not merged.
        int multiplier = (10 + data.maxX - data.minX) * count;

        double angle = spawnPlayerLoc.getYaw();

        for (Block abstractBlock : data.blocks) {
            Block block = new Block();
            block.location = abstractBlock.location.clone();
            block.type = abstractBlock.type;
            block.rotation = abstractBlock.rotation;

            double offsetX = 0, offsetZ = 0;

            if(Math.abs(angle) > 45 && Math.abs(angle) < 135) {
                /*double distance = Math.sqrt(
                        Math.pow(spawnPlayerLoc.getBlockX() - block.location.getBlockX(), 2)
                                + Math.pow(spawnPlayerLoc.getBlockZ() - block.location.getBlockZ(), 2));

                offsetX = distance * Math.cos(angleRadians);
                offsetZ = distance * Math.sin(angleRadians);*/

                offsetX = block.location.getBlockZ() - spawnPlayerLoc.getBlockZ();
                offsetZ = block.location.getBlockX() - spawnPlayerLoc.getBlockX();
            } else if(Math.abs(angle) >= 135){
                offsetX = block.location.getBlockX() - spawnPlayerLoc.getBlockX();
                offsetZ = block.location.getBlockZ() - spawnPlayerLoc.getBlockZ();
            }

            block.location.setX(Math.round(spawnPlayerLoc.getBlockX() - offsetX) + multiplier);
            block.location.setZ(Math.round(spawnPlayerLoc.getBlockZ() - offsetZ));

            blocks.add(block);

            block.location.getBlock().setType(block.type);
            block.location.getBlock().setData(block.rotation);
        }

        spawnPlayerLoc.setX(spawnPlayerLoc.getX() + multiplier);

        for(byte i = 0; i < 5; i++) {
            Location location = spawnPlayerLoc.clone();

            location.add(-2 + i, -1, 20);

            location.getBlock().setType(Material.valueOf(Main.getInstance().getConfig().getString("settings.finish-block")));
        }

        spawnPlayerLoc.setYaw(0);

        minX = data.minX + multiplier;
        maxX = data.maxX + multiplier;

        minY = data.minY;
        maxY = data.maxY;

        minZ = data.minZ;
        maxZ = data.maxZ;

        type = data.type;
    }

    public void reset() {
        String animation = Main.getInstance().getConfig().getString("settings.block-remove-animation");

        for(org.bukkit.block.Block block : placedBlocks) {
            switch (animation.toLowerCase()) {
                case "falling": {
                    block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), (byte) 0);
                    break;
                }
                case "DROP": {
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
                    break;
                }
                case "EXPLODE": {
                    block.getWorld().playEffect(block.getLocation(), Effect.EXPLOSION, 100, 100);
                    break;
                }
            }

            block.setType(Material.AIR);
        }

        placedBlocks.clear();
    }

}
