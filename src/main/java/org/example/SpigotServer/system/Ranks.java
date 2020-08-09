package org.example.SpigotServer.system;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.SpigotServer.utils.sendColor;

public class Ranks implements Listener
{
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if(RankManager.hasRank((OfflinePlayer) event.getPlayer()))
            event.setFormat(sendColor.format(String.format("&8&l[&9&lPolice&8&l] &9%s &8> &r%s", event.getPlayer(), event.getMessage())));
        else
            event.setFormat(sendColor.format(String.format("&7%s &8> &7%s", event.getPlayer(), event.getMessage())));
    }
}
