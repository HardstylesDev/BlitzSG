package org.example.SpigotServer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.SpigotServer.commands.time.DayCommand;
import org.example.SpigotServer.commands.time.NightCommand;
import org.example.SpigotServer.commands.time.ResetCommand;
import org.example.SpigotServer.commands.time.SunsetCommand;
import org.example.SpigotServer.system.Ranks;
import org.example.SpigotServer.test.BuildStuff;
import org.example.SpigotServer.test.Login;

public class SpigotServer extends JavaPlugin {

    private static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;
        registerEvents(this, new Login(), new Ranks(), new BuildStuff());
        this.getCommand("day").setExecutor(new DayCommand());
        this.getCommand("night").setExecutor(new NightCommand());
        this.getCommand("sunset").setExecutor(new SunsetCommand());
        this.getCommand("resettime").setExecutor(new ResetCommand());
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