package me.syesstyles.blitz.kit;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitManager {
	
	public ArrayList<Kit> kits;
	
	public KitManager() {
		this.kits = new ArrayList<Kit>();
		loadKits();
	}
	
	private void loadKits() {
		this.kits.add(new Kit("Bomberman", Material.TNT
				, Arrays.asList(350, 650, 950)
				, Arrays.asList(Arrays.asList("§7Start the game with 3x TNT.")
						, Arrays.asList("§7Start the game with 5x TNT.")
						, Arrays.asList("§7Start the game with 8x TNT."))
				, Arrays.asList(Arrays.asList(new ItemStack(Material.TNT, 3))
						, Arrays.asList(new ItemStack(Material.TNT, 5))
						, Arrays.asList(new ItemStack(Material.TNT, 8)))));
	}

	public ArrayList<Kit> getKits() {
		return kits;
	}

	public Kit getKit(String kitName) {
		for(Kit p : kits)
			if(kitName.contains(p.getName()))
				return p;
		return null;
	}

}
