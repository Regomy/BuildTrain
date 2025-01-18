package me.rejomy.buildtrain.util;

import me.rejomy.buildtrain.Main;

public class NumberUtil {

    /**
     * Return number value or -1 if line is not integer.
     */
    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception exception) {
            Main.INSTANCE.getLogger().warning("Value " + value + " is not number!");
            return -1;
        }
    }
}
