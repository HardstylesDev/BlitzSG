package me.hardstyles.blitz.gui.impl.shop;

import me.hardstyles.blitz.gui.MenuContainer;
import me.hardstyles.blitz.gui.MenuItem;
import me.hardstyles.blitz.gui.impl.cosmetics.AuraGUI;
import me.hardstyles.blitz.gui.impl.cosmetics.TauntGUI;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopGUI {

    public static void openGUI(Player p) {
        MenuContainer gui = new MenuContainer(ChatUtil.color("&8Blitz Shop"), 6);
        MenuItem basic = new MenuItem(new ItemBuilder(new ItemStack(Material.IRON_INGOT)).name("&aBasic Kit Upgrades").make(), e -> ShopKitGUI.openGUI(p, true));
        MenuItem advanced = new MenuItem(new ItemBuilder(new ItemStack(Material.GOLD_INGOT)).name("&aAdvanced Kit Upgrades").make(), e -> ShopKitGUI.openGUI(p, false));
        MenuItem stars = new MenuItem(new ItemBuilder(new ItemStack(Material.NETHER_STAR)).name("&aBlitz Star Shop").make(), e -> ShopStarGUI.openGUI(p));
        MenuItem taunts = new MenuItem(new ItemBuilder(new ItemStack(Material.MAGMA_CREAM)).name("&aTaunt Selector").make(), e -> TauntGUI.openGUI(p));
        MenuItem auras = new MenuItem(new ItemBuilder(new ItemStack(Material.BLAZE_POWDER)).name("&aAura Selector").make(), e -> AuraGUI.openGUI(p));

        gui.setItem(11, basic);
        gui.setItem(13, stars);
        gui.setItem(15, advanced);
        gui.setItem(30, auras);
        gui.setItem(32, taunts);

        gui.show(p);
    }
}
