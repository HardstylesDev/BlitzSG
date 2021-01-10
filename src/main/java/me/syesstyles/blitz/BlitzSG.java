package me.syesstyles.blitz;

import me.syesstyles.blitz.arena.ArenaHandler;
import me.syesstyles.blitz.arena.ArenaManager;
import me.syesstyles.blitz.arena.ArenaUtils;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerHandler;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerManager;
import me.syesstyles.blitz.commands.CommandHandler;
import me.syesstyles.blitz.elo.EloManager;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.game.GameHandler;
import me.syesstyles.blitz.game.GameManager;
import me.syesstyles.blitz.game.GameMobHandler;
import me.syesstyles.blitz.gamestar.StarManager;
import me.syesstyles.blitz.gui.GUIManager;
import me.syesstyles.blitz.gui.InventoryHandler;
import me.syesstyles.blitz.kit.KitManager;
import me.syesstyles.blitz.rank.RankManager;
import me.syesstyles.blitz.scoreboard.ScoreboardManager;
import me.syesstyles.blitz.utils.EnchantListener;
import me.syesstyles.blitz.utils.FireworkCommand;
import me.syesstyles.blitz.utils.PlayerUtils;
import me.syesstyles.blitz.utils.WorldCommand;
import me.syesstyles.blitz.utils.nametag.NametagManager;
import me.syesstyles.blitz.utils.nickname.NicknameCommand;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlitzSG extends JavaPlugin {

    public static String CORE_NAME = EnumChatFormat.GRAY + "[" + EnumChatFormat.RED + "B-SG" + EnumChatFormat.GRAY + "] " + EnumChatFormat.WHITE;


    public static BlitzSG instance;
    private NametagManager nametagManager;
    private ArenaManager arenaManager;
    private BlitzSGPlayerManager blitzSGPlayerManager;
    private GameManager gameManager;
    private ScoreboardManager scoreboardManager;
    private RankManager rankManager;
    private EloManager eloManager;
    private GUIManager guiManager;
    private KitManager kitManager;
    private StarManager starManager;

    public static Location lobbySpawn;

    public BlitzSG() {
        instance = this;
    }

    public void onEnable() {

        //Load UHC Core:
        kitManager = new KitManager();
        arenaManager = new ArenaManager();
        blitzSGPlayerManager = new BlitzSGPlayerManager();
        gameManager = new GameManager();
        scoreboardManager = new ScoreboardManager();
        eloManager = new EloManager();
        guiManager = new GUIManager();
        starManager = new StarManager();

        rankManager = new RankManager();
        nametagManager = new NametagManager();
        //Register Commands::

        this.getCommand("bsg").setExecutor(new CommandHandler());
        this.getCommand("world").setExecutor(new WorldCommand());
        this.getCommand("nick").setExecutor(new NicknameCommand());
        this.getCommand("fw").setExecutor(new FireworkCommand());

        //Register Handlers:
        getServer().getPluginManager().registerEvents(new ArenaHandler(), this);
        getServer().getPluginManager().registerEvents(new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new GameMobHandler(), this);
        getServer().getPluginManager().registerEvents(new BlitzSGPlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);

        //Load Players:
        PlayerUtils.loadPlayerData();
        for (Player p : getServer().getOnlinePlayers())
            if (!blitzSGPlayerManager.getUhcPlayers().containsKey(p.getUniqueId()))
                new BlitzSGPlayer(p.getUniqueId());

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
        //Save Arenas:

        PlayerUtils.savePlayerData();
       // try {
       //     ArenaUtils.saveArenas();
       // }catch (Exception e){
       //     System.out.println("we good");
       //     e.printStackTrace();
       // }
        //Reset Running Games:
        for (Game g : gameManager.getRunningGames())
            g.resetGame();
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
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

    public GUIManager getGuiManager() {
        return guiManager;
    }
    public StarManager getStarManager() {
        return starManager;
    }

    public KitManager getKitManager() {
        return kitManager;
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
