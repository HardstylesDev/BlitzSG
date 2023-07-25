package me.hardstyles.blitz.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.hardstyles.blitz.BlitzSG;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class Database {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public MongoClient getMongoClient() {
        if (mongoClient == null) {
            createMongoClient();
        }
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            createDatabase();
        }
        return database;
    }

    private void createMongoClient() {
        String host = "localhost";
        int port = 27017; // Default MongoDB port

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(host, port))))
                .build();
        mongoClient = MongoClients.create(settings);
    }

    private void createDatabase() {
        database = getMongoClient().getDatabase("BlitzSG");
        // Perform any other necessary setup operations for MongoDB collections here
        // For example, create or retrieve collections, indexes, etc.

        Bukkit.getLogger().info("Connected to MongoDB.");
    }

    private static void generateConfig() {
        File file = new File(BlitzSG.getInstance().getDataFolder(), "database.yml");
        FileConfiguration fc = new YamlConfiguration();
        fc.set("host", "localhost");
        fc.set("port", "27017"); // Default MongoDB port
        fc.set("database", "BlitzSG");
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

        config.put("host", fc.getString("host"));
        config.put("port", fc.getString("port"));
        config.put("database", fc.getString("database"));
        config.put("username", fc.getString("username"));
        config.put("password", fc.getString("password"));
        return config;
    }
}