package me.syesstyles.blitz.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {
	
	private String name;
	private Material icon;
	private List<List<String>> levelDescriptions;
	private List<Integer> kitPrices;
	
	private List<List<ItemStack>> kitItems;
	
	public Kit(String name, Material icon, List<Integer> kitPrices, List<List<String>> levelDescriptions
			, List<List<ItemStack>> kitItems) {
		this.name = name;
		this.icon = icon;
		this.levelDescriptions = levelDescriptions;
		this.kitPrices = kitPrices;
		if(kitItems == null)
			this.kitItems = new ArrayList<List<ItemStack>>();
		else
			this.kitItems = kitItems;
	}

	public String getName() {
		return name;
	}

	public Material getIcon() {
		return icon;
	}
	
	public List<String> getDescription(int level) {
		if(levelDescriptions.size() <= level)
			return levelDescriptions.get(levelDescriptions.size()-1);
		return levelDescriptions.get(level);
	}

	public int getPrice(int level) {
		if(kitPrices.size() <= level)
			return -1;
		return kitPrices.get(level);
	}

	public void giveKit(Player p, int level) {
		if(level == 0)
			return;
		if(kitItems.size() > 0)
			for(ItemStack is : kitItems.get(level-1))
				if(p.getInventory().firstEmpty() == -1)
					p.getWorld().dropItemNaturally(p.getLocation(), is.clone());
				else
					p.getInventory().addItem(is.clone());
	}

}
