package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

public class KitGUI {

    public static void openGUI(Player p) {
        IPlayer uhcPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§8Kit Selector");

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
