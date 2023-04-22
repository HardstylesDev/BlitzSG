package me.hardstyles.blitz.cosmetic.cosmetics.taunt;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.player.IPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class RichJamesTaunt extends Taunt {
    public RichJamesTaunt() {
        super("richjames", "Rich James", "While taunting, you flaunt your greed by showing off your diamonds.", BlitzSG.getInstance().getRankManager().getRankByName("MVP"), new ItemStack(Material.DIAMOND, 1));
    }


    public void run(Player player) {
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());

        for (int t = 0; t < 20; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    drop(player.getLocation());
                    drop(player.getLocation());
                    player.getWorld().getPlayers().forEach(player1 -> player1.playSound(player.getLocation(), Sound.ORB_PICKUP, 2,1));
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 5);
        }

    }

    Random random = new Random();

    private void drop(Location location) {
        ItemStack toDrop = new ItemStack(Material.DIAMOND);
        final Item item = location.getWorld().dropItem(location.clone().add(0,1,0), toDrop);
        item.setVelocity(new Vector((random.nextDouble() - 0.5) / 3, 0.25, (random.nextDouble() - 0.5) / 3));
        item.setPickupDelay(Integer.MAX_VALUE);

        new BukkitRunnable() {
            @Override
            public void run() {
                item.remove();
            }
        }.runTaskLater(BlitzSG.getInstance(), 10);
    }
}
