package me.syesstyles.blitz.gui;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ShopGUI {

    public static void openGUI(Player p) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, (int) (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Blitz Shop");

        //Add Items

            inv.setItem(11, ItemUtils.buildItem(new ItemStack(Material.IRON_INGOT)
                    , ChatColor.GREEN + "Basic Kits"
                    , Arrays.asList(ChatColor.GRAY + "Unlock and upgrade your kits.")));
        inv.setItem(13, ItemUtils.buildItem(new ItemStack(Material.NETHER_STAR)
                , ChatColor.GREEN + "Blitz Powerups"
                , Arrays.asList(ChatColor.GRAY + "Unlock Blitz powerups to change the gameplay.")));
        inv.setItem(15, ItemUtils.buildItem(new ItemStack(Material.GOLD_INGOT)
                , ChatColor.GREEN + "Advanced Kits"
                , Arrays.asList(ChatColor.GRAY + "Unlock and upgrade your kits.")));

        inv.setItem(13+9+9, ItemUtils.buildItem(new ItemStack(Material.BLAZE_POWDER)
                , ChatColor.GREEN + "Auras"
                , Arrays.asList(ChatColor.GRAY + "Cosmetic particle auras that make you stand out!")));
        inv.setItem(13+9+7, ItemUtils.buildItem(new ItemStack(Material.MAGMA_CREAM)
                , ChatColor.GREEN + "Taunts"
                , Arrays.asList(ChatColor.GRAY + "Unlock and customize the Taunt ability")));


        //Open the GUI
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }
}
