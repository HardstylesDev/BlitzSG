package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gremlin extends Star {
    public Gremlin(){
        super("Gremlin", Material.POTION,"Steal all exp from enemies", 5000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        List<Player> targets = new ArrayList<>(user.getGame().getAlivePlayers());

        targets.forEach(player -> {
            if(player != p){
                p.giveExp(player.getTotalExperience());
                player.setTotalExperience(0);
                player.setExp(0);
                player.setLevel(0);
            }
        });

    }
}
