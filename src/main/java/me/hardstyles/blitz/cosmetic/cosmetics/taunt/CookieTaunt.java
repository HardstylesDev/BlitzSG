package me.hardstyles.blitz.cosmetic.cosmetics.taunt;


import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.player.IPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class CookieTaunt extends Taunt {
    public CookieTaunt() {
        super("cookie", "Cookie", "While taunting, you throw up some still-edible cookies.", BlitzSG.getInstance().getRankManager().getRankByName("MVP"), new ItemStack(Material.COOKIE, 1));
    }


    public void run(Player player) {
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());

        for (int t = 0; t < 10; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {

                    shoot(player);
                    player.getWorld().getPlayers().forEach(player1 -> player1.playSound(player.getLocation(), Sound.EAT, 2, 1));

                }
            }.runTaskLater(BlitzSG.getInstance(), t * 4);
        }

    }


    private void shoot(Player player) {

        Location fiveBlocksAhead = player.getLocation().add(player.getLocation().getDirection().multiply(5));
        Vector vectorFix = player.getLocation().toVector().subtract(fiveBlocksAhead.toVector()).normalize();
        Item item = player.getWorld().dropItem(player.getLocation().clone().add(Math.random() - 0.5, 1.3, Math.random() - 0.5), new ItemStack(Material.COOKIE));
        item.setVelocity(vectorFix.multiply(-1));

        new BukkitRunnable() {
            @Override
            public void run() {
                item.remove();
            }
        }.runTaskLater(BlitzSG.getInstance(), 10);
    }
}
