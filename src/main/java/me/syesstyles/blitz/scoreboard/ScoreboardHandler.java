package me.syesstyles.blitz.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardHandler implements Listener
{
    private Map<Player, ScoreboardHelper> boardHelper;
    private WeakHashMap<Player, ScoreboardHelper> helper;
    
    public ScoreboardHandler() {
        this.boardHelper = new HashMap<Player, ScoreboardHelper>();
        this.helper = new WeakHashMap<Player, ScoreboardHelper>();
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            this.handleScoreboard(player);
        }
    }
    
    public ScoreboardHelper getScoreboardFor(Player player) {
        return this.helper.get(player);
    }
    
    public void registerScoreboards(Player player) {
        Scoreboard sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        player.setScoreboard(sb);
        this.resendTab(player);
        for(Player other : Bukkit.getServer().getOnlinePlayers()) {
            if (other != player) {
                if (this.getScoreboardFor(other) != null) {
                    final Scoreboard scoreboard = this.getScoreboardFor(other).getScoreBoard();
                    final Team otherTeam = this.getTeam(scoreboard, "other", "&c".replace("&", "§"));
                    otherTeam.addEntry(player.getName());
                }
            }
        }
    }
    
    public void resendTab(Player player) {
        if (this.getScoreboardFor(player) == null) {
            return;
        }
        final Scoreboard scoreboard = this.getScoreboardFor(player).getScoreBoard();
        this.unregister(scoreboard, "player");
        this.unregister(scoreboard, "other");
        final Team playerTeam = this.getTeam(scoreboard, "player", "&a".replace("&", "§"));
        final Team otherTeam = this.getTeam(scoreboard, "other", "&c".replace("&", "§"));
        for(Player other : Bukkit.getServer().getOnlinePlayers()) {
            if (other == player) {
                playerTeam.addEntry(other.getName());
            }
            else {
                otherTeam.addEntry(other.getName());
            }
        }
    }
    
    public Team getTeam(final Scoreboard board, final String name, final String prefix) {
        Team team = board.getTeam(name);
        if (team == null) {
            team = board.registerNewTeam(name);
        }
        team.setPrefix(prefix);
        return team;
    }
    
    public void unregister(final Scoreboard board, final String name) {
        final Team team = board.getTeam(name);
        if (team != null) {
            team.unregister();
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.handleScoreboard(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        this.boardHelper.remove(event.getPlayer());
        this.helper.remove(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.boardHelper.remove(event.getPlayer());
        this.helper.remove(event.getPlayer());
    }
    
    private void handleScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);
        ScoreboardHelper helper = new ScoreboardHelper(board, "&e&lBLITZ SG");
        this.boardHelper.put(player, helper);
    }
    
    public ScoreboardHelper getScoreboard(final Player player) {
        return this.boardHelper.get(player);
    }
}
