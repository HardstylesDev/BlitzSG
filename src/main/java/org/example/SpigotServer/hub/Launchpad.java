package org.example.SpigotServer.hub;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.example.SpigotServer.SpigotServer;
import org.example.SpigotServer.utils.sendColor;

public class Launchpad implements Listener {
    @EventHandler
    public void launchpad(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction().equals(Action.PHYSICAL)) {
            if (event.getClickedBlock().getType() == Material.IRON_PLATE) {
                if (p.getWorld().getName().equalsIgnoreCase("world") && p.getLocation().getWorld().getBlockAt(event.getPlayer().getLocation()).getRelative(0, -1, 0).getType() == Material.IRON_BLOCK) {
                    if (!p.isSneaking())
                        p.setVelocity(new Vector(event.getPlayer().getVelocity().getX(), 4.0D, event.getPlayer().getVelocity().getZ()));
                    else {
                        p.setVelocity(new Vector(event.getPlayer().getVelocity().getX(), 4.0D, event.getPlayer().getVelocity().getZ()));
                        Bukkit.getScheduler().runTaskLater(SpigotServer.plugin, new Runnable() {
                            @Override
                            public void run() {
                                p.setVelocity(new Vector(event.getPlayer().getVelocity().getX(), 4.0D, event.getPlayer().getVelocity().getZ()));

                            }
                        }, 5);
                    }
                    p.sendMessage(sendColor.format("&eWoosh!"));
                    p.playSound(p.getLocation(), "tile.piston.out", 1, 1);

                }
            }
        }
    }
}
