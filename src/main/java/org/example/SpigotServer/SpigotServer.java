package org.example.SpigotServer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.SpigotServer.commands.informative.PingCommand;
import org.example.SpigotServer.commands.informative.PlaytimeCommand;
import org.example.SpigotServer.commands.staff.*;
import org.example.SpigotServer.commands.time.DayCommand;
import org.example.SpigotServer.commands.time.NightCommand;
import org.example.SpigotServer.commands.time.ResetCommand;
import org.example.SpigotServer.commands.time.SunsetCommand;
import org.example.SpigotServer.commands.utils.FlySpeedCommand;
import org.example.SpigotServer.system.RankCommand;
import org.example.SpigotServer.system.Ranks;
import org.example.SpigotServer.test.BuildStuff;
import org.example.SpigotServer.test.Login;
import org.example.SpigotServer.test.WorldEvent;

public class SpigotServer extends JavaPlugin {

    private static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;
        registerEvents(this, new Login(), new Ranks(), new BuildStuff(), new WorldEvent());
        this.getCommand("day").setExecutor(new DayCommand());
        this.getCommand("night").setExecutor(new NightCommand());
        this.getCommand("sunset").setExecutor(new SunsetCommand());
        this.getCommand("resettime").setExecutor(new ResetCommand());
        this.getCommand("tp").setExecutor(new TeleportCommand());

        this.getCommand("gmc").setExecutor(new GamemodeCreativeCommand());
        this.getCommand("gms").setExecutor(new GamemodeSurvivalCommand());
        this.getCommand("gmsp").setExecutor(new GamemodeSpectatorCommand());
        this.getCommand("gma").setExecutor(new GamemodeAdventureCommand());
        this.getCommand("flyspeed").setExecutor(new FlySpeedCommand());

        this.getCommand("ping").setExecutor(new PingCommand());
        this.getCommand("rank").setExecutor(new RankCommand());
        this.getCommand("playtime").setExecutor(new PlaytimeCommand());
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