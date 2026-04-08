package me.hardstyles.blitz.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import me.hardstyles.blitz.shared.JoinRequest;
import me.hardstyles.blitz.shared.JsonCodec;
import me.hardstyles.blitz.shared.RedisChannels;
import me.hardstyles.blitz.shared.ServerHeartbeat;
import me.hardstyles.blitz.shared.ServerState;
import me.hardstyles.blitz.shared.ServerType;
import me.hardstyles.blitz.shared.SpawnRequest;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Plugin(
        id = "blitzproxy",
        name = "BlitzProxy",
        version = "0.2.0",
        authors = {"Hardstyles"}
)
public class BlitzProxyPlugin {

    private final Map<String, Deque<UUID>> queuedByMap = new ConcurrentHashMap<>();
    private final Map<String, ServerHeartbeat> serversById = new ConcurrentHashMap<>();
    private final Map<String, Long> readyAtByServerId = new ConcurrentHashMap<>();
    private final Set<String> pendingMapSpawns = ConcurrentHashMap.newKeySet();

    private final ProxyServer proxyServer;
    private final Logger logger;
    private final Path dataDirectory;
    private Map<String, Object> config;
    private JedisPool jedisPool;
    private JedisPubSub subscriber;
    private ExecutorService subscriberExecutor;
    private volatile boolean running;

    @Inject
    public BlitzProxyPlugin(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        loadConfiguration();
        initializeRedis();
        registerCommands();
        startRedisSubscriber();
        logger.info("BlitzProxy enabled. Redis routing active.");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        stopRedisSubscriber();
        if (jedisPool != null) {
            jedisPool.close();
        }
        logger.info("BlitzProxy disabled.");
    }

    private void registerCommands() {
        proxyServer.getCommandManager().register(
                proxyServer.getCommandManager().metaBuilder("play").aliases("joinmap", "jmap").build(),
                new PlayCommand(this)
        );
        proxyServer.getCommandManager().register(
                proxyServer.getCommandManager().metaBuilder("blitzproxyreload").aliases("bpr").build(),
                new BlitzProxyReloadCommand(this)
        );
    }

    @SuppressWarnings("unchecked")
    private void loadConfiguration() {
        File folder = dataDirectory.toFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File configFile = new File(folder, "config.yml");
        if (!configFile.exists()) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml")) {
                if (in == null) {
                    throw new IllegalStateException("Missing bundled config.yml");
                }
                Files.copy(in, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to create config.yml", ex);
            }
        }

        try {
            Yaml yaml = new Yaml();
            try (InputStream in = Files.newInputStream(configFile.toPath())) {
                Object loaded = yaml.load(in);
                if (loaded instanceof Map) {
                    config = (Map<String, Object>) loaded;
                } else {
                    config = new LinkedHashMap<>();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load config.yml", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> section(String key) {
        if (config == null) {
            return Collections.emptyMap();
        }
        Object value = config.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return Collections.emptyMap();
    }

    private String configString(String section, String key, String fallback) {
        Object value = section(section).get(key);
        if (value == null) {
            return fallback;
        }
        String converted = String.valueOf(value).trim();
        return converted.isEmpty() ? fallback : converted;
    }

    private int configInt(String section, String key, int fallback) {
        Object value = section(section).get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt(((String) value).trim());
            } catch (NumberFormatException ignored) {
                return fallback;
            }
        }
        return fallback;
    }

    private void initializeRedis() {
        String host = configString("redis", "host", "127.0.0.1");
        int port = configInt("redis", "port", 6379);
        int timeoutMs = configInt("redis", "timeoutMs", 2000);
        String password = configString("redis", "password", "");

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(configInt("redis", "poolMaxTotal", 16));
        poolConfig.setMaxIdle(configInt("redis", "poolMaxIdle", 8));

        if (password == null || password.isEmpty()) {
            jedisPool = new JedisPool(poolConfig, host, port, timeoutMs);
        } else {
            jedisPool = new JedisPool(poolConfig, host, port, timeoutMs, password);
        }
    }

    private void startRedisSubscriber() {
        running = true;
        subscriberExecutor = Executors.newSingleThreadExecutor();
        subscriberExecutor.execute(() -> {
            while (running) {
                try (Jedis jedis = jedisPool.getResource()) {
                    subscriber = new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            handleRedisMessage(channel, message);
                        }
                    };
                    jedis.subscribe(subscriber, RedisChannels.SERVER_HEARTBEAT, RedisChannels.MATCH_JOIN_REQUEST);
                } catch (Exception ex) {
                    if (running) {
                        logger.warn("Redis subscriber disconnected: {}", ex.getMessage());
                        try {
                            Thread.sleep(1500L);
                        } catch (InterruptedException ignored) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
            }
        });
    }

