package me.syesstyles.blitz.blitzsgplayer;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.arena.Arena;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.kit.Kit;

public class BlitzSGPlayer {
	
	private UUID uuid;

	private int gameKills;
	
	private int elo;
	private int wins;
	private int kills;
	private int deaths;
	private int coins;
	
	private HashMap<Kit, Integer> kitLevels;
	
	private Arena editedArena;
	
	public BlitzSGPlayer(UUID uuid) {
		this.uuid = uuid;
		this.elo = 0;
		this.wins = 0;
		this.kills = 0;
		this.deaths = 0;
		this.coins = 0;
		
		this.kitLevels = new HashMap<Kit, Integer>();
		for(Kit p : BlitzSG.getInstance().getKitManager().getKits())
			this.kitLevels.put(p, 0);
		
		this.gameKills = 0;
		BlitzSG.getInstance().getBlitzSGPlayerManager().addUhcPlayer(this.uuid, this);
	}

	public void loadStats(FileConfiguration statsFile) {
		this.elo = statsFile.getInt("ELO");
		this.wins = statsFile.getInt("Wins");
		this.kills = statsFile.getInt("Kills");
		this.deaths = statsFile.getInt("Deaths");
		this.coins = statsFile.getInt("Coins");
		for(String str : statsFile.getConfigurationSection("Kits").getKeys(false))
			this.kitLevels.put(BlitzSG.getInstance().getKitManager().getKit(str)
					, statsFile.getConfigurationSection("Kits").getInt(str));
	}

	//Player Stats
	public UUID getUuid() {
		return uuid;
	}
	
	public int getElo() {
		return elo;
	}
	
	public void setElo(int elo) {
		this.elo = elo;
	}
	
	public void addElo(int elo) {
		this.elo += elo;
	}
	
	public void removeElo(int elo) {
		if(this.elo - elo <= 0) {
			this.elo = 0;
			return;
		}
		this.elo += -elo;
	}
	
	public int getWins() {
		return this.wins;
	}
	
	public void addWin() {
		this.wins += 1;
	}
	
	public int getKills() {
		return this.kills;
	}
	
	public void addKill() {
		this.kills += 1;
	}
	
	public int getDeaths() {
		return this.deaths;
	}
	
	public void addDeath() {
		this.deaths += 1;
	}
	
	public int getCoins() {
		return this.coins;
	}
	
	public void addCoins(int coins) {
		this.coins += coins;
	}
	
	public void removeCoins(int coins) {
		this.coins += -coins;
	}
	
	public int getKitLevel(Kit p) {
		if(!kitLevels.containsKey(p))
			return 0;
		return this.kitLevels.get(p);
	}
	
	public void setKitLevel(Kit p, int level) {
		this.kitLevels.put(p, level);
	}
	
	
	//Game Stats
	
	public int getGameKills() {
		return this.gameKills;
	}

	public void resetGameKills() {
		this.gameKills = 0;
	}
	
	public void addGameKill() {
		this.gameKills += 1;
		this.kills += 1;
	}
	
	
	//Game Handling Methods
	public boolean isInGame() {
		if(BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayerGame(this) == null)
			return false;
		else 
			return true;
	}

	public Game getGame() {
		return BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayerGame(this);
	}

	public void setGame(Game g) {
		BlitzSG.getInstance().getBlitzSGPlayerManager().setUhcPlayerGame(this, g);
	}

	public boolean isEditingArena() {
		if(this.editedArena == null)
			return false;
		else 
			return true;
	}

	public Arena getEditedArena() {
		return this.editedArena;
	}

	public void setEditedArena(Arena arena) {
		this.editedArena = arena;
	}

}
