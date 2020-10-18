package org.example.SpigotServer.scoreboard;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


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
            board.clear();
            Date now = new Date();
            if(true) {
                board.add(lines);
                //board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
                board.add("&fOnline: &e" + Bukkit.getOnlinePlayers().size());
                board.add(separator);
               //if(Core.getInstance().getServer().getIp().length() == 0) {
               //    board.add("&7&oTo edit IP-display:");
               //    board.add(" &7&oserver.properties");
               //    board.add(" &7&o \u2514&l> &7&oserver-ip");
               //}else
               //    board.add("&7&o" + Core.getInstance().getServer().getIp());
                //board.add("&7&orelium.eu");
                board.add(lines);
            }else {
               // Game g = Core.getInstance().gameManager.getGame(p);
               // if(g.isJoinable()) {
               //     board.add(lines);
               //     //board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
               //     if(g.alivePlayers.size() <= 1)
               //         board.add("&fWaiting...");
               //     else
               //         board.add("&fStarting in: &e" + g.getCountdowntime());
               //     board.add(separator);
               //     board.add("&fPlayers: &e" + g.alivePlayers.size() + "/4");
               //     board.add(separator);
               //     board.add("&7&orelium.eu");
               //     board.add(lines);
               // }else if(g.getGameMode() == GameMode.INGAME) {
               //     board.add(lines);
               //     //board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
               //     board.add("&fNext Event:");
               //     board.add(" &e" + g.getNextEvent() + " &7(" + smallConvert(g.getNextEventTime()) + ")");
               //     board.add(separator);
               //     board.add("&fAlive: &e" + g.alivePlayers.size() + " &7(" + g.alivePlayers.size() + "/" + (g.deadPlayers.size() + g.alivePlayers.size()) + ")");
               //     board.add(separator);
               //     board.add("&7&orelium.eu");
               //     board.add(lines);
               // }else if(g.getGameMode() == GameMode.RESETTING) {
               //     board.add(lines);
               //     //board.add("&7" + now.getDate() + "/" + (now.getMonth()+1) + " &8Mini01");
               //     board.add("&fNext Event:");
               //     board.add(" &eGame Ended!");
               //     board.add(separator);
               //     if(g.alivePlayers.size() == 1)
               //         board.add("&fWinner: &e" + g.alivePlayers.get(0).getName());
               //     else
               //         board.add("&fDraw!");
               //     board.add(separator);
               //     board.add("&7&orelium.eu");
               //     board.add(lines);
                //}
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

    public String smallConvert(final int seconds) {
        final int h = seconds / 3600;
        final int i = seconds - h * 3600;
        final int m = i / 60;
        final int s = i - m * 60;
        String timeF = "";
        if (m >= 1) {
            timeF = String.valueOf(timeF) + m + "m";
        }
        /*if (s < 10) {
            timeF = String.valueOf(timeF) + "0";
        }*/
        timeF = String.valueOf(timeF) + s + "s";
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
