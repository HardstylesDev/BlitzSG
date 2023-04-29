package me.hardstyles.blitz.map;

import lombok.SneakyThrows;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.game.Game;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapManager {
    public MapManager() {

    }
    @SneakyThrows
    public void populateMap(Game g, Map map) {
        String worldName = map.getMapId();
        FileConfiguration fc = new YamlConfiguration();
        fc.load(new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + map.getMapName().toLowerCase() + ".yml"));
        System.out.println("Loading map: " + worldName);
        World world = Bukkit.getWorld(worldName.toLowerCase());
        world.setAutoSave(false);
        setGameRules(world);
        world.getEntities().forEach(Entity::remove);
        if (g.isGodGame()) {
            List<Location> existingSpawns = new ArrayList<>();
            fc.getConfigurationSection("Spawns").getKeys(false).forEach(str -> {
                Location spawn = new Location(Bukkit.getWorld(worldName), fc.getInt("Spawns." + str + ".X"), fc.getInt("Spawns." + str + ".Y"), fc.getInt("Spawns." + str + ".Z"));
                existingSpawns.add(spawn);
                map.getSpawns().add(spawn);
            });
            int spawnCount = 100;
            int existingSpawnCount = existingSpawns.size();
            if (existingSpawnCount == 0) {
                throw new RuntimeException("No existing spawns found for god game mode.");
            }
            while (map.getSpawns().size() < spawnCount) {
                Location existingSpawn = existingSpawns.get((int) (Math.random() * existingSpawnCount));
                Location newSpawn = existingSpawn.clone();
                map.getSpawns().add(newSpawn);
            }

        } else {
            fc.getConfigurationSection("Spawns").getKeys(false).forEach(str -> map.getSpawns().add(new Location(Bukkit.getWorld(worldName), fc.getInt("Spawns." + str + ".X"), fc.getInt("Spawns." + str + ".Y"), fc.getInt("Spawns." + str + ".Z"))));
        }
        map.setLobby(new Location(world, fc.getInt("Lobby.X"), fc.getInt("Lobby.Y"), fc.getInt("Lobby.Z")));
        int deathmatchY = fc.getInt("Deathmatch.Y");
        if (deathmatchY != 0) {
            map.setDeathmatch(new Location(world, fc.getInt("Deathmatch.X"), deathmatchY, fc.getInt("Deathmatch.Z")));
            int deathmatchDistance = fc.getInt("Deathmatch.Distance");
            if (deathmatchDistance != 0) {
                map.setDeatchmatchSpead(deathmatchDistance);
            }
        }
    }

    private void setGameRules(World world) {
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("doDaylightCycle", "true");
        world.setGameRuleValue("randomTickSpeed", "0");
    }

    public Map getRandomMap() {
        Map map = new Map(random());
        return map;
    }

    private String random() {
        File folder = new File(BlitzSG.getInstance().getDataFolder() + "/arenas");
        File[] listOfFiles = folder.listFiles();
        List<String> maps = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                maps.add(file.getName().replace(".yml", ""));
            }
        }
        return maps.get((int) (Math.random() * maps.size()));
    }

    public void deleteWorlds() {
        File worlds = new File("worlds");
        if (worlds.exists()) {
            for (File f : worlds.listFiles()) {
                if (!f.getName().equalsIgnoreCase("world")) {
                    try {
                        FileUtils.forceDelete(f);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Bukkit.getLogger().info("Deleted " + f.getName() + " from " + f.getAbsolutePath() + " ");
                }
            }
        }
    }
}
