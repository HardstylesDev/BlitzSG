package me.hardstyles.blitz.statistics;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.hardstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        }, 0, 20 * 60 * 1);
        blocks.put(3, new Location(Bukkit.getWorld("world"), -42, 104, 15));
        blocks.put(2, new Location(Bukkit.getWorld("world"), -44, 104, 15));
        blocks.put(1, new Location(Bukkit.getWorld("world"), -43, 105, 15));

        signs.put(3, new Location(Bukkit.getWorld("world"), -42, 103, 14));
        signs.put(2, new Location(Bukkit.getWorld("world"), -44, 103, 14));
        signs.put(1, new Location(Bukkit.getWorld("world"), -43, 104, 14));
    }

    public void update() {
        try (Connection connection = BlitzSG.getInstance().getDb().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `stats` ORDER BY `wins` DESC LIMIT 3")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
                leaderboard.put(i, new LeaderboardPlayer(resultSet.getInt("wins"), UUID.fromString(resultSet.getString("uuid")), i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(leaderboard.get(1).getName() + " " + leaderboard.get(1).getWins());
        System.out.println(leaderboard.get(2).getName() + " " + leaderboard.get(2).getWins());
        System.out.println(leaderboard.get(3).getName() + " " + leaderboard.get(3).getWins());

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
                        String displayName = String.format("&a#%d &7%s", player.getPosition(), player.getName());
                        String[] lines = {displayName};
                        for (int j = 0; j < lines.length; j++) {
                            String line = lines[j];
                            if (line.length() > 15) {
                                line = line.substring(0, 15);
                            }
                            signLoc.clone().add(0, j, 0).getBlock().setType(Material.WALL_SIGN);
                            Sign sign = (Sign) signLoc.clone().add(0, j, 0).getBlock().getState();
                            sign.setLine(1, ChatColor.translateAlternateColorCodes('&', player.getName()));
                            sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "" + player.getWins()));
                            sign.update();
                        }
                    } else {
                        skull.setOwner(null);
                        skull.update(true);
                        signLoc.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }


    class LeaderboardPlayer {
        private int wins;
        private String name;
        private UUID uuid;
        private int position;

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
                String name = json.get("name").getAsString();
                return name;
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