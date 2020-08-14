package org.example.SpigotServer.test;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerTeleportEvent e) {
        e.getPlayer().setGameMode(GameMode.CREATIVE);
    }
}
