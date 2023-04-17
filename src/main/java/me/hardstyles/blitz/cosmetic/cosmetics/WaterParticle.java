package me.hardstyles.blitz.cosmetic.cosmetics;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WaterParticle extends Aura {
    public WaterParticle() {
        super("WaterParticle", "Water Particle Aura", "For when you're doing a little too hot!", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.WATER_BUCKET, 1),11);
    }

    @Override
    public void tick(Player p) {
        Location loc = p.getLocation().clone().add(0, 1, 0);

        //p.playEffect(p.getLocation(),Effect.TILE_DUST, Material.WOOL.getId(),3,4,2);

        Bukkit.getOnlinePlayers().forEach(player1 -> p.spigot().playEffect(loc, Effect.SPLASH, 0, 0, (float) .2, (float) 0.5, (float) .2, 0, 2, 64));


    }
}
