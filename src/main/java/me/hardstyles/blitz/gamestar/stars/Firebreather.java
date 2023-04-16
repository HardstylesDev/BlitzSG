package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Firebreather extends Star {
    public Firebreather() {
        super("Firebreather", Material.FIREBALL, "Shoot fireballs for 30 seconds.", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
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