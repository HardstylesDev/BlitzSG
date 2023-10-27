package me.hardstyles.blitz.menu.impl.cosmetics;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.cosmetic.wardrobe.WardrobeMenu;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerGUI {

    private static WardrobeMenu wardrobeMenu = new WardrobeMenu();

    public static void open(Player p) {

        MenuContainer menu = new MenuContainer(ChatColor.GRAY + "Cosmetics", 3);
        MenuItem gadget = new MenuItem(new ItemBuilder(Material.CHEST).name("&aGadgets").make(), e -> GadgetGUI.open(p));
        MenuItem wardrobe = new MenuItem(new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&aWardrobe").make(), e -> wardrobeMenu.open(p));

        menu.setItem(12, gadget);
        menu.setItem(14, wardrobe);

        menu.open(p);
    }
}
