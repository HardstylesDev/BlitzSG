package me.hardstyles.blitz.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtils {

	public static ItemStack buildItem(ItemStack i, String name, List<String> lore) {
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name.replace("&", "§"));
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack buildEnchantedItem(ItemStack i, Enchantment ench, int level) {
		ItemMeta im = i.getItemMeta();
		im.addEnchant(ench, level, true);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack buildPotion(PotionEffectType effectType, int durationTicks, int potionLevel, short durability, int amount) {
		ItemStack p = new ItemStack(Material.POTION);
		PotionMeta pm = (PotionMeta) p.getItemMeta();
		pm.addCustomEffect(new PotionEffect(effectType, durationTicks, potionLevel-1), false);
		p.setItemMeta(pm);
		p.setDurability(durability);
		p.setAmount(amount);
		return p;
	}

	public static ItemStack buildPotion(PotionEffect[] potions, short durability) {
		ItemStack p = new ItemStack(Material.POTION);
		PotionMeta pm = (PotionMeta) p.getItemMeta();
		for (PotionEffect potion : potions) {
			pm.addCustomEffect(potion, false);
		}
		p.setItemMeta(pm);
		p.setDurability(durability);
		return p;
	}


}
