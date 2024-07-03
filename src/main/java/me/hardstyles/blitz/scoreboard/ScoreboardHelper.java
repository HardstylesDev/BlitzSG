package me.hardstyles.blitz.scoreboard;

import me.hardstyles.blitz.BlitzSG;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardHelper {

    private List<ScoreboardText> list;
    private Scoreboard scoreBoard;
    private Objective tag;
    private List<String> animatedTitle;
    private int animationDelay;
    private int titleIndex;
    private Map<Integer, Team> teams;
    private int lastSentCount;

    public ScoreboardHelper(Scoreboard scoreBoard, String title, int animationDelay) {
        this.list = new ArrayList<ScoreboardText>();
        this.scoreBoard = scoreBoard;
        this.animatedTitle = new ArrayList<>();
        this.animatedTitle.add(title);
        this.animationDelay = animationDelay;
        this.lastSentCount = -1;
        this.titleIndex = 0;
        this.teams = new HashMap<>();
        this.tag = getOrCreateObjective("sidebar", "dummy");
        this.tag.setDisplaySlot(DisplaySlot.SIDEBAR);
        updateTitle(animatedTitle.get(0));
    }

    public Scoreboard getScoreBoard() {
        return scoreBoard;
    }

    public void setAnimatedTitle(List<String> animatedTitle) {
        this.animatedTitle = animatedTitle;
        this.titleIndex = 0;
        updateTitle(animatedTitle.get(0));
    }

    public void updateTitle(String title) {
        tag.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
    }

    public void add(String paramString) {
        paramString = ChatColor.translateAlternateColorCodes('&', paramString);
        ScoreboardText localScoreboardInput = null;
        if (paramString.length() <= 16) {
            localScoreboardInput = new ScoreboardText(paramString, "");
        }
        else {
            String str1 = paramString.substring(0, 16);
            String str2 = paramString.substring(16, paramString.length());
            if (str1.endsWith(String.valueOf("§"))) {
                str1 = str1.substring(0, str1.length() - 1);
                str2 = String.valueOf(String.valueOf("§")) + str2;
            }
            final String str3 = ChatColor.getLastColors(str1);
            str2 = String.valueOf(String.valueOf(str3)) + str2;
            localScoreboardInput = new ScoreboardText(str1.replace("&", "§"), StringUtils.left(str2, 16).replace("&", "§"));
        }
        this.list.add(localScoreboardInput);
    }

    private void addSingleLine(String text) {
        int index = animatedTitle.indexOf(tag.getDisplayName());
        if (index >= 0) {
            Team team = teams.get(index);
            if (team == null) {
                team = scoreBoard.registerNewTeam("team-" + index);
                teams.put(index, team);
            }

            String entry = ChatColor.values()[index].toString();
            team.addEntry(entry);
            team.setPrefix(text);
            tag.getScore(entry).setScore(index);
        }
    }

    private void addMultipleLines(String text) {
        List<String> lines = splitText(text);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int index = animatedTitle.indexOf(tag.getDisplayName()) + i;
            if (index >= 0) {
                Team team = teams.get(index);
                if (team == null) {
                    team = scoreBoard.registerNewTeam("team-" + index);
                    teams.put(index, team);
                }

                String entry = ChatColor.values()[index].toString();
                team.addEntry(entry);
                team.setPrefix(line);
                tag.getScore(entry).setScore(index);
            }
        }
    }

    private List<String> splitText(String text) {
        List<String> lines = new ArrayList<>();
        while (text.length() > 0) {
            if (text.length() <= 16) {
                lines.add(text);
                break;
            } else {
                int lastIndex = text.substring(0, 16).lastIndexOf('§');
                if (lastIndex == -1 || lastIndex == 15) {
                    lines.add(text.substring(0, 16));
                    text = text.substring(16);
                } else {
                    lines.add(text.substring(0, lastIndex + 2));
                    text = text.substring(lastIndex + 2);
                }
            }
        }
        return lines;
    }

    public void remove(final int paramInt) {
        final String str = this.getNameForIndex(paramInt);
        this.scoreBoard.resetScores(str);
        final Team localTeam = this.getOrCreateTeam(String.valueOf(String.valueOf(ChatColor.stripColor(StringUtils.left(String.valueOf(this.tag), 14)))) + paramInt, paramInt);
        localTeam.unregister();
    }

    public Team getOrCreateTeam(final String team, final int i) {
        Team value = this.scoreBoard.getTeam(team);
        if (value == null) {
            value = this.scoreBoard.registerNewTeam(team);
            value.addEntry(this.getNameForIndex(i));
        }
        return value;
    }

    public Objective getOrCreateObjective(final String objective, final String type) {
        Objective value = this.scoreBoard.getObjective(objective);
        if (value == null) {
            value = this.scoreBoard.registerNewObjective(objective, type);
        }
        value.setDisplayName(objective);
        return value;
    }

    public String getNameForIndex(final int index) {
        return String.valueOf(String.valueOf(ChatColor.values()[index].toString())) + ChatColor.RESET;
    }

    public void update(final Player paramPlayer) {
        paramPlayer.setScoreboard(this.scoreBoard);
        for (int i = 0; i < this.list.size(); ++i) {
            final Team localTeam = this.getOrCreateTeam(String.valueOf(String.valueOf(ChatColor.stripColor(StringUtils.left(String.valueOf(this.tag), 14)))) + i, i);
            final ScoreboardText localScoreboardInput = this.list.get(this.list.size() - i - 1);
            localTeam.setPrefix(localScoreboardInput.getLeft());
            localTeam.setSuffix(localScoreboardInput.getRight());
            this.tag.getScore(this.getNameForIndex(i)).setScore(i + 1);
        }
        if (this.lastSentCount != -1) {
            for (int i = this.list.size(), j = 0; j < this.lastSentCount - i; ++j) {
                this.remove(i + j);
            }
        }
        this.lastSentCount = this.list.size();
    }

    public void startAnimation() {
        new BukkitRunnable() {
            @Override
            public void run() {
                updateTitle(animatedTitle.get(titleIndex));
                titleIndex = (titleIndex + 1) % animatedTitle.size();
            }
        }.runTaskTimer(BlitzSG.getInstance(), animationDelay * 20L, animationDelay * 20L);
    }

    public void clear() {
        this.list.clear();
    }
    public static class ScoreboardText
    {
        private String left;
        private String right;

        public ScoreboardText(final String left, final String right) {
            this.left = left;
            this.right = right;
        }

        public String getLeft() {
            return this.left;
        }

        public void setLeft(final String left) {
            this.left = left;
        }

        public String getRight() {
            return this.right;
        }

        public void setRight(final String right) {
            this.right = right;
        }
    }
}