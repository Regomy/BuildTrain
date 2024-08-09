package me.rejomy.buildtrain.util.file;

import me.rejomy.buildtrain.Main;
import me.rejomy.buildtrain.util.file.impl.LobbyFile;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileManager {
    public CopyOnWriteArrayList<FileBuilder> files = new CopyOnWriteArrayList<>();
    public void loadFiles() {
        File islandTypeFolder = new File(Main.getInstance().getDataFolder(), "island");
        islandTypeFolder.mkdirs();

        files.add(new LobbyFile());
    }
    public File getFileByPath(String path) {
        return new File(Main.getInstance().getDataFolder(), path);
    }

    public FileBuilder getFileByName(String name) {
        return files.stream().filter(file -> file.name.equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
