package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.gamestar.Star;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class Explosion extends Star {
    public Explosion() {
        super("Explosion", Material.ANVIL, "KABOOM!", 5000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);

        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 0));

        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());


        for (int t = 0; t < 20; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME)
                        return;
                    else if (!user.getGame().getAlivePlayers().contains(p))
                        return;
                    List<Entity> a = p.getNearbyEntities(5, 5, 5);
                    for (Entity entity : a) {
                        if (!(entity instanceof Player) || entity == p)
                            continue;
                        if (user.getGame().getAlivePlayers().contains(entity) && entity.getFallDistance() < 5) {
                            Vector throwVector = new Vector(0, 5, 0);
                            //throwVector.normalize();
                            try {
                                entity.setVelocity(throwVector);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    for (int b = 0; b < 16; ++b) {
                        int randomX = (int) (Math.random() * 8 + 1);
                        int randomZ = (int) (Math.random() * 8 + 1);
                        p.getWorld().playEffect(p.getLocation().clone().add(randomX - 4, 0, randomZ - 4), Effect.EXPLOSION_HUGE, 10);
                    }
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 20);
        }

    }
}
