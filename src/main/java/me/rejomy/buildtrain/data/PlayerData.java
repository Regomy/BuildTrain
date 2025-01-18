package me.rejomy.buildtrain.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.rejomy.buildtrain.island.CreateData;
import me.rejomy.buildtrain.island.Island;
import me.rejomy.buildtrain.util.inventory.implement.SettingInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public class PlayerData {

    @NotNull
    final Player player;

    public boolean oneStack, spectator;
    public long bestTime = 9999, currentTime;
    public double sessionBestTime = 9999;
    public Island island;
    public String islandType;
    public CreateData create;
    public SettingInventory inventory = new SettingInventory(this);

    public int taskId;

    public HashMap<Byte, ItemStack> savedInventory;
}
