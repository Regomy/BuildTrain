package me.rejomy.buildtrain.generator;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {
    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        byte[][] result = new byte[world.getMaxHeight() / 16][];
        return result;
    }
}
