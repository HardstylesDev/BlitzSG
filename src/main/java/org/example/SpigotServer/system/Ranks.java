package org.example.SpigotServer.system;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.example.SpigotServer.utils.sendColor;

public class Ranks implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        String ign = event.getPlayer().getName();
        
        if (ign.equalsIgnoreCase("Hardstyles")) {
            event.setFormat(sendColor.format(String.format("&cADMIN %s&f: %s", ign, event.getMessage())));
        } else {
            event.setFormat(sendColor.format(String.format("&7%s&f: &7%s", ign, event.getMessage())));
        }
    }

    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }
}
