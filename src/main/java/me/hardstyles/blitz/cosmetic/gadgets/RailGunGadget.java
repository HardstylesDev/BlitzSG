package me.hardstyles.blitz.cosmetic.gadgets;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;

import java.util.Set;

public class RailGunGadget extends Gadget {
    public RailGunGadget() {
        super("Railgun", new ItemBuilder(Material.WOOD_HOE).name("&aRailgun &7(Right Click)").make(), "&7Right click to shoot!");
        setCooldown(500);
        setRank(BlitzSG.getInstance().getRankManager().getRankByName("MVP"));
    }

    @Override
    public void onUse(Player player) {
        Location loc = player.getLocation().add(0, 1, 0);
        Block block = player.getTargetBlock((Set<Material>) null, 50);
        if (block == null) {
            return;
        }
        Location loc2 = block.getLocation();

        // iterate over all blocks between loc and loc2 and spawn a firework at each block
        // this will create a line of firework particles

        for (int i = 0; i < 50; i++) {
            Location loc3 = loc.clone().add(loc2.clone().subtract(loc).toVector().multiply(i / 50.0));
            Firework fw = (Firework) loc3.getWorld().spawnEntity(loc3, EntityType.FIREWORK);
            BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), fw::remove, 1);
        }



    }
}
