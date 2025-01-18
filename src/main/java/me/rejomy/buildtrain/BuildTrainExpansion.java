package me.rejomy.buildtrain;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.rejomy.buildtrain.data.PlayerData;
import me.rejomy.buildtrain.util.NumberUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildTrainExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "bt";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Rejomy";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if(params.equalsIgnoreCase("best")) {
            PlayerData data = Main.getInstance().USERS.get(player);
            double timeSeconds = ((int) ((data.bestTime / 1000.0) * 100)) / 100.0;
            return String.valueOf(timeSeconds);
        }

        if(params.contains("session_best_")) {
            String forParseTopNumber = params.replaceAll("session_best_", "");
            int topNumber = NumberUtil.parseInt(forParseTopNumber);

            if (Main.getInstance().getDataManager().bestForSession.size() < topNumber) {
                return "";
            }

            PlayerData data = Main.getInstance().getDataManager().bestForSession.get(topNumber - 1);
            return data.getPlayer().getName() + " - " + data.sessionBestTime;
        }

        if(params.equalsIgnoreCase("current")) {
            PlayerData data = Main.getInstance().USERS.get(player);
            double value = ((int) ((data.currentTime / 1000.0) * 100)) / 100.0;
            return String.valueOf(value);
        }

        if(params.equalsIgnoreCase("island")) {
            PlayerData data = Main.getInstance().USERS.get(player);

            return data.island != null? data.island.type : "UNEXPECTED";
        }

        if(params.equalsIgnoreCase("placed")) {
            PlayerData data = Main.getInstance().USERS.get(player);
            return String.valueOf(data.island.placedBlocks.size());
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
