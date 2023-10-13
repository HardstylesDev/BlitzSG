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
        long days = diffInSeconds / 86400;
        long hours = (diffInSeconds % 86400) / 3600;
        long minutes = (diffInSeconds % 3600) / 60;
        long seconds = diffInSeconds % 60;

        StringBuilder formattedTime = new StringBuilder();
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


    public long calculateFutureTime(String durationStr) {
        long currentTimeMillis = System.currentTimeMillis();
        long durationInMillis = 0;
        try {
            int duration = Integer.parseInt(durationStr.substring(0, durationStr.length() - 1));
            char unit = durationStr.charAt(durationStr.length() - 1);
            durationInMillis = convertToMilliseconds(duration, unit);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return -1; // Handle invalid input more explicitly.
        }
        return currentTimeMillis + durationInMillis;
    }

    private long convertToMilliseconds(int duration, char unit) {
        switch (unit) {
            case 's':
                return duration * 1000L;
            case 'm':
                return duration * 60L * 1000L;
            case 'h':
                return duration * 60L * 60L * 1000L;
            case 'd':
                return duration * 24L * 60L * 60L * 1000L;
            default:
                return -1; // Handle invalid units more explicitly.
        }
    }



}