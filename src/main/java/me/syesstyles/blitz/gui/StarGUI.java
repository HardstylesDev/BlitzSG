package me.syesstyles.blitz.gui;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.ItemBuilder;
import me.syesstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class StarGUI {

    public static void openGUI(Player p) {
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 27, "§8Star Selector");

        ArrayList<Star> stars = BlitzSG.getInstance().getStarManager().getStars();
        int index = 0;
        for (Star star : stars) {
            inv.setItem(index, new ItemBuilder(star.getIcon()).name(ChatColor.GOLD + star.getName()).lore(ChatColor.GRAY + star.getDescription()).make());
            index++;
        }
        //Open the GUI
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }
}
