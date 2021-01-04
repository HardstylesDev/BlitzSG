package me.syesstyles.blitz.scoreboard;

import java.util.Date;

import me.syesstyles.blitz.game.Game;
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
            BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
            board.clear();
    		Date now = new Date();
            if(!bsgPlayer.isInGame()) {
                board.add(separator);
                board.add("Kills: &a" + bsgPlayer.getKills());
                board.add("Wins: &a" + bsgPlayer.getWins());
                board.add("Blitz Score: &cN/A");
                board.add("Blitz Rank: &cN/A");


                board.add(separator);

                board.add("Coins: &a" + bsgPlayer.getCoins());
                board.add("Unlocks: &cN/A");

                board.add(separator);
                board.add("&ewww.hypixel.net");
            }else if(bsgPlayer.isInGame()) {
            	if(bsgPlayer.getGame().getGameMode() == GameMode.WAITING) {
                    board.clear();
            	}else if(bsgPlayer.getGame().getGameMode() == GameMode.STARTING) {
                    board.add(separator);
                    board.add("&fNext Event");
                    board.add("&aBlitz Star 05:00");
                    board.add(separator);
                    board.add("Players: &a" + bsgPlayer.getGame().getAlivePlayers().size() );
                    board.add("Kills: &a" + bsgPlayer.getGameKills() );
                    board.add(separator);
                    board.add("Taunt");
                    board.add(bsgPlayer.getGameTaunt() == -1 ? "&cUnavailable" : (bsgPlayer.getGameTaunt() == 0 ? "&aREADY" : "&cUSED"));
                }else if(bsgPlayer.getGame().getGameMode() == GameMode.INGAME) {

                    board.add(separator);
                    board.add("&fNext Event");
                    board.add("&a" + nextEvent(bsgPlayer.getGame().getNextEvent()) +  " " + convert(300 - bsgPlayer.getGame().getGameTime()));
                    board.add(separator);
                    board.add("&fPlayers: &a" + bsgPlayer.getGame().getAlivePlayers().size());
                    board.add("&fKills: &a" + bsgPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(bsgPlayer.getGameTaunt() == -1 ? "&cUnavailable" : (bsgPlayer.getGameTaunt() == 0 ? "&aREADY" : "&cUSED"));

                }else if(bsgPlayer.getGame().getGameMode() == GameMode.RESETING) {
                    board.add(separator);
                    board.add("&fWinner");
                    board.add("&a" + bsgPlayer.getRank().getPrefix() + bsgPlayer.getGame().getWinner().getName());
                    board.add(separator);
                    board.add("&fPlayers: &a" + bsgPlayer.getGame().getAlivePlayers().size());
                    board.add("&fKills: &a" + bsgPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(bsgPlayer.getGameTaunt() == -1 ? "&cUnavailable" : (bsgPlayer.getGameTaunt() == 0 ? "&aREADY" : "&cUSED"));

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

    private String nextEvent(Game.NextEvent nextEvent){
        if(nextEvent == Game.NextEvent.STAR)
            return "Blitz Star";
        return "" + nextEvent;
    }
    public ScoreboardHandler getScoreboardHandler() {
    	return this.scoreboardHandler;
    }
}
