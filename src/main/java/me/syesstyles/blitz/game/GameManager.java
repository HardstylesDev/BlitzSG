package me.syesstyles.blitz.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.game.Game.GameMode;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;

public class GameManager {
	
	private HashSet<Game> games;
	
	public GameManager() {
		games = new HashSet<Game>();
	}
	
	public Game getAvailableGame() {
		for(Game g : games) {
			if(g.getGameMode() == GameMode.WAITING || g.getGameMode() == GameMode.STARTING) {
				return g;
			}
		}
		if(BlitzSG.getInstance().getArenaManager().getRandomArena() != null){
			return new Game();
		}
		return null;
	}
	
	public ArrayList<Game> getRunningGames() {
		ArrayList<Game> runningGames = new ArrayList<Game>();
		for(Game g : games) {
			if(g.getGameMode() != GameMode.RESETING) 
				runningGames.add(g);
		}
		return runningGames;
	}
	
	public void addGame(Game g) {
		games.add(g);
	}
	
	public void removeGame(Game g) {
		games.add(g);
	}
    
    public HashMap<Integer, BlitzSGPlayer> getTopKillers(Game g) {
    	Comparator<BlitzSGPlayer> killSorter = new Comparator<BlitzSGPlayer>() {
			@Override
			public int compare(BlitzSGPlayer a, BlitzSGPlayer b) {
				if(a.getGameKills() > b.getGameKills()) return -1;
				else if(a.getGameKills() < b.getGameKills()) return 1;
				return 0;
			}
    	};
    	HashMap<Integer, BlitzSGPlayer> map = new HashMap<Integer, BlitzSGPlayer>();
    	ArrayList<BlitzSGPlayer> kitPlayers = new ArrayList<BlitzSGPlayer>();
    	for(Player kp : g.getAllPlayers()) {
    		kitPlayers.add(BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(kp.getUniqueId()));
    	}
    	kitPlayers.sort(killSorter);
    	for(BlitzSGPlayer kp : kitPlayers) {
    		map.put(kitPlayers.indexOf(kp)+1, kp);
    	}
		return map;
    }

}
