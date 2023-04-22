package me.hardstyles.blitz.cosmetic.cosmetics.aura;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FlowerTrail extends Aura {
    public FlowerTrail() {
        super("FlowerTrail", "Flower Trail", "Aww, I bet you have a crush!", BlitzSG.getInstance().getRankManager().getRankByName("MVP"), new ItemStack(Material.RED_ROSE),18);
    }

    @Override
    public void tick(Player p) {
        Location loc = p.getLocation().clone().subtract(0, 0, 0);
        Block b = loc.getBlock();
        if (b.getType() == Material.AIR && b.getLocation().clone().subtract(0,1,0).getBlock().getType().isOccluding()) {

            Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendBlockChange(loc, Material.RED_ROSE, (byte) (new Random().nextInt(8 - 0 + 1) + 0)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    b.getState().update();
                }
            }.runTaskLaterAsynchronously(BlitzSG.getInstance(), 20);
        }
    }
}
