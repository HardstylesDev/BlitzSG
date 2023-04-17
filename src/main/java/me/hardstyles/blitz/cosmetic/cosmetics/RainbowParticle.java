package me.hardstyles.blitz.cosmetic.cosmetics;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RainbowParticle extends Aura {
    public RainbowParticle() {
        super("RainbowParticle", "Rainbow Particle Aura", "Sorry, it's only a single rainbow, can't do a double rainbow due to budget cuts!", BlitzSG.getInstance().getRankManager().getRankByName("VIP+"), new ItemStack(Material.WOOL, 1),4);
    }

    @Override
    public void tick(Player p) {
        Location loc = p.getLocation().clone().add(0, 1, 0);

        //p.playEffect(p.getLocation(),Effect.TILE_DUST, Material.WOOL.getId(),3,4,2);
        int particle = randomColor();
        Bukkit.getOnlinePlayers().forEach(player1 ->         p.spigot().playEffect(loc, Effect.TILE_DUST, Material.WOOL.getId(), particle, (float) .2, (float) 0.5, (float) .2, 0, 3, 64));


    }
    int[] random = new int[]{14, 1, 4, 5, 9, 11, 2, 10};
    public int randomColor() {
        int rnd = new Random().nextInt(random.length);
        return random[rnd];
    }
}
