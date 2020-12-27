package me.syesstyles.blitz.arena;

import java.util.ArrayList;
import java.util.Random;

public class ArenaManager {

	public ArrayList<Arena> arenas;
	
	public ArenaManager() {
		arenas = new ArrayList<Arena>();
	}
	
	public Arena getArena(String arenaName) {
		for(Arena a : arenas)
			if(a.getName().equalsIgnoreCase(arenaName))
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
		if(arenas.size() == 0)
			return null;
		Arena a = arenas.get(new Random().nextInt(arenas.size()));
		if(!a.isInUse() && a.isUsable())
			return a;
		for(Arena aa : arenas) {
			if(!aa.isInUse() && aa.isUsable()) 
				return aa;
		}
		return null;
	}
	
}
