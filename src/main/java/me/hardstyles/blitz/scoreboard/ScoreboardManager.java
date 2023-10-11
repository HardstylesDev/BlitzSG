package me.hardstyles.blitz.scoreboard;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class ScoreboardManager extends BukkitRunnable {
    private final ScoreboardHandler scoreboardHandler;
    private final String lines;
    private final String separator;

    public ScoreboardManager() {
        this.scoreboardHandler = new ScoreboardHandler();
        this.lines = "&7&m---------------------";
        this.separator = "&f";
    }

    public void run() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ScoreboardHelper board = this.scoreboardHandler.getScoreboard(p);
            IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
            board.clear();
            Date now = new Date();
            if (!iPlayer.isInGame()) {
                board.add(separator);
                board.add("Kills: &a" + f(iPlayer.getKills()));
                board.add("Wins: &a" + f(iPlayer.getWins()));
                board.add("Blitz Score: &c" + f(iPlayer.getElo()));
                board.add("Blitz Rank: &cN/A");


                board.add(separator);

                board.add("Coins: &a" + f(iPlayer.getCoins()));
                board.add("Unlocks: &cN/A");

                board.add(separator);
                board.add("&ewww.blitzsg.lol");
            } else if (iPlayer.isInGame()) {
                if (iPlayer.getGame().getGameMode() == Game.GameMode.WAITING) {
                    board.clear();
                } else if (iPlayer.getGame().getGameMode() == Game.GameMode.STARTING) {
                    board.add(separator);
                    board.add("&fNext Event");
                    board.add("&aBlitz Star 05:00");
                    board.add(separator);
                    board.add("Players: &a" + iPlayer.getGame().getAlivePlayers().size());
                    board.add("Kills: &a" + iPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(iPlayer.getTauntStatus());
                } else if (iPlayer.getGame().getGameMode() == Game.GameMode.INGAME) {

                    board.add(separator);
                    board.add("&fNext Event");
                    if (!iPlayer.getGame().isDeathmatchStarting()) {
                        if (iPlayer.getGame().getNextEvent() == Game.NextEvent.STAR)
                            board.add("&a" + nextEvent(iPlayer.getGame().getNextEvent()) + " " + convert(300 - iPlayer.getGame().getGameTime()));
                        else if (iPlayer.getGame().getNextEvent() == Game.NextEvent.REFILL)
                            board.add("&a" + nextEvent(iPlayer.getGame().getNextEvent()) + " " + convert(600 - iPlayer.getGame().getGameTime()));
                        else if (iPlayer.getGame().getNextEvent() == Game.NextEvent.DEATHMATCH)
                            board.add("&a" + nextEvent(iPlayer.getGame().getNextEvent()) + " " + convert(900 - iPlayer.getGame().getGameTime()));
                        else
                            board.add("&a" + nextEvent(iPlayer.getGame().getNextEvent()) + " " + convert(1200 - iPlayer.getGame().getGameTime()));
                    }
                    else{
                        if (iPlayer.getGame().getDeathmatchStartTime() < 45)
                            board.add("&aDeathmatch" + " " + convert(45 - iPlayer.getGame().getDeathmatchStartTime()));
                        else
                            board.add("&aEnding" + " " + convert(255 - iPlayer.getGame().getDeathmatchStartTime()));

                    }
                    board.add(separator);
                    board.add("&fPlayers: &a" + iPlayer.getGame().getAlivePlayers().size());
                    board.add("&fKills: &a" + iPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(iPlayer.getTauntStatus());

                } else if (iPlayer.getGame().getGameMode() == Game.GameMode.RESETTING) {
                    if (iPlayer.getGame().getWinner() == null) {
                        continue;
                    }
                    Game g = iPlayer.getGame();

                    board.add(separator);
                    board.add("&fWinner");
                    board.add("&a" + g.getWinnerRank().getPrefix() + g.getWinner().getName());
                    board.add(separator);
                    board.add("&fPlayers: &a" + iPlayer.getGame().getAlivePlayers().size());
                    board.add("&fKills: &a" + iPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(iPlayer.getTauntStatus());

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
            timeF = timeF + "0";
        }
        timeF = timeF + m + ":";
        if (s < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + s;
        return timeF;
    }

    private String format(final double data) {
        final int minutes = (int) (data / 60.0);
        final int seconds = (int) (data % 60.0);
        final String str = String.format("%02d:%02d", minutes, seconds);
        return str;
    }

    private String nextEvent(Game.NextEvent nextEvent) {
        if (nextEvent == Game.NextEvent.STAR)
            return "Blitz Star";
        if (nextEvent == Game.NextEvent.REFILL)
            return "Chest Refill";
        if (nextEvent == Game.NextEvent.DEATHMATCH)
            return "Deathmatch";
        if (nextEvent == Game.NextEvent.ENDING)
            return "Ending";
        return "" + nextEvent;
    }

    private String f(int i) {
        return NumberFormat.getNumberInstance(Locale.US).format(i);
    }

    public ScoreboardHandler getScoreboardHandler() {
        return this.scoreboardHandler;
    }
}
