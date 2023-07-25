package me.hardstyles.blitz.kit;

import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.text.NumberFormat;
import java.util.*;

public class KitUtils {

	public static String getKitTag(int level) {
		if(level == 0)
			return "";
		else if(level == 1)
			return " I";
		else if(level == 2)
			return " II";
		else if(level == 3)
			return " III";
		else if(level == 4)
			return " IV";
		else if(level == 5)
			return " V";
		else if(level == 6)
			return " VI";
		else if(level == 7)
			return " VII";
		else if(level == 8)
			return " VIII";
		else if(level == 9)
			return " IX";
		else if(level >= 10)
			return " X";
		return " ERROR";
	}

	public static String getName(IPlayer iPlayer, Kit kit) {
		int kitPrice = kit.getPrice(iPlayer.getKitLevel(kit));
		Rank playerRank = iPlayer.getRank();
		String roman = KitUtils.getKitTag(iPlayer.getKitLevel(kit));
		switch (kitPrice) {
			case -1:
				return "§c" + kit.getName() + roman;
			case 0:
				if (playerRank.getPosition() >= kit.getRequiredRank().getPosition()) {
					return "§a" + kit.getName() + roman;
				} else {
					return "§c" + kit.getName() + roman;
				}
			default:
				if (kitPrice <= iPlayer.getCoins()) {
					return "§a" + kit.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(kit) + 1);
				} else {
					return "§c" + kit.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(kit) + 1);
				}
		}
	}

	public static ArrayList<String> getFullDescription(IPlayer player, Kit kit) {
		ArrayList<String> desc = new ArrayList<>();
		int kitLevel = player.getKitLevel(kit);
		int kitPrice = kit.getPrice(kitLevel);
		Rank playerRank = player.getRank();
		Rank requiredRank = kit.getRequiredRank();

		// Add item description
		desc.add(ChatColor.GRAY + kit.getDescription());
		getItemDescription(kit, kitLevel).forEach(s -> desc.add(ChatColor.DARK_GRAY + s.replaceAll("" + ChatColor.RESET, "")));
		desc.add("");
		if(kitLevel == 0 && kitPrice > 10000) {
			if (requiredRank.getPosition() <= playerRank.getPosition()) {
				desc.add("§eClick to unlock!");
				desc.add("");
				desc.add("§7This is free because you are a " + playerRank.getRankFormatted());
			} else {
				desc.add("§7Requires " + requiredRank.getRankFormatted() + " §7or unlock it for §6" + NumberFormat.getNumberInstance(Locale.US).format(kitPrice) + " §7coins.");
			}
			return desc;
		}
		if (kitPrice == -1) {
			desc.add("§7Cost: §61,000,000");
		} else {
			desc.add("§7Cost: §6" + NumberFormat.getNumberInstance(Locale.US).format(kitPrice));
//			desc.add("");
//			if (kitPrice <= player.getCoins()) {
//				desc.add("§eClick to unlock!");
//			} else {
//				desc.add("§cNot enough coins!");
//			}
		}
		return desc;
	}

	public static List<String> getItemDescription(Kit kit, int level) {
		List<ItemStack> items = level == 10 || level == 0 ? kit.getKitItems(level) : kit.getKitItems(level + 1);
		List<String> desc = new ArrayList<>();

		for (ItemStack item : items) {
			if (item.getType() == Material.MONSTER_EGG) {
				String eggName = item.getItemMeta().getDisplayName().split(" ")[0];
				desc.add(ChatColor.RESET + "" + item.getAmount() + "x " + eggName + " Egg");
			} else if (!item.getEnchantments().isEmpty()) {
				String enchantString = getEnchantString(item);
				String formattedType = capitalizeString(item.getType().toString().replaceAll("_", " "));
				desc.add(ChatColor.RESET + formattedType + " (" + enchantString + ")");
			} else if (item.getType() == Material.POTION) {
				PotionMeta meta = (PotionMeta) item.getItemMeta();
				String potionName = capitalizeString(meta.getCustomEffects().get(0).getType().getName().toLowerCase());
				int potionLevel = meta.getCustomEffects().get(0).getAmplifier() + 1;
				desc.add(ChatColor.RESET + (item.getAmount() > 1 ? item.getAmount() + "x " : "") + potionName + KitUtils.getKitTag(Math.max(1, potionLevel)) + " Potion" + (item.getAmount() > 1 ? "s" : ""));
			} else {
				String formattedType = capitalizeString(item.getType().toString().replaceAll("_", " "));
				desc.add(ChatColor.RESET + (item.getAmount() > 1 ? item.getAmount() + "x " : "") + formattedType);
			}
		}
		return desc;
	}

	private static final Map<String, String> SPECIAL_CASES = new HashMap<>();

	static {
		SPECIAL_CASES.put("slow", "Slowness");
		SPECIAL_CASES.put("heal", "Health");
	}

	public static String capitalizeString(String string) {
		String lowercase = string.toLowerCase();
		if (SPECIAL_CASES.containsKey(lowercase)) {
			return SPECIAL_CASES.get(lowercase);
		}
		return Character.toTitleCase(lowercase.charAt(0)) + lowercase.substring(1)
				.replaceAll("chestplate", "Chestplate")
				.replaceAll("leggings", "Leggings")
				.replaceAll("sword", "Sword")
				.replaceAll("boots", "Boots")
				.replaceAll("helmet", "Helmet")
				.replaceAll("ball", "Balls")
				.replaceAll("barding", "Barding")
				.replaceAll("rod", "Rod")
				.replaceAll("beef", "Beef")
				.replaceAll("potato", "Potato")
				.replaceAll("fish", "Fish")
				.replaceAll("pickaxe", "Pickaxe")
				.replaceAll("axe", "Axe")
				.replaceAll("flesh", "Flesh")
				.replaceAll("Tnt", "TNT");
	}

	public static String getEnchantString(ItemStack itemStack) {
		Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
		if (enchantments.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			Enchantment enchantment = entry.getKey();
			int level = entry.getValue();
			String enchantName = null;
			if (enchantment == Enchantment.ARROW_KNOCKBACK) {
				enchantName = "Punch";
			} else if (enchantment == Enchantment.PROTECTION_ENVIRONMENTAL) {
				enchantName = "Protection";
			} else if (enchantment == Enchantment.PROTECTION_PROJECTILE) {
				enchantName = "Projectile Protection";
			} else if (enchantment == Enchantment.PROTECTION_FALL) {
				enchantName = "Feather Falling";
			} else if (enchantment == Enchantment.DAMAGE_ALL) {
				enchantName = "Sharpness";
			} else if (enchantment == Enchantment.DURABILITY) {
				enchantName = "Unbreaking";
			} else if (enchantment == Enchantment.KNOCKBACK) {
				enchantName = "Knockback";
			} else if (enchantment == Enchantment.PROTECTION_EXPLOSIONS) {
				enchantName = "Blast Protection";
			} else if (enchantment == Enchantment.OXYGEN) {
				enchantName = "Respiration";
			} else if (enchantment == Enchantment.PROTECTION_FIRE) {
				enchantName = "Fire Resistance";
			} else {
				enchantName = enchantment.getName();
			}
			sb.append(enchantName).append(KitUtils.getKitTag(level)).append(" ");
		}
		return sb.toString().trim();
	}
}
