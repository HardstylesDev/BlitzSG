package me.hardstyles.blitz.database;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.database.impl.MongoProvider;
import me.hardstyles.blitz.database.impl.MySQLProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseProvider {
    Map<String, String> config = null;
    public DatabaseProvider() {
        config = getDatabaseConfig();
    }

    public IDatabase getDatabase() {

        if(!config.containsKey("type")){
            Bukkit.getLogger().severe("Invalid database type in config.yml! Add type: mysql or type: mongo");
            Bukkit.getPluginManager().disablePlugin(BlitzSG.getInstance());
            return null;
        }

        switch (config.get("type")) {
            case "mysql":
                return new MySQLProvider(config);
            case "mongo":
                return new MongoProvider(config);
            default:
                Bukkit.getLogger().severe("Invalid database type in config.yml!");
                Bukkit.getPluginManager().disablePlugin(BlitzSG.getInstance());
                return null;
        }
    }

    private static void generateConfig() {
        File file = new File(BlitzSG.getInstance().getDataFolder(), "database.yml");
        FileConfiguration fc = new YamlConfiguration();
        fc.set("type", "mysql");
        fc.set("host", "localhost");
        fc.set("port", "27017"); // Default MongoDB port
        fc.set("database", "BlitzSG");
        fc.set("username", "root");
        fc.set("password", "admin");

        try {
            fc.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().disablePlugin(BlitzSG.getInstance());
    }

    private static HashMap<String, String> getDatabaseConfig() {
        HashMap<String, String> config = new HashMap<>();
        File file = new File(BlitzSG.getInstance().getDataFolder(), "database.yml");
        if (!file.exists()) {
            generateConfig();
        }
        FileConfiguration fc = new YamlConfiguration();
        try {
            fc.load(new File(BlitzSG.getInstance().getDataFolder(), "database.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        config.put("type", fc.getString("type"));
        config.put("host", fc.getString("host"));
        config.put("port", fc.getString("port"));
        config.put("database", fc.getString("database"));
        config.put("username", fc.getString("username"));
        config.put("password", fc.getString("password"));
        return config;
    }
}
