package me.syesstyles.blitz.rank;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.rank.ranks.Admin;
import me.syesstyles.blitz.rank.ranks.Default;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RankManager {

    public static final Class<? extends Rank>[] RANKS = new Class[]{Default.class, Admin.class};
    private final ArrayList<Rank> ranks = new ArrayList<>();

    public Rank getRank(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (bsgPlayer.getRank() == null)
            return new Default();
        return bsgPlayer.getRank();

    }


    public void setRank(Player p, Rank rank) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        bsgPlayer.setRank(rank);
    }

    public RankManager() {
        for (Class<? extends Rank> rankClass : RANKS) {
            try {
                this.ranks.add(rankClass.getConstructor().newInstance());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public Rank getRankByName(String name) {
        for (Rank r : this.ranks) {
            if (!r.getRank().equalsIgnoreCase(name)) continue;
            return r;
        }
        return null;
    }

    public Set<Rank> getRanks() {
        HashSet<Rank> allModules = new HashSet<Rank>();
        allModules.addAll(this.ranks);
        return allModules;
    }
}
