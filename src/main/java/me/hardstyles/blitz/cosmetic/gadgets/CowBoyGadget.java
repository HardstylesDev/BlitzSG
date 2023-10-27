package me.hardstyles.blitz.cosmetic.gadgets;

import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CowBoyGadget extends Gadget {
    public CowBoyGadget() {
        super("Cowboy", new ItemBuilder(Material.CACTUS).name("&aCowboy &7(Right Click)").make(), "&7Right click to ride another player!");
        setCooldown(15000);
    }

    @Override
    public void onUse(Player player) {
        Location loc = player.getLocation();
        player.getNearbyEntities(5, 5, 5).stream().filter(entity -> entity instanceof Player).forEach(entity -> {
            if (entity != player) {
                entity.setPassenger(player);
            }
        });
    }
}
