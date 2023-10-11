package me.hardstyles.blitz;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.hardstyles.blitz.command.broadcast.BroadcastCommand;
import me.hardstyles.blitz.command.coins.SetCoinsCommand;
import me.hardstyles.blitz.command.join.JoinCommand;
import me.hardstyles.blitz.command.list.ListCommand;
import me.hardstyles.blitz.command.party.PartyChatCommand;
import me.hardstyles.blitz.command.party.PartyCommand;
import me.hardstyles.blitz.command.taunt.TauntCommand;
import me.hardstyles.blitz.command.vote.VoteCommand;
import me.hardstyles.blitz.database.DatabaseProvider;
import me.hardstyles.blitz.database.IDatabase;
import me.hardstyles.blitz.database.impl.MySQLProvider;
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
import me.hardstyles.blitz.punishments.commands.ban.BanCommand;
import me.hardstyles.blitz.punishments.commands.ban.UnbanCommand;
import me.hardstyles.blitz.punishments.commands.mute.MuteCommand;
import me.hardstyles.blitz.punishments.commands.mute.UnmuteCommand;
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
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class BlitzSG extends JavaPlugin {

    public static String CORE_NAME = EnumChatFormat.GRAY + "[" + EnumChatFormat.RED + "B-SG" + EnumChatFormat.GRAY + "]: " + EnumChatFormat.WHITE;

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
    private long startTime;

    public BlitzSG() {
        instance = this;
        startTime = System.currentTimeMillis();
    }

    public IDatabase db() {
        return db;
    }


    public void onEnable() {

        World world = Bukkit.getWorld("world");
        if (world == null) {
            Bukkit.getLogger().severe("World 'world' does not exist!");
        }
        world.setAutoSave(false);


        //delete all directories in the /worlds folder except for the world folder


        db = new DatabaseProvider().getDatabase();
        iPlayerManager = new IPlayerManager();
        rankManager = new RankManager();
        kitManager = new KitManager();
        mapManager = new MapManager();
        BlitzSG.getInstance().getMapManager().deleteWorlds();

        gameManager = new GameManager();
        scoreboardManager = new ScoreboardManager();
        eloManager = new EloManager();
        starManager = new StarManager();

        punishmentManager = new PunishmentManager();
        cosmeticsManager = new CosmeticsManager();
        cosmeticsManager.init();
        leaderboardManager = new LeaderboardManager();
        partyManager = new PartyManager(iPlayerManager);


        //Register Commands::
        registerCommands();

//        getCommand("nick").setExecutor(new NicknameCommand());

        //Register Handlers:
        getServer().getPluginManager().registerEvents(this.gameHandler = new GameHandler(), this);
        getServer().getPluginManager().registerEvents(new GameMobHandler(), this);
        getServer().getPluginManager().registerEvents(new IPlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);

        lobbySpawn = new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0);

        for (Player p : getServer().getOnlinePlayers()) {
            IPlayer iPlayer = BlitzSG.getInstance().getDb().loadPlayer(p.getUniqueId());
            iPlayerManager.toLobby(p);

        }
        scoreboardManager.runTaskTimer(this, 20, 20);

        //KarhuAPI.getEventRegistry().addListener(anticheat);
    }


    private void registerCommands() {
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
        new UnmuteCommand();
        new SetCoinsCommand();
        new BroadcastCommand();
        new JoinCommand();
        new ListCommand();
        new TauntCommand();
        new VoteCommand();
        new PartyCommand();
        new PartyChatCommand();
        Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&d&lFinished Loading Commands!"));

    }

    public void onDisable() {
        if (gameManager != null) {
            for (Game g : gameManager.getRunningGames()) {
                g.resetGame();
            }
        }

        if (iPlayerManager != null) {
            getIPlayerManager().getBlitzPlayers().forEach((uuid, iPlayer) -> {
                getDb().savePlayer(iPlayer);
            });
            BlitzSG.getInstance().getMapManager().deleteWorlds();
        }
        
        this.generateNewWorld();
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
                if(!index.exists()) continue;
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

    public static BlitzSG getInstance() {
        return instance;
    }


}
