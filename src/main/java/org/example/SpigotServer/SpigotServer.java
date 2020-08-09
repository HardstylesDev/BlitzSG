package org.example.SpigotServer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.SpigotServer.system.RankCommand;
import org.example.SpigotServer.system.Ranks;
import org.example.SpigotServer.test.Test;

public class SpigotServer extends JavaPlugin {

    private static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;
        registerEvents(this, new Test(), new Ranks());
        this.getCommand("rank").setExecutor(new RankCommand());
        getLogger().info("onEnable is called!");
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

}