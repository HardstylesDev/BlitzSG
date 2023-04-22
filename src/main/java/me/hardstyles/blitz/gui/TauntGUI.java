package me.hardstyles.blitz.gui;


import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class TauntGUI {


    public static void openGUI(Player p) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Taunts");
        int firstItem = 10;

        for (Taunt aura : BlitzSG.getInstance().getCosmeticsManager().getTaunts()) {
            inv.setItem(firstItem, ItemUtils.buildItem(new ItemStack(aura.getIcon())
                    , ChatColor.GREEN + aura.getName()
                    , getFullDescription(bsgPlayer, aura)));
            if ((firstItem + 2) % 9 == 0) {
                firstItem += 3;
                continue;
            }
            firstItem++;
        }

        //Open the GUI
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }


    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Taunt p) {
        ArrayList<String> desc = new ArrayList<>();
        desc.add(ChatColor.GRAY + p.getDescription());
        desc.add("");
        if (iPlayer.getTaunt() == p) {
            desc.add("§aSELECTED!");
            return desc;
        } else if (iPlayer.getTaunt() == null && iPlayer.getRank().getPosition() == 0) {
            desc.add("§7Price: §62,000");
            return desc;
        }
        if (p.getRequiredRank().getPosition() == 0)
            desc.add("§7Available for everyone");

        else
            desc.add(p.getRequiredRank().getRankFormatted() + " §7Exclusive");
        return desc;
    }


}
