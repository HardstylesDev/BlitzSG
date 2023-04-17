package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Imprison extends Star {
    public Imprison() {
        super("Imprison", Material.IRON_FENCE, "Prevent all opponents from moving!", 5000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        for (Player alivePlayer : user.getGame().getAlivePlayers()) {
            if (alivePlayer != p)
                alivePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 9));
        }

    }
}