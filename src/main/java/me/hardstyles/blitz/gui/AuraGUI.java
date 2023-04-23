package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.cosmetic.Aura;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class AuraGUI {

    public static int get(int a) {
        if (a >= 1 && a <= 7) {
            return a + 9;
        }
        if (a >= 8 && a <= 14) {
            return a + 11;
        } else return a + 13;
    }

    public static void openGUI(Player p) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, (int) (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Auras");

        //Add Items
        int firstItem = 10;

        Set<Aura> auras = BlitzSG.getInstance().getCosmeticsManager().getAuras();
        for (Aura aura : BlitzSG.getInstance().getCosmeticsManager().getAuras()) {

            inv.setItem(get(aura.getLocation()), ItemUtils.buildItem(new ItemStack(aura.getIcon())
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


    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Aura p) {
        ArrayList<String> desc = new ArrayList<String>();
        desc.add(ChatColor.GRAY + p.getDescription());
        desc.add("");
        if (iPlayer.getAura() == p) {
            desc.add("§aSELECTED!");
            return desc;
        }
        desc.add(p.getRequiredRank().getRankFormatted() + " §7Exclusive");

        return desc;
    }


}
