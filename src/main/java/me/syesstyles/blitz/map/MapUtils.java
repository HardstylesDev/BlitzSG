package me.syesstyles.blitz.map;

import me.syesstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MapUtils {

    public static HashMap<Player, Location> cornerLoc1 = new HashMap<Player, Location>();
    public static HashMap<Player, Location> cornerLoc2 = new HashMap<Player, Location>();

    public static Location getCornerLoc1(Player p) {
        return new Location(p.getWorld(), 0, 0, 0);
    }

    public static Location setCornerLoc1(Player p, Location loc) {
        return cornerLoc1.put(p, loc);
    }

    public static Location getCornerLoc2(Player p) {
        return new Location(p.getWorld(), 0, 0, 0);
    }

    public static Location setCornerLoc2(Player p, Location loc) {
        return cornerLoc2.put(p, loc);
    }

    public static void saveArenas() {
        for (Map a : BlitzSG.getInstance().getArenaManager().getArenas()) {
            File f = new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + a.getName() + ".yml");
            if (!f.exists()) {
                try {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileConfiguration fc = new YamlConfiguration();
            try {
                fc.load(f);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            System.out.println(a);
            fc.set("Name", a.getName());
            fc.set("World", a.getName());

            for (Location loc : a.getSpawns()) {
                fc.set("Spawns." + a.getSpawns().indexOf(loc) + ".X", loc.getBlockX());
                fc.set("Spawns." + a.getSpawns().indexOf(loc) + ".Y", loc.getBlockY());
                fc.set("Spawns." + a.getSpawns().indexOf(loc) + ".Z", loc.getBlockZ());
            }
            try {
                fc.set("Lobby.X", a.getLobby().getBlockX());
                fc.set("Lobby.Y", a.getLobby().getBlockY());
                fc.set("Lobby.Z", a.getLobby().getBlockZ());
            } catch (NullPointerException ignored) {
            }

            try {
                fc.set("Deathmatch.X", a.getDeathmatch().getBlockX());
                fc.set("Deathmatch.Y", a.getDeathmatch().getBlockY());
                fc.set("Deathmatch.Z", a.getDeathmatch().getBlockZ());

                fc.set("Deathmatch.Distance", a.getDeathmatchDistance());

            } catch (NullPointerException ignored) {
            }
            try {
                fc.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bukkit.unloadWorld(a.getArenaWorld(), false);
        }
    }

    public static void loadArenas() {

    }

}
