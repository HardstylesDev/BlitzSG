package me.syesstyles.blitz.perk;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Perk {

	public static enum PerkType {
		GAMESTARTEFFECT, PVPSTARTEFFECT, KILLEFFECT
	}
	
	private String name;
	private Material icon;
	private List<List<String>> levelDescriptions;
	private List<Integer> levelPrices;
	private PerkType perkType;
	
	private List<List<PotionEffect>> perkEffects;
	private List<List<ItemStack>> perkItems;
	
	public Perk(String name, Material icon, PerkType perkType, List<Integer> levelPrices, List<List<String>> levelDescriptions
			, List<List<PotionEffect>> perkEffects, List<List<ItemStack>> perkItems) {
		this.name = name;
		this.icon = icon;
		this.levelDescriptions = levelDescriptions;
		this.levelPrices = levelPrices;
		this.perkType = perkType;

		if(perkEffects == null)
			this.perkEffects = new ArrayList<List<PotionEffect>>();
		else
			this.perkEffects = perkEffects;
		if(perkItems == null)
			this.perkItems = new ArrayList<List<ItemStack>>();
		else
			this.perkItems = perkItems;
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
		if(levelPrices.size() <= level)
			return -1;
		return levelPrices.get(level);
	}

	public PerkType getPerkType() {
		return perkType;
	}

	public void givePerk(Player p, int level) {
		if(level == 0)
			return;
		if(perkEffects.size() > 0)
			for(PotionEffect pe : perkEffects.get(level-1))
				p.addPotionEffect(pe);
		if(perkItems.size() > 0)
			for(ItemStack is : perkItems.get(level-1))
				if(p.getInventory().firstEmpty() == -1)
					p.getWorld().dropItemNaturally(p.getLocation(), is.clone());
				else
					p.getInventory().addItem(is.clone());
	}

}
