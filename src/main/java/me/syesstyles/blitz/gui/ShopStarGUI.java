package me.syesstyles.blitz.gui;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.kit.KitUtils;
import me.syesstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShopStarGUI {

    public static void openGUI(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, (int) (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Blitz Star Shop");

        //Add Items
        int firstItem = 10;
        for (Star star : BlitzSG.getInstance().getStarManager().getStars()) {
            inv.setItem(firstItem, ItemUtils.buildItem(new ItemStack(star.getIcon())
                    , bsgPlayer.getStars().contains(p) ? ChatColor.GREEN + star.getName() : ChatColor.YELLOW + star.getName()
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


    public static ArrayList<String> getFullDescription(BlitzSGPlayer uhcPlayer, Star p) {
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
