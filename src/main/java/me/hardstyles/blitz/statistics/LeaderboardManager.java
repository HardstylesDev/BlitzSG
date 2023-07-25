package me.hardstyles.blitz.statistics;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class LeaderboardManager {

    HashMap<Integer, LeaderboardPlayer> leaderboard = new HashMap<>();
    HashMap<Integer, Location> blocks = new HashMap<>();
    HashMap<Integer, Location> signs = new HashMap<>();

    public LeaderboardManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BlitzSG.getInstance(), () -> {
            update();
            updateBlocks();
        }, 0, 20 * 60 * 5);
        blocks.put(3, new Location(Bukkit.getWorld("world"), -42, 104, 15));
        blocks.put(2, new Location(Bukkit.getWorld("world"), -44, 104, 15));
        blocks.put(1, new Location(Bukkit.getWorld("world"), -43, 105, 15));

        signs.put(3, new Location(Bukkit.getWorld("world"), -42, 103, 14));
        signs.put(2, new Location(Bukkit.getWorld("world"), -44, 103, 14));
        signs.put(1, new Location(Bukkit.getWorld("world"), -43, 104, 14));
    }

    public void update() {
        try {
            MongoDatabase database = BlitzSG.getInstance().getDb().getDatabase();
            MongoCollection<Document> collection = database.getCollection("stats");
            leaderboard.clear();

            Document sortQuery = new Document("wins", -1);
            Document projectionQuery = new Document("uuid", 1).append("wins", 1);
            int i = 0;
            for (Document doc : collection.find().sort(sortQuery).projection(projectionQuery).limit(3)) {
                i++;
                int wins = doc.getInteger("wins", 0);
                UUID uuid = UUID.fromString(doc.getString("uuid"));
                leaderboard.put(i, new LeaderboardPlayer(wins, uuid, i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBlocks() {
        for (int i = 1; i <= 3; i++) {
            Location blockLoc = blocks.get(i);
            Location signLoc = signs.get(i);
            if (blockLoc != null && signLoc != null) {
                Block block = blockLoc.getBlock();
                if (block.getState() instanceof Skull) {
                    Skull skull = (Skull) block.getState();
                    if (leaderboard.containsKey(i)) {
                        LeaderboardPlayer player = leaderboard.get(i);
                        skull.setOwner(player.getName());
                        skull.update(true);
                        signLoc.clone().getBlock().setType(Material.WALL_SIGN);
                        Sign sign = (Sign) signLoc.clone().add(0, 0, 0).getBlock().getState();
                        sign.setLine(1, ChatUtil.color(player.getName()));
                        sign.setLine(2, ChatUtil.color(String.valueOf(player.getWins())));
                        sign.update();
                    } else {
                        skull.setOwner(null);
                        skull.update(true);
                        signLoc.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }


    static class LeaderboardPlayer {
        private final int wins;
        private final String name;
        private final UUID uuid;
        private final int position;

        public LeaderboardPlayer(int wins, UUID uuid, int position) {
            this.wins = wins;
            this.uuid = uuid;
            this.position = position;
            this.name = getNameFromUuid(uuid);
        }

        private String getNameFromUuid(UUID uuid) {
            String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "");
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                JsonObject json = new JsonParser().parse(response.toString()).getAsJsonObject();
                return json.get("name").getAsString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public int getWins() {
            return wins;
        }

        public String getName() {
            return name;
        }

        public UUID getUuid() {
            return uuid;
        }

        public int getPosition() {
            return position;
        }
    }
}