package me.hardstyles.blitz;

import com.zaxxer.hikari.HikariDataSource;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.hardstyles.blitz.command.broadcast.BroadcastCommand;
import me.hardstyles.blitz.command.coins.SetCoinsCommand;
import me.hardstyles.blitz.command.fly.FlyCommand;
import me.hardstyles.blitz.command.friend.FriendCommand;
import me.hardstyles.blitz.command.game.WTFMapCommand;
import me.hardstyles.blitz.command.join.JoinCommand;
import me.hardstyles.blitz.command.list.ListCommand;
import me.hardstyles.blitz.command.me.MeCommand;
import me.hardstyles.blitz.command.party.PartyChatCommand;
import me.hardstyles.blitz.command.party.PartyCommand;
import me.hardstyles.blitz.command.taunt.TauntCommand;
import me.hardstyles.blitz.command.vote.VoteCommand;
import me.hardstyles.blitz.cosmetic.wardrobe.WardrobeCommand;
import me.hardstyles.blitz.database.DatabaseProvider;
import me.hardstyles.blitz.database.IDatabase;
import me.hardstyles.blitz.database.impl.MySQLProvider;
import me.hardstyles.blitz.nickname.NickManager;
import me.hardstyles.blitz.nickname.NicknameCommand;
import me.hardstyles.blitz.nickname.SkinManager;
import me.hardstyles.blitz.party.PartyManager;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.player.IPlayerHandler;
import me.hardstyles.blitz.player.IPlayerManager;
import me.hardstyles.blitz.command.fireworks.FireworkCommand;
import me.hardstyles.blitz.command.hub.HubCommand;
import me.hardstyles.blitz.command.message.MessageCommand;
import me.hardstyles.blitz.command.message.ReplyCommand;
import me.hardstyles.blitz.command.rank.RankCommand;
import me.hardstyles.blitz.command.world.WorldCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.StarManager;
import me.hardstyles.blitz.kit.KitManager;
import me.hardstyles.blitz.map.MapManager;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.punishments.commands.ban.BanCommand;
import me.hardstyles.blitz.punishments.commands.ban.UnbanCommand;
import me.hardstyles.blitz.punishments.commands.mute.MuteCommand;
import me.hardstyles.blitz.punishments.commands.mute.UnmuteCommand;
import me.hardstyles.blitz.punishments.commands.punishment.PunishmentsCommand;
import me.hardstyles.blitz.runtime.InstanceRuntimeConfig;
import me.hardstyles.blitz.runtime.InstanceLifecycleController;
import me.hardstyles.blitz.runtime.ServerRuntimeMode;
import me.hardstyles.blitz.runtime.ServerType;
import me.hardstyles.blitz.statistics.LeaderboardManager;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.EnchantListener;
import me.hardstyles.blitz.command.game.GameCommand;
import me.hardstyles.blitz.cosmetic.CosmeticsManager;
import me.hardstyles.blitz.elo.EloManager;
import me.hardstyles.blitz.game.GameHandler;
import me.hardstyles.blitz.game.GameManager;
import me.hardstyles.blitz.game.GameMobHandler;
import me.hardstyles.blitz.punishments.PunishmentManager;
import me.hardstyles.blitz.rank.RankManager;
import me.hardstyles.blitz.scoreboard.ScoreboardManager;
import me.hardstyles.blitz.menu.MenuListener;
import me.hardstyles.blitz.util.ReflectionUtil;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class BlitzSG extends JavaPlugin implements PluginMessageListener {

    public static String CORE_NAME = EnumChatFormat.GRAY + "[" + EnumChatFormat.RED + "B-SG" + EnumChatFormat.GRAY + "]: " + EnumChatFormat.WHITE;

    @Getter
    public static BlitzSG instance;
    private MapManager mapManager;
    private IPlayerManager iPlayerManager;
    private GameManager gameManager;
    private LeaderboardManager leaderboardManager;
    private ScoreboardManager scoreboardManager;
    private RankManager rankManager;
    private EloManager eloManager;
    private GameHandler gameHandler;
    private KitManager kitManager;
    private StarManager starManager;
    private PunishmentManager punishmentManager;
    private HikariDataSource hikari;
    public static Location lobbySpawn;
    private IDatabase db;
    private CosmeticsManager cosmeticsManager;
    private PartyManager partyManager;
    private SkinManager skinManager;
    private NickManager nickManager;
    private ServerRuntimeMode runtimeMode;
    private String runtimeMapName;
    private String runtimeServerName;
    private long startTime;
    private long emptySinceMillis = -1L;
    private final java.util.Map<String, Integer> hubPlayerCounts = new ConcurrentHashMap<>();

    public BlitzSG() {
        instance = this;
        startTime = System.currentTimeMillis();
    }

    public IDatabase db() {
        return db;
    }

    public ServerType getServerType() {
        return runtimeMode == ServerRuntimeMode.HUB ? ServerType.HUB : ServerType.GAME;
    }

    public static ServerType GetServerType() {
        BlitzSG plugin = getInstance();
        if (plugin == null) {
            return ServerType.GAME;
        }
        return plugin.getServerType();
    }

    public static boolean isHubServer() {
        return GetServerType() == ServerType.HUB;
    }

    public static boolean isGameServer() {
        return GetServerType() == ServerType.GAME;
    }

    public static void sendPlayerToServer(Player player, String serverName) {
        if (player == null || !player.isOnline()) {
            return;
        }

        if (serverName == null || serverName.trim().isEmpty()) {
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName.trim());
        player.sendPluginMessage(getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void sendPlayerToHub(Player player) {
        List<String> hubs = getInstance().getConfig().getStringList("runtime.hubServers");
        String fallback = getInstance().getConfig().getString("runtime.defaultHubServer", "hub-1");
        String target = (hubs == null || hubs.isEmpty()) ? fallback : hubs.get(0);
        sendPlayerToServer(player, target);
    }

    public static void sendPlayerToAnotherHub(Player player) {
        BlitzSG plugin = getInstance();
        List<String> hubs = plugin.getConfig().getStringList("runtime.hubServers");
        if (hubs == null || hubs.isEmpty()) {
            sendPlayerToHub(player);
            return;
        }

        String current = plugin.runtimeServerName == null ? "" : plugin.runtimeServerName.trim().toLowerCase(Locale.ROOT);
        for (String hub : hubs) {
            if (hub == null || hub.trim().isEmpty()) {
                continue;
            }
            if (!hub.trim().toLowerCase(Locale.ROOT).equals(current)) {
                sendPlayerToServer(player, hub);
                return;
            }
        }

        sendPlayerToHub(player);
    }

    public static List<String> getConfiguredHubServers() {
        List<String> hubs = getInstance().getConfig().getStringList("runtime.hubServers");
        if (hubs == null || hubs.isEmpty()) {
            List<String> fallback = new ArrayList<>();
            fallback.add(getInstance().getConfig().getString("runtime.defaultHubServer", "hub-1"));
            return fallback;
        }
        return hubs;
    }

    public int getHubPlayerCount(String serverName) {
        if (serverName == null) {
            return -1;
        }
        Integer count = hubPlayerCounts.get(serverName.trim().toLowerCase(Locale.ROOT));
        return count == null ? -1 : count;
    }

    public void requestHubPlayerCounts(Player requester) {
        if (requester == null || !requester.isOnline()) {
            return;
        }

        for (String hub : getConfiguredHubServers()) {
            if (hub == null || hub.trim().isEmpty()) {
                continue;
            }
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PlayerCount");
            out.writeUTF(hub.trim());
            requester.sendPluginMessage(this, "BungeeCord", out.toByteArray());
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!"BungeeCord".equals(channel) || message == null) {
            return;
        }

        try {
            com.google.common.io.ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subChannel = in.readUTF();
            if (!"PlayerCount".equalsIgnoreCase(subChannel)) {
                return;
            }

            String server = in.readUTF();
            int playerCount = in.readInt();
            if (server != null) {
                hubPlayerCounts.put(server.trim().toLowerCase(Locale.ROOT), playerCount);
            }
        } catch (Exception ignored) {
        }
    }


    public void onEnable() {
        saveDefaultConfig();

        InstanceRuntimeConfig runtimeConfig = InstanceRuntimeConfig.load(this);
        runtimeMode = runtimeConfig.getMode();
        runtimeMapName = runtimeConfig.getMapName();
        runtimeServerName = runtimeConfig.getServerName();

        getLogger().info("Runtime mode=" + runtimeMode.name() + ", map=" + (runtimeMapName == null ? "" : runtimeMapName));

        World world = Bukkit.getWorld("world");
        if (world == null) {
            Bukkit.getLogger().severe("World 'world' does not exist!");
        } else {
            world.setAutoSave(false);
        }


        db = new DatabaseProvider().getDatabase();
        iPlayerManager = new IPlayerManager();
        rankManager = new RankManager();
        kitManager = new KitManager();
        starManager = new StarManager();
        scoreboardManager = new ScoreboardManager();
        punishmentManager = new PunishmentManager();
        cosmeticsManager = new CosmeticsManager();
        cosmeticsManager.init();
        leaderboardManager = new LeaderboardManager();
        partyManager = new PartyManager(iPlayerManager);
        skinManager = new SkinManager();
        nickManager = new NickManager();

        if (runtimeMode != ServerRuntimeMode.HUB) {
            mapManager = new MapManager();
            BlitzSG.getInstance().getMapManager().deleteWorlds();

            gameManager = new GameManager();
            eloManager = new EloManager();
        }


        //Register Commands::
        registerCommands();


        //Register Handlers:
        getServer().getPluginManager().registerEvents(new IPlayerHandler(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);

        if (runtimeMode != ServerRuntimeMode.HUB) {
            getServer().getPluginManager().registerEvents(this.gameHandler = new GameHandler(), this);
            getServer().getPluginManager().registerEvents(new GameMobHandler(), this);
        }

        lobbySpawn = new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0);

        for (Player p : getServer().getOnlinePlayers()) {
            IPlayer iPlayer = BlitzSG.getInstance().getDb().loadPlayer(p.getUniqueId());
            if (runtimeMode == ServerRuntimeMode.HUB) {
                iPlayerManager.toLobby(p);
                continue;
            }

            if (gameManager != null) {
                Game availableGame = gameManager.getAvailableGame();
                if (availableGame != null) {
                    availableGame.addPlayer(p);
                }
            }

        }

        if (runtimeMode == ServerRuntimeMode.HUB) {
            getLogger().info("Running in HUB mode. Game systems are disabled for this instance.");
            scoreboardManager.runTaskTimer(this, 20, 20);
            return;
        }

        bootstrapPreferredMap();
        startIdleShutdownWatchdog();
        scoreboardManager.runTaskTimer(this, 20, 20);
    }

    private void startIdleShutdownWatchdog() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (!isGameServer()) {
                return;
            }

            int online = Bukkit.getOnlinePlayers().size();
            if (online > 0) {
                emptySinceMillis = -1L;
                return;
            }

            long now = System.currentTimeMillis();
            if (emptySinceMillis <= 0) {
                emptySinceMillis = now;
                return;
            }

            if (now - emptySinceMillis >= 120000L) {
                InstanceLifecycleController.requestSelfStop(this, "empty-for-2-minutes");
                emptySinceMillis = Long.MAX_VALUE;
            }
        }, 20L, 20L * 10L);
    }

    private void bootstrapPreferredMap() {
        if (runtimeMapName == null || runtimeMapName.trim().isEmpty()) {
            return;
        }

        String normalizedMap = runtimeMapName.trim().toLowerCase(Locale.ROOT);
        File worldTemplate = new File("arenas", normalizedMap);
        if (!worldTemplate.exists()) {
            getLogger().warning("Configured runtime map template is missing: " + worldTemplate.getPath());
            return;
        }

        getLogger().info("Bootstrapping preferred map: " + normalizedMap);
        Bukkit.getScheduler().runTask(this, () -> new Game(new Map(normalizedMap)));
    }


    private void registerCommands() {
        CommandMap map = null;
        try {
            map = (CommandMap) ReflectionUtil.getField(Bukkit.getServer().getClass(), "commandMap").get(Bukkit.getServer());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ReflectionUtil.unregisterCommands(map, "me");
        ReflectionUtil.unregisterCommands(map, "minecraft:me");
        ReflectionUtil.unregisterCommands(map, "tell");
        ReflectionUtil.unregisterCommands(map, "minecraft:tell");
        ReflectionUtil.unregisterCommands(map, "msg");
        ReflectionUtil.unregisterCommands(map, "minecraft:msg");
        ReflectionUtil.unregisterCommands(map, "say");
        ReflectionUtil.unregisterCommands(map, "minecraft:say");

        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&d&lLoading Commands..."));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        new RankCommand();
        new GameCommand();
        new HubCommand();
        new FireworkCommand();
        new MessageCommand();
        new ReplyCommand();
        new WorldCommand();
        new BanCommand();
        new UnbanCommand();
        new MuteCommand();
        new WardrobeCommand();
        new UnmuteCommand();
        new SetCoinsCommand();
        new BroadcastCommand();
        new JoinCommand();
        new ListCommand();
        new PunishmentsCommand();
        new TauntCommand();
        new WTFMapCommand();
        new NicknameCommand();
        new FlyCommand();
        new VoteCommand();
        new PartyCommand();
        new PartyChatCommand();
        new FriendCommand();
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&d&lFinished Loading Commands!"));

    }

    public void onDisable() {
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");

        if (runtimeMode != ServerRuntimeMode.HUB && gameManager != null) {
            for (Game g : gameManager.getRunningGames()) {
                g.resetGame();
            }
        }

        if (iPlayerManager != null) {
            getIPlayerManager().getBlitzPlayers().forEach((uuid, iPlayer) -> {
                if (runtimeMode == ServerRuntimeMode.HUB) {
                    getDb().savePlayerProfile(iPlayer);
                } else {
                    getDb().savePlayerStats(iPlayer);
                }
            });
            if (runtimeMode != ServerRuntimeMode.HUB && BlitzSG.getInstance().getMapManager() != null) {
                BlitzSG.getInstance().getMapManager().deleteWorlds();
            }
        }

        if (runtimeMode != ServerRuntimeMode.HUB) {
            this.generateNewWorld();
        }
        if (this.db != null) {
            this.db.disconnect();
        }
        //this.clearWorldData();
    }

    private void generateNewWorld() {
        File source = new File("arenas/world");
        File target = new File("worlds/world");
        try {
            FileUtils.copyDirectory(source, target);
        } catch (Exception e) {
            System.out.println("Failed to generate new world on shutdown. " + e.getMessage());
        }
        clearWorldData();
    }

    private void clearWorldData() {
        try {
            String[] directories = new String[]{"playerdata", "stats"};
            for (String directory : directories) {
                final File index = new File(Bukkit.getWorlds().get(0).getWorldFolder() + "/" + directory);
                if (!index.exists()) continue;
                for (String s : index.list()) {
                    new File(index.getPath(), s).delete();
                }
                index.delete();
            }

            String[] files = new String[]{"uid.dat", "session.lock"};
            for (String file : files) {
                final File index = new File(Bukkit.getWorlds().get(0).getWorldFolder() + "/" + file);
                index.delete();
            }


        } catch (Exception e) {
            System.out.println("Failed to clear world data on shutdown. " + e.getMessage());
        }
    }

    public static void broadcast(String message, World world) {
        if (world == null)
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        else
            world.getPlayers().forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
    }

    public static void send(Player p, String message) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


}
