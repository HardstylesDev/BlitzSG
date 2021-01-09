package me.syesstyles.blitz.blitzsgplayer;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.arena.Arena;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.rank.Rank;
import me.syesstyles.blitz.utils.nickname.Nick;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class BlitzSGPlayer {

    private Kit selectedKit;
    private int gameTaunt;
    private UUID uuid;

    private int gameKills;
    private Nick nick;
    private HashSet<Entity> gameEntities;
    private Location gameSpawn;
    private Rank rank;
    private int elo;
    private int wins;
    private int kills;
    private int deaths;
    private int coins;

    private HashMap<Kit, Integer> kitLevels;

    private Arena editedArena;

    public BlitzSGPlayer(UUID uuid) {
        this.nick = null;
        this.uuid = uuid;
        this.elo = 0;
        this.wins = 0;
        this.kills = 0;
        this.deaths = 0;
        this.coins = 0;
        this.rank = null;
        this.gameEntities = new HashSet<Entity>();
        this.kitLevels = new HashMap<Kit, Integer>();
        for (Kit p : BlitzSG.getInstance().getKitManager().getKits())
            this.kitLevels.put(p, 0);

        this.gameKills = 0;
        this.gameTaunt = -1;
        this.gameSpawn = null;
        this.selectedKit = null;
        BlitzSG.getInstance().getBlitzSGPlayerManager().addUhcPlayer(this.uuid, this);
    }

    public void loadStats(FileConfiguration statsFile) {
        this.rank = BlitzSG.getInstance().getRankManager().getRankByName(statsFile.getString("Rank"));
        this.elo = statsFile.getInt("ELO");
        this.wins = statsFile.getInt("Wins");
        this.kills = statsFile.getInt("Kills");
        this.deaths = statsFile.getInt("Deaths");
        if (statsFile.contains("Nickname"))
            this.nick = new Nick(statsFile.getString("Nickname"), null, null, !statsFile.getString("Nickname").equalsIgnoreCase(""));
        else this.nick = null;
        this.coins = statsFile.getInt("Coins");
        this.selectedKit = BlitzSG.getInstance().getKitManager().getKit(statsFile.getString("SelectedKit"));
        for (String str : statsFile.getConfigurationSection("Kits").getKeys(false))
            this.kitLevels.put(BlitzSG.getInstance().getKitManager().getKit(str)
                    , statsFile.getConfigurationSection("Kits").getInt(str));
    }

    //Player Stats
    public Rank getRank() {

        return rank;
    }

    public Rank getRank(boolean checkNick) {
        if (nick.isNicked())
            return BlitzSG.getInstance().getRankManager().getRankByName("Default");
        return rank;
    }

    public Nick getNick() {
        return nick;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getElo() {
        return elo;
    }

    public Kit getSelectedKit() {
        return selectedKit;
    }

    public void setSelectedKit(Kit kit) {
        this.selectedKit = kit;
    }

    public void setNickName(String nickName) {
        this.nick.setNickName(nickName);
    }

    public boolean isNicked() {
        return (nick.isNicked());
    }

    public String getNickName() {
        if (this.nick != null)
            return this.nick.getNickName();
        return null;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void addElo(int elo) {
        this.elo += elo;
    }

    public void removeElo(int elo) {
        if (this.elo - elo <= 0) {
            this.elo = 0;
            return;
        }
        this.elo += -elo;
    }

    public Location getGameSpawn() {
        return gameSpawn;
    }


    public void setGameSpawn(Location gameSpawn) {
        this.gameSpawn = gameSpawn;
    }

    public int getWins() {
        return this.wins;
    }

    public void addWin() {
        this.wins += 1;
    }

    public int getKills() {
        return this.kills;
    }

    public HashSet<Entity> getGameEntities(){
        return gameEntities;
    }

    public void addKill() {
        this.kills += 1;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void addDeath() {
        this.deaths += 1;
    }

    public int getCoins() {
        return this.coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void removeCoins(int coins) {
        this.coins += -coins;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getKitLevel(Kit p) {
        if (!kitLevels.containsKey(p))
            return 0;
        return this.kitLevels.get(p);
    }

    public void setKitLevel(Kit p, int level) {
        this.kitLevels.put(p, level);
    }


    //Game Stats

    public int getGameKills() {
        return this.gameKills;
    }

    public int getGameTaunt() {
        return this.gameTaunt;
    }

    public void resetGameKills() {
        this.gameKills = 0;
    }

    public void resetGameTaunt(int i) {
        this.gameTaunt = i;
    }

    public void addGameKill() {
        this.gameKills += 1;
        this.kills += 1;
    }


    //Game Handling Methods
    public boolean isInGame() {
        if (BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayerGame(this) == null)
            return false;
        else
            return true;
    }

    public Game getGame() {
        return BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayerGame(this);
    }

    public void setGame(Game g) {
        BlitzSG.getInstance().getBlitzSGPlayerManager().setUhcPlayerGame(this, g);
    }

    public boolean isEditingArena() {
        if (this.editedArena == null)
            return false;
        else
            return true;
    }

    public Arena getEditedArena() {
        return this.editedArena;
    }

    public void setEditedArena(Arena arena) {
        this.editedArena = arena;
    }

}
