package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class StarGUI {

    public static void openGUI(Player p) {
        IPlayer uhcPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

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
