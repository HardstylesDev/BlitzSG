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
    public void onChat(AsyncPlayerChatEvent event) {
       // if(RankManager.hasRank((OfflinePlayer) event.getPlayer()))

       // else
        //    event.setFormat(sendColor.format(String.format("&7%s &8> &7%s", event.getPlayer().getName(), event.getMessage())));

    }
}
