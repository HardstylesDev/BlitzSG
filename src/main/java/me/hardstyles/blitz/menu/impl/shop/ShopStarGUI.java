package me.hardstyles.blitz.menu.impl.shop;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.menu.MenuContainer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
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

        MenuItem back = new MenuItem(new ItemBuilder(new ItemStack(Material.ARROW)).name("&aBack").make(), e -> ShopGUI.openGUI(p));
        gui.setItem(gui.getBottomLeft(), back);
        gui.show(p);
    }


    public static String[] getFullDescription(IPlayer iPlayer, Star p) {
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.RESET).append(p.getDescription()).append("\n\n");

        if (iPlayer.getStars().contains(p)) {
            builder.append(ChatColor.GREEN).append("UNLOCKED!");
            return builder.toString().split("\n");
        }

        if (p.getPrice() == 0) {
            builder.append(ChatColor.GREEN).append("UNLOCKED!");
        } else {
            builder.append(ChatColor.GRAY).append("Price: ")
                    .append(ChatColor.GOLD).append(NumberFormat.getNumberInstance(Locale.US).format(p.getPrice()))
                    .append("\n\n");
            if (p.getPrice() <= iPlayer.getCoins()) {
                builder.append(ChatColor.YELLOW).append("Click to unlock!");
            } else {
                builder.append(ChatColor.RED).append("Not enough coins!");
            }
        }

        return builder.toString().split("\n");
    }


}
