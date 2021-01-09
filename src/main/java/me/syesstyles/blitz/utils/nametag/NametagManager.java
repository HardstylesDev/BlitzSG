package me.syesstyles.blitz.utils.nametag;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.rank.Rank;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

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

            nametag.setNametag(onlinePlayer, bsgPlayer.getRank().getPrefix(), bsgPlayer.getNick().isNicked());
        }
    }
}
