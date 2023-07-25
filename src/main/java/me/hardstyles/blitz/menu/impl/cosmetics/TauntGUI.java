package me.hardstyles.blitz.menu.impl.cosmetics;


import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.menu.impl.shop.ShopGUI;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TauntGUI {


    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        MenuContainer gui = new MenuContainer(ChatUtil.color("&8Taunts"), 4);
        int firstItem = 10;

        for (Taunt aura : BlitzSG.getInstance().getCosmeticsManager().getTaunts()) {
            ItemStack icon = new ItemBuilder(aura.getIcon()).name(ChatColor.GOLD + aura.getName()).lores(getFullDescription(iPlayer, aura)).make();
            MenuItem menuItem = new MenuItem(icon, e -> {
                e.setCancelled(true);
                if (iPlayer.isInGame()) {
                    return;
                }
                if (iPlayer.getRank().getPosition() < aura.getRequiredRank().getPosition()) {
                    p.sendMessage("§cYou must be " + aura.getRequiredRank().getRankFormatted() + " §cor higher to use that!");
                    return;
                }
                if (iPlayer.getRank().getPosition() == 0 && aura.getRequiredRank().getPosition() == 0 && iPlayer.getTaunt() == null) {
                    if (iPlayer.getCoins() < 2000) {
                        p.sendMessage("§cYou don't have enough coins to buy this!");
                        return;
                    }
                    iPlayer.removeCoins(2000);
                    iPlayer.setTaunt(aura);
                    p.sendMessage("§aYou unlocked the default taunt!");

                    return;
                }
                p.sendMessage(ChatColor.GREEN + "You selected the " + ChatColor.GOLD + aura.getName() + " Taunt" + ChatColor.GREEN + "!");
                p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

                iPlayer.setTaunt(aura);
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


    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Taunt p) {
        ArrayList<String> desc = new ArrayList<>();
        desc.add(ChatColor.GRAY + p.getDescription());
        desc.add("");
        if (iPlayer.getTaunt() == p) {
            desc.add("§aSELECTED!");
            return desc;
        } else if (iPlayer.getTaunt() == null && iPlayer.getRank().getPosition() == 0) {
            desc.add("§7Price: §62,000");
            return desc;
        }
        if (p.getRequiredRank().getPosition() == 0)
            desc.add("§7Available for everyone");

        else
            desc.add(p.getRequiredRank().getRankFormatted() + " §7Exclusive");
        return desc;
    }


}
