package me.hardstyles.blitz.gui.impl.game;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.gui.MenuContainer;
import me.hardstyles.blitz.gui.MenuItem;
import me.hardstyles.blitz.gui.impl.cosmetics.AuraGUI;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class KitGUI {
    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        MenuContainer gui = new MenuContainer("§8Kit Selector", 3);

        ArrayList<Kit> kits = BlitzSG.getInstance().getKitManager().getKits();
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                if (index >= kits.size()) {
                    break;
                }
                Kit kit = kits.get(index);
                MenuItem item = new MenuItem(new ItemBuilder(new ItemStack(kit.getIcon())).name("&6" + kit.getName()).lore(kit.getDescription()).make(), e -> {
                    if (!iPlayer.isInGame()) {
                        return;
                    }
                    if (iPlayer.getKitLevel(kit) == 0) {
                        if (!(kit.getPrice(0) == 0) && !((kit.getPrice(0) == 125000) && iPlayer.getRank().getMultiplier() >= 2) && !((kit.getPrice(0) == 250000) && iPlayer.getRank().getMultiplier() >= 3)) {
                            BlitzSG.send((Player) e.getWhoClicked(), BlitzSG.CORE_NAME + "&cYou don't have this kit!");
                            return;
                        }
                    }
                    BlitzSG.send((Player) e.getWhoClicked(), BlitzSG.CORE_NAME + "&eYou have chosen the &a" + kit.getName() + " &ekit, You will get your items 60 seconds after the game starts.");
                    iPlayer.setSelectedKit(kit);
                });
                gui.setItem(row * 9 + col, item);
                index++;
            }
        }
        gui.show(p);
    }
}
