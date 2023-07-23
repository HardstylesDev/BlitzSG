package me.hardstyles.blitz.command.world.sub;


import com.google.common.collect.ImmutableList;
import lombok.var;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class WorldFormDeathmatch extends SubCommand {

    public WorldFormDeathmatch() {
        super("formdm", ImmutableList.of(), "blitz.rank.formdddm", "/world formdm");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        Location loc = s.getLocation();
        createCircle(loc);
    }

    public void createCircle(Location center) {
        int radius = 15;
        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();

        // Loop to create the circle shape
        int await = 0;
        for (int x = -radius; x <= radius; x++) {
            await++;
            for (int z = -radius; z <= radius; z++) {
                int distanceSquared = x * x + z * z;
                if (distanceSquared <= radius * radius) {
                    int y = center.getBlockY();
                    Location currentLoc = new Location(center.getWorld(), centerX + x, y, centerZ + z);
                    BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
                        currentLoc.getBlock().setType(Material.SMOOTH_BRICK);
                    }, await);
                }
            }
        }

        BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
            int count = 0;

            for (int i = 0; i < 15; i++) {
                List<Location> outerLayer = getOuterLayer(center, radius - i);
                for (Location loc : outerLayer) {
                    count++;
                    BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
                        if (loc.getBlock().getType().equals(Material.SMOOTH_BRICK)) {
                            pushBlock(loc);
                        }
                    }, count);
                }
            }
        }, 100L);


    }

    public List<Location> getOuterLayer(Location center, int radius) {
        List<Location> outerLayer = new ArrayList<>();
        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();
        var angle = 0.0;
        while (angle < 360) {
            int x = (int) Math.round(radius * Math.cos(angle));
            int z = (int) Math.round(radius * Math.sin(angle));
            Location loc = new Location(center.getWorld(), centerX + x, center.getBlockY(), centerZ + z);
            if (loc.getBlock().getType() == Material.SMOOTH_BRICK) {
                outerLayer.add(loc);
            }
            angle += 1;
        }
        return outerLayer;
    }

    private void pushBlock(Location loc) {
        FallingBlock fallingBlock = loc.getWorld().spawnFallingBlock(loc, Material.SMOOTH_BRICK, (byte) 0);
        fallingBlock.setDropItem(false);
        fallingBlock.setVelocity(new Vector(0, 0.5, 0));
        loc.getBlock().setType(Material.AIR);
    }
}