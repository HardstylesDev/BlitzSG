package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Firebreather extends Star {
    public Firebreather() {
        super("Firebreather", Material.FIREBALL, "Shoot fireballs for 30 seconds.", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30*20, 2));
        for (int t = 0; t < 30; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME)
                        return;
                    else if(!user.getGame().getAlivePlayers().contains(p))
                        return;
                    p.launchProjectile(Fireball.class);
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 20);
        }

    }
}