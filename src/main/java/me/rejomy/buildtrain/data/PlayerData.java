package me.rejomy.buildtrain.data;

import me.rejomy.buildtrain.island.CreateData;
import me.rejomy.buildtrain.island.Island;
import me.rejomy.buildtrain.util.inventory.implement.SettingInventory;

public class PlayerData {
    public boolean oneStack, spectator;
    public long bestTime = 9999, currentTime;
    public double sessionBestTime = 9999;
    public Island island;
    public String islandType, args;
    public CreateData create;
    public SettingInventory inventory = new SettingInventory(this);

    public int taskId;
}
