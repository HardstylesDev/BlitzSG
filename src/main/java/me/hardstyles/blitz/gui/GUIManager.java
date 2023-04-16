package me.hardstyles.blitz.gui;

import java.util.HashSet;

import org.bukkit.entity.Player;

public class GUIManager {
	
	public HashSet<Player> inGUI;
	
	public GUIManager() {
		inGUI = new HashSet<Player>();
	}
	
	public void setInGUI(Player p, boolean bool) {
		if(bool) inGUI.add(p);
		else inGUI.remove(p);
	}
	
	public boolean isInGUI(Player p) {
		return inGUI.contains(p);
	}

}
