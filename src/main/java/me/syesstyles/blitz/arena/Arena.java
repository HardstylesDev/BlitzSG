package me.syesstyles.blitz.arena;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.syesstyles.blitz.BlitzSG;

public class Arena {
	
	private String name;

	private World arenaWorld;
	private Location arenaMinCorner;
	private Location arenaMaxCorner;
	
	private ArrayList<Location> spawns;
	
	private boolean inUse;
	
	public Arena(Location corner1, Location corner2, String name) {
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
	
	public Arena(World w, String name) {
		this.name = name;
		this.inUse = false;
		this.spawns = new ArrayList<Location>();
		BlitzSG.getInstance().getArenaManager().addArena(this);
		//SpeedUHC.getInstance().getArenaManager().addArenaPreset(this);
	}
	
	public void resetArena() {
		for(Player p : this.getArenaWorld().getPlayers()) {
			p.teleport(BlitzSG.lobbySpawn);
			if(p.getWorld() != BlitzSG.lobbySpawn.getWorld())
				p.kickPlayer("�6>> �e�lArena Resetting �6<<");
		}
		new BukkitRunnable() {
			public void run() {
				Bukkit.unloadWorld(getArenaWorld(), false);
				
				//World w = Bukkit.getWorld(fc.getString("World"));
				File fo = getArenaWorld().getWorldFolder();
				fo.delete();
				
				File f = new File(BlitzSG.getInstance().getDataFolder() + "/worlds/" + name);
				File f1 = new File(name + "_temp");
				if(f1.exists())
					f1.delete();
				try {
					FileUtils.copyDirectory(f, f1);
				} catch (IOException e) {
					e.printStackTrace();
				}
				for(File f2 : f1.listFiles()) {
					if(f2.getName().contains("uid") || f2.getName().contains("session")
							|| f2.getName().contains("level") || f2.getName().contains("playerdata"))
						f2.delete();
				}
				
				File session = new File(name + "_temp" + "/session.lock");
		        try {
					session.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        try {
		            session.createNewFile();
		            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(session));
		 
		            try {
		                dataoutputstream.writeLong(System.currentTimeMillis());
		            } finally {
		                dataoutputstream.close();
		            }
		        } catch (IOException ioexception) {
		        	ioexception.printStackTrace();
		        }
				
				Bukkit.getServer().createWorld(new WorldCreator(name + "_temp"));
				World w = Bukkit.getWorld(name + "_temp");
				
				w.setAutoSave(false);
		        w.setTicksPerAnimalSpawns(1);
		        w.setTicksPerMonsterSpawns(1);
		        w.setGameRuleValue("doMobSpawning", "false");
		        w.setGameRuleValue("mobGriefing", "false");
		        w.setGameRuleValue("doFireTick", "false");
		        w.setGameRuleValue("showDeathMessages", "false");
		        setArenaWorld(w);
		        for(Location loc : spawns) {
		        	loc.setWorld(w);
		        }
		        arenaMinCorner.setWorld(w);
		        arenaMaxCorner.setWorld(w);
		        
		        setInUse(false);
			}
		}.runTaskLater(BlitzSG.getInstance(), 3);
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
				(arenaMinCorner.getBlockX() + arenaMaxCorner.getBlockX())/2
				, (arenaMinCorner.getBlockY() + arenaMaxCorner.getBlockY())/2
				, (arenaMinCorner.getBlockZ() + arenaMaxCorner.getBlockZ())/2);
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

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	public boolean isUsable() {
		if(arenaMinCorner == null && arenaMaxCorner == null && spawns.size() < 2)
			return false;
		else
			return true;
	}
	
}
