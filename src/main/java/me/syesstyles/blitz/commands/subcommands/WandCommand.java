package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		ItemStack br = new ItemStack(Material.BLAZE_ROD);
		ItemMeta brm = br.getItemMeta();
		brm.setDisplayName("§eArena Wand");
		br.setItemMeta(brm);
		p.getInventory().addItem(br);
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc wand";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
