package me.syesstyles.blitz.map;

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

public class MapManager {

    public ArrayList<Map> maps;

    public MapManager() {
        maps = new ArrayList<Map>();
    }

    public Map getArena(String arenaName) {
        for (Map a : maps)
            if (a.getName().equalsIgnoreCase(arenaName))
                return a;
        return null;
    }

    public void addArena(Map map) {
        maps.add(map);
    }

    public void removeArena(Map map) {
        maps.remove(map);
    }

    public ArrayList<Map> getArenas() {
        return this.maps;
    }

    public Map getRandomArena() {
        if (maps.size() == 0)
            return null;
        Map a = maps.get(new Random().nextInt(maps.size()));
        if (!a.isInUse() && a.isUsable())
            return a;
        for (Map aa : maps) {
            if (!aa.isInUse() && aa.isUsable())
                return aa;
        }
        return null;
    }


    public boolean deleteMap(String map) {
        return deleteMap(getArena(map));
    }

    public boolean deleteMap(Map map) {
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

    public void unloadMap(Map map) {
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
            world.setThundering(false);
            world.setStorm(false);
            for (Entity entity : world.getEntities()) {
                entity.remove();
            }
            Location cornerMin = new Location(Bukkit.getWorld(fc.getString("Name") + "_temp"), fc.getInt("Bounds.Min.X")
                    , fc.getInt("Bounds.Min.Y"), fc.getInt("Bounds.Min.Z"));
            Location cornerMax = new Location(Bukkit.getWorld(fc.getString("Name") + "_temp"), fc.getInt("Bounds.Max.X")
                    , fc.getInt("Bounds.Max.Y"), fc.getInt("Bounds.Max.Z"));

            Map a = new Map(cornerMin, cornerMax, fc.getString("Name"));
            for (String str : fc.getConfigurationSection("Spawns").getKeys(false)) {
                a.addSpawn(new Location(Bukkit.getWorld(fc.getString("Name") + "_temp")
                        , fc.getInt("Spawns." + str + ".X"), fc.getInt("Spawns." + str + ".Y"), fc.getInt("Spawns." + str + ".Z")));
            }
            a.setLobby(new Location(a.getArenaWorld(), fc.getInt("Lobby.X"), fc.getInt("Lobby.Y"), fc.getInt("Lobby.Z")));
            if (fc.getInt("Deathmatch.Y") != 0) {
                a.setDeathmatch(new Location(a.getArenaWorld(), fc.getInt("Deathmatch.X"), fc.getInt("Deathmatch.Y"), fc.getInt("Deathmatch.Z")));
                if (fc.getInt("Deathmatch.Distance") != 0) {
                    a.setDeathmatchDistance(fc.getInt("Deathmatch.Distance"));
                }
            }


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

    public void fixSpawns(Map map) {
        World world = Bukkit.getWorld(map.getName().toLowerCase());
        ArrayList<Location> locations = map.getSpawns();
        for (Location location : locations) {
            location.setWorld(world);
        }

        map.setWorld(world);
        map.setSpawns(locations);

        Location lobby = map.getLobby();
        lobby.setWorld(world);
        map.setLobby(lobby);

        try {
            Location deathmatch = map.getDeathmatch();
            deathmatch.setWorld(world);
            map.setDeathmatch(deathmatch);
        }catch (NullPointerException ignored){}
    }

    public String getRandom(){
        File dir = new File(BlitzSG.getInstance().getDataFolder() + "/arenas/");
        System.out.println(BlitzSG.getInstance().getDataFolder() + "/arenas/");

        File[] files = dir.listFiles();
        System.out.println("amount of files: "  + files.length);
        Random rand = new Random();

        File file = files[rand.nextInt(files.length)];
        String t = file.getName().replaceAll(".yml", "");
        System.out.println(t);
        return t;
    }
}
