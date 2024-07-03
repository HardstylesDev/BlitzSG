package me.hardstyles.blitz.scoreboard;

import java.util.*;

import me.hardstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
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
            registerScoreboards(player);
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
        event.setQuitMessage("");
    }

    private void handleScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);

        // Animated title: Rotate through colors for the first few characters
        List<String> animatedTitle = Arrays.asList(
                "&e&lBLITZ SG", "&6&lB&e&lLITZ SG", "&f&lB&6&lL&e&lITZ SG", "&f&lBL&6&lI&e&lTZ SG", "&f&lBLI&6&lT&e&lZ SG",
                "&f&lBLIT&6&lZ&e&l SG", "&f&lBLITZ&6&l S&e&lG", "&f&lBLITZ S&6&lG", "&f&lBLITZ SG",
                "&f&lBLITZ SG","&f&lBLITZ SG","&f&lBLITZ SG", "&f&lBLITZ SG","&f&lBLITZ SG","&f&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG","&f&lBLITZ SG","&f&lBLITZ SG","&f&lBLITZ SG","&f&lBLITZ SG","&f&lBLITZ SG","&f&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG","&e&lBLITZ SG"
        );

        int animationDelay = 2; // Change this to adjust the animation speed
        ScoreboardHelper helper = new ScoreboardHelper(board, animatedTitle.get(0), animationDelay);
        helper.setAnimatedTitle(animatedTitle);
        this.boardHelper.put(player, helper);

        new BukkitRunnable() {
            int frame = 0;
            int pauseTicks = 3 * 10; // 3-second pause (20 ticks per second)

            @Override
            public void run() {
                if (pauseTicks > 0) {
                    pauseTicks--;
                    return; // Don't update the title during the pause
                }

                helper.updateTitle(animatedTitle.get(frame));
                frame = (frame + 1) % animatedTitle.size();

                // Reset the pause counter when we reach the end of the animation
                if (frame == 0) {
                    pauseTicks = 3 * 10; // Reset the pause counter to 3 seconds
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, animationDelay);
    }

    public ScoreboardHelper getScoreboard(final Player player) {
        return this.boardHelper.get(player);
    }
}
