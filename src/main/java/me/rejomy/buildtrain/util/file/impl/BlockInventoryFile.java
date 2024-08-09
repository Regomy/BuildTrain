package me.rejomy.buildtrain.util.file.impl;

import me.rejomy.buildtrain.util.file.FileBuilder;

import java.util.ArrayList;
import java.util.List;

public class BlockInventoryFile extends FileBuilder {
    public BlockInventoryFile() {
        super("inventory/block");

        VALUES.put("name", "&0Blocks:");

        {
            VALUES.put("items.next.slot", 46);
            VALUES.put("items.next.name", "Next page");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for open next page.");

                VALUES.put("items.next.lore", list);
            }
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[OPEN] NEXT-PAGE");

                VALUES.put("items.next.command", list);
            }
            VALUES.put("items.next.type", "STONE_BUTTON");
        }

        {
            VALUES.put("items.back.slot", 44);
            VALUES.put("items.back.name", "Back page");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for open back page.");

                VALUES.put("items.back.lore", list);
            }
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[OPEN] BACK-PAGE");

                VALUES.put("items.back.command", list);
            }
            VALUES.put("items.back.type", "WOOD_BUTTON");
        }

        {
            VALUES.put("items.return.slot", 45);
            VALUES.put("items.return.name", "Back to setting");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for back.");

                VALUES.put("items.return.lore", list);
                VALUES.put("items.return.sound", "");
            }
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[OPEN] SETTING");

                VALUES.put("items.return.command", list);
            }
            VALUES.put("items.return.type", "BARRIER");
        }

        init();
    }
}
