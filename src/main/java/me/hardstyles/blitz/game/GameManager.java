package me.hardstyles.blitz.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import org.bukkit.entity.Player;

public class GameManager {
	
	private HashSet<Game> games;
	
	public GameManager() {
		games = new HashSet<>();
	}
	
	public Game getAvailableGame() {
		for(Game g : games) {
			if(g.getGameMode() == Game.GameMode.WAITING) {
				return g;
			}
		}
		if(BlitzSG.getInstance().getArenaManager().getRandomArena() != null){
			BlitzSG.getInstance().getArenaManager().loadMap("citadel");
		}
		return null;
	}
	
	public ArrayList<Game> getRunningGames() {
		ArrayList<Game> runningGames = new ArrayList<Game>();
		for(Game g : games) {
			if(g.getGameMode() != Game.GameMode.RESETING)
				runningGames.add(g);
		}
		return runningGames;
	}
	
	public void addGame(Game g) {
		games.add(g);
	}
	
	public void removeGame(Game g) {
		games.remove(g);
		g.setGameMode(Game.GameMode.WAITING);

		if (games.size() == 0) {

			Game game = new Game();


			String gameName = BlitzSG.getInstance().getArenaManager().getRandomArena().getName();
			BlitzSG.getInstance().getGameManager().addGame(game);



		}
	}


    
    public HashMap<Integer, IPlayer> getTopKillers(Game g) {
    	Comparator<IPlayer> killSorter = (a, b) -> {
			if(a.getGameKills() > b.getGameKills()) return -1;
			else if(a.getGameKills() < b.getGameKills()) return 1;
			return 0;
		};
    	HashMap<Integer, IPlayer> map = new HashMap<Integer, IPlayer>();
    	ArrayList<IPlayer> kitPlayers = new ArrayList<IPlayer>();
    	for(Player kp : g.getAllPlayers()) {
    		kitPlayers.add(BlitzSG.getInstance().getIPlayerManager().getPlayer(kp.getUniqueId()));
    	}
    	kitPlayers.sort(killSorter);
    	for(IPlayer kp : kitPlayers) {
    		map.put(kitPlayers.indexOf(kp)+1, kp);
    	}
		return map;
    }

}
