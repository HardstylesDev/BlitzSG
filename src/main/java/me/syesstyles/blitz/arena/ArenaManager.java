package me.syesstyles.blitz.arena;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.utils.VoidGenerator;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ArenaManager {

    public ArrayList<Arena> arenas;

    public ArenaManager() {
        arenas = new ArrayList<Arena>();
    }

    public Arena getArena(String arenaName) {
        for (Arena a : arenas)
            if (a.getName().equalsIgnoreCase(arenaName))
                return a;
        return null;
    }

    public void addArena(Arena arena) {
        arenas.add(arena);
    }

    public void removeArena(Arena arena) {
        arenas.remove(arena);
    }

    public ArrayList<Arena> getArenas() {
        return this.arenas;
    }

    public Arena getRandomArena() {
        if (arenas.size() == 0)
            return null;
        Arena a = arenas.get(new Random().nextInt(arenas.size()));
        if (!a.isInUse() && a.isUsable())
            return a;
        for (Arena aa : arenas) {
            if (!aa.isInUse() && aa.isUsable())
                return aa;
        }
        return null;
    }


    public boolean deleteMap(String map) {
        return deleteMap(getArena(map));
    }

    public boolean deleteMap(Arena map) {
        map.setInUse(false);
        World world = Bukkit.getWorld(map.getName().toLowerCase());
        if (world != null) {
            unloadMap(map);
            try {
                FileUtils.forceDelete(world.getWorldFolder());
            } catch (IOException exception) {
               // exception.printStackTrace();
            }
        }
        File worldFile = new File("worlds/" + map.getName().toLowerCase());
        if (worldFile != null) {
            try {
                FileUtils.forceDelete(worldFile);
                return true;
            } catch (IOException exception) {
              //  exception.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void unloadMap(String map) {
        World world = Bukkit.getWorld(map.toLowerCase());
        if (world != null) {
            Bukkit.getWorld(map.toLowerCase()).getPlayers().forEach(player -> {
                player.teleport(BlitzSG.lobbySpawn);
            });
        }
        Bukkit.unloadWorld(map, false);
    }

    public void unloadMap(Arena map) {
        unloadMap(map.getName());
    }


    public boolean loadArena(String arena) {
        try {
            copyFileStructure(new File("arenas/" + arena.toLowerCase()), new File("worlds/" + arena.toLowerCase()));
            new WorldCreator(arena.toLowerCase()).generator(new VoidGenerator()).createWorld();

            FileConfiguration fc = new YamlConfiguration();
            try {
                fc.load(new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + arena.toLowerCase() + ".yml"));
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            World world = Bukkit.getWorld(arena.toLowerCase());
            world.setAutoSave(false);
            world.setGameRuleValue("doMobSpawning", "false");
            world.setGameRuleValue("mobGriefing", "false");
            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("showDeathMessages", "false");
            for (Entity entity : world.getEntities()) {
                entity.remove();
            }
            Location cornerMin = new Location(Bukkit.getWorld(fc.getString("Name") + "_temp"), fc.getInt("Bounds.Min.X")
                    , fc.getInt("Bounds.Min.Y"), fc.getInt("Bounds.Min.Z"));
            Location cornerMax = new Location(Bukkit.getWorld(fc.getString("Name") + "_temp"), fc.getInt("Bounds.Max.X")
                    , fc.getInt("Bounds.Max.Y"), fc.getInt("Bounds.Max.Z"));

            Arena a = new Arena(cornerMin, cornerMax, fc.getString("Name"));
            for (String str : fc.getConfigurationSection("Spawns").getKeys(false)) {
                a.addSpawn(new Location(Bukkit.getWorld(fc.getString("Name") + "_temp")
                        , fc.getInt("Spawns." + str + ".X"), fc.getInt("Spawns." + str + ".Y"), fc.getInt("Spawns." + str + ".Z")));
            }
            a.setLobby(new Location(a.getArenaWorld(), fc.getInt("Lobby.X"),fc.getInt("Lobby.Y"), fc.getInt("Lobby.Z")));



            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void copyFileStructure(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void fixSpawns(Arena arena){
        World world = Bukkit.getWorld(arena.getName().toLowerCase());
        ArrayList<Location> locations = arena.getSpawns();
        for (Location location : locations) {
            location.setWorld(world);
        }

        arena.setWorld(world);
        arena.setSpawns(locations);

        Location lobby = arena.getLobby();
        lobby.setWorld(world);
        arena.setLobby(lobby);
    }
}
