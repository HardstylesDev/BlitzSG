package me.hardstyles.blitz.database.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import me.hardstyles.blitz.database.IDatabase;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.punishments.PlayerBan;
import me.hardstyles.blitz.punishments.PlayerMute;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import me.hardstyles.blitz.BlitzSG;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MongoProvider implements IDatabase {

    Map<String, String> config = null;
    public MongoProvider(Map<String, String> config) {
        this.config = config;
    }

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public MongoClient getMongoClient() {
        if (mongoClient == null) {
            createMongoClient();
        }
        return mongoClient;
    }

    public MongoDatabase getDatabase() {

        return database;
    }

    private void createMongoClient() {
        String host = config.get("host");
        int port = Integer.parseInt(config.get("port"));
        MongoClientSettings settings = MongoClientSettings.builder().applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(host, port)))).build();
        mongoClient = MongoClients.create(settings);
    }

    @Override
    public void connect() {
        database = getMongoClient().getDatabase("BlitzSG");
        Bukkit.getLogger().info("Connected to MongoDB.");
    }

    @Override
    public void disconnect() {
        // TODO: Disconnect from MongoDB, probably not needed
    }

    @Override
    public void savePlayer(IPlayer player) {

    }

    @Override
    public IPlayer loadPlayer(UUID uuid) {
        return null;
    }

    @Override
    public Map<String, Integer> getLeaderboard() {
        return null;
    }

    @Override
    public PlayerMute getMute(UUID uuid) {
        return null;
    }

    @Override
    public void saveBan(UUID uuid, PlayerBan ban) {

    }

    @Override
    public void saveMute(UUID uuid, PlayerMute mute) {

    }

    @Override
    public PlayerBan getBan(UUID uuid) {
        return null;
    }

    @Override
    public void deleteData(UUID playerId) {

    }

    @Override
    public void removeBan(UUID uniqueId) {

    }

    @Override
    public void removeMute(UUID uniqueId) {

    }
}