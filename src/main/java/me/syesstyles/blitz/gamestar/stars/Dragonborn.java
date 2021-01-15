package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class Dragonborn extends Star {
    public Dragonborn() {
        super("Dragonborn", Material.DRAGON_EGG, "Fus-Roh-Dah!", 5000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        p.chat("Fus-Roh-Dah!");
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30 * 20, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 1));

        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        List<Entity> a = p.getNearbyEntities(15, 15, 15);
        a.forEach(entity -> {
            if (entity != p) {
                Location playerCenterLocation = entity.getLocation();
                Location playerToThrowLocation = p.getLocation();

                double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
                double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
                double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

                Vector throwVector = new Vector(x, y, z);

                throwVector.normalize();
                throwVector.multiply(-2.5D);
                throwVector.setY(1.0D);

               entity.setVelocity(throwVector);

            }
        });


    }
}
