package me.hardstyles.blitz.cosmetic.cosmetics.aura;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
public class HeartParticle extends Aura {
    private static final int NUM_PARTICLES = 1; // Number of particles in the circle
    private static final double RADIUS = 0.5; // Radius of the circle
    private double angle = 0; // Current angle of the particle
    private static final double ANGLE_INCREMENT = Math.PI * 2 / 20; // Angle increment between each tick (adjusted for 200ms ticks)

    public HeartParticle() {
        super("HeartParticle", "Heart Particle", "Hidden aura!", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.WOOL, 1, (byte) 11),2);
    }

    @Override
    public void tick(Player p) {
        Location center = p.getLocation().clone().add(0, 1, 0);

        double x = center.getX() + RADIUS * Math.cos(angle);
        double y = center.getY() + 1;
        double z = center.getZ() + RADIUS * Math.sin(angle);
        Location particleLoc = new Location(center.getWorld(), x, y, z);

        for (Player viewer : Bukkit.getOnlinePlayers()) {
            viewer.spigot().playEffect(particleLoc, Effect.HEART, 0, 0, (float) 0.2, (float) 0.5, (float) 0.2, 0, NUM_PARTICLES, 64);
        }

        angle += ANGLE_INCREMENT;
        if (angle > 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
    }
}