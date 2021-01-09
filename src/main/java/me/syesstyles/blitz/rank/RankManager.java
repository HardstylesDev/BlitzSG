package me.syesstyles.blitz.rank;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.rank.ranks.*;
import me.syesstyles.blitz.utils.nametag.Nametag;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RankManager {

    public static final Class<? extends Rank>[] RANKS = new Class[]{Default.class, Admin.class, Helper.class, Champion.class, Moderator.class, Vip.class, VipPlus.class, Mvp.class, MvpPlus.class, Youtuber.class};
    private final ArrayList<Rank> ranks = new ArrayList<>();
    private final ArrayList<Nametag> nametagEdits = new ArrayList<>();

    public Rank getRank(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (bsgPlayer.getRank() == null)
            return getRankByName("Default");
        return bsgPlayer.getRank();
    }
    public ArrayList<Nametag> getNametags(){
        return nametagEdits;
    }
    public Rank getRank(Player p, boolean hideNick) {
        BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (blitzSGPlayer.getNickName() != null && !blitzSGPlayer.getNickName().equalsIgnoreCase("")) {
            return getRankByName("Default");
        }
        return getRank(p);
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
