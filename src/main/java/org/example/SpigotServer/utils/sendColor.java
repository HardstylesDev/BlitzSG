package org.example.SpigotServer.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class sendColor
{
    public static String format(String format){
        return ChatColor.translateAlternateColorCodes('&', format);
    }
}

