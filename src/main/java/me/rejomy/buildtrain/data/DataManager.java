package me.rejomy.buildtrain.data;

import me.rejomy.buildtrain.Main;
import org.bukkit.entity.Player;

public class DataManager {
    public boolean isInArena(Player player) {
        return Main.getInstance().USERS.containsKey(player) && Main.getInstance().USERS.get(player).island != null;
    }

    public void remove(Player player) {
        if(Main.getInstance().USERS.containsKey(player)) {
            PlayerData data = Main.getInstance().USERS.get(player);

            if(data.island != null) {
                Main.getInstance().getIslandManager().remove(data.island);
                data.island = null;
            }

            StringBuilder args = new StringBuilder();

            if(data.oneStack) {
                args.append("oneStack");
            }

            Main.getInstance().getDataBase().set(player.getName(), data.islandType, data.bestTime, data.args);
        }
    }

    public void load(Player player) {
        PlayerData data = new PlayerData();

        data.islandType = Main.getInstance().getDataBase().get(player.getName(),2);

        String bestTime = Main.getInstance().getDataBase().get(player.getName(),3);
        if(!bestTime.isEmpty()) {
            data.bestTime = Long.parseLong(Main.getInstance().getDataBase().get(player.getName(), 3));
        }

        data.oneStack = Main.getInstance().getDataBase().get(player.getName(),4).toLowerCase().contains("onestack");

        Main.getInstance().USERS.put(player, data);
    }
}
