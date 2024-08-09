package me.rejomy.buildtrain.util.file.impl;

import me.rejomy.buildtrain.util.file.FileBuilder;

import java.util.ArrayList;
import java.util.List;

public class SettingInventoryFile extends FileBuilder {
    public SettingInventoryFile() {
        super("inventory/setting");

        VALUES.put("name", "&0Settings:");

        {
            VALUES.put("items.onestack.slot", 16);
            VALUES.put("items.onestack.name", "Set oneStack value");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for toggle.");

                VALUES.put("items.onestack.lore", list);
            }
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[ACTION] ONESTACK");

                VALUES.put("items.onestack.command", list);
            }
        }

        {
            VALUES.put("items.block.slot", 10);
            VALUES.put("items.block.name", "Select a block for building");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for select block.");

                VALUES.put("items.block.lore", list);
            }
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[OPEN] BLOCK");

                VALUES.put("items.block.command", list);
            }
        }

        {
            VALUES.put("items.island.slot", 14);
            VALUES.put("items.island.name", "Select island type");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for select island.");

                VALUES.put("items.island.lore", list);
            }
            VALUES.put("items.island.type", "STONE");
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[OPEN] ISLAND");

                VALUES.put("items.island.command", list);
            }
        }

        {
            VALUES.put("items.exit.slot", 44);
            VALUES.put("items.exit.name", "Exit from the island");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for exit from island.");

                VALUES.put("items.exit.lore", list);
            }
            VALUES.put("items.exit.type", "BARRIER");
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[PLAYER] bt stop");

                VALUES.put("items.exit.command", list);
            }
        }

        {
            VALUES.put("items.close.slot", 46);
            VALUES.put("items.close.name", "Close the menu");
            {
                List<String> list = new ArrayList<>();

                list.add("Click here for close the menu.");

                VALUES.put("items.close.lore", list);
            }
            VALUES.put("items.close.type", "STONE_BUTTON");
            {
                List<String> list = new ArrayList<>();

                list.add("[SOUND] NONE");
                list.add("[CLOSE]");

                VALUES.put("items.close.command", list);
            }
        }

        init();
    }
}
