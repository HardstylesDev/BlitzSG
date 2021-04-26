package me.syesstyles.blitz;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.zaxxer.hikari.HikariDataSource;
import me.liwk.karhu.api.KarhuAPI;
import me.syesstyles.blitz.statistics.StatisticsManager;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerHandler;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerManager;
import me.syesstyles.blitz.cosmetic.CosmeticsManager;
import me.syesstyles.blitz.elo.EloManager;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.game.GameHandler;
import me.syesstyles.blitz.game.GameManager;
import me.syesstyles.blitz.game.GameMobHandler;
import me.syesstyles.blitz.gamestar.StarManager;
import me.syesstyles.blitz.gui.GUIManager;
import me.syesstyles.blitz.gui.InventoryHandler;
import me.syesstyles.blitz.kit.KitManager;
import me.syesstyles.blitz.map.MapHandler;
import me.syesstyles.blitz.map.MapManager;
import me.syesstyles.blitz.punishments.ACBan;
import me.syesstyles.blitz.punishments.PunishmentManager;
import me.syesstyles.blitz.punishments.commands.Unban;
import me.syesstyles.blitz.rank.RankManager;
import me.syesstyles.blitz.scoreboard.ScoreboardManager;
import me.syesstyles.blitz.utils.*;
import me.syesstyles.blitz.utils.bungee.BungeeMessaging;
import me.syesstyles.blitz.utils.database.Database;
import me.syesstyles.blitz.utils.nametag.NametagManager;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;

public class BlitzSG extends JavaPlugin {

    public static String CORE_NAME = EnumChatFormat.GRAY + "[" + EnumChatFormat.RED + "B-SG" + EnumChatFormat.GRAY + "]: " + EnumChatFormat.WHITE;

    private JedisPool pool;

    public static BlitzSG instance;
    private KarhuAnticheat karhuAnticheat;
    private NametagManager nametagManager;
    private MapManager mapManager;
    private BlitzSGPlayerManager blitzSGPlayerManager;
    private GameManager gameManager;
    private ScoreboardManager scoreboardManager;
    private RankManager rankManager;
    private EloManager eloManager;
    private GUIManager guiManager;
    private KitManager kitManager;
    private StarManager starManager;
    private PunishmentManager punishmentManager;
    private StatisticsManager statisticsManager;

    private HikariDataSource hikari;
    public static Location lobbySpawn;

    private Database database;
    private CosmeticsManager cosmeticsManager;

    public BlitzSG() {
        instance = this;
    }

    public void onEnable() {
        try {
            new VanillaCommands().remove();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        pool = new JedisPool("127.0.0.1");
        Jedis j = null;
        try {

            j = pool.getResource();
            j.set("canJoin", "false");
            System.out.println("Disabled joins, canJoin = false");
        } finally {
            j.close();
        }


        karhuAnticheat = new KarhuAnticheat();
        database = new Database();
        blitzSGPlayerManager = new BlitzSGPlayerManager();
        statisticsManager = new StatisticsManager();
        rankManager = new RankManager();
        kitManager = new KitManager();
        mapManager = new MapManager();

        gameManager = new GameManager();
        scoreboardManager = new ScoreboardManager();
        eloManager = new EloManager();
        guiManager = new GUIManager();
        starManager = new StarManager();


        nametagManager = new NametagManager();
        punishmentManager = new PunishmentManager();
        cosmeticsManager = new CosmeticsManager();
        cosmeticsManager.initializeAuras();
        //Register Commands::

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessaging());


        // this.getCommand("world").setExecutor(new WorldCommand());

        this.getCommand("fw").setExecutor(new FireworkCommand());
        this.getCommand("acban").setExecutor(new ACBan());
        this.getCommand("unban").setExecutor(new Unban());
        this.getCommand("taunt").setExecutor(new GameHandler());
        this.getCommand("wtfmap").setExecutor(new WTFMapCommand());
        this.getCommand("startdm").setExecutor(new StartDMCommand());

        //Register Handlers:
        getServer().getPluginManager().registerEvents(new MapHandler(), this);
        getServer().getPluginManager().registerEvents(new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new GameMobHandler(), this);
        getServer().getPluginManager().registerEvents(new BlitzSGPlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);

        getServer().setWhitelist(false);

        KarhuAPI.getEventRegistry().addListener(karhuAnticheat);



        World world =  Bukkit.getWorld("world");
        File playerdataFolder = new File(world.getWorldFolder() + "/playerdata/");
        File[] contents = playerdataFolder.listFiles();
        if(contents != null){
            for (File content : contents) {
                content.delete();
            }
        }

        //Load Players:
        //PlayerUtils.loadPlayerData();
        //new LoadStats().load();
        //statisticsManager.load();
        // System.out.println("looaded dataaa");
        for (Player p : getServer().getOnlinePlayers()) {
            statisticsManager.load(p.getUniqueId());
            BlitzSGPlayer bsgPlayer = blitzSGPlayerManager.getBsgPlayer(p.getUniqueId());
            blitzSGPlayerManager.addBsgPlayer(p.getUniqueId(), bsgPlayer);
            bsgPlayer.setName(p.getDisplayName());
            bsgPlayer.setIp(p.getAddress().toString().split(":")[0].replaceAll("/", ""));
            // p.setPlayerListName(bsgPlayer.getRank(true).getPrefix() + p.getName() + BlitzSG.getInstance().getEloManager().getEloLevel(bsgPlayer.getElo()).getPrefix() + " [" + bsgPlayer.getElo() + "]");
        }


        mapManager.loadArena(mapManager.getRandom());

        //Load Arena:
        //ArenaUtils.loadArenas();
        // arenaManager.loadArena("aelinstower");

        //Start Scoreboard:


        scoreboardManager.runTaskTimer(this, 20, 20);

        //Load LobbySpawn:

        lobbySpawn = new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0);
        nametagManager.update();


    }

    public void onDisable() {
        Jedis jedisResource = pool.getResource();
        jedisResource.set("canJoin", "false");
        jedisResource.close();
        //PlayerUtils.savePlayerData();
        //statisticsManager.save();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("lobby");
            onlinePlayer.sendPluginMessage(BlitzSG.getInstance(), "BungeeCord", out.toByteArray());
        }
        try {
            //new SaveStats().saveAll();
        } catch (Exception e) {
        }
        //Reset Running Games:
        for (Game g : gameManager.getRunningGames()) {

            g.resetGame();
            mapManager.removeArena(g.getArena());

        }


        pool.close();
    }

    public KarhuAnticheat getKarhuAnticheat(){return karhuAnticheat;}
    public Database getData() {
        return database;
    }

    public MapManager getArenaManager() {
        return mapManager;
    }

    public BlitzSGPlayerManager getBlitzSGPlayerManager() {
        return blitzSGPlayerManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public EloManager getEloManager() {
        return eloManager;
    }

    public CosmeticsManager getCosmeticsManager() {
        return cosmeticsManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public StarManager getStarManager() {
        return starManager;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public StatisticsManager getStatisticsManager() {
        return statisticsManager;
    }

    public JedisPool getJedisPool() {
        return pool;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public NametagManager getNametagManager() {
        return nametagManager;
    }

    public static BlitzSG getInstance() {
        return instance;
    }


    public static void broadcast(String message, World world) {
        if (world == null)
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        else
            world.getPlayers().forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
    }

    public static void broadcast(String message) {
        broadcast(message, null);
    }

    public static void send(Player p, String message) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
