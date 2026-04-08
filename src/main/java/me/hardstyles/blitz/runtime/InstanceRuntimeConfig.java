package me.hardstyles.blitz.runtime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class InstanceRuntimeConfig {
    private final String serverId;
    private final String serverName;
    private final ServerRuntimeMode mode;
    private final String mapName;

    private InstanceRuntimeConfig(String serverId, String serverName, ServerRuntimeMode mode, String mapName) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.mode = mode;
        this.mapName = mapName;
    }

    public static InstanceRuntimeConfig load(JavaPlugin plugin) {
        Properties properties = loadInstanceProperties();

        String serverId = trimToNull(properties.getProperty("server.id"));
        String serverName = trimToNull(properties.getProperty("server.name"));
        ServerRuntimeMode mode = parseMode(properties.getProperty("server.type"));
        String mapName = trimToNull(properties.getProperty("match.map"));

        InstanceRuntimeConfig local = new InstanceRuntimeConfig(serverId, serverName, mode, mapName);
        return loadRedisOverride(plugin, local);
    }

    private static Properties loadInstanceProperties() {
        Properties properties = new Properties();
        File instanceFile = new File("instance.properties");
        if (!instanceFile.exists()) {
            return properties;
        }

        try (FileInputStream stream = new FileInputStream(instanceFile)) {
            properties.load(stream);
        } catch (IOException ignored) {
        }
        return properties;
    }

    private static InstanceRuntimeConfig loadRedisOverride(JavaPlugin plugin, InstanceRuntimeConfig local) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("runtime.redisOverride.enabled", true)) {
            return local;
        }

        if (local.serverId == null || local.serverId.isEmpty()) {
            return local;
        }

        String runtimeValue = fetchRuntimeValue(config, local.serverId);
        if (runtimeValue == null || runtimeValue.isEmpty()) {
            return local;
        }

        Map<String, String> parts = parseDelimitedPayload(runtimeValue);
        String serverName = firstNonBlank(parts.get("serverName"), local.serverName);
        ServerRuntimeMode mode = parseMode(firstNonBlank(parts.get("serverType"), local.mode.name()));
        String mapName = firstNonBlank(parts.get("mapName"), local.mapName);

        return new InstanceRuntimeConfig(local.serverId, serverName, mode, mapName);
    }

    private static String fetchRuntimeValue(FileConfiguration config, String serverId) {
        String key = "blitz:server:runtime:" + serverId;

        String redisUrl = trimToNull(config.getString("runtime.redisOverride.url", ""));
        if (redisUrl != null) {
            try (Jedis jedis = new Jedis(URI.create(redisUrl))) {
                return jedis.get(key);
            } catch (Exception ignored) {
                return null;
            }
        }

        String host = config.getString("runtime.redisOverride.host", "127.0.0.1");
        int port = config.getInt("runtime.redisOverride.port", 6379);
        String password = trimToNull(config.getString("runtime.redisOverride.password", ""));

        try (Jedis jedis = new Jedis(host, port)) {
            if (password != null) {
                jedis.auth(password);
            }
            return jedis.get(key);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static Map<String, String> parseDelimitedPayload(String payload) {
        Map<String, String> values = new HashMap<>();
        String[] entries = payload.split(";");
        for (String entry : entries) {
            if (entry == null || entry.isEmpty()) {
                continue;
            }
            int separator = entry.indexOf('=');
            if (separator <= 0 || separator == entry.length() - 1) {
                continue;
            }
            String key = entry.substring(0, separator).trim();
            String value = entry.substring(separator + 1).trim();
            if (!key.isEmpty()) {
                values.put(key, value);
            }
        }
        return values;
    }

    private static ServerRuntimeMode parseMode(String value) {
        String normalized = value == null ? "" : value.trim();
        if ("HUB".equalsIgnoreCase(normalized)) {
            return ServerRuntimeMode.HUB;
        }
        return ServerRuntimeMode.GAME;
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private static String firstNonBlank(String preferred, String fallback) {
        String cleaned = trimToNull(preferred);
        return cleaned == null ? fallback : cleaned;
    }

    public String getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public ServerRuntimeMode getMode() {
        return mode;
    }

    public String getMapName() {
        return mapName;
    }
}
