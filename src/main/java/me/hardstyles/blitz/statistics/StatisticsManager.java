package me.hardstyles.blitz.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.cosmetic.Aura;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.punishments.PlayerMute;
import me.hardstyles.blitz.rank.Rank;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class StatisticsManager {

    public void saveAll() {
        BlitzSG.getInstance().getIPlayerManager().getBlitzPlayers().values().forEach(this::savePlayer);
    }

    public void savePlayer(Player p) {
        savePlayer(BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId()));
    }

    public void savePlayer(IPlayer p) {
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
            if(p.getTaunt() != null) {
                preparedStatement.setString(11, p.getTaunt().getName());
            } else {
                preparedStatement.setNull(11, java.sql.Types.VARCHAR);
            }

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

    private String starsToString(IPlayer p) {
        StringBuilder sb = new StringBuilder();
        for (Star star : p.getStars()) {
            sb.append(star.getName()).append("|");
        }
        return sb.toString();
    }

    private String kitsToJson(IPlayer p) {
        Map<String, Integer> kits = new HashMap<>();
        p.getKits().forEach((kit, integer) -> {
            if (kit != null && kit.getName() != null) {
                kits.put(kit.getName(), integer);
            }
        });
        return new GsonBuilder().setPrettyPrinting().create().toJson(kits);

    }


    public void loadPlayer(UUID uuid) {
        try (Connection connection = BlitzSG.getInstance().getDb().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `stats` WHERE `uuid`=?")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                IPlayer player = new IPlayer(uuid);

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

                String tauntName = resultSet.getString("taunt");
                if(tauntName != null) {
                    Taunt t = BlitzSG.getInstance().getCosmeticsManager().getTauntByName(tauntName);
                    if(t != null) {
                        player.setTaunt(t);
                    }
                }

                BlitzSG.getInstance().getIPlayerManager().addPlayer(uuid, player);







            } else {
                BlitzSG.getInstance().getIPlayerManager().addPlayer(uuid, new IPlayer(uuid));
            }
            muteCheck(uuid);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void muteCheck(UUID uuid) {
        try {
            Connection conn = BlitzSG.getInstance().db().getConnection();
            String banSql = "SELECT * FROM mutes WHERE uuid = ?;";
            PreparedStatement banPs = conn.prepareStatement(banSql);
            banPs.setString(1, uuid.toString());
            ResultSet banRs = banPs.executeQuery();
            while (banRs.next()) {
                if (banRs.getDouble("expires") != -1) {
                    double expireDate = banRs.getDouble("expires");
                    if (System.currentTimeMillis() > expireDate) {
                        String deleteCommand = "DELETE FROM bans WHERE uuid = ?";
                        PreparedStatement deletePs = conn.prepareStatement(deleteCommand);
                        deletePs.setString(1, uuid.toString());
                        deletePs.execute();
                        return;
                    }
                    String reason = banRs.getString("reason");

                    IPlayer player = BlitzSG.getInstance().getIPlayerManager().getPlayer(uuid);
                    player.setMute(new PlayerMute((long) expireDate, reason));
                }
                banRs.close();
                banPs.close();
                conn.close();
                return;
            }
            banRs.close();
            banPs.close();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}



