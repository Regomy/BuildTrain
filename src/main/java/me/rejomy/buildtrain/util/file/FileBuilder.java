package me.rejomy.buildtrain.util.file;

import me.rejomy.buildtrain.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FileBuilder {
    public final String name;
    public final HashMap<String, Object> VALUES = new HashMap<>();
    private File file;
    private YamlConfiguration yaml;

    public FileBuilder(String fileName) {
        name = fileName.replace(".yml", "");
        this.file = new File(Main.getInstance().getDataFolder(), name + ".yml");
    }

    protected void init() {
        if (!file.exists()) {
            createFile();
        }

        loadConfiguration();
        loadValues();
    }

    public void loadValues() {
        for (Map.Entry<String, Object> value : VALUES.entrySet()) {
            Object fileValue = yaml.get(value.getKey());

            if(fileValue != null) {
                VALUES.put(value.getKey(),
                        value.getValue() instanceof List && !(fileValue instanceof List)? new ArrayList<>() : fileValue);
                continue;
            }

            yaml.set(value.getKey(), value.getValue());
        }

        saveYamlToFile();
    }

    private void saveYamlToFile() {
        try {
            yaml.save(file);
        } catch (IOException exception) {
            exception.getCause();
        }
    }

    private void createFile() {
        try {
            file.createNewFile();
        } catch (IOException exception) {
            exception.getCause();
        }
    }

    private void loadConfiguration() {
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getYaml() {
        return yaml;
    }

}
