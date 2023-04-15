package me.syesstyles.blitz.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.cosmetic.Aura;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.rank.Rank;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class StatisticsManager {

    public void saveAll() {
        BlitzSG.getInstance().getBlitzSGPlayerManager().getBlitzPlayers().values().forEach(this::savePlayer);
    }

    public void savePlayer(Player p) {
        savePlayer(BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()));
    }

    public void savePlayer(BlitzSGPlayer p) {
        try (Connection connection = BlitzSG.getInstance().getDb().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO `stats`(`uuid`, `coins`, `kills`, `deaths`, `wins`, `rank`, `nickname`, `stars`, `kits`, `elo`, `taunt`, `selectedKit`, `aura`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setString(1, p.getUuid().toString());
            preparedStatement.setInt(2, p.getCoins());
            preparedStatement.setInt(3, p.getKills());
            preparedStatement.setInt(4, p.getDeaths());
            preparedStatement.setInt(5, p.getWins());

            Rank rank = p.getRank();
            if (rank != null) {
                preparedStatement.setString(6, rank.getRank());
            } else {
                preparedStatement.setNull(6, java.sql.Types.VARCHAR);
            }

            String nickName = p.getNickName();
            if (nickName != null) {
                preparedStatement.setString(7, nickName);
            } else {
                preparedStatement.setNull(7, java.sql.Types.VARCHAR);
            }

            preparedStatement.setString(8, starsToString(p));
            preparedStatement.setString(9, kitsToJson(p));
            preparedStatement.setInt(10, p.getElo());
            preparedStatement.setInt(11, 0);

            Kit selectedKit = p.getSelectedKit();
            if (selectedKit != null) {
                preparedStatement.setString(12, selectedKit.getName());
            } else {
                preparedStatement.setNull(12, java.sql.Types.VARCHAR);
            }

            Aura aura = p.getAura();
            if (aura != null) {
                preparedStatement.setString(13, aura.getName());
            } else {
                preparedStatement.setNull(13, java.sql.Types.VARCHAR);
            }

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String starsToString(BlitzSGPlayer p) {
        StringBuilder sb = new StringBuilder();
        for (Star star : p.getStars()) {
            sb.append(star.getName()).append("|");
        }
        return sb.toString();
    }

    private String kitsToJson(BlitzSGPlayer p) {
        Map<String, Integer> kits = new HashMap<>();
        p.getKits().forEach((kit, integer) -> {
            if (kit != null && kit.getName() != null) {
                kits.put(kit.getName(), integer);
            }
        });
        return new GsonBuilder().setPrettyPrinting().create().toJson(kits);
    }

    public void loadAll() {
        try (Connection connection = BlitzSG.getInstance().getDb().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery("SELECT * FROM `stats`");

            while (result.next()) {
                UUID uuid = UUID.fromString(result.getString("uuid"));
                int coins = result.getInt("coins");
                int kills = result.getInt("kills");
                int deaths = result.getInt("deaths");
                int wins = result.getInt("wins");

                String rankName = result.getString("rank");
                Rank rank = (rankName != null) ? BlitzSG.getInstance().getRankManager().getRankByName(rankName) : null;

                String nickName = result.getString("nickname");

                String starsString = result.getString("stars");
                HashSet<Star> stars = new HashSet<>();
                if (starsString != null) {
                    String[] starNames = starsString.split("\\|");
                    for (String starName : starNames) {
                        Star star = BlitzSG.getInstance().getStarManager().getStar(starName);
                        if (star != null) {
                            stars.add(star);
                        }
                    }
                }

                String kitsJson = result.getString("kits");
                Aura aura = null;
                String auraName = result.getString("aura");
                if (auraName != null) {
                    Aura a = BlitzSG.getInstance().getCosmeticsManager().getAuraByName(auraName);
                    if (a != null) {
                        aura = a;
                    }
                }


                HashMap<Kit, Integer> kits = new HashMap<>();
                if (kitsJson != null) {
                    Type type = new TypeToken<HashMap<String, Integer>>() {
                    }.getType();
                    Map<String, Integer> map = new Gson().fromJson(kitsJson, type);
                    map.forEach((kitName, level) -> {
                        Kit kit = BlitzSG.getInstance().getKitManager().getKit(kitName);
                        if (kit != null) {
                            kits.put(kit, level);
                        }
                    });
                }

                int elo = result.getInt("elo");

                String selectedKitName = result.getString("selectedKit");
                Kit selectedKit = (selectedKitName != null) ? BlitzSG.getInstance().getKitManager().getKit(selectedKitName) : null;

                BlitzSGPlayer player = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(uuid);
                if (player != null) {
                    player.setCoins(coins);
                    player.setKills(kills);
                    player.setDeaths(deaths);
                    player.setWins(wins);
                    player.setRank(rank);
                    player.setStars(stars);
                    player.setKitLevels(kits);
                    player.setElo(elo);
                    player.setSelectedKit(selectedKit);
                    player.setAura(aura);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void loadPlayer(UUID uuid) {
        try (Connection connection = BlitzSG.getInstance().getDb().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `stats` WHERE `uuid`=?")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                BlitzSGPlayer player = new BlitzSGPlayer(uuid);

                player.setCoins(resultSet.getInt("coins"));
                player.setKills(resultSet.getInt("kills"));
                player.setDeaths(resultSet.getInt("deaths"));
                player.setWins(resultSet.getInt("wins"));

                String rank = resultSet.getString("rank");
                if (rank != null) {
                    player.setRank(BlitzSG.getInstance().getRankManager().getRankByName(rank));
                }

                String aura = resultSet.getString("aura");
                if (aura != null) {
                    Aura a = BlitzSG.getInstance().getCosmeticsManager().getAuraByName(aura);
                    if (a != null) {
                        player.setAura(a);
                    }
                }



                player.getStars().clear();
                String starsString = resultSet.getString("stars");
                if (starsString != null) {
                    for (String starName : starsString.split("\\|")) {
                        if (!starName.isEmpty()) {
                            player.getStars().add(BlitzSG.getInstance().getStarManager().getStar(starName));
                        }
                    }
                }

                player.getKits().clear();
                String kitsJson = resultSet.getString("kits");
                if (kitsJson != null) {
                    Map<String, Integer> kits = new Gson().fromJson(kitsJson, new TypeToken<Map<String, Integer>>() {
                    }.getType());
                    for (Map.Entry<String, Integer> entry : kits.entrySet()) {
                        Kit kit = BlitzSG.getInstance().getKitManager().getKit(entry.getKey());
                        if (kit != null) {
                            player.getKits().put(kit, entry.getValue());
                        }
                    }
                }

                player.setElo(resultSet.getInt("elo"));

                String selectedKitName = resultSet.getString("selectedKit");
                if (selectedKitName != null) {
                    Kit selectedKit = BlitzSG.getInstance().getKitManager().getKit(selectedKitName);
                    if (selectedKit != null) {
                        player.setSelectedKit(selectedKit);
                    }
                }

                BlitzSG.getInstance().getBlitzSGPlayerManager().addPlayer(uuid, player);
            } else {
                BlitzSG.getInstance().getBlitzSGPlayerManager().addPlayer(uuid, new BlitzSGPlayer(uuid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



