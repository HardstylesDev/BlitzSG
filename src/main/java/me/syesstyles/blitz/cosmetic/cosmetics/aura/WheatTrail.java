package me.syesstyles.blitz.cosmetic.cosmetics.aura;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class WheatTrail extends Aura {
    public WheatTrail() {
        super("WheatTrail", "Wheat Crop Trail", "Mooooooooooooooooo.", BlitzSG.getInstance().getRankManager().getRankByName("MVP"),new ItemStack(Material.WHEAT, 1),19);
    }

    @Override
    public void uh(Player p) {
        Location loc = p.getLocation().clone().subtract(0, 0, 0);
        Block b = loc.getBlock();
        if (b.getType() == Material.AIR && b.getLocation().clone().subtract(0,1,0).getBlock().getType().isOccluding()) {

            Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendBlockChange(loc, Material.CROPS, (byte) (new Random().nextInt(7 - 1 + 1) + 1)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    b.getState().update();
                }
            }.runTaskLaterAsynchronously(BlitzSG.getInstance(), 20);
        }
    }
}
