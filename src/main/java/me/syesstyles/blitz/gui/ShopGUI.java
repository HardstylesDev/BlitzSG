package me.syesstyles.blitz.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.kit.KitUtils;
import me.syesstyles.blitz.utils.ItemUtils;

public class ShopGUI {
	
	public static void openGUI(Player p) {
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		
		//Create GUI
		Inventory inv = Bukkit.createInventory(null, (int)(((BlitzSG.getInstance().getKitManager().getKits().size()+1)/9)+3) * 9 + 9, "§8SpeedUHC Shop");
		
		//Add Items
		int firstItem = 10;
		for(Kit kit : BlitzSG.getInstance().getKitManager().getKits()) {
			inv.setItem(firstItem, ItemUtils.buildItem(new ItemStack(kit.getIcon())
					, getName(uhcPlayer, kit)
					, getFullDescription(uhcPlayer, kit)));
			if((firstItem+2) % 9 == 0) {
				firstItem += 3;
				continue;
			}
			firstItem++;
		}
		
		//Open the GUI
		BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
		p.openInventory(inv);
	}
	
	public static String getName(BlitzSGPlayer uhcPlayer, Kit p) {
		if(p.getPrice(uhcPlayer.getKitLevel(p)) == -1)
			return "§a" + p.getName() + KitUtils.getKitTag(uhcPlayer.getKitLevel(p));
		else if(p.getPrice(uhcPlayer.getKitLevel(p)) <= uhcPlayer.getCoins())
			return "§e" + p.getName() + KitUtils.getKitTag(uhcPlayer.getKitLevel(p)+1);
		else
			return "§c" + p.getName() + KitUtils.getKitTag(uhcPlayer.getKitLevel(p)+1);
	}
	
	public static ArrayList<String> getFullDescription(BlitzSGPlayer uhcPlayer, Kit p) {
		ArrayList<String> desc = new ArrayList<String>();
		for(String str : p.getDescription(uhcPlayer.getKitLevel(p)))
			desc.add(str);
		desc.add("");
		if(p.getPrice(uhcPlayer.getKitLevel(p)) == -1) {
			desc.add("§aMAX LEVEL!");
			return desc;
		}
		desc.add("§7Price: §6" + p.getPrice(uhcPlayer.getKitLevel(p)));
		desc.add("");
		if(p.getPrice(uhcPlayer.getKitLevel(p)) <= uhcPlayer.getCoins())
			desc.add("§eClick to unlock!");
		else
			desc.add("§cNot enough coins!");
		return desc;
	}
}
