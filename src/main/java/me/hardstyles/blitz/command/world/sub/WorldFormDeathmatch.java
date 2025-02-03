package me.hardstyles.blitz.command.world.sub;


import com.google.common.collect.ImmutableList;
import lombok.var;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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

        if (args.length == 2) {
            createFilledHexagon(loc, 16);
            return;
        }

        createCircle(loc, 15);
    }


    public void createFilledHexagon(Location center, int sideLength) {
        List<List<Location>> hexagonLayers = getHexagonLayers(center, sideLength);

        // Fill the hexagon layer by layer from the outside in
        int delay = 0;
        for (int i = hexagonLayers.size() - 1; i >= 0; i--) {
            List<Location> layer = hexagonLayers.get(i);
            for (Location loc : layer) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        loc.getBlock().setType(Material.SMOOTH_BRICK);
                    }
                }.runTaskLater(BlitzSG.getInstance(), delay);
            }
            if(Math.random() < 0.2){
                delay += 1;
            }
        }

        // Push the hexagon layer by layer from the outside in
        new BukkitRunnable() {
            int currentLayer = 0;

            @Override
            public void run() {
                if (currentLayer >= hexagonLayers.size()) {
                    cancel();
                    return;
                }

                List<Location> layer = hexagonLayers.get(currentLayer);
                for (Location loc : layer) {
                    if (loc.getBlock().getType() == Material.SMOOTH_BRICK) {
                        pushBlock(loc);
                    }
                }
                currentLayer++;
            }
        }.runTaskTimer(BlitzSG.getInstance(), delay, 5);
    }

    private List<List<Location>> getHexagonLayers(Location center, int sideLength) {
        List<List<Location>> hexagonLayers = new ArrayList<>();
        List<Location> allHexagonPoints = new ArrayList<>();

        int centerX = center.getBlockX();
        int centerZ = center.getBlockZ();
        int y = center.getBlockY();

        double angle = Math.PI / 3; // 60 degrees in radians

        // Calculate the vertices of the hexagon
        double[][] vertices = new double[6][2];
        for (int i = 0; i < 6; i++) {
            vertices[i][0] = centerX + sideLength * Math.cos(i * angle);
            vertices[i][1] = centerZ + sideLength * Math.sin(i * angle);
        }

        // Loop through the bounding box of the hexagon and store each point
        int minX = centerX - sideLength;
        int maxX = centerX + sideLength;
        int minZ = centerZ - (int) (Math.sqrt(3) / 2 * sideLength);
        int maxZ = centerZ + (int) (Math.sqrt(3) / 2 * sideLength);

        for (int z = minZ; z <= maxZ; z++) {
            for (int x = minX; x <= maxX; x++) {
                if (isPointInHexagon(x, z, vertices)) {
                    Location currentLoc = new Location(center.getWorld(), x, y, z);
                    allHexagonPoints.add(currentLoc);
                }
            }
        }

        // Determine the layers by distance from the center
        while (!allHexagonPoints.isEmpty()) {
            List<Location> currentLayer = new ArrayList<>();
            List<Location> nextPoints = new ArrayList<>();
            double maxDistance = 0;

            // Determine the maximum distance of the current points from the center
            for (Location loc : allHexagonPoints) {
                double distance = center.distance(loc);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }

            // Separate the points into the current layer and the next points
            for (Location loc : allHexagonPoints) {
                double distance = center.distance(loc);
                if (distance == maxDistance) {
                    currentLayer.add(loc);
                } else {
                    nextPoints.add(loc);
                }
            }

            hexagonLayers.add(currentLayer);
            allHexagonPoints = nextPoints;
        }

        return hexagonLayers;
    }

    // Function to check if a point is inside a hexagon
    private boolean isPointInHexagon(int x, int z, double[][] vertices) {
        int intersections = 0;
        for (int i = 0; i < 6; i++) {
            double x1 = vertices[i][0];
            double z1 = vertices[i][1];
            double x2 = vertices[(i + 1) % 6][0];
            double z2 = vertices[(i + 1) % 6][1];

            if ((z1 <= z && z2 > z) || (z2 <= z && z1 > z)) {
                double intersectX = x1 + (z - z1) / (z2 - z1) * (x2 - x1);
                if (intersectX > x) {
                    intersections++;
                }
            }
        }
        return intersections % 2 != 0;
    }


    public void createCircle(Location center, int radius) {
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