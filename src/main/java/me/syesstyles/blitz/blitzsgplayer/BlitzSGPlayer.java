package me.syesstyles.blitz.blitzsgplayer;

import com.google.gson.JsonObject;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.cosmetic.Aura;
import me.syesstyles.blitz.cosmetic.Taunt;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.map.Map;
import me.syesstyles.blitz.rank.Rank;
import me.syesstyles.blitz.utils.nickname.Nick;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class BlitzSGPlayer {

    private Kit selectedKit;
    private int gameTaunt;
    private UUID uuid;

    private String customTag;
    private boolean robinhood;
    private boolean wobbuffet;
    private int gameKills;
    private boolean punched;
    private Nick nick;
    private HashSet<Entity> gameEntities;
    private HashSet<Star> stars;
    private Location gameSpawn;
    private Rank rank;
    private int elo;
    private int wins;
    private int kills;
    private int deaths;
    private boolean hideOthers;
    private int coins;
    private Taunt selectedTaunt;
    private Aura aura;
    private JsonObject jsonObject;
    private String ip;

    private String name;
    private HashMap<Kit, Integer> kitLevels;

    private Map editedMap;

    public void setHideOthers(boolean b){
        this.hideOthers = b;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setGameKills(int gameKills) {
        this.gameKills = gameKills;
    }

    public void setNick(Nick nick) {
        this.nick = nick;
    }

    public void setGameEntities(HashSet<Entity> gameEntities) {
        this.gameEntities = gameEntities;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setKitLevels(HashMap<Kit, Integer> kitLevels) {
        this.kitLevels = kitLevels;
    }

    public BlitzSGPlayer(UUID uuid) {
        this.jsonObject = new JsonObject();



        this.hideOthers = false;
        this.nick = null;
        this.uuid = uuid;
        this.elo = 0;
        this.wins = 0;
        this.kills = 0;
        this.customTag = null;
        this.deaths = 0;
        this.coins = 0;
        this.rank = null;
        this.gameEntities = new HashSet<Entity>();
        this.stars = new HashSet<Star>();
        this.kitLevels = new HashMap<Kit, Integer>();
        for (Kit p : BlitzSG.getInstance().getKitManager().getKits())
            this.kitLevels.put(p, 0);


        this.gameKills = 0;
        this.gameTaunt = -1;
        this.gameSpawn = null;
        this.selectedKit = null;
        this.selectedTaunt = null;
        this.aura = null;
        BlitzSG.getInstance().getBlitzSGPlayerManager().addBsgPlayer(this.uuid, this);
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
        // for(Object str : statsFile.getList("Stars")){
        //     System.out.println(str.getClass() + " : " + str);
        //}
    }

    //Player Stats


    public HashMap<Kit, Integer> getKits() {
        return this.kitLevels;
    }

    public void addStar(Star star) {
        this.stars.add(star);
    }

    public Rank getRank() {

        return rank;
    }

    public void setPunched(boolean b) {
        this.punched = b;
    }

    public boolean getPunched() {
        return this.punched;
    }

    public void setStars(HashSet<Star> stars) {
        this.stars = stars;
    }

    public HashSet<Star> getStars() {
        return stars;
    }

    public Rank getRank(boolean checkNick) {
        if (nick != null && nick.isNicked())
            return BlitzSG.getInstance().getRankManager().getRankByName("Default");
        if (nick == null)
            this.nick = new Nick("", null, null, false);
        return rank;
    }

    public boolean doesHideOthers(){
        return this.hideOthers;
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

    public boolean isNicked() {
        return (nick.isNicked());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setCustomTag(String tag) {
        this.customTag = tag;
    }

    public String getCustomTag() {
        return this.customTag;
    }

    public String getName() {
        return this.name;
    }

 // public String getNickName() {
 //     if (this.nick != null)
 //         return this.nick.getNickName();
 //     return null;
 // }

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
    public void setTaunt(Taunt taunt){
        this.selectedTaunt = taunt;
    }
    public Taunt getTaunt(){
        return this.selectedTaunt;
    }
    public Location getGameSpawn() {
        return gameSpawn;
    }


    public void setGameSpawn(Location gameSpawn) {
        this.gameSpawn = gameSpawn;
    }

    public void setAura(Aura aura) {
        this.aura = aura;
        Player p = Bukkit.getPlayer(getUuid());
        if (p != null && p.isOnline())
            if (!BlitzSG.getInstance().getCosmeticsManager().getPlayers().contains(Bukkit.getPlayer(getUuid())))
                BlitzSG.getInstance().getCosmeticsManager().add(Bukkit.getPlayer(getUuid()));
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

    public HashSet<Entity> getGameEntities() {
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
        if (rank == null)
            this.rank = BlitzSG.getInstance().getRankManager().getRankByName("Default");
        else
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

    public void setGameTaunt(int i) {
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

    public void setWobbuffet(boolean idk) {
        wobbuffet = idk;
    }

    public boolean getWobbuffet() {
        return this.wobbuffet;
    }

    public void setRobinhood(boolean idk) {
        robinhood = idk;
    }

    public boolean getRobinhood() {
        return this.robinhood;
    }


    public void setGame(Game g) {
        BlitzSG.getInstance().getBlitzSGPlayerManager().setUhcPlayerGame(this, g);
    }

    public boolean isEditingArena() {
        if (this.editedMap == null)
            return false;
        else
            return true;
    }

    public Map getEditedArena() {
        return this.editedMap;
    }

    public void setEditedArena(Map map) {
        this.editedMap = map;
    }

    public Aura getAura() {
        return this.aura;
    }

    public JsonObject getJsonObject() {
        return this.jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
