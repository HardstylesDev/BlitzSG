package me.syesstyles.blitz.utils.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import me.syesstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Database {

    private static final HashMap<String, String> config = getDatabaseConfig();
    private static final String host = config.get("host");
    private static final String port = config.get("port");
    private static final String database = config.get("database");
    private static final String user = config.get("username");
    private static final String password = config.get("password");

    private static final String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET", host, port, database);

    private static DataSource dataSource;

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
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setPoolName("MysqlPool-1");
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setConnectionTimeout(Duration.ofSeconds(30).toMillis());
        hikariConfig.setIdleTimeout(Duration.ofMinutes(10).toMillis());
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        dataSource = hikariDataSource;

        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `stats` (`uuid` VARCHAR(255) PRIMARY KEY, `coins` INT NOT NULL, `kills` INT NOT NULL, `wins` INT NOT NULL, `deaths` INT NOT NULL, `rank` VARCHAR(255) NOT NULL, `nickname` VARCHAR(255) NULL, `stars` TEXT NULL, `aura` TEXT NULL, `kits` TEXT NULL, `elo` INT NOT NULL, `taunt` VARCHAR(255) NULL, `selectedKit` VARCHAR(255) NULL) ENGINE = InnoDB;").executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `bans` (`id` INT NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(255) NOT NULL, `reason` VARCHAR(255) NOT NULL, `expires` DOUBLE NOT NULL, `executor` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;").executeUpdate();
            Bukkit.getLogger().info("Connected to database.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateConfig() {
        File file = new File(BlitzSG.getInstance().getDataFolder(), "database.yml");
        FileConfiguration fc = new YamlConfiguration();
        fc.set("host", "localhost");
        fc.set("port", "3306");
        fc.set("database", "database");
        fc.set("username", "root");
        fc.set("password", "password");
        try {
            fc.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created database.yml, please edit it and restart the server.");
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