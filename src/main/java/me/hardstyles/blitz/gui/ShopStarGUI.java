package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ShopStarGUI {

    public static void openGUI(Player p) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, (int) (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Blitz Star Shop");

        //Add Items
        int firstItem = 10;
        for (Star star : BlitzSG.getInstance().getStarManager().getStars()) {
            inv.setItem(firstItem, ItemUtils.buildItem(new ItemStack(star.getIcon())
                    , star.getName()
                    , getFullDescription(bsgPlayer, star)));
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


    public static ArrayList<String> getFullDescription(IPlayer uhcPlayer, Star p) {
        ArrayList<String> desc = new ArrayList<String>();
        desc.add(ChatColor.RESET + p.getDescription());
        desc.add("");
        if (uhcPlayer.getStars().contains(p)) {
            desc.add("§aUNLOCKED!");
            return desc;
        }
        desc.add("§7Price: §6" + NumberFormat.getNumberInstance(Locale.US).format(p.getPrice()));
        desc.add("");
        if (p.getPrice() <= uhcPlayer.getCoins())
            desc.add("§eClick to unlock!");
        else
            desc.add("§cNot enough coins!");
        return desc;
    }


}
