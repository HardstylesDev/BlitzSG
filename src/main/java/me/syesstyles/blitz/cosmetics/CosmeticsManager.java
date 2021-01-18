package me.syesstyles.blitz.cosmetics;

import me.syesstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CosmeticsManager {
    private boolean stop = false;

    public void enable() {

    }

    public void disable() {

    }

    public void init() {
        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        Location loc = player.getLocation().clone().subtract(0, 1, 0);
                        Block b = loc.getBlock();
                        if (!b.getType().isTransparent() && b.getType().isSolid() && b.getType().isOccluding()) {
                            Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendBlockChange(loc, Material.STAINED_GLASS, (byte) randomColor()));
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    b.getState().update();
                                }
                            }.runTaskLaterAsynchronously(BlitzSG.getInstance(), 20);
                        }
                    }
                }.runTaskTimerAsynchronously(BlitzSG.getInstance(), 4, 4);
            }
        }
    }

    int[] random = new int[]{14, 1, 4, 5, 9, 11, 2, 10};

    public int randomColor() {

        int rnd = new Random().nextInt(random.length);
        return random[rnd];
    }

}
