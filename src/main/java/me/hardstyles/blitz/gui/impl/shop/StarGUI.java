package me.hardstyles.blitz.gui.impl.shop;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.gui.MenuItem;
import me.hardstyles.blitz.gui.MenuContainer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class StarGUI {

    public static void openGUI(Player p) {
        MenuContainer gui = new MenuContainer("§8Star Selector", 3);
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
                    BlitzSG.send(p, "&cYou don't have this star unlocked!");
                    return;
                }
                if (!p.getInventory().contains(Material.NETHER_STAR)) {
                    p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "HELL NAWH! You need a Nether Star to use this star!");
                    return;
                }
                iPlayer.getGame().message(BlitzSG.CORE_NAME + iPlayer.getRank(true).getChatColor() + p.getName() + " &6BLITZ! &e" + star.getName());

                star.run(p);
                event.setCancelled(true);
                p.closeInventory();
            });
            gui.setItem(index, 1, menuItem);
            index++;
        }
        gui.show(p);

    }
}
