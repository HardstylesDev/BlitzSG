package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Gremlin extends Star {
    public Gremlin(){
        super("Gremlin", Material.POTION,"Steal all exp from enemies", 5000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
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
