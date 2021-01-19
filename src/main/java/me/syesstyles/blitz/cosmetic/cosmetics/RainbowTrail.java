package me.syesstyles.blitz.cosmetic.cosmetics;

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

public class RainbowTrail extends Aura {
    public RainbowTrail() {
        super("RainbowTrail", "Rainbow Tron Trail", "Great. Can it send me to hawaii?", BlitzSG.getInstance().getRankManager().getRankByName("MVP+"),new ItemStack(Material.STAINED_GLASS, 1),14);
    }

    @Override
    public void uh(Player p) {
        Location loc = p.getLocation().clone().subtract(0, 1, 0);
        Block b = loc.getBlock();
        if (!b.getType().isTransparent() && b.getType().isSolid() && b.getType().isOccluding()) {
            int random = randomColor();
            Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendBlockChange(loc, Material.STAINED_GLASS, (byte) random));
            new BukkitRunnable() {
                @Override
                public void run() {
                    b.getState().update();
                }
            }.runTaskLaterAsynchronously(BlitzSG.getInstance(), 20);
        }
    }

    int[] random = new int[]{14, 1, 4, 5, 9, 11, 2, 10};
    public int randomColor() {
        int rnd = new Random().nextInt(random.length);
        return random[rnd];
    }


}
