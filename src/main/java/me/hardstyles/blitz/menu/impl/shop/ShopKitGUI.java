package me.hardstyles.blitz.menu.impl.shop;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.kit.KitUtils;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopKitGUI {

    public static void openGUI(Player p, boolean isBasic) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        MenuContainer gui = new MenuContainer(ChatColor.DARK_GRAY + "Basic Kit Upgrades", 4);
        int firstItem = 11;
        for (Kit kit : BlitzSG.getInstance().getKitManager().getKits()) {
            if ((isBasic && kit.getPrice(0) != 0) || (!isBasic && kit.getPrice(0) == 0)) {
                continue;
            }
            ItemStack icon = new ItemBuilder(kit.getIcon()).name(KitUtils.getName(iPlayer, kit)).lores(KitUtils.getFullDescription(iPlayer, kit)).make();
            MenuItem item = new MenuItem(icon, e -> {
                if (iPlayer.isInGame()) {
                    return;
                }
                if (iPlayer.getKitLevel(kit) == 0 && e.getInventory().getName() == "§8Basic Kit Upgrades") {
                    if (iPlayer.getCoins() < kit.getPrice(iPlayer.getKitLevel(kit) + 1)) {
                        p.sendMessage("§cYou don't have enough coins to purchase this upgrade!");
                        return;
                    }
                    p.sendMessage(ChatColor.GOLD + "You purchased " + ChatColor.GREEN + kit.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(kit) + 2) + "");
                    p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

                    iPlayer.removeCoins(kit.getPrice(iPlayer.getKitLevel(kit) + 1));
                    iPlayer.setKitLevel(kit, iPlayer.getKitLevel(kit) + 2);
                    p.closeInventory();
                    return;
                }
                if (iPlayer.getKitLevel(kit) == 0 && kit.getRequiredRank().getPosition() <= iPlayer.getRank().getPosition()) {
                    p.sendMessage(ChatColor.GOLD + "You unlocked the " + ChatColor.GREEN + kit.getName() + ChatColor.GOLD + " kit!");
                    p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

                    iPlayer.setKitLevel(kit, 1);
                    p.closeInventory();
                    return;
                }
                if (kit.getPrice(iPlayer.getKitLevel(kit)) == -1) {
                    p.sendMessage("§cYou have already unlocked that level!");
                    return;
                }
                if (iPlayer.getCoins() < kit.getPrice(iPlayer.getKitLevel(kit))) {
                    p.sendMessage("§cYou don't have enough coins!");
                    return;
                }
                p.sendMessage(ChatColor.GOLD + "You purchased " + ChatColor.GREEN + kit.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(kit) + 1) + ChatColor.GOLD + "!");
                p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

                iPlayer.removeCoins(kit.getPrice(iPlayer.getKitLevel(kit)));
                iPlayer.setKitLevel(kit, iPlayer.getKitLevel(kit) + 1);
                if (iPlayer.getKitLevel(kit) == 10) {
                    Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getWorld() == p.getWorld()).forEach(player -> {
                        player.sendMessage(ChatUtil.color(iPlayer.getRank(true).getPrefix() + p.getName() + " &6has unlocked &a" + kit.getName() + " X&6!"));
                        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
                    });
                }
                p.closeInventory();
            });


            if (firstItem == 16 || firstItem == 25 || firstItem == 34) {
                firstItem = firstItem + 4;
            }

            gui.setItem(firstItem, item);


            firstItem++;
        }

        MenuItem back = new MenuItem(new ItemBuilder(new ItemStack(Material.ARROW)).name("&aBack").make(), e -> ShopGUI.openGUI(p));
        gui.setItem(gui.getBottomLeft(), back);

        gui.show(p);
    }

}