package me.rejomy.buildtrain.util.file.impl;

import me.rejomy.buildtrain.util.file.FileBuilder;

import java.util.ArrayList;
import java.util.List;

public class IslandInventoryFile extends FileBuilder {
    public IslandInventoryFile() {
        super("inventory/island");

        VALUES.put("name", "&0Select island type:");

        {
            VALUES.put("items.back.slot", 10);
            VALUES.put("items.back.name", "Back to setting");
            VALUES.put("items.back.type", "STONE");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here to back.");

                VALUES.put("items.back.lore", list);
            }
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[OPEN] SETTING");

                VALUES.put("items.back.command", list);
            }
        }

        init();
    }
}