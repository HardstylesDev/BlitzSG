package me.hardstyles.blitz.gui.impl.shop;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.gui.MenuItem;
import me.hardstyles.blitz.gui.MenuContainer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ShopStarGUI {

    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        // Create GUI
        MenuContainer gui = new MenuContainer("§8Blitz Star Shop", 5);

        // Add Items
        int firstItem = 10;
        for (Star star : BlitzSG.getInstance().getStarManager().getStars()) {
            ItemStack itemStack = new ItemBuilder(star.getIcon()).name(ChatUtil.color("&a" + star.getName())).lores(getFullDescription(iPlayer, star)).make();
            MenuItem menuItem = new MenuItem(itemStack, e -> {
                e.setCancelled(true);
                if (iPlayer.isInGame()) {
                    return;
                }
                if (star.getPrice() == 0) {
                    p.sendMessage(ChatUtil.color("&cYou already have this star unlocked!"));
                    return;
                }
                if (iPlayer.getCoins() < star.getPrice()) {
                    p.sendMessage(ChatUtil.color("&cYou don't have enough coins to purchase this Blitz!"));

                    return;
                }
                if (iPlayer.getStars().contains(star)) {
                    p.sendMessage(ChatUtil.color("&cYou already have this Blitz unlocked!"));
                    return;
                }

                p.sendMessage(ChatUtil.color("&aYou have unlocked the &6" + star.getName() + "&a Blitz!"));
                p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                iPlayer.removeCoins(star.getPrice());
                iPlayer.addStar(star);
                p.closeInventory();
            });
            gui.setItem(firstItem, menuItem);
            if ((firstItem + 2) % 9 == 0) {
                firstItem += 3;
                continue;
            }
            firstItem++;
        }

        // Open the GUI
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        gui.show(p);
    }


    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Star p) {
        ArrayList<String> desc = new ArrayList<String>();
        desc.add(ChatColor.RESET + p.getDescription());
        desc.add("");
        if (iPlayer.getStars().contains(p)) {
            desc.add("§aUNLOCKED!");
            return desc;
        }
        if (p.getPrice() == 0) {
            desc.add("§aUNLOCKED!");
        } else {
            desc.add("§7Price: §6" + NumberFormat.getNumberInstance(Locale.US).format(p.getPrice()));
            desc.add("");
            if (p.getPrice() <= iPlayer.getCoins()) {
                desc.add("§eClick to unlock!");
            } else {
                desc.add("§cNot enough coins!");
            }
        }

        return desc;
    }


}
