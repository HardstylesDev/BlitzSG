package me.hardstyles.blitz.database.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.database.IDatabase;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.punishments.PlayerBan;
import me.hardstyles.blitz.punishments.PlayerMute;
import me.hardstyles.blitz.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLProvider implements IDatabase {

    Map<String, String> config = null;
    Connection connection = null;

    private static String connectUrl;
    private static HikariDataSource dataSource;

    public MySQLProvider(Map<String, String> config) {
        this.config = config;
        connectUrl = String.format("jdbc:mysql://%s:%s/%s?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET", config.get("host"), config.get("port"), config.get("database"));
        connect();
    }

    @Override
    public void connect() {
        createDataSource();

    }

    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private synchronized DataSource getDataSource() {
        if (dataSource == null) {
            createDataSource();
        }
        return dataSource;
    }

    private void createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(connectUrl);
        hikariConfig.setUsername(config.get("username"));
        hikariConfig.setPassword(config.get("password"));
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setPoolName("MysqlPool-1");
        hikariConfig.setMaximumPoolSize(15);
        hikariConfig.setConnectionTimeout(Duration.ofSeconds(30).toMillis());
        hikariConfig.setIdleTimeout(Duration.ofMinutes(10).toMillis());
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        dataSource = hikariDataSource;

        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `stats` (`uuid` VARCHAR(255) PRIMARY KEY, `coins` INT NOT NULL, `kills` INT NOT NULL, `wins` INT NOT NULL, `deaths` INT NOT NULL, `rank` VARCHAR(255) NOT NULL, `nickname` VARCHAR(255) NULL, `stars` TEXT NULL, `aura` TEXT NULL, `taunt` TEXT NULL, `kits` TEXT NULL, `elo` INT NOT NULL, `taunt` VARCHAR(255) NULL, `selectedKit` VARCHAR(255) NULL) ENGINE = InnoDB;").executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `bans` (`id` INT NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(255) NOT NULL, `reason` VARCHAR(255) NOT NULL, `expires` FLOAT NOT NULL, `executor` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;").executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `mutes` (`id` INT NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(255) NOT NULL, `reason` VARCHAR(255) NOT NULL, `expires` FLOAT NOT NULL, `executor` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;").executeUpdate();

            Bukkit.getLogger().info("Connected to database.");
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not connect to SQL database.");
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void savePlayer(IPlayer p) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO `stats`(`uuid`, `coins`, `kills`, `deaths`, `wins`, `rank`, `nickname`, `stars`, `kits`, `elo`, `taunt`, `selectedKit`, `aura`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
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
            if (p.getTaunt() != null) {
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

    @Override
    public IPlayer loadPlayer(UUID uuid) {
        try (Connection connection = getConnection();
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
                if (tauntName != null) {
                    Taunt t = BlitzSG.getInstance().getCosmeticsManager().getTauntByName(tauntName);
                    if (t != null) {
                        player.setTaunt(t);
                    }
                }

                BlitzSG.getInstance().getIPlayerManager().addPlayer(uuid, player);


                PlayerMute mute = BlitzSG.getInstance().getDb().getMute(uuid);
                if(mute != null){
                    Bukkit.getLogger().info("Mute is not null");
                    if(mute.isMuted()){
                        Bukkit.getLogger().info("Mute is muted");
                        player.setMute(mute);
                    }
                }


                return player;

            } else {

                BlitzSG.getInstance().getIPlayerManager().addPlayer(uuid, new IPlayer(uuid));
                return new IPlayer(uuid);
            }

            //muteCheck(uuid);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Integer> getLeaderboard() {

        Map<String, Integer> players = new HashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `stats` ORDER BY `wins` DESC LIMIT 3")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
                players.put(resultSet.getString("uuid"), resultSet.getInt("wins"));
            }
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public PlayerMute getMute(UUID uuid) {
        PlayerMute playerMute = null;
        String query = "SELECT * FROM mutes WHERE uuid = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String reason = resultSet.getString("reason");
                long expires = resultSet.getLong("expires");
                String executor = resultSet.getString("executor");
                playerMute = new PlayerMute(expires, reason, executor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerMute;
    }

    @Override
    public void saveBan(UUID uuid, PlayerBan ban) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `bans`(`uuid`, `reason`, `expires`, `executor`) VALUES (?,?,?,?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, ban.getReason());
            preparedStatement.setFloat(3, ban.getEndTime());
            preparedStatement.setString(4, ban.getSender());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerBan getBan(UUID uuid) {
        PlayerBan playerBan = null;
        String query = "SELECT * FROM bans WHERE uuid = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String reason = resultSet.getString("reason");
                long expires = resultSet.getLong("expires");
                String executor = resultSet.getString("executor");
                playerBan = new PlayerBan(expires, reason, executor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerBan;
    }

    @Override
    public void saveMute(UUID uuid, PlayerMute mute) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `mutes`(`uuid`, `reason`, `expires`, `executor`) VALUES (?,?,?,?)")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, mute.getReason());
            preparedStatement.setFloat(3, mute.getEndTime());
            preparedStatement.setString(4, mute.getSender());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteData(UUID playerId) {

    }

    @Override
    public void removeBan(UUID uniqueId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `bans` WHERE `uuid`=?")) {
            preparedStatement.setString(1, uniqueId.toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeMute(UUID uniqueId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `mutes` WHERE `uuid`=?")) {
            preparedStatement.setString(1, uniqueId.toString());
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

}
