package me.hardstyles.blitz.cosmetic.cosmetics;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LavaParticle extends Aura {
    public LavaParticle() {
        super("LavaParticle", "Lava Particle Aura", "Lava Particle to make you look hot!", BlitzSG.getInstance().getRankManager().getRankByName("VIP+"), new ItemStack(Material.LAVA_BUCKET, 1),9);
    }

    @Override
    public void tick(Player p) {
        Location loc = p.getLocation().clone().add(0, 1, 0);

        //p.playEffect(p.getLocation(),Effect.TILE_DUST, Material.WOOL.getId(),3,4,2);

        Bukkit.getOnlinePlayers().forEach(player1 -> player1.spigot().playEffect(loc, Effect.LAVADRIP, 0, 0, (float) .2, (float) 0.5, (float) .2, 0, 1, 64));


    }
}
