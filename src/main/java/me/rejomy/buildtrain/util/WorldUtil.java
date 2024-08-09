package me.rejomy.buildtrain.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

public class WorldUtil {
    public static boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public static void unloadWorld(World world) {
        if(world != null) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }
}
