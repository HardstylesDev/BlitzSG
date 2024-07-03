package me.hardstyles.blitz.cosmetic.gadgets;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;


@SuppressWarnings("deprecation")
public class PaintballGunGadget extends Gadget implements Listener {

    RegionCorner pointA = null;
    RegionCorner pointB = null;

    public PaintballGunGadget() {
        super("Paintball Gun", new ItemBuilder(Material.DIAMOND_BARDING).name("&aPaintball Gun &7(Right Click)").make(), "&7Shoot paintballs!");
        BlitzSG.getInstance().getServer().getPluginManager().registerEvents(this, BlitzSG.getInstance());
        pointA = new RegionCorner(14, 98, -4);
        pointB = new RegionCorner(19, 109, 3);
        setCooldown(2500);
    }

    private boolean isInRegion(Location loc) {
        return loc.getBlockX() >= pointA.x && loc.getBlockX() <= pointB.x && loc.getBlockY() >= pointA.y && loc.getBlockY() <= pointB.y && loc.getBlockZ() >= pointA.z && loc.getBlockZ() <= pointB.z;
    }

    @Override
    public void onUse(final Player player) {
        final Snowball snowball = player.launchProjectile(Snowball.class);

        new BukkitRunnable() {
            @Override
            public void run() {
                Location impactLocation = snowball.getLocation();
                if (snowball.isDead() || !snowball.isValid()) {
                    DyeColor[] colors = {DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW, DyeColor.GREEN, DyeColor.BLUE, DyeColor.PURPLE};
                    World world = impactLocation.getWorld();
                    HashMap<Location, IBlock> blocks = new HashMap<>();
                    DyeColor color = colors[(int) (Math.random() * colors.length)];
                    for (int x = -1; x <= 1; x++) {
                        for (int y = 0; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                if (isInRegion(impactLocation.clone().add(x, y, z))) continue;
                                if (Math.random() > 0.2) {
                                    Location blockLocation = impactLocation.clone().add(x, y, z);
                                    if (blockLocation.getBlock().getType() != Material.AIR && blockLocation.getBlock().getType().isSolid()) {
                                        if (blockLocation.getBlock().getType() == Material.STAINED_CLAY) continue;
                                        blocks.put(blockLocation, new IBlock(blockLocation, blockLocation.getBlock().getType(), blockLocation.getBlock().getData()));
                                        world.getPlayers().forEach(player1 -> player1.sendBlockChange(blockLocation, Material.STAINED_CLAY, color.getData()));
                                    }
                                }
                            }
                        }
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (IBlock block : blocks.values()) {
                                block.location.getBlock().getState().update();
                            }
                        }
                    }.runTaskLater(BlitzSG.getInstance(), 100L);
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 1L, 1L);
    }

    private static class RegionCorner {
        int x;
        int y;
        int z;

        public RegionCorner(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static class IBlock {
        private final Location location;
        private final Material material;
        private final byte data;

        public IBlock(Location a, Material b, byte c) {
            location = a;
            material = b;
            data = c;
        }
    }
}
