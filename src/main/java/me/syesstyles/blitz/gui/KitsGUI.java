package me.syesstyles.blitz.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.utils.ItemUtils;

public class KitsGUI {
	
	public static void openGUI(Player p) {
		//Create GUI
		Inventory inv = Bukkit.createInventory(null, 9, "�7Kit Selector");
		
		//Add Items
		inv.addItem(ItemUtils.buildItem(new ItemStack(Material.GOLDEN_APPLE), "�aHealer", Arrays.asList("�7Medicine for all!")));
		inv.addItem(ItemUtils.buildItem(new ItemStack(Material.DIAMOND), "�aTrader", Arrays.asList("�7From rags to riches.")));
		inv.addItem(ItemUtils.buildItem(new ItemStack(Material.GOLD_AXE), "�aLumberjack", Arrays.asList("�7Gathering wood.")));
		inv.addItem(ItemUtils.buildItem(new ItemStack(Material.IRON_HELMET), "�aArchaeologist", Arrays.asList("�7Searching for ancient treasure.")));
		inv.addItem(ItemUtils.buildItem(new ItemStack(Material.BREWING_STAND_ITEM), "�aAlchemist", Arrays.asList("�7Why don't you taste this drink?")));
		inv.addItem(ItemUtils.buildItem(new ItemStack(Material.IRON_SPADE), "�aExcavator", Arrays.asList("�7Digging down deep.")));
		
		//Open the GUI
		BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
		p.openInventory(inv);
	}

}
