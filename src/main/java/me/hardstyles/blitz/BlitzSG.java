package me.hardstyles.blitz;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.hardstyles.blitz.arena.ArenaHandler;
import me.hardstyles.blitz.arena.ArenaManager;
import me.hardstyles.blitz.blitzsgplayer.IPlayerHandler;
import me.hardstyles.blitz.blitzsgplayer.IPlayerManager;
import me.hardstyles.blitz.command.fireworks.FireworkCommand;
import me.hardstyles.blitz.command.hub.HubCommand;
import me.hardstyles.blitz.command.rank.RankCommand;
import me.hardstyles.blitz.commands.CommandHandler;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.StarManager;
import me.hardstyles.blitz.gui.GUIManager;
import me.hardstyles.blitz.gui.InventoryHandler;
import me.hardstyles.blitz.kit.KitManager;
import me.hardstyles.blitz.punishments.ACBan;
import me.hardstyles.blitz.statistics.LeaderboardManager;
import me.hardstyles.blitz.statistics.StatisticsManager;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.EnchantListener;
import me.hardstyles.blitz.utils.nickname.NicknameCommand;
import me.hardstyles.blitz.command.game.GameCommand;
import me.hardstyles.blitz.cosmetic.CosmeticsManager;
import me.hardstyles.blitz.elo.EloManager;
import me.hardstyles.blitz.game.GameHandler;
import me.hardstyles.blitz.game.GameManager;
import me.hardstyles.blitz.game.GameMobHandler;
import me.hardstyles.blitz.punishments.PunishmentManager;
import me.hardstyles.blitz.punishments.commands.Unban;
import me.hardstyles.blitz.rank.RankManager;
import me.hardstyles.blitz.scoreboard.ScoreboardManager;
import me.hardstyles.blitz.utils.WorldCommand;
import me.hardstyles.blitz.utils.database.Database;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public class BlitzSG extends JavaPlugin {

    public static String CORE_NAME = EnumChatFormat.GRAY + "[" + EnumChatFormat.RED + "B-SG" + EnumChatFormat.GRAY + "]: " + EnumChatFormat.WHITE;


    public static BlitzSG instance;
    private StatisticsManager statisticsManager;
    private ArenaManager arenaManager;
    private IPlayerManager iPlayerManager;
    private GameManager gameManager;
    private LeaderboardManager leaderboardManager;
    private ScoreboardManager scoreboardManager;
    private RankManager rankManager;
    private EloManager eloManager;
    private GUIManager guiManager;
    private GameHandler gameHandler;
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

        //delete all directories in the /worlds folder except for the world folder

        deleteWorlds();

        db = new Database();
        statisticsManager = new StatisticsManager();
        iPlayerManager = new IPlayerManager();
        rankManager = new RankManager();
        kitManager = new KitManager();
        arenaManager = new ArenaManager();

        gameManager = new GameManager();
        scoreboardManager = new ScoreboardManager();
        eloManager = new EloManager();
        guiManager = new GUIManager();
        starManager = new StarManager();

        punishmentManager = new PunishmentManager();
        cosmeticsManager = new CosmeticsManager();
        cosmeticsManager.init();
        leaderboardManager = new LeaderboardManager();

        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&d&lLoading Commands"));
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color(" "));
        new RankCommand();
        new GameCommand();
        new HubCommand();
        new FireworkCommand();


        //Register Commands::

        getCommand("bsg").setExecutor(new CommandHandler());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("nick").setExecutor(new NicknameCommand());
        getCommand("acban").setExecutor(new ACBan());
        getCommand("unban").setExecutor(new Unban());

        //Register Handlers:
        getServer().getPluginManager().registerEvents(new ArenaHandler(), this);
        getServer().getPluginManager().registerEvents(this.gameHandler = new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new GameMobHandler(), this);
        getServer().getPluginManager().registerEvents(new IPlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);

        lobbySpawn = new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0);

        for (Player p : getServer().getOnlinePlayers()) {
            statisticsManager.loadPlayer(p.getUniqueId());
            iPlayerManager.toLobby(p);
        }
        scoreboardManager.runTaskTimer(this, 20, 20);
    }

    public void onDisable() {
        //Save Arenas:
        BlitzSG.getInstance().getStatisticsManager().saveAll();
        for (Game g : gameManager.getRunningGames()) {
            g.resetGame();
        }

        BlitzSG.getInstance().getStatisticsManager().saveAll();
        deleteWorlds();
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



    private void deleteWorlds() {
        File worlds = new File("worlds");
        if (worlds.exists()) {
            for (File f : worlds.listFiles()) {
                if (!f.getName().equalsIgnoreCase("world")) {
                    try {
                        FileUtils.forceDelete(f);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Bukkit.getLogger().info("Deleted " + f.getName() + " from " + f.getAbsolutePath() + " ");
                }
            }
        }
    }

}
