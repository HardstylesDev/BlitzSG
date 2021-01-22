package me.syesstyles.blitz.gui;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.cosmetic.Aura;
import me.syesstyles.blitz.cosmetic.Taunt;
import me.syesstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class TauntGUI {



    public static void openGUI(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, (int) (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Taunts");

        //Add Items
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


    public static ArrayList<String> getFullDescription(BlitzSGPlayer uhcPlayer, Taunt p) {
        ArrayList<String> desc = new ArrayList<String>();
        desc.add(ChatColor.GRAY + p.getDescription());
        desc.add("");
        if (uhcPlayer.getTaunt() == p) {
            desc.add("§aSELECTED!");
            return desc;
        }
        else if (uhcPlayer.getTaunt() == null && uhcPlayer.getRank().getPosition() == 0){
            desc.add("§7Price: §62,000");
            return desc;
        }
        if(p.getRequiredRank().getPosition() == 0)
            desc.add("§7Available for everyone");

        else
        desc.add(p.getRequiredRank().getRankFormatted() + " §7Exclusive");

        return desc;
    }


}
