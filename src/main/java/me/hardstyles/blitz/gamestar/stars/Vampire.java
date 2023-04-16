package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Vampire extends Star {
    public Vampire() {
        super("Vampire", Material.REDSTONE, "Drain the health of nearby players for 20 seconds!", 10000);
    }

    int time = 0;

    @Override
    public void run(Player p) {
        time = 0;
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 3));

        for (int t = 0; t < 21; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME) {
                        return;
                    } else if (!user.getGame().getAlivePlayers().contains(p)) {
                        return;
                    }
                    for (Entity entity : user.getGame().getAlivePlayers()) {
                        if (!(entity instanceof Player))
                            continue;
                        if (user.getGame().getAlivePlayers().contains(entity)) {
                            if (entity != p) {
                                ((Player) entity).damage(0.3);
                                ((Player) entity).playSound(entity.getLocation(), Sound.SILVERFISH_HIT, 1, 1);
                                drawLine(p.getEyeLocation().clone().add(new Vector(0, -.5, 0)), ((Player) entity).getEyeLocation().clone().add(new Vector(0, -.5, 0)), 2);
                            }

                        }
                    }
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 20);
        }

    }

    public void drawLine(Location point1, Location point2, double space) {
        if (point1.getWorld() != point2.getWorld())
            return;
        World world = point1.getWorld();

        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            world.playEffect(new Location(world, p1.getX(), p1.getY(), p1.getZ()), Effect.HEART, 1);
            length += space;
        }
    }

}