    private void stopRedisSubscriber() {
        running = false;
        if (subscriber != null) {
            try {
                subscriber.unsubscribe();
            } catch (Exception ignored) {
            }
        }
        if (subscriberExecutor != null) {
            subscriberExecutor.shutdownNow();
        }
    }

    private void handleRedisMessage(String channel, String message) {
        try {
            if (RedisChannels.SERVER_HEARTBEAT.equals(channel)) {
                ServerHeartbeat heartbeat = JsonCodec.fromJson(message, ServerHeartbeat.class);
                handleHeartbeat(heartbeat);
                return;
            }

            if (RedisChannels.MATCH_JOIN_REQUEST.equals(channel)) {
                JoinRequest request = JsonCodec.fromJson(message, JoinRequest.class);
                if (request != null && request.getPlayerUuid() != null && request.getMapName() != null) {
                    UUID playerUuid = UUID.fromString(request.getPlayerUuid());
                    queuePlayerForMap(playerUuid, request.getMapName(), request.getSource());
                }
            }
        } catch (Exception ex) {
            logger.warn("Failed handling Redis message on {}: {}", channel, ex.getMessage());
        }
    }

    private synchronized void handleHeartbeat(ServerHeartbeat heartbeat) {
        if (heartbeat == null || heartbeat.getServerId() == null || heartbeat.getServerName() == null) {
            return;
        }

        if (heartbeat.getServerType() != ServerType.GAME && heartbeat.getServerType() != ServerType.HUB) {
            return;
        }

        if (heartbeat.getState() == ServerState.OFFLINE) {
            serversById.remove(heartbeat.getServerId());
            readyAtByServerId.remove(heartbeat.getServerId());
            unregisterServerByName(heartbeat.getServerName());
            return;
        }

        serversById.put(heartbeat.getServerId(), heartbeat);
        ensureRegistered(heartbeat);

        if (heartbeat.getServerType() == ServerType.GAME && heartbeat.getMapName() != null && heartbeat.getState() == ServerState.READY) {
            readyAtByServerId.putIfAbsent(heartbeat.getServerId(), System.currentTimeMillis());
            pendingMapSpawns.remove(normalizeMap(heartbeat.getMapName()));
            dispatchQueuedPlayers(heartbeat.getMapName());
        } else if (heartbeat.getServerType() == ServerType.GAME) {
            readyAtByServerId.remove(heartbeat.getServerId());
        }
    }

    private void ensureRegistered(ServerHeartbeat heartbeat) {
        InetSocketAddress address = new InetSocketAddress(heartbeat.getHost(), heartbeat.getPort());
        Optional<RegisteredServer> existing = proxyServer.getServer(heartbeat.getServerName());
        if (!existing.isPresent()) {
            proxyServer.registerServer(new ServerInfo(heartbeat.getServerName(), address));
            logger.info("Registered dynamic server {} at {}", heartbeat.getServerName(), address);
            return;
        }

        InetSocketAddress currentAddress = existing.get().getServerInfo().getAddress();
        if (!address.equals(currentAddress)) {
            proxyServer.unregisterServer(existing.get().getServerInfo());
            proxyServer.registerServer(new ServerInfo(heartbeat.getServerName(), address));
            logger.info("Updated dynamic server {} from {} to {}", heartbeat.getServerName(), currentAddress, address);
        }
    }

    private void unregisterServerByName(String serverName) {
        if (serverName == null || serverName.trim().isEmpty()) {
            return;
        }

        proxyServer.getServer(serverName.trim()).ifPresent((registeredServer) -> {
            proxyServer.unregisterServer(registeredServer.getServerInfo());
            logger.info("Unregistered dynamic server {}", serverName);
        });
    }

    public synchronized void queuePlayerForMap(UUID playerUuid, String mapName, String source) {
        String normalizedMap = normalizeMap(mapName);
        queuedByMap.computeIfAbsent(normalizedMap, ignored -> new ArrayDeque<>()).add(playerUuid);

        proxyServer.getPlayer(playerUuid)
                .ifPresent(player -> player.sendMessage(Component.text("Queueing for map " + normalizedMap + "...")));

        if (!dispatchQueuedPlayers(normalizedMap)) {
            requestSpawnIfNeeded(normalizedMap, source);
        }
    }

