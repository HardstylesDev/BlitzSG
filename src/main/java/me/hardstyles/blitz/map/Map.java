package me.hardstyles.blitz.map;


import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.EmptyWorldGenerator;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

@Getter
@Setter
public class Map {
    private String mapId, mapName;
    private Location lobby, deathmatch;
    private ArrayList<Location> spawns;
    private double deatchmatchSpead;
    private boolean inUse = false;
    private World world;

    public Map(String mapName) {
        this.mapId = mapName + "_" + generateId();
        this.mapName = mapName;
        this.spawns = new ArrayList<>();

    }

    private String generateId(){
        return UUID.randomUUID().toString().substring(0, 8) + "_hardstyles";
    }

    public void destroy() {
        World world = Bukkit.getWorld(mapId);
        if (world == null) {
            Bukkit.getLogger().log(Level.WARNING, "World " + mapId + "'s Map#destroy() method was called, but the world does not exist!");
            return;
        }
        world.getEntities().forEach(entity -> {
            if (!(entity instanceof Player)) {
                entity.remove();
            } else {
                Player player = (Player) entity;
                BlitzSG.getInstance().getIPlayerManager().toLobby(player);
            }
        });
        File worldFolder = world.getWorldFolder();
        if (worldFolder == null || !worldFolder.exists()) {
            Bukkit.getLogger().log(Level.WARNING, "World folder for world " + mapId + " does not exist!");
            return;
        }
        Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> Bukkit.unloadWorld(mapId, false), 20L);
        Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
            try {
                FileUtils.deleteDirectory(worldFolder);
                if (worldFolder.exists()) {
                    FileUtils.forceDelete(worldFolder);
                    if (worldFolder.exists()) {
                        worldFolder.delete();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 40L);
    }


    @SneakyThrows
    public void copy() {
        File source = new File("arenas/" + mapName);
        File destination = new File("worlds/" + mapId);

        Path sourceLevelDat = source.toPath().resolve("level.dat");
        Path sourceRegion = source.toPath().resolve("region");

        if (!destination.exists()) {
            destination.mkdirs();
        }

        Path levelFile = destination.toPath().resolve("level.dat");
        if(Files.exists(levelFile)) {
            Files.copy(sourceLevelDat, destination.toPath().resolve("level.dat"));
        }

        FileUtils.copyDirectory(sourceRegion.toFile(), destination.toPath().resolve("region").toFile(), file -> !file.getName().equals("playerdata") && !file.getName().equals("data") && !file.getName().equals("session.lock") && !file.getName().equals("uid.dat"));
    }

    public void load() {
        this.world = new WorldCreator(mapId).generator(new EmptyWorldGenerator()).type(WorldType.FLAT).createWorld();
        this.world.setKeepSpawnInMemory(false);
    }
}
