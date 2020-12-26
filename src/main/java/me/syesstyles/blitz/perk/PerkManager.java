package me.syesstyles.blitz.perk;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.syesstyles.blitz.perk.Perk.PerkType;
import me.syesstyles.blitz.utils.ItemUtils;

public class PerkManager {
	
	public ArrayList<Perk> perks;
	
	public PerkManager() {
		this.perks = new ArrayList<Perk>();
		loadPerks();
	}
	
	private void loadPerks() {
		this.perks.add(new Perk("Bloodlust", Material.REDSTONE, PerkType.KILLEFFECT
				, Arrays.asList(1500, 3750, 6500)
				, Arrays.asList(Arrays.asList("�7Receive �64\u2764 �7on Kill for 5 seconds.")
						, Arrays.asList("�7Receive �64\u2764 �7on Kill for 10 seconds.")
						, Arrays.asList("�7Receive �64\u2764 �7on Kill for 15 seconds.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1))
						, Arrays.asList(new PotionEffect(PotionEffectType.ABSORPTION, 200, 1))
						, Arrays.asList(new PotionEffect(PotionEffectType.ABSORPTION, 300, 1)))
				, null));
		this.perks.add(new Perk("Rush", Material.ENDER_PEARL, PerkType.PVPSTARTEFFECT
				, Arrays.asList(1150, 2750, 4350, 8500)
				, Arrays.asList(Arrays.asList("�7Receive Speed 2 for 5 seconds", "�7when PvP enables.")
						, Arrays.asList("�7Receive Speed 2 for 7 seconds", "�7when PvP enables.")
						, Arrays.asList("�7Receive Speed 2 for 10 seconds", "�7when PvP enables.")
						, Arrays.asList("�7Receive Speed 2 for 15 seconds", "�7when PvP enables.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 100, 1))
						, Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 160, 1))
						, Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 200, 1))
						, Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 300, 1)))
				, null));
		this.perks.add(new Perk("Nutrition", Material.CAKE, PerkType.KILLEFFECT
				, Arrays.asList(500, 1250, 3000)
				, Arrays.asList(Arrays.asList("�7Receive Saturation 1 & Regeneration 1", "�7for 5 seconds on Kill.")
						, Arrays.asList("�7Receive Saturation 1 & Regeneration 1", "�7for 7 seconds on Kill.")
						, Arrays.asList("�7Receive Saturation 1 & Regeneration 1", "�7for 10 seconds on Kill.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.SATURATION, 100, 0)
						, new PotionEffect(PotionEffectType.REGENERATION, 100, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.SATURATION, 140, 0)
								, new PotionEffect(PotionEffectType.REGENERATION, 140, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.SATURATION, 200, 0)
								, new PotionEffect(PotionEffectType.REGENERATION, 200, 0)))
				, null));
		this.perks.add(new Perk("Hastey", Material.IRON_PICKAXE, PerkType.GAMESTARTEFFECT
				, Arrays.asList(550, 1250, 2000)
				, Arrays.asList(Arrays.asList("�7Receive Haste 1 for the first 15 seconds", "�7of the game.")
						, Arrays.asList("�7Receive Haste 1 for the first 25 seconds", "�7of the game.")
						, Arrays.asList("�7Receive Haste 1 for the first 35 seconds", "�7of the game.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.FAST_DIGGING, 300, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.FAST_DIGGING, 500, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.FAST_DIGGING, 700, 0)))
				, null));
		this.perks.add(new Perk("Tank", Material.IRON_CHESTPLATE, PerkType.PVPSTARTEFFECT
				, Arrays.asList(1500, 3750, 5900)
				, Arrays.asList(Arrays.asList("�7Receive Resistance 1 for 5 seconds", "�7when PvP enables.")
						, Arrays.asList("�7Receive Resistance 1 for 7 seconds", "�7when PvP enables.")
						, Arrays.asList("�7Receive Resistance 1 for 10 seconds", "�7when PvP enables.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 140, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0)))
				, null));
		this.perks.add(new Perk("Tomb Robber", Material.GOLD_INGOT, PerkType.KILLEFFECT
				, Arrays.asList(1000, 1750, 3500)
				, Arrays.asList(Arrays.asList("�7Receive 4x Gold Ingots on Kill.")
						, Arrays.asList("�7Receive 6x Gold Ingots on Kill.")
						, Arrays.asList("�7Receive 8x Gold Ingots", "�7& 1x Diamond on Kill."))
				, null
				, Arrays.asList(Arrays.asList(new ItemStack(Material.GOLD_INGOT, 4))
						, Arrays.asList(new ItemStack(Material.GOLD_INGOT, 6))
						, Arrays.asList(new ItemStack(Material.GOLD_INGOT, 4), new ItemStack(Material.DIAMOND, 1)))));
		this.perks.add(new Perk("Genius", Material.EXP_BOTTLE, PerkType.GAMESTARTEFFECT
				, Arrays.asList(450, 950, 1800)
				, Arrays.asList(Arrays.asList("�7Start the game with 3x Exp Bottles.")
						, Arrays.asList("�7Start the game with 5x Exp Bottles.")
						, Arrays.asList("�7Start the game with 8x Exp Bottles", "�7& 1x Enchantment Table."))
				, null
				, Arrays.asList(Arrays.asList(new ItemStack(Material.EXP_BOTTLE, 3))
						, Arrays.asList(new ItemStack(Material.EXP_BOTTLE, 5))
						, Arrays.asList(new ItemStack(Material.EXP_BOTTLE, 8), new ItemStack(Material.ENCHANTMENT_TABLE, 1)))));
		this.perks.add(new Perk("Essentials", Material.IRON_INGOT, PerkType.GAMESTARTEFFECT
				, Arrays.asList(700, 1150, 1750)
				, Arrays.asList(Arrays.asList("�7Start the game with 3x Iron Ingots.")
						, Arrays.asList("�7Start the game with 5x Iron Ingots", "�7& 2x Gold Ingots.")
						, Arrays.asList("�7Start the game with 7x Iron Ingots", "�7& 4x Gold Ingots."))
				, null
				, Arrays.asList(Arrays.asList(new ItemStack(Material.IRON_INGOT, 3))
						, Arrays.asList(new ItemStack(Material.IRON_INGOT, 5), new ItemStack(Material.GOLD_INGOT, 2))
						, Arrays.asList(new ItemStack(Material.IRON_INGOT, 7), new ItemStack(Material.GOLD_INGOT, 4)))));
		this.perks.add(new Perk("Marksman", Material.ARROW, PerkType.GAMESTARTEFFECT
				, Arrays.asList(1500, 2500, 4250)
				, Arrays.asList(Arrays.asList("�7Start the game with 1x Bow", "�7& 8x Arrows.")
						, Arrays.asList("�7Start the game with 1x Bow", "�7& 14x Arrows.")
						, Arrays.asList("�7Start the game with 1x Bow (Power 1)", "�7& 18x Arrows."))
				, null
				, Arrays.asList(Arrays.asList(new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 8))
						, Arrays.asList(new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 14))
						, Arrays.asList(ItemUtils.buildEnchantedItem(new ItemStack(Material.BOW), Enchantment.ARROW_DAMAGE, 1), new ItemStack(Material.ARROW, 18)))));
		this.perks.add(new Perk("Feast", Material.COOKED_BEEF, PerkType.GAMESTARTEFFECT
				, Arrays.asList(350, 550, 875)
				, Arrays.asList(Arrays.asList("�7Receive Saturation 1 for the first 20 seconds", "�7of the game.")
						, Arrays.asList("�7Receive Saturation 1 for the first 40 seconds", "�7of the game.")
						, Arrays.asList("�7Receive Saturation 1 for the first 60 seconds", "�7of the game.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.SATURATION, 400, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.SATURATION, 800, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.SATURATION, 1200, 0)))
				, null));
		this.perks.add(new Perk("Berserk", Material.BLAZE_POWDER, PerkType.KILLEFFECT
				, Arrays.asList(1750, 4250, 6750)
				, Arrays.asList(Arrays.asList("�7Receive Strength 1 for 3 seconds on Kill.")
						, Arrays.asList("�7Receive Strength 1 for 4 seconds on Kill.")
						, Arrays.asList("�7Receive Strength 1 for 5 seconds on Kill.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0)))
				, null));
		this.perks.add(new Perk("Farmer", Material.WHEAT, PerkType.GAMESTARTEFFECT
				, Arrays.asList(250, 400, 700)
				, Arrays.asList(Arrays.asList("�7Start the game with 1x Apple.")
						, Arrays.asList("�7Start the game with 2x Apples", "�7& 1x Golden Carrot.")
						, Arrays.asList("�7Start the game with 3x Apples", "�7& 2x Golden Carrots."))
				, null
				, Arrays.asList(Arrays.asList(new ItemStack(Material.APPLE))
						, Arrays.asList(new ItemStack(Material.APPLE, 2), new ItemStack(Material.GOLDEN_CARROT, 1))
						, Arrays.asList(new ItemStack(Material.APPLE, 3), new ItemStack(Material.GOLDEN_CARROT, 2)))));
		this.perks.add(new Perk("Fishing", Material.RAW_FISH, PerkType.GAMESTARTEFFECT
				, Arrays.asList(450, 900, 1450)
				, Arrays.asList(Arrays.asList("�7Start the game with 1x Fishing Rod (Lure 1).")
						, Arrays.asList("�7Start the game with 1x Fishing Rod (Lure 3)", "�7& 1x Water Bucket.")
						, Arrays.asList("�7Start the game with 1x Fishing Rod (Lure 5)", "�7& 2x Water Bucket."))
				, null
				, Arrays.asList(Arrays.asList(ItemUtils.buildEnchantedItem(new ItemStack(Material.FISHING_ROD), Enchantment.LURE, 1))
						, Arrays.asList(ItemUtils.buildEnchantedItem(new ItemStack(Material.FISHING_ROD), Enchantment.LURE, 3)
								, new ItemStack(Material.WATER_BUCKET, 1))
						, Arrays.asList(ItemUtils.buildEnchantedItem(new ItemStack(Material.FISHING_ROD), Enchantment.LURE, 5)
								, new ItemStack(Material.WATER_BUCKET, 2)))));
		this.perks.add(new Perk("Stealth", Material.IRON_BOOTS, PerkType.PVPSTARTEFFECT
				, Arrays.asList(500, 650, 850)
				, Arrays.asList(Arrays.asList("�7Receive Invisibility 1 for 10 seconds", "�7when PvP enables.")
						, Arrays.asList("�7Receive Invisibility 1 for 15 seconds", "�7when PvP enables.")
						, Arrays.asList("�7Receive Invisibility 1 for 20 seconds", "�7when PvP enables.")), 
				Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 0)))
				, null));
		this.perks.add(new Perk("Bomberman", Material.TNT, PerkType.GAMESTARTEFFECT
				, Arrays.asList(350, 650, 950)
				, Arrays.asList(Arrays.asList("�7Start the game with 3x TNT.")
						, Arrays.asList("�7Start the game with 5x TNT.")
						, Arrays.asList("�7Start the game with 8x TNT."))
				, null
				, Arrays.asList(Arrays.asList(new ItemStack(Material.TNT, 3))
						, Arrays.asList(new ItemStack(Material.TNT, 5))
						, Arrays.asList(new ItemStack(Material.TNT, 8)))));
		this.perks.add(new Perk("Pyromancer", Material.LAVA_BUCKET, PerkType.KILLEFFECT
				, Arrays.asList(450, 650, 925)
				, Arrays.asList(Arrays.asList("�7Receive Fire Resistance 1 for 10 seconds on Kill.")
						, Arrays.asList("�7Receive Fire Resistance 1 for 20 seconds on Kill.")
						, Arrays.asList("�7Receive Fire Resistance 1 for 30 seconds on Kill."))
				, Arrays.asList(Arrays.asList(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 400, 0))
						, Arrays.asList(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0)))
				, null));
	}

	public ArrayList<Perk> getPerks() {
		return perks;
	}

	public ArrayList<Perk> getGameStartPerks() {
		ArrayList<Perk> gameStartPerks = new ArrayList<Perk>();
		for(Perk p : perks)
			if(p.getPerkType() == PerkType.GAMESTARTEFFECT)
				gameStartPerks.add(p);
		return gameStartPerks;
	}

	public ArrayList<Perk> getKillPerks() {
		ArrayList<Perk> killPerks = new ArrayList<Perk>();
		for(Perk p : perks)
			if(p.getPerkType() == PerkType.KILLEFFECT)
				killPerks.add(p);
		return killPerks;
	}

	public ArrayList<Perk> getPvpStartPerks() {
		ArrayList<Perk> pvpStartPerks = new ArrayList<Perk>();
		for(Perk p : perks)
			if(p.getPerkType() == PerkType.PVPSTARTEFFECT)
				pvpStartPerks.add(p);
		return pvpStartPerks;
	}

	public Perk getPerk(String perkName) {
		for(Perk p : perks)
			if(perkName.contains(p.getName()))
				return p;
		return null;
	}

}
