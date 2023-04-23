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
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§8Kit Selector");

        ArrayList<Kit> kits = BlitzSG.getInstance().getKitManager().getKits();
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (index >= kits.size()) {
                    break;
                }
                Kit kit = kits.get(index);
                inv.setItem(row * 9 + col, ItemUtils.buildItem(kit.getIcon(), ChatColor.GOLD + kit.getName(), Arrays.asList(ChatColor.GRAY + kit.getDescription())));
                index++;
            }
        }
        // Add the remaining kit on the last row if any
        if (index < kits.size()) {
            for (int col = 0; col < 6; col++) {
                if (index >= kits.size()) {
                    break;
                }
                Kit kit = kits.get(index);
                inv.setItem(18 + col, ItemUtils.buildItem(kit.getIcon(), ChatColor.GOLD + kit.getName(), Arrays.asList(ChatColor.GRAY + kit.getDescription())));
                index++;
            }
        }
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }
}
