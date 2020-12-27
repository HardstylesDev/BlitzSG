package me.syesstyles.blitz.scoreboard;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.game.Game.GameMode;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;

public class ScoreboardManager extends BukkitRunnable
{
    private ScoreboardHandler scoreboardHandler;
    private String lines;
    private String separator;
    
    public ScoreboardManager() {
        this.scoreboardHandler = new ScoreboardHandler();
        this.lines = "&7&m---------------------";
        this.separator = "&f";
    }
    
    public void run() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            ScoreboardHelper board = this.scoreboardHandler.getScoreboard(p);
            BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
            board.clear();
    		Date now = new Date();
            if(!uhcPlayer.isInGame()) {
                board.add(separator);
                board.add("Kills: &a" + uhcPlayer.getKills());
                board.add("Wins: &a" + uhcPlayer.getWins());
                board.add("Blitz Score: &cN/A");
                board.add("Blitz Rank: &cN/A");


                board.add(separator);

                board.add("Coins: &a" + uhcPlayer.getCoins());
                board.add("Unlocks: &cN/A");

                board.add(separator);
                board.add("&ewww.hypixel.net");
            }else if(uhcPlayer.isInGame()) {
            	if(uhcPlayer.getGame().getGameMode() == GameMode.WAITING) {
                    board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
                    board.add(separator);
                    board.add("&fPlayers: &a" + uhcPlayer.getGame().getAlivePlayers().size() + "/" + uhcPlayer.getGame().getArena().getSpawns().size());
                    board.add(separator);
                    board.add("&fGame starting if &a1 &fmore");
                    board.add("&fplayer(s) join...");
                    board.add(separator);
                    board.add("&fMode: &6&lSolo");
                    board.add(separator);
                    board.add("&erelium.eu");
            	}else if(uhcPlayer.getGame().getGameMode() == GameMode.STARTING) {
                    board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
                    board.add(separator);
                    board.add("&fPlayers: &a" + uhcPlayer.getGame().getAlivePlayers().size() + "/" + uhcPlayer.getGame().getArena().getSpawns().size());
                    board.add(separator);
                    board.add("&fStarting in: &a" + format(uhcPlayer.getGame().getCountdownTime()));
                    board.add(separator);
                    board.add("&fMode: &6&lSolo");
                    board.add(separator);
                    board.add("&erelium.eu");
                }else if(uhcPlayer.getGame().getGameMode() == GameMode.INGAME) {
                    board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
                    board.add(separator);
                    board.add("&fGame Time: ");
                    board.add("&a" + convert(uhcPlayer.getGame().getGameTime()));
                    board.add(separator);
                    board.add("&fSurface Players: &a" + uhcPlayer.getGame().getAlivePlayers().size());
                    board.add(separator);
                    board.add("&fKills: &a" + uhcPlayer.getGameKills());
                    board.add(separator);
                    board.add("&fWorld Border:");
                    if(uhcPlayer.getGame().getGameTime() >= 120)
                    	board.add("&e-" + (int)uhcPlayer.getGame().getArena().getArenaWorld().getWorldBorder().getSize()/2
                    			+ ", +" + (int)uhcPlayer.getGame().getArena().getArenaWorld().getWorldBorder().getSize()/2);
                    else
                    	board.add("&a-" + (int)uhcPlayer.getGame().getArena().getArenaWorld().getWorldBorder().getSize()/2
                    			+ ", +" + (int)uhcPlayer.getGame().getArena().getArenaWorld().getWorldBorder().getSize()/2);
                    board.add(separator);
                    board.add("&erelium.eu");
                }else if(uhcPlayer.getGame().getGameMode() == GameMode.RESETING) {
                    board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
                    board.add(separator);
                    board.add("&fGame Time: ");
                    board.add("&aGame ended!");
                    board.add(separator);
                    board.add("&fWinner: &a" + uhcPlayer.getGame().getWinner().getName());
                    board.add(separator);
                    board.add("&fKills: &a" + uhcPlayer.getGameKills());
                    board.add(separator);
                    board.add("&fWorld Border:");
                    board.add("&e-" + (int)uhcPlayer.getGame().getArena().getArenaWorld().getWorldBorder().getSize()/2
                    		+ ", +" + (int)uhcPlayer.getGame().getArena().getArenaWorld().getWorldBorder().getSize()/2);
                    board.add(separator);
                    board.add("&erelium.eu");
                }
            }
            board.update(p);
        }
    }
    
    public static String convert(final int seconds) {
        final int h = seconds / 3600;
        final int i = seconds - h * 3600;
        final int m = i / 60;
        final int s = i - m * 60;
        String timeF = "";
        if (h < 10) {
            timeF = String.valueOf(timeF) + "0";
        }
        timeF = String.valueOf(timeF) + h + ":";
        if (m < 10) {
            timeF = String.valueOf(timeF) + "0";
        }
        timeF = String.valueOf(timeF) + m + ":";
        if (s < 10) {
            timeF = String.valueOf(timeF) + "0";
        }
        timeF = String.valueOf(timeF) + s;
        return timeF;
    }
    
    private String format(final double data) {
        final int minutes = (int)(data / 60.0);
        final int seconds = (int)(data % 60.0);
        final String str = String.format("%02d:%02d", minutes, seconds);
        return str;
    }
    
    public ScoreboardHandler getScoreboardHandler() {
    	return this.scoreboardHandler;
    }
}
