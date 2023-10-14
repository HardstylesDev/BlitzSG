package me.hardstyles.blitz.database.impl;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import me.hardstyles.blitz.database.IDatabase;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.punishments.punishtype.PlayerBan;
import me.hardstyles.blitz.punishments.punishtype.PlayerMute;
import me.hardstyles.blitz.punishments.punishtype.PunishType;
import org.bson.Document;
import org.bukkit.Bukkit;
import me.hardstyles.blitz.BlitzSG;

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
        Document document = new Document("uuid", player.getUuid().toString())
                .append("rank", player.getRank().getRank())
                .append("coins", player.getCoins())
                .append("kills", player.getKills())
                .append("deaths", player.getDeaths())
                .append("wins", player.getWins());
        Document kits = new Document();
        player.getKits().forEach((kit, level) -> kits.append(kit.getName(), level));
        getDatabase().getCollection("players").insertOne(document);
    }

    @Override
    public IPlayer loadPlayer(UUID uuid) {
        Document document = getDatabase().getCollection("players").find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            IPlayer p = new IPlayer(UUID.fromString(document.getString("uuid")));
            p.setRank(BlitzSG.getInstance().getRankManager().getRankByName(document.getString("rank")));
            p.setCoins(document.getInteger("coins"));
            p.setKills(document.getInteger("kills"));
            p.setDeaths(document.getInteger("deaths"));
            p.setWins(document.getInteger("wins"));
            p.setDeaths(document.getInteger("deaths"));
            // set kits to their levels
            Document kits = (Document) document.get("kits");
            for (String kit : kits.keySet()) {
                p.setKitLevel(BlitzSG.getInstance().getKitManager().getKit(kit), kits.getInteger(kit));
            }
            return p;

        }

        return null;
    }

    @Override
    public Map<String, Integer> getLeaderboard() {



        return null;
    }

    @Override
    public PlayerMute getMute(UUID uuid) {
        Document document = getDatabase().getCollection("mutes").find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            return new PlayerMute(document.getLong("startTime"), document.getLong("endTime"), document.getString("reason"), document.getString("executor"), document.getBoolean("active"));
        }
        return null;
    }

    @Override
    public ArrayList<PunishType> getMutes(UUID uuid) {
        return null;
    }

    @Override
    public ArrayList<PunishType> getBans(UUID uuid) {
        return null;
    }

    @Override
    public void saveBan(UUID uuid, PlayerBan ban) {
        Document document = new Document("uuid", uuid.toString())
                .append("time", ban.getEndTime())
                .append("reason", ban.getReason())
                .append("executor", ban.getExecutor());
        getDatabase().getCollection("bans").insertOne(document);
    }

    @Override
    public void saveMute(UUID uuid, PlayerMute mute) {
        Document document = new Document("uuid", uuid.toString())
                .append("time", mute.getEndTime())
                .append("reason", mute.getReason())
                .append("executor", mute.getExecutor());
        getDatabase().getCollection("mutes").insertOne(document);
    }

    @Override
    public void revokeBan(UUID uuid) {

    }

    @Override
    public PlayerBan getBan(UUID uuid) {
        Document document = getDatabase().getCollection("bans").find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            return new PlayerBan(document.getLong("startTime"), document.getLong("endTime"), document.getString("reason"), document.getString("executor"), document.getBoolean("active"));
        }
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

    @Override
    public void revokeMute(UUID uniqueId) {

    }

    @Override
    public void remove(UUID id, PunishType punishment) {

    }
}