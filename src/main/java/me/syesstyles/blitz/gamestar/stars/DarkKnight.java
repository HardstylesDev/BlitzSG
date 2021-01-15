package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class DarkKnight extends Star {
    public DarkKnight() {
        super("Dark Knight", Material.PAINTING, "Bat fights!", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);

        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        for (int i = 0; i < 40; ++i) {
            int randomX = (int) (Math.random() * 6 + 1);
            int randomZ = (int) (Math.random() * 6 + 1);
            Entity bat = p.getWorld().spawnEntity(p.getEyeLocation().add(randomX - 3, -1, randomZ - 3), EntityType.BAT);
        }
        List<Entity> a = p.getNearbyEntities(20, 15, 20);
        a.forEach(entity -> {
            if (entity != p && entity instanceof Player) {
                ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 1));

                for (int i = 0; i < 15; ++i) {
                    entity.getWorld().spawnEntity(((Player) entity).getEyeLocation().add(0, -1, 0), EntityType.BAT);
                }
            }
        });
    }
}
