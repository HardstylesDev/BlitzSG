package me.hardstyles.blitz.runtime;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

public final class InstanceLifecycleController {

    private static final String STOP_CHANNEL = "blitz:core:stop-request";

    private InstanceLifecycleController() {
    }

    public static void requestSelfStop(JavaPlugin plugin, String reason) {
        requestSelfStop(plugin, reason, null);
    }

    public static void requestSelfStop(JavaPlugin plugin, String reason, Integer timeoutMs) {
        String serverId = loadServerId();
        if (serverId == null || serverId.trim().isEmpty()) {
            plugin.getLogger().warning("Cannot request core stop: missing server.id in instance.properties");
            return;
        }

        JsonObject payload = new JsonObject();
        payload.addProperty("serverId", serverId);
        payload.addProperty("reason", reason == null ? "unspecified" : reason);
        if (timeoutMs != null && timeoutMs > 0) {
            payload.addProperty("timeoutMs", timeoutMs);
        }

        String serialized = payload.toString();
        if (!publish(plugin.getConfig(), serialized)) {
            plugin.getLogger().warning("Failed to publish stop request for " + serverId);
            return;
        }

        plugin.getLogger().info("Published stop request for " + serverId + " (" + payload.get("reason").getAsString() + ")");
    }

    private static String loadServerId() {
        Properties properties = new Properties();
        File instanceFile = new File("instance.properties");
        if (!instanceFile.exists()) {
            return null;
        }

        try (FileInputStream stream = new FileInputStream(instanceFile)) {
            properties.load(stream);
            return trimToNull(properties.getProperty("server.id"));
        } catch (IOException ignored) {
            return null;
        }
    }

    private static boolean publish(FileConfiguration config, String payload) {
        String redisUrl = trimToNull(config.getString("runtime.redisOverride.url", ""));
        if (redisUrl != null) {
            try (Jedis jedis = new Jedis(URI.create(redisUrl))) {
                jedis.publish(STOP_CHANNEL, payload);
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }

        String host = config.getString("runtime.redisOverride.host", "127.0.0.1");
        int port = config.getInt("runtime.redisOverride.port", 6379);
        String password = trimToNull(config.getString("runtime.redisOverride.password", ""));

        try (Jedis jedis = new Jedis(host, port)) {
            if (password != null) {
                jedis.auth(password);
            }
            jedis.publish(STOP_CHANNEL, payload);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
