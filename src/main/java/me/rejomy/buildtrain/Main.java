package me.rejomy.buildtrain;

import me.rejomy.buildtrain.command.BuildTrainCommand;
import me.rejomy.buildtrain.data.DataManager;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.database.DataBase;
import me.rejomy.buildtrain.database.SQLite;
import me.rejomy.buildtrain.generator.VoidGenerator;
import me.rejomy.buildtrain.island.IslandManager;
import me.rejomy.buildtrain.listener.*;
import me.rejomy.buildtrain.util.PlayerUtil;
import me.rejomy.buildtrain.util.WorldUtil;
import me.rejomy.buildtrain.util.file.FileManager;
import me.rejomy.buildtrain.util.inventory.implement.BlockInventory;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class Main extends JavaPlugin {
    public final HashMap<Player, PlayerData> USERS = new HashMap<>();
    private final FileManager fileManager = new FileManager();
    public FileManager getFileManager() {
        return fileManager;
    }
    public DataBase getDataBase() {
        return dataBase;
    }
    private DataBase dataBase;
    private IslandManager islandManager;
    public IslandManager getIslandManager() {
        return islandManager;
    }
    private final DataManager dataManager = new DataManager();
    public final PlayerUtil playerUtil = new PlayerUtil();
    public DataManager getDataManager() {
        return dataManager;
    }

    public static Main getInstance() {
        return INSTANCE;
    }
    private BlockInventory blockInventory;
    public BlockInventory getBlockInventory() {
        return blockInventory;
    }

    public static Main INSTANCE;

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            Main.getInstance().getDataManager().remove(player);
        }
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();

        islandManager = new IslandManager();

        fileManager.loadFiles();

        dataBase = new SQLite();

        configureWorld();

        blockInventory = new BlockInventory();

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new BuildTrainExpansion().register();
        }

        getCommand("buildtrain").setExecutor(new BuildTrainCommand());

        registerListener(new ConnectionListener());
        registerListener(new TeleportListener());
        registerListener(new MoveListener());
        registerListener(new BlockListener());
        registerListener(new InteractListener());
        registerListener(new DamageListener());
        registerListener(new EntityListener());
        registerListener(new PistonListener());
        registerListener(new ItemListener());

        islandManager.fill();
    }

    private void configureWorld() {
        World world = Bukkit.getWorld("bt_world");

        if (world != null) {
            WorldUtil.unloadWorld(world);
            WorldUtil.deleteWorld(new File("bt_world"));
        }

        WorldCreator worldCreator = new WorldCreator("bt_world");
        worldCreator.generator(new VoidGenerator());
        world = worldCreator.createWorld();

        if(getConfig().getBoolean("settings.cancel-time-change")) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(6000);
        }
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getInstance());
    }

    public void disablePlugin(String reason) {
        getLogger().severe(reason);
        Bukkit.getPluginManager().disablePlugin(this);
    }

}
