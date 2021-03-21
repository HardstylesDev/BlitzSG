package me.syesstyles.blitz.utils.nametag;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NametagManager {

    private final ArrayList<Nametag> nametags = new ArrayList<>();
    private Nametag nametag;

    public NametagManager() {
        this.nametag = new Nametag();

    }

    public Nametag getNametagByRank(Rank name) {
        for (Nametag r : this.nametags) {
            if (!(r.getRequiredRank() == (name))) continue;
            return r;
        }
        return null;
    }

    public void updater() {
    }

    public void update() {

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(onlinePlayer.getUniqueId());
            if(bsgPlayer == null || bsgPlayer.getRank() == null || bsgPlayer.getRank().getPrefix() == null || bsgPlayer.getNick() == null)
                return;
            if (bsgPlayer.isInGame())
                nametag.setNametag(onlinePlayer, ChatColor.RED + "");
            else
                nametag.setNametag(onlinePlayer, bsgPlayer.getRank().getPrefix());
        }
    }
}
