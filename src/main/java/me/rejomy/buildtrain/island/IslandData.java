package me.rejomy.buildtrain.island;

import me.rejomy.buildtrain.util.Block;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class IslandData {
    public List<Block> blocks = new ArrayList<>();
    public Location spawnPlayerLoc;
    public int minX, maxX, minY, maxY, minZ, maxZ;
    public String type;
    public Material icon;

}
