package me.syesstyles.blitz.arena;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;

public class ArenaUtils {
	
	public static HashMap<Player, Location> cornerLoc1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> cornerLoc2 = new HashMap<Player, Location>();
	
	public static Location getCornerLoc1(Player p) {
		return cornerLoc1.get(p);
	}
	
	public static Location setCornerLoc1(Player p, Location loc) {
		return cornerLoc1.put(p, loc);
	}
	
	public static Location getCornerLoc2(Player p) {
		return cornerLoc2.get(p);
	}
	
	public static Location setCornerLoc2(Player p, Location loc) {
		return cornerLoc2.put(p, loc);
	}
	
	public static void saveArenas() {
		for(Arena a : BlitzSG.getInstance().getArenaManager().getArenas()) {
			File f = new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + a.getName() + ".yml");
			if(!f.exists()) {
				try {
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
			
			if(fc.get("World") == null)
				fc.set("World", a.getArenaWorld().getName().replace("plugins\\SpeedUHC/worlds/", ""));
			fc.set("Name", a.getName());
			fc.set("Bounds.Min.X", a.getArenaMinCorner().getBlockX());
			fc.set("Bounds.Min.Y", a.getArenaMinCorner().getBlockY());
			fc.set("Bounds.Min.Z", a.getArenaMinCorner().getBlockZ());
			fc.set("Bounds.Max.X", a.getArenaMaxCorner().getBlockX());
			fc.set("Bounds.Max.Y", a.getArenaMaxCorner().getBlockY());
			fc.set("Bounds.Max.Z", a.getArenaMaxCorner().getBlockZ());
			for(Location loc : a.getSpawns()) {
				fc.set("Spawns." + a.getSpawns().indexOf(loc) + ".X", loc.getBlockX());
				fc.set("Spawns." + a.getSpawns().indexOf(loc) + ".Y", loc.getBlockY());
				fc.set("Spawns." + a.getSpawns().indexOf(loc) + ".Z", loc.getBlockZ());
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
		if(!new File (BlitzSG.getInstance().getDataFolder() + "/arenas").exists())
			return;
		for(File fe : new File(BlitzSG.getInstance().getDataFolder() + "/arenas").listFiles()) {
			FileConfiguration fc = new YamlConfiguration();
			try {
				fc.load(fe);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			File f = new File(BlitzSG.getInstance().getDataFolder() + "/worlds/" + fc.getString("World"));
			File f1 = new File(fc.getString("World") + "_temp");
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
			
			File session = new File(fc.getString("World") + "_temp" + "/session.lock");
	        try {
				session.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	        
			Bukkit.getServer().createWorld(new WorldCreator(fc.getString("World") + "_temp"));
			World w = Bukkit.getWorld(fc.getString("World") + "_temp");
			
			w.setAutoSave(false);
	        w.setTicksPerAnimalSpawns(1);
	        w.setTicksPerMonsterSpawns(1);
	        w.setGameRuleValue("doMobSpawning", "false");
	        w.setGameRuleValue("mobGriefing", "false");
	        w.setGameRuleValue("doFireTick", "false");
	        w.setGameRuleValue("showDeathMessages", "false");
			
			Location cornerMin = new Location(Bukkit.getWorld(fc.getString("Name") + "_temp"), fc.getInt("Bounds.Min.X")
					, fc.getInt("Bounds.Min.Y"), fc.getInt("Bounds.Min.Z"));
			Location cornerMax = new Location(Bukkit.getWorld(fc.getString("Name") + "_temp"), fc.getInt("Bounds.Max.X")
					, fc.getInt("Bounds.Max.Y"), fc.getInt("Bounds.Max.Z"));
			
			Arena a = new Arena(cornerMin, cornerMax, fc.getString("Name"));
			for(String str : fc.getConfigurationSection("Spawns").getKeys(false)) {
				a.addSpawn(new Location(Bukkit.getWorld(fc.getString("Name") + "_temp")
						, fc.getInt("Spawns." + str + ".X"), fc.getInt("Spawns." + str + ".Y"), fc.getInt("Spawns." + str + ".Z")));
			}
		}
	}

}
