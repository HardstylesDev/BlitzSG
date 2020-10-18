package org.example.SpigotServer.system;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.SpigotServer.SpigotServer;
import org.example.SpigotServer.utils.NametagEdit;
import org.example.SpigotServer.utils.SendCenteredMessages;
import org.example.SpigotServer.utils.sendColor;

import java.util.ArrayList;

public class Login implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        event.getPlayer().sendMessage(sendColor.format("&7&m----------------------------------------"));
        event.getPlayer().sendMessage("");
        SendCenteredMessages.sendCenteredMessage(event.getPlayer(), sendColor.format("&a&lWelcome to the test server"));
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage(sendColor.format("&7&m----------------------------------------"));


        Bukkit.getScheduler().runTaskLater(SpigotServer.plugin, new Runnable() {
            @Override
            public void run() {
                join(event.getPlayer());
            }
        }, 1);

    }

    public static void join(Player p) {
        p.setGameMode(GameMode.SURVIVAL);
        p.setAllowFlight(true);
        p.setFlying(true);

        //p.getInventory().clear();
        //ItemStack compass = new ItemStack(Material.COMPASS, 1);
        //ItemMeta im = compass.getItemMeta();
        //ArrayList<String> lore = new ArrayList<String>();
        //lore.add("§fRight Click to open the Server Selector!");
//
        //im.setDisplayName("§a§lServer Selector §f(Right Click)");
        //im.setLore(lore);
//
        //compass.setItemMeta(im);
//
        //p.getInventory().setItem(0, compass);

        //NametagEdit.setScoreboard();
    }

}