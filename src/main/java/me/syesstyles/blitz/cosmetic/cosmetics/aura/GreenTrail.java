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

public class GreenTrail extends Aura {
    public GreenTrail() {
        super("GreenTrail", "Green Tron Trail", "The trail of future grass!", BlitzSG.getInstance().getRankManager().getRankByName("VIP+"),new ItemStack(Material.STAINED_GLASS, 1, (byte) 13),17);
    }

    @Override
    public void uh(Player p) {
        Location loc = p.getLocation().clone().subtract(0, 1, 0);
        Block b = loc.getBlock();
        if (!b.getType().isTransparent() && b.getType().isSolid() && b.getType().isOccluding()) {

            Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendBlockChange(loc, Material.STAINED_GLASS, (byte) 13));
            new BukkitRunnable() {
                @Override
                public void run() {
                    b.getState().update();
                }
            }.runTaskLaterAsynchronously(BlitzSG.getInstance(), 20);
        }
    }
}
