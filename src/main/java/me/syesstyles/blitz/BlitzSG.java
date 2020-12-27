package me.syesstyles.blitz;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.syesstyles.blitz.arena.ArenaHandler;
import me.syesstyles.blitz.arena.ArenaManager;
import me.syesstyles.blitz.arena.ArenaUtils;
import me.syesstyles.blitz.commands.CommandHandler;
import me.syesstyles.blitz.elo.EloManager;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.game.GameHandler;
import me.syesstyles.blitz.game.GameManager;
import me.syesstyles.blitz.gui.GUIManager;
import me.syesstyles.blitz.gui.InventoryHandler;
import me.syesstyles.blitz.perk.PerkManager;
import me.syesstyles.blitz.scoreboard.ScoreboardManager;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerHandler;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayerManager;
import me.syesstyles.blitz.utils.EnchantListener;
import me.syesstyles.blitz.utils.PlayerUtils;

public class BlitzSG extends JavaPlugin {

	public static BlitzSG instance;
	
	private ArenaManager arenaManager;
	private BlitzSGPlayerManager blitzSGPlayerManager;
	private GameManager gameManager;
	private ScoreboardManager scoreboardManager;
	private EloManager eloManager;
	private GUIManager guiManager;
	private PerkManager perkManager;
	
	public static Location lobbySpawn;
	
	public BlitzSG() {
		instance = this;
	}
	
	public void onEnable() {
		//Load UHC Core:
		arenaManager = new ArenaManager();
		blitzSGPlayerManager = new BlitzSGPlayerManager();
		gameManager = new GameManager();
		scoreboardManager = new ScoreboardManager();
		eloManager = new EloManager();
		guiManager = new GUIManager();
		perkManager = new PerkManager();
		
		//Register Commands::
		getCommand("speeduhc").setExecutor(new CommandHandler());
		
		//Register Handlers:
		getServer().getPluginManager().registerEvents(new ArenaHandler(), this);
		getServer().getPluginManager().registerEvents(new GameHandler(), this);
		getServer().getPluginManager().registerEvents(new BlitzSGPlayerHandler(), this);
		getServer().getPluginManager().registerEvents(new EnchantListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
		getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);
		
		//Load Players:
		PlayerUtils.loadPlayerData();
		for(Player p : getServer().getOnlinePlayers())
			if(!blitzSGPlayerManager.getUhcPlayers().containsKey(p.getUniqueId()))
				new BlitzSGPlayer(p.getUniqueId());
		
		//Load Arena:
		ArenaUtils.loadArenas();
		
		//Start Scoreboard:




		scoreboardManager.runTaskTimer(this, 20, 20);
		
		//Load LobbySpawn:
		//lobbySpawn = new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0);
		//if(Bukkit.getWorld(this.getConfig().getString("Lobby.Spawn")) == null){
		//	lobbySpawn = new Location(Bukkit.getWorlds().get(0), 0 , 100 , 0);
		//}else
		//	lobbySpawn = new Location(Bukkit.getWorld(this.getConfig().getString("Lobby.Spawn"))
		//		, this.getConfig().getInt("Lobby.X"), this.getConfig().getInt("Lobby.Y"), this.getConfig().getInt("Lobby.Z"));
	}
	
	public void onDisable() {
		//Reset Running Games:
		for(Game g : gameManager.getRunningGames())
			g.resetGame();
		
		//Save Arenas:
		ArenaUtils.saveArenas();
		PlayerUtils.savePlayerData();
	}
	
	public ArenaManager getArenaManager() {
		return arenaManager;
	}
	
	public BlitzSGPlayerManager getSpeedUHCPlayerManager() {
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

	public PerkManager getPerkManager() {
		return perkManager;
	}

	public static BlitzSG getInstance() {
		return instance;
	}

}
