package me.hardstyles.blitz.game;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameManager {

    private CopyOnWriteArrayList<Game> games;

    public GameManager() {
        games = new CopyOnWriteArrayList<>();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(BlitzSG.getInstance(), () -> {
            for (Game g : games) {
                if (g.getGameMode() == Game.GameMode.WAITING) {
                    if (g.getAllPlayers().size() == 0) {
                        Bukkit.getLogger().info("Game " + g.getMap().getMapName() + " has been reset due to no players.");
                        g.resetGame();
                        games.remove(g);
                    }
                }
            }

            if (games.size() == 0) {
                new Game();
            }
        }, 100, 20 * 60 * 5);

    }

    public Game getAvailableGame() {
        for (Game g : games) {
            if (g.getGameMode() == Game.GameMode.WAITING) {
                return g;
            }
        }
        return null;
    }

    public ArrayList<Game> getRunningGames() {
        ArrayList<Game> runningGames = new ArrayList<Game>();
        for (Game g : games) {
            if (g.getGameMode() != Game.GameMode.RESETING)
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
            Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), Game::new, 50);
        }
    }


    public LinkedHashMap<Integer, IPlayer> getTopKillers(Game g) {
        Comparator<IPlayer> killSorter = (a, b) -> {
            return Integer.compare(b.getGameKills(), a.getGameKills());
        };

        List<IPlayer> players = new ArrayList<>();
        for (Player p : g.getAllPlayers()) {
            players.add(BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId()));
        }

        players.sort(killSorter);

        LinkedHashMap<Integer, IPlayer> map = new LinkedHashMap<>();
        int rank = 1;
        for (IPlayer p : players) {
            map.put(rank++, p);
        }

        return map;
    }







}
