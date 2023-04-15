package me.syesstyles.blitz.blitzsgplayer;

import lombok.Getter;
import lombok.Setter;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.arena.Arena;
import me.syesstyles.blitz.cosmetic.Aura;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
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

@Getter
@Setter
public class BlitzSGPlayer {

    private Kit selectedKit;
    private int gameTaunt;
    private UUID uuid;

    private boolean robinhood, punched, wobbuffet;
    private int gameKills;
    private Nick nick;
    private HashSet<Entity> gameEntities;
    private HashSet<Star> stars;
    private Location gameSpawn;
    private Rank rank;
    private int deaths, coins, kills, wins, elo;
    private Aura aura;

    private HashMap<Kit, Integer> kitLevels;
    private float lastDamage;
    private Player lastDamager;

    public Player getLastAttacker() {
        if (lastDamager != null && lastDamager.isOnline()) {
            if (System.currentTimeMillis() - lastDamage < 15000) {
                return lastDamager;
            }
        }
        return null;
    }


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
        this.stars = new HashSet<Star>();
        this.kitLevels = new HashMap<Kit, Integer>();
        for (Kit p : BlitzSG.getInstance().getKitManager().getKits())
            this.kitLevels.put(p, 0);


        this.gameKills = 0;
        this.gameTaunt = -1;
        this.gameSpawn = null;
        this.selectedKit = null;
        this.aura = null;
        BlitzSG.getInstance().getBlitzSGPlayerManager().addPlayer(this.uuid, this);
    }

    public HashMap<Kit, Integer> getKits() {
        return this.kitLevels;
    }

    public void addStar(Star star) {
        this.stars.add(star);
    }


    public boolean isSpectating(){
        return getGame() != null && getGame().getDeadPlayers().contains(Bukkit.getPlayer(getUuid()));
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


    public boolean isNicked() {
        return (nick.isNicked());
    }

    public String getNickName() {
        if (this.nick != null)
            return this.nick.getNickName();
        return null;
    }

    public void removeElo(int elo) {
        if (this.elo - elo <= 0) {
            this.elo = 0;
            return;
        }
        this.elo += -elo;
    }


    public void setAura(Aura aura) {
        this.aura = aura;
        if (!BlitzSG.getInstance().getCosmeticsManager().getPlayers().contains(Bukkit.getPlayer(getUuid())))
            BlitzSG.getInstance().getCosmeticsManager().add(Bukkit.getPlayer(getUuid()));
    }

    public void addWin() {
        this.wins += 1;
    }

    public void addKill() {
        this.kills += 1;
    }

    public void addDeath() {
        this.deaths += 1;
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

    public void resetGameKills() {
        this.gameKills = 0;
    }

    public void addGameKill() {
        this.gameKills += 1;
        this.kills += 1;
    }

    public boolean isInGame() {
        return BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayerGame(this) != null;
    }

    public Game getGame() {
        return BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayerGame(this);
    }

    public void setGame(Game g) {
        BlitzSG.getInstance().getBlitzSGPlayerManager().setUhcPlayerGame(this, g);
    }

    public boolean isEditingArena() {
        return this.editedArena != null;
    }

    public void addElo(int eloChange) {
        this.elo += eloChange;
    }

    public void setNickName(String s) {
        this.nick.setNickName(s);
    }
}
