package me.syesstyles.blitz;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.syesstyles.blitz.command.game.GameCommand;
import me.syesstyles.blitz.command.rank.RankCommand;
import me.syesstyles.blitz.statistics.StatisticsManager;
import me.syesstyles.blitz.arena.ArenaHandler;
import me.syesstyles.blitz.arena.ArenaManager;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerHandler;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerManager;
import me.syesstyles.blitz.commands.CommandHandler;
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
import me.syesstyles.blitz.punishments.ACBan;
import me.syesstyles.blitz.punishments.PunishmentManager;
import me.syesstyles.blitz.punishments.commands.Unban;
import me.syesstyles.blitz.rank.RankManager;
import me.syesstyles.blitz.scoreboard.ScoreboardManager;
import me.syesstyles.blitz.utils.ChatUtil;
import me.syesstyles.blitz.utils.EnchantListener;
import me.syesstyles.blitz.utils.FireworkCommand;
import me.syesstyles.blitz.utils.WorldCommand;
import me.syesstyles.blitz.utils.database.Database;
import me.syesstyles.blitz.utils.nametag.NametagManager;
import me.syesstyles.blitz.utils.nickname.NicknameCommand;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BlitzSG extends JavaPlugin {

    public static String CORE_NAME = EnumChatFormat.GRAY + "[" + EnumChatFormat.RED + "B-SG" + EnumChatFormat.GRAY + "] " + EnumChatFormat.WHITE;


    public static BlitzSG instance;
    private NametagManager nametagManager;
    private StatisticsManager statisticsManager;
    private ArenaManager arenaManager;
    private BlitzSGPlayerManager blitzSGPlayerManager;
    private GameManager gameManager;
    private ScoreboardManager scoreboardManager;
    private RankManager rankManager;
    private EloManager eloManager;
    private GUIManager guiManager;
    private KitManager kitManager;
    private StarManager starManager;
    private PunishmentManager punishmentManager;
    private HikariDataSource hikari;
    public static Location lobbySpawn;
    private Database db;
    private CosmeticsManager cosmeticsManager;

    public BlitzSG() {
        instance = this;
    }

    public Database db(){
        return db;
    }


    public void onEnable() {

        db = new Database();
        statisticsManager = new StatisticsManager();
        blitzSGPlayerManager = new BlitzSGPlayerManager();
        rankManager = new RankManager();
        kitManager = new KitManager();
        arenaManager = new ArenaManager();

        gameManager = new GameManager();
        scoreboardManager = new ScoreboardManager();
        eloManager = new EloManager();
        guiManager = new GUIManager();
        starManager = new StarManager();


        nametagManager = new NametagManager();
        punishmentManager = new PunishmentManager();
        cosmeticsManager = new CosmeticsManager();
        cosmeticsManager.init();

        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&d&lLoading Commands"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        new RankCommand();

        new GameCommand();


        //Register Commands::

        getCommand("bsg").setExecutor(new CommandHandler());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("nick").setExecutor(new NicknameCommand());
        getCommand("fw").setExecutor(new FireworkCommand());
        getCommand("acban").setExecutor(new ACBan());
        getCommand("unban").setExecutor(new Unban());

        //Register Handlers:
        getServer().getPluginManager().registerEvents(new ArenaHandler(), this);
        getServer().getPluginManager().registerEvents(new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new GameMobHandler(), this);
        getServer().getPluginManager().registerEvents(new BlitzSGPlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);


        for (Player p : getServer().getOnlinePlayers()) {
            statisticsManager.loadPlayer(p.getUniqueId());
        }
        scoreboardManager.runTaskTimer(this, 20, 20);
        lobbySpawn = new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0);
        nametagManager.update();
    }

    public void onDisable() {
        //Save Arenas:
        BlitzSG.getInstance().getStatisticsManager().saveAll();
        for (Game g : gameManager.getRunningGames()) {
            g.resetGame();
        }

        BlitzSG.getInstance().getStatisticsManager().saveAll();

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

    public static BlitzSG getInstance() {
        return instance;
    }
}
