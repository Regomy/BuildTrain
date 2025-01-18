package me.rejomy.buildtrain.data;

import me.rejomy.buildtrain.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public List<PlayerData> bestForSession = new ArrayList<>();

    public boolean isInArena(Player player) {
        return Main.getInstance().USERS.containsKey(player) && Main.getInstance().USERS.get(player).island != null;
    }

    public void remove(Player player) {
        if (Main.getInstance().USERS.containsKey(player)) {
            PlayerData data = Main.getInstance().USERS.get(player);

            bestForSession.remove(data);

            if (data.island != null) {
                Main.getInstance().getIslandManager().remove(data.island);
                data.island = null;
            }

            if (data.savedInventory != null) {
                data.savedInventory.forEach((key, value) -> player.getInventory().setItem(key, value));
                data.savedInventory = null;
            }

            StringBuilder args = new StringBuilder();

            if (data.oneStack) {
                args.append("oneStack");
            }

            Main.getInstance().getDataBase().set(player.getName(), data.islandType, data.bestTime, args.toString());
        }
    }

    public void load(Player player) {
        String name = player.getName();
        PlayerData data = new PlayerData(player);

        data.islandType = Main.getInstance().getDataBase().get(name, 2);

        String bestTime = Main.getInstance().getDataBase().get(name, 3);
        if (!bestTime.isEmpty()) {
            data.bestTime = Long.parseLong(Main.getInstance().getDataBase().get(name, 3));
        }

        data.oneStack = Main.getInstance().getDataBase().get(name, 4).toLowerCase().contains("onestack");

        Main.getInstance().USERS.put(player, data);
    }
}
