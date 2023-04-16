package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Ninja extends Star {
    public Ninja() {
        super("Ninja", Material.BOOK, "Turn invisible!", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30*20,1, false,false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30*20,0));
    }
}