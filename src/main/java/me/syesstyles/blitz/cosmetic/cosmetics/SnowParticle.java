package me.syesstyles.blitz.cosmetic.cosmetics;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SnowParticle extends Aura {
    public SnowParticle() {
        super("SnowParticle", "Snow Particle Aura", "Show off your cold hearted side!", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.SNOW_BLOCK, 1),10);
    }

    @Override
    public void uh(Player p) {
        Location loc = p.getLocation().clone().add(0, 1, 0);

        //p.playEffect(p.getLocation(),Effect.TILE_DUST, Material.WOOL.getId(),3,4,2);

        Bukkit.getOnlinePlayers().forEach(player1 -> p.spigot().playEffect(loc, Effect.SNOWBALL_BREAK, 0, 0, (float) .2, (float) 0.5, (float) .2, 0, 1, 64));


    }
}
