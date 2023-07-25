package me.hardstyles.blitz.util;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.Date;

@UtilityClass
public class ChatUtil {

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String formatDate(double milis) {
        Date start = new Date(System.currentTimeMillis());
        Date end = new Date((long) milis);

        long diffInSeconds = (end.getTime() - start.getTime()) / 1000;
        long weeks = diffInSeconds / 604800;
        long days = (diffInSeconds % 604800) / 86400;
        long hours = (diffInSeconds % 86400) / 3600;
        long minutes = (diffInSeconds % 3600) / 60;
        long seconds = diffInSeconds % 60;

        StringBuilder formattedTime = new StringBuilder();
        if (weeks > 0) {
            formattedTime.append(weeks).append("w ");
        }
        if (days > 0) {
            formattedTime.append(days).append("d ");
        }
        if (hours > 0) {
            formattedTime.append(hours).append("h ");
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append("m ");
        }
        if (seconds > 0) {
            formattedTime.append(seconds).append("s");
        }
        return formattedTime.toString();
    }

}