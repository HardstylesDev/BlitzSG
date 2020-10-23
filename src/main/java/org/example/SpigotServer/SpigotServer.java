package org.example.SpigotServer;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.SpigotServer.commands.building.BuildToolsCommand;
import org.example.SpigotServer.commands.building.RenameCommand;
import org.example.SpigotServer.commands.informative.BackCommand;
import org.example.SpigotServer.commands.informative.PingCommand;
import org.example.SpigotServer.commands.informative.PlaytimeCommand;
import org.example.SpigotServer.commands.staff.*;
import org.example.SpigotServer.commands.time.DayCommand;
import org.example.SpigotServer.commands.time.NightCommand;
import org.example.SpigotServer.games.kitpvp.ClassGUI;
import org.example.SpigotServer.games.kitpvp.Damage;
import org.example.SpigotServer.games.kitpvp.KitPVP;
import org.example.SpigotServer.games.kitpvp.classes.Warrior;
import org.example.SpigotServer.hub.HubGUI;
import org.example.SpigotServer.scoreboard.ScoreboardManager;
import org.example.SpigotServer.worldgeneration.VoidGenerator;
import org.example.SpigotServer.worldgeneration.WorldCommand;
import org.example.SpigotServer.commands.time.SunsetCommand;
import org.example.SpigotServer.commands.utils.FlySpeedCommand;
import org.example.SpigotServer.hub.Hub;
import org.example.SpigotServer.hub.Launchpad;
import org.example.SpigotServer.system.Ranks;
import org.example.SpigotServer.hub.JoinHandler;
import org.example.SpigotServer.test.BuildStuff;
import org.example.SpigotServer.system.Login;
import org.example.SpigotServer.worldgeneration.Worlds;

public class SpigotServer extends JavaPlugin {
    private JavaPlugin parent;



    public static Plugin plugin;
    public ScoreboardManager scoreboardManager;


    @Override
    public void onEnable() {
        plugin = this;
        this.scoreboardManager = new ScoreboardManager();
        Worlds.loadWorlds();
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);

        registerEvents(this, new Hub(), new HubGUI(), new KitPVP(), new Warrior(), new ClassGUI(), new Damage(), new Launchpad(), new Login(), new Ranks(), new BuildStuff(), new BuildToolsCommand(), new BackCommand(), new JoinHandler());
        this.getCommand("day").setExecutor(new DayCommand());
        this.getCommand("night").setExecutor(new NightCommand());
        this.getCommand("sunset").setExecutor(new SunsetCommand());
        this.getCommand("tp").setExecutor(new TeleportCommand());

        this.getCommand("world").setExecutor(new WorldCommand());
        this.getCommand("class").setExecutor(new ClassGUI());

        this.getCommand("gmc").setExecutor(new GamemodeCreativeCommand());
        this.getCommand("gms").setExecutor(new GamemodeSurvivalCommand());
        this.getCommand("gmsp").setExecutor(new GamemodeSpectatorCommand());
        this.getCommand("gma").setExecutor(new GamemodeAdventureCommand());
        this.getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        this.getCommand("buildtools").setExecutor(new BuildToolsCommand());
        this.getCommand("back").setExecutor(new BackCommand());

        //this.getCommand("s").setExecutor(new SCommand());
        this.getCommand("rename").setExecutor(new RenameCommand());
        this.getCommand("kitpvp").setExecutor(new KitPVP());
        this.getCommand("ping").setExecutor(new PingCommand());
        this.getCommand("playtime").setExecutor(new PlaytimeCommand());
        getLogger().info("onEnable is called!");

        scoreboardManager.runTaskTimer(this, 2, 2);
        new WorldCreator("kitpvp").generator(new VoidGenerator()).createWorld();
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