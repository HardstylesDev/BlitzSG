package me.syesstyles.blitz.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.perk.Perk;
import me.syesstyles.blitz.perk.PerkUtils;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.utils.ItemUtils;

public class ShopGUI {
	
	public static void openGUI(Player p) {
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		
		//Create GUI
		Inventory inv = Bukkit.createInventory(null, (int)(((BlitzSG.getInstance().getPerkManager().getPerks().size()+1)/9)+3) * 9 + 9, "�8SpeedUHC Shop");
		
		//Add Items
		int firstItem = 10;
		for(Perk perk : BlitzSG.getInstance().getPerkManager().getPerks()) {
			inv.setItem(firstItem, ItemUtils.buildItem(new ItemStack(perk.getIcon())
					, getName(uhcPlayer, perk)
					, getFullDescription(uhcPlayer, perk)));
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
	
	public static String getName(BlitzSGPlayer uhcPlayer, Perk p) {
		if(p.getPrice(uhcPlayer.getPerkLevel(p)) == -1)
			return "�a" + p.getName() + PerkUtils.getPerkTag(uhcPlayer.getPerkLevel(p));
		else if(p.getPrice(uhcPlayer.getPerkLevel(p)) <= uhcPlayer.getCoins())
			return "�e" + p.getName() + PerkUtils.getPerkTag(uhcPlayer.getPerkLevel(p)+1);
		else
			return "�c" + p.getName() + PerkUtils.getPerkTag(uhcPlayer.getPerkLevel(p)+1);
	}
	
	public static ArrayList<String> getFullDescription(BlitzSGPlayer uhcPlayer, Perk p) {
		ArrayList<String> desc = new ArrayList<String>();
		for(String str : p.getDescription(uhcPlayer.getPerkLevel(p)))
			desc.add(str);
		desc.add("");
		if(p.getPrice(uhcPlayer.getPerkLevel(p)) == -1) {
			desc.add("�aMAX LEVEL!");
			return desc;
		}
		desc.add("�7Price: �6" + p.getPrice(uhcPlayer.getPerkLevel(p)));
		desc.add("");
		if(p.getPrice(uhcPlayer.getPerkLevel(p)) <= uhcPlayer.getCoins())
			desc.add("�eClick to unlock!");
		else
			desc.add("�cNot enough coins!");
		return desc;
	}
}
