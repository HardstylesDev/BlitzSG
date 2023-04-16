package me.hardstyles.blitz.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Preconditions;

public class ScoreboardHelper
{
    private List<ScoreboardText> list;
    private Scoreboard scoreBoard;
    private Objective objective;
    private Objective healthName;
    private String tag;
    private int lastSentCount;
    
    public ScoreboardHelper(final Scoreboard scoreBoard) {
        this.list = new ArrayList<ScoreboardText>();
        this.tag = "PlaceHolder";
        this.lastSentCount = -1;
        this.scoreBoard = scoreBoard;
        (this.objective = this.getOrCreateObjective(this.tag, "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    public Scoreboard getScoreBoard() {
        return this.scoreBoard;
    }
    
    public ScoreboardHelper(final Scoreboard scoreBoard, final String title) {
        this.list = new ArrayList<ScoreboardText>();
        this.tag = "PlaceHolder";
        this.lastSentCount = -1;
        Preconditions.checkState(title.length() <= 32, (Object)"title can not be more than 32");
        this.tag = ChatColor.translateAlternateColorCodes('&', title);
        this.scoreBoard = scoreBoard;
        (this.objective = this.getOrCreateObjective(this.tag, "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        (this.healthName = this.getOrCreateObjective("healthName", "health")).setDisplaySlot(DisplaySlot.BELOW_NAME);
        this.healthName.setDisplayName("§c\u2764");
      //  this.getOrCreateObjective("healthTab", "health").setDisplaySlot(DisplaySlot.PLAYER_LIST);
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
    
    public void update(final Player paramPlayer) {
        paramPlayer.setScoreboard(this.scoreBoard);
        for (int i = 0; i < this.list.size(); ++i) {
            final Team localTeam = this.getOrCreateTeam(String.valueOf(String.valueOf(ChatColor.stripColor(StringUtils.left(this.tag, 14)))) + i, i);
            final ScoreboardText localScoreboardInput = this.list.get(this.list.size() - i - 1);
            localTeam.setPrefix(localScoreboardInput.getLeft());
            localTeam.setSuffix(localScoreboardInput.getRight());
            this.objective.getScore(this.getNameForIndex(i)).setScore(i + 1);
        }
        if (this.lastSentCount != -1) {
            for (int i = this.list.size(), j = 0; j < this.lastSentCount - i; ++j) {
                this.remove(i + j);
            }
        }
        this.lastSentCount = this.list.size();
    }
    
    public void clear() {
        this.list.clear();
    }
    
    public void remove(final int paramInt) {
        final String str = this.getNameForIndex(paramInt);
        this.scoreBoard.resetScores(str);
        final Team localTeam = this.getOrCreateTeam(String.valueOf(String.valueOf(ChatColor.stripColor(StringUtils.left(this.tag, 14)))) + paramInt, paramInt);
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
