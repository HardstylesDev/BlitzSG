package me.syesstyles.blitz.gui;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class KitGUI {

    public static void openGUI(Player p) {
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, 27, "§8Kit Selector");

        //Add Items
        //inv.setItem(13, ItemUtils.buildItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), "§eEnable Player Heads?"
        //		, Arrays.asList("§7Left-Click to vote §aTrue", "§7Right-Click to vote §cFalse"
        //		, "§7", "§7Status:", "§a" + uhcPlayer.getGame().getTrueVotes()
        //		+ " §7/ §c" + uhcPlayer.getGame().getFalseVotes() + " §8("
        //		+ uhcPlayer.getGame().getVotingPercentage() + "%)")));
        //

        ArrayList<Kit> kits = BlitzSG.getInstance().getKitManager().getKits();
        int index = 0;
        for (Kit kit : kits) {
            inv.setItem(index, ItemUtils.buildItem(kit.getIcon(), ChatColor.GOLD + kit.getName(), Arrays.asList(ChatColor.GRAY + kit.getDescription())));
            index++;
        }
        //Open the GUI
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }
}
