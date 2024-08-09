package me.rejomy.buildtrain.util.file.impl;

import me.rejomy.buildtrain.util.file.FileBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LobbyFile extends FileBuilder {
    public Location spawn;
    public LobbyFile() {
        super("spawn");

        VALUES.put("x", null);
        VALUES.put("y", null);
        VALUES.put("z", null);
        VALUES.put("yaw", null);
        VALUES.put("pitch", null);
        VALUES.put("world", null);

        init();

        if(VALUES.get("x") != null) {
            spawn = new Location(Bukkit.getWorld((String) VALUES.get("world")),
                    (double) VALUES.get("x"),
                    (double) VALUES.get("y"),
                    (double) VALUES.get("z"),
                    Float.parseFloat(String.valueOf(VALUES.get("yaw"))),
                    Float.parseFloat(String.valueOf(VALUES.get("pitch")))
            );
        }
    }
}
