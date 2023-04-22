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

public class MyceliumTrail extends Aura {
    public MyceliumTrail() {
        super("MyceliumTrail", "Mycelium Trail", "Now just some red and white colored cows and we're all set!", BlitzSG.getInstance().getRankManager().getRankByName("VIP+"),new ItemStack(Material.MYCEL, 1),12);
    }

    @Override
    public void tick(Player p) {
        Location loc = p.getLocation().clone().subtract(0, 1, 0);
        Block b = loc.getBlock();
        if (!b.getType().isTransparent() && b.getType().isSolid() && b.getType().isOccluding()) {

            Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendBlockChange(loc, Material.MYCEL, (byte) 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    b.getState().update();
                }
            }.runTaskLaterAsynchronously(BlitzSG.getInstance(), 20);
        }
    }
}
