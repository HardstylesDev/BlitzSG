package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ShopGUI {

    public static void openGUI(Player p) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, (int) (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Blitz Shop");
        inv.setItem(11, ItemUtils.buildItem(new ItemStack(Material.IRON_INGOT)
                , ChatColor.GREEN + "Basic Kits"
                , Arrays.asList(ChatColor.GRAY + "Unlock and upgrade your kits.")));
        inv.setItem(13, ItemUtils.buildItem(new ItemStack(Material.NETHER_STAR)
                , ChatColor.GREEN + "Blitz Powerups"
                , Arrays.asList(ChatColor.GRAY + "Unlock Blitz powerups to change the gameplay.")));
        inv.setItem(15, ItemUtils.buildItem(new ItemStack(Material.GOLD_INGOT)
                , ChatColor.GREEN + "Advanced Kits"
                , Arrays.asList(ChatColor.GRAY + "Unlock and upgrade your kits.")));

        inv.setItem(13 + 9 + 9, ItemUtils.buildItem(new ItemStack(Material.BLAZE_POWDER)
                , ChatColor.GREEN + "Auras"
                , Arrays.asList(ChatColor.GRAY + "Cosmetic particle auras that make you stand out!")));

        inv.setItem(13 + 9 + 9 + 9 + 9, ItemUtils.buildItem(new ItemStack(Material.MAGMA_CREAM, 1)
                , ChatColor.GREEN + "Taunts"
                , Arrays.asList(ChatColor.GRAY + "Unlock and customize your taunts.")));


        //Open the GUI
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }
}
