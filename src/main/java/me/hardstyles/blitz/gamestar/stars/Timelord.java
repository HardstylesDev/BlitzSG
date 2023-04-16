package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Timelord extends Star {
    public Timelord() {
        super("Timelord", Material.ENDER_CHEST, "Be the lord of time!", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30*20,0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30*20,0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30*20,0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 30*20,0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 30*20,0));
        p.setHealth(20);
        p.setFoodLevel(20);
    }
}