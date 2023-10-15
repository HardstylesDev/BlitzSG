package me.hardstyles.blitz.cosmetic.gadgets;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WhenPigsFlyGadget extends Gadget {
    public WhenPigsFlyGadget() {
        super("When Pigs Fly", new ItemBuilder(Material.SADDLE).name("&aWhen Pigs Fly &7(Right Click)").make(), "&7Ride a pig!");
    }

    @Override
    public void onUse(Player player) {
        Location loc = player.getLocation();
        Entity pig = loc.getWorld().spawnEntity(loc, EntityType.PIG);
        pig.setPassenger(player);
        int b = BlitzSG.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(BlitzSG.getInstance(), () -> {
            pig.setVelocity(player.getLocation().getDirection().multiply(0.5));
        }, 0, 1);
        BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
            BlitzSG.getInstance().getServer().getScheduler().cancelTask(b);
            pig.remove();
        }, 100);

    }
}
