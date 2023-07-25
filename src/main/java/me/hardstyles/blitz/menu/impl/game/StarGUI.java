package me.hardstyles.blitz.menu.impl.game;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.menu.impl.shop.ShopGUI;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.util.ItemBuilder;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.menu.MenuContainer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class StarGUI {

    public static void openGUI(Player p) {
        MenuContainer gui = new MenuContainer("§8Star Selector", 2);
        ArrayList<Star> stars = BlitzSG.getInstance().getStarManager().getStars();
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        int index = 0;
        for (Star star : stars) {
            ItemStack icon = new ItemBuilder(star.getIcon()).name(ChatColor.GOLD + star.getName()).lore(ChatColor.GRAY + star.getDescription()).make();
            MenuItem menuItem = new MenuItem(icon, event -> {
                if (iPlayer.getGame().isDeathmatchStarting()) {
                    if (iPlayer.getGame().getDeathmatchStartTime() >= 15) {
                        p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "The Blitz Star has been disabled!");
                        return;
                    }
                }
                if (!iPlayer.isInGame()) {
                    p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "You must be in a game to use a star!");
                    return;
                }
                if (star.getPrice() > 0 && !(iPlayer.getStars().contains(star))) {
                    p.sendMessage(ChatColor.RED + "You don't have this star unlocked!");
                    return;
                }
                if (!p.getInventory().contains(Material.NETHER_STAR)) {
                    p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "You need a Nether Star to use this star! " + ChatColor.GRAY + "(Nice try though)");
                    return;
                }

                iPlayer.getGame().message(BlitzSG.CORE_NAME + iPlayer.getRank(true).getChatColor() + p.getName() + " &6BLITZ! &e" + star.getName());

                star.run(p);
                event.setCancelled(true);
                p.closeInventory();
            });
            gui.setItem(index, 0, menuItem);
            index++;
        }
        MenuItem back = new MenuItem(new ItemBuilder(new ItemStack(Material.ARROW)).name("&aBack").make(), e -> ShopGUI.openGUI(p));
        gui.setItem(gui.getBottomLeft(), back);
        gui.show(p);

    }
}
