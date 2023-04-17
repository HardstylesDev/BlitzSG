package me.hardstyles.blitz.cosmetic.cosmetics;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PortalParticle extends Aura {
    public PortalParticle() {
        super("PortalParticle", "Portal Particle Aura", "For when you're feeling out of this world!", BlitzSG.getInstance().getRankManager().getRankByName("MVP+"), new ItemStack(Material.OBSIDIAN, 1),6);
    }

    @Override
    public void tick(Player p) {
        Location loc = p.getLocation().clone().add(0, 1, 0);

        //p.playEffect(p.getLocation(),Effect.TILE_DUST, Material.WOOL.getId(),3,4,2);

        Bukkit.getOnlinePlayers().forEach(player1 -> p.spigot().playEffect(loc, Effect.PORTAL, 0, 0, (float) .2, (float) 0.5, (float) .2, 0, 3, 64));


    }
}
