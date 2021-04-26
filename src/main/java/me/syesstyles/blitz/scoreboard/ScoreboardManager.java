package me.syesstyles.blitz.scoreboard;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.game.Game.GameMode;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

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
            BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
            board.clear();
            Date now = new Date();
            if (!bsgPlayer.isInGame()) {
                board.add(separator);
                board.add("Kills: &a" + bsgPlayer.getKills());
                board.add("Wins: &a" + bsgPlayer.getWins());
                board.add("Blitz Score: &c" + bsgPlayer.getElo());
                board.add("Blitz Rank: &cN/A");


                board.add(separator);

                board.add("Coins: &a" + bsgPlayer.getCoins());
                board.add("Unlocks: &cN/A");

                board.add(separator);
                board.add("&ewww.hypixel.net");
            } else if (bsgPlayer.isInGame()) {
                if (bsgPlayer.getGame().getGameMode() == GameMode.WAITING) {
                    board.clear();
                } else if (bsgPlayer.getGame().getGameMode() == GameMode.STARTING) {
                    board.add(separator);
                    board.add("&fNext Event");
                    board.add("&aBlitz Star 05:00");
                    board.add(separator);
                    board.add("Players: &a" + bsgPlayer.getGame().getAlivePlayers().size());
                    board.add("Kills: &a" + bsgPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(bsgPlayer.getTaunt() == null ? "&cUnavailable" : (bsgPlayer.getGameTaunt() == 0 ? "&aREADY" : (bsgPlayer.getGameTaunt() == 1 ? "&aTAUNTING" : "&cUSED")));
                } else if (bsgPlayer.getGame().getGameMode() == GameMode.INGAME) {

                    board.add(separator);
                    board.add("&fNext Event");
                    if (!bsgPlayer.getGame().isDeathmatchStarting()) {
                        if (bsgPlayer.getGame().getNextEvent() == Game.NextEvent.STAR)
                            board.add("&a" + nextEvent(bsgPlayer.getGame().getNextEvent()) + " " + convert(300 - bsgPlayer.getGame().getGameTime()));
                        else if (bsgPlayer.getGame().getNextEvent() == Game.NextEvent.REFILL)
                            board.add("&a" + nextEvent(bsgPlayer.getGame().getNextEvent()) + " " + convert(600 - bsgPlayer.getGame().getGameTime()));
                        else if (bsgPlayer.getGame().getNextEvent() == Game.NextEvent.DEATHMATCH)
                            board.add("&a" + nextEvent(bsgPlayer.getGame().getNextEvent()) + " " + convert(900 - bsgPlayer.getGame().getGameTime()));
                        else
                            board.add("&a" + nextEvent(bsgPlayer.getGame().getNextEvent()) + " " + convert(1200 - bsgPlayer.getGame().getGameTime()));
                    } else {
                        if (bsgPlayer.getGame().getDeathmatchStartTime() < 45)
                            board.add("&aDeathmatch" + " " + convert(45 - bsgPlayer.getGame().getDeathmatchStartTime()));
                        else
                            board.add("&aEnding" + " " + convert(255 - bsgPlayer.getGame().getDeathmatchStartTime()));

                    }
                    board.add(separator);
                    board.add("&fPlayers: &a" + bsgPlayer.getGame().getAlivePlayers().size());
                    board.add("&fKills: &a" + bsgPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(bsgPlayer.getTaunt() == null ? "&cUnavailable" : (bsgPlayer.getGameTaunt() == 0 ? "&aREADY" : (bsgPlayer.getGameTaunt() == 1 ? "&aTAUNTING" : "&cUSED")));

                } else if (bsgPlayer.getGame().getGameMode() == GameMode.RESETING) {
                    if (bsgPlayer.getGame().getWinner() == null)
                        continue;
                    BlitzSGPlayer winner = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(bsgPlayer.getGame().getWinner().getUniqueId());
                    board.add(separator);
                    board.add("&fWinner");
                    if (winner.getRank(true).getPrefix() != null)
                        board.add("&a" + winner.getRank(true).getPrefix() + bsgPlayer.getGame().getWinner().getName());
                    else
                        board.add("&a" + winner.getRank().getPrefix() + bsgPlayer.getGame().getWinner().getName());

                    board.add(separator);
                    board.add("&fPlayers: &a" + bsgPlayer.getGame().getAlivePlayers().size());
                    board.add("&fKills: &a" + bsgPlayer.getGameKills());
                    board.add(separator);
                    board.add("Taunt");
                    board.add(bsgPlayer.getGameTaunt() == -1 ? "&cUnavailable" : (bsgPlayer.getGameTaunt() == 0 ? "&aREADY" : "&cUSED"));

                }
            }
            board.update(p);

            if (bsgPlayer.getNick() != null && bsgPlayer.getNick().isNicked()) {
                PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(ChatColor.RED + "You're currently nicked " + ChatColor.GRAY + "(in-game only)"), (byte) 2);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
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

    public ScoreboardHandler getScoreboardHandler() {
        return this.scoreboardHandler;
    }
}