    private synchronized boolean dispatchQueuedPlayers(String mapName) {
        String normalizedMap = normalizeMap(mapName);
        Deque<UUID> queue = queuedByMap.get(normalizedMap);
        if (queue == null || queue.isEmpty()) {
            return false;
        }

        ServerHeartbeat target = serversById.values().stream()
                .filter(server -> server.getState() == ServerState.READY)
                .filter(server -> normalizeMap(server.getMapName()).equals(normalizedMap))
                .filter(server -> server.getCurrentPlayers() < server.getMaxPlayers())
                .min(Comparator.comparingInt(ServerHeartbeat::getCurrentPlayers))
                .orElse(null);

        if (target == null) {
            return false;
        }

        java.util.Optional<RegisteredServer> registeredServer = proxyServer.getServer(target.getServerName());
        if (!registeredServer.isPresent()) {
            return false;
        }

        int warmupMs = configInt("queue", "dispatchWarmupMs", 2500);
        if (warmupMs > 0) {
            long readyAt = readyAtByServerId.getOrDefault(target.getServerId(), System.currentTimeMillis());
            if (System.currentTimeMillis() - readyAt < warmupMs) {
                return false;
            }
        }

        int availableSlots = Math.max(1, target.getMaxPlayers() - target.getCurrentPlayers());
        int moved = 0;
        while (!queue.isEmpty() && moved < availableSlots) {
            UUID playerUuid = queue.poll();
            java.util.Optional<Player> player = proxyServer.getPlayer(playerUuid);
            if (player.isPresent() && player.get().isActive()) {
                player.get().sendMessage(Component.text("Sending you to " + target.getServerName() + "..."));
                player.get().createConnectionRequest(registeredServer.get()).fireAndForget();
                moved++;
            }
        }

        if (queue.isEmpty()) {
            queuedByMap.remove(normalizedMap);
        }

        return moved > 0;
    }

    private synchronized void requestSpawnIfNeeded(String mapName, String source) {
        String normalizedMap = normalizeMap(mapName);
        if (pendingMapSpawns.contains(normalizedMap)) {
            return;
        }

        pendingMapSpawns.add(normalizedMap);
        SpawnRequest request = new SpawnRequest(
                UUID.randomUUID().toString(),
                normalizedMap,
                ServerType.GAME.name(),
                null,
                null,
                2,
                configInt("queue", "defaultGameMaxPlayers", 24),
                System.currentTimeMillis()
        );

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(RedisChannels.CORE_SPAWN_REQUEST, JsonCodec.toJson(request));
        }

        logger.info("Spawn requested for map {} (source={})", normalizedMap, source);
    }

    private String normalizeMap(String mapName) {
        return mapName == null ? "" : mapName.trim().toLowerCase();
    }

    private static final class PlayCommand implements SimpleCommand {
        private final BlitzProxyPlugin plugin;

        private PlayCommand(BlitzProxyPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public void execute(Invocation invocation) {
            CommandSource sender = invocation.source();
            String[] args = invocation.arguments();

            if (!(sender instanceof Player)) {
                sender.sendMessage(Component.text("This command is player-only."));
                return;
            }

            if (args.length < 1) {
                sender.sendMessage(Component.text("Usage: /play <mapName>"));
                return;
            }

            Player player = (Player) sender;
            plugin.queuePlayerForMap(player.getUniqueId(), args[0], "proxy-command");
        }

        @Override
        public List<String> suggest(Invocation invocation) {
            return Collections.emptyList();
        }
    }

    private static final class BlitzProxyReloadCommand implements SimpleCommand {
        private final BlitzProxyPlugin plugin;

        private BlitzProxyReloadCommand(BlitzProxyPlugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public void execute(Invocation invocation) {
            CommandSource sender = invocation.source();

            plugin.stopRedisSubscriber();
            plugin.loadConfiguration();
            if (plugin.jedisPool != null) {
                plugin.jedisPool.close();
            }
            plugin.initializeRedis();
            plugin.startRedisSubscriber();
            sender.sendMessage(Component.text("BlitzProxy runtime reloaded."));
        }

        @Override
        public boolean hasPermission(Invocation invocation) {
            return invocation.source().hasPermission("blitz.admin");
        }
    }
}
