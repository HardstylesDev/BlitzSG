package me.hardstyles.blitz.map;


import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.BlitzSG;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        // Runtime game instances are already staged with a single active world folder.
        this.mapId = "world";
        this.mapName = mapName;
        this.spawns = new ArrayList<>();

    }

    public void destroy() {
        World world = Bukkit.getWorld(this.mapId);
        if (world == null) {
            Bukkit.getLogger().log(Level.WARNING, "World " + this.mapId + "'s Map#destroy() method was called, but the world does not exist!");
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
        // The core orchestrator owns world lifecycle for runtime instances.
        // Do not unload/delete "world" from inside the plugin.
        if (!"world".equalsIgnoreCase(this.mapId)) {
            File worldFolder = world.getWorldFolder();
            if (worldFolder == null || !worldFolder.exists()) {
                Bukkit.getLogger().log(Level.WARNING, "World folder for world " + this.mapId + " does not exist!");
                return;
            }
            Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> Bukkit.unloadWorld(this.mapId, false), 20L);
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
    }

    public void copy() {
        // No-op in runtime mode: world content is already staged by blitz-core.
    }

    public void load() {
        this.world = Bukkit.getWorld(this.mapId);
        if (this.world == null) {
            this.world = new WorldCreator(this.mapId).type(WorldType.NORMAL).createWorld();
        }
        this.world.setKeepSpawnInMemory(false);
    }
}
