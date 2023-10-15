package me.hardstyles.blitz.menu.impl.cosmetics;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GadgetGUI {
    public static void open(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        MenuContainer menu = new MenuContainer(ChatColor.GRAY + "Gadgets", 3);
        int index = 0;
        for (Gadget gadget : BlitzSG.getInstance().getCosmeticsManager().getGadgets()) {
            ItemStack item = gadget.getItem().clone();
            ItemMeta meta = item.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatUtil.color(gadget.getDescription()));
            lore.add("");
            lore.add(ChatUtil.color("&7Required Rank: " + gadget.getRank().getPrefix()));
            meta.setDisplayName(ChatUtil.color("&e" + gadget.getName()));
            meta.setLore(lore);
            item.setItemMeta(meta);
            MenuItem menuItem = new MenuItem(item, e -> {
                if(iPlayer.getRank().getPosition() < gadget.getRank().getPosition()) {
                    p.sendMessage(ChatUtil.color("&cYou do not have permission to use this gadget!"));
                    return;
                }
                iPlayer.setGadget(gadget);
                p.sendMessage(ChatUtil.color("&aYou have selected the " + gadget.getName() + " gadget!"));
                p.getInventory().setItem(6, iPlayer.getGadget().getItem());
            });
            menu.setItem(index, menuItem);
            index++;
        }
        menu.open(p);
    }
}
