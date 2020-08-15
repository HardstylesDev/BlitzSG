package org.example.SpigotServer.test;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.example.SpigotServer.SpigotServer;

public class WorldEvent implements Listener
{
    @EventHandler
    public void onJoin(PlayerTeleportEvent e)
    {
        Bukkit.getScheduler().runTaskLater(SpigotServer.plugin, new Runnable() {
            @Override
            public void run() {
                e.getPlayer().setGameMode(GameMode.CREATIVE);
                e.getPlayer().setAllowFlight(true);
                e.getPlayer().setFlying(true);
            }
        }, 2);

    }
}
