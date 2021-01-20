package me.syesstyles.blitz.map;

import me.syesstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Map {

    private String name;

    private World arenaWorld;
    private Location arenaMinCorner;
    private Location arenaMaxCorner;
    private Location lobby;
    private Location deathmatch;
    private int deathmatchDistance;
    public void setSpawns(ArrayList<Location> spawns) {
        ArrayList<Location> test = new ArrayList<>();
        spawns.forEach(location -> {
            if (test.contains(location))
                Bukkit.broadcastMessage(location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + " is double!");
            test.add(location);
        });
        this.spawns = spawns;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public void setWorld(World world) {
        this.arenaWorld = world;
    }

    private ArrayList<Location> spawns;

    private boolean inUse;

    public Map(Location corner1, Location corner2, String name) {
        this.name = name;
        this.spawns = new ArrayList<Location>();

        this.arenaWorld = corner1.getWorld();
        this.inUse = false;

        double x1 = Math.min(corner1.getBlockX(), corner2.getBlockX());
        double y1 = Math.min(corner1.getBlockY(), corner2.getBlockY());
        double z1 = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
        double x2 = Math.max(corner1.getBlockX(), corner2.getBlockX());
        double y2 = Math.max(corner1.getBlockY(), corner2.getBlockY());
        double z2 = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
        arenaMinCorner = new Location(corner1.getWorld(), x1, y1, z1);
        arenaMaxCorner = new Location(corner1.getWorld(), x2, y2, z2);

        BlitzSG.getInstance().getArenaManager().addArena(this);
    }

    public Map(World w, String name) {
        this.name = name;
        this.inUse = false;
        this.spawns = new ArrayList<Location>();
        this.lobby = null;
        BlitzSG.getInstance().getArenaManager().addArena(this);
        //SpeedUHC.getInstance().getArenaManager().addArenaPreset(this);
    }

    public void resetArena() {
        for (Player p : this.getArenaWorld().getPlayers()) {
            p.teleport(BlitzSG.lobbySpawn);
            if (p.getWorld() != new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)) // todo change back
                p.kickPlayer("Map is resetting");
        }

        try {
            new BukkitRunnable() {
                public void run() {
                    setInUse(false);

                    try {
                        if (!BlitzSG.getInstance().getArenaManager().deleteMap(getArenaWorld().getName()));
                        //    System.out.println("Error unloading + removing map: " + getArenaWorld().getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.runTaskLater(BlitzSG.getInstance(), 5);
        } catch (IllegalPluginAccessException ignored) {
        }
    }

    public String getName() {
        return name;
    }

    public World getArenaWorld() {
        return arenaWorld;
    }

    public void setArenaWorld(World arenaWorld) {
        this.arenaWorld = arenaWorld;
    }

    public Location getCenter() {
        return new Location(getArenaWorld(),
                (arenaMinCorner.getBlockX() + arenaMaxCorner.getBlockX()) / 2
                , (arenaMinCorner.getBlockY() + arenaMaxCorner.getBlockY()) / 2
                , (arenaMinCorner.getBlockZ() + arenaMaxCorner.getBlockZ()) / 2);
    }

    public Location getArenaMinCorner() {
        return arenaMinCorner;
    }

    public void setArenaMinCorner(Location arenaMinCorner) {
        this.arenaMinCorner = arenaMinCorner;
    }

    public Location getArenaMaxCorner() {
        return arenaMaxCorner;
    }

    public void setArenaMaxCorner(Location arenaMaxCorner) {
        this.arenaMaxCorner = arenaMaxCorner;
    }

    public void addSpawn(Location location) {
        spawns.add(location);
    }


    public ArrayList<Location> getSpawns() {
        return spawns;
    }

    public Location getLobby() {
        return lobby;
    }

    public Location getDeathmatch() {
        return deathmatch;
    }

    public void setDeathmatch(Location location) {
        this.deathmatch = location;
    }

    public int getDeathmatchDistance() {
        return deathmatchDistance;
    }

    public void setDeathmatchDistance(int distance) {
        this.deathmatchDistance = distance;
    }



    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public boolean isUsable() {
        if (arenaMinCorner == null && arenaMaxCorner == null && spawns.size() < 2)
            return false;
        else
            return true;
    }

}
