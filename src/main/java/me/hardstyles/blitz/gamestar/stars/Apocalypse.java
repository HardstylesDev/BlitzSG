package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Apocalypse extends Star {
    public Apocalypse() {
        super("Apocalypse", Material.TNT, "Start a fireball apocalypse!", 0);
    }

    int time = 0;

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30 * 20, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30 * 20, 2));

        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        initializeTimer();

        for (int t = 0; t < 30; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {

                    if (user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME)
                        return;
                    else if (!user.getGame().getAlivePlayers().contains(p))
                        return;
                    List<Entity> a = p.getNearbyEntities(20, 20, 20);
                    for (Entity entity : a) {
                        if (!(entity instanceof Player))
                            continue;

                        if (user.getGame().getAlivePlayers().contains(entity)) {
                            for (int b = 0; b < 5; ++b)
                                spawnMeteor((Player) entity);
                        }
                    }
                    if (time == 29) {
                        return;
                    }
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 20);
        }

    }

    public void initializeTimer() {
        for (int t = 0; t < 600; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    updateVelocity();
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 20);
        }
    }

    List<Entity> fireballs = new ArrayList<>();

    public void updateVelocity() {
        if (fireballs.isEmpty())
            return;
        List<Entity> clear = new ArrayList<>();

        fireballs.forEach(entity -> {
            if (!entity.isValid()) {
                clear.add(entity);
                return;
            }
            ((Fireball) entity).setDirection(new Vector(0, -1, 0));
        });
        clear.forEach(entity -> fireballs.remove(entity));
    }

    public void spawnMeteor(Player p) {
        int randomX = (int) (Math.random() * 20 + 1);
        int randomZ = (int) (Math.random() * 20 + 1);


        Location loc = p.getLocation().clone().add(0, 40, 0);

        Entity fireball = p.getWorld().spawnEntity(loc.clone().add(randomX - 10, 0, randomZ - 10), EntityType.FIREBALL);

        fireball.setVelocity(new Vector(0, -1, 0));
        ((Fireball) fireball).setDirection(new Vector(0, -1, 0));
        fireballs.add(fireball);


    }
}

//License: FCSQ-IPUQ-H2UO-P9XM