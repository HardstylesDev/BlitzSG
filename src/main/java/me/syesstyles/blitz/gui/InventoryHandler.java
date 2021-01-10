package me.syesstyles.blitz.gui;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.kit.KitUtils;
import me.syesstyles.blitz.rank.ranks.Admin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginEvent;

public class InventoryHandler implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        BlitzSG.getInstance().getGuiManager().setInGUI((Player)
                e.getPlayer(), false);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame())
            if (!(bsgPlayer.getRank() instanceof Admin))
                e.setCancelled(true);
        if (BlitzSG.getInstance().getGuiManager().isInGUI(p))
            e.setCancelled(true);
        //if(e.getInventory().getName() != "§7Kit Selector")
        //return;

        if (e.getRawSlot() >= e.getInventory().getSize() || e.getRawSlot() <= -1)
            return;
        if (e.getInventory().getItem(e.getSlot()) == null)
            return;
        if (e.getInventory().getName() == "§8Kit Selector") {
            e.setCancelled(true);
            if (e.getInventory().getItem(e.getSlot()).getType() != Material.AIR) {
                if (!bsgPlayer.isInGame())
                    return;
                Kit kit = BlitzSG.getInstance().getKitManager().getKit(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                if (kit == null)
                    return;

                //e.getWhoClicked().sendMessage("Selected: " + kit.getName());
                if (bsgPlayer.getKitLevel(kit) == 0) {
                    System.out.println("price at 1: " + kit.getPrice(1));
                    System.out.println("price at 0: " + kit.getPrice(0));
                    if (!(kit.getPrice(0) == 0) && !((kit.getPrice(0) == 125000) && bsgPlayer.getRank().getMultiplier() >= 2) && !((kit.getPrice(0) == 250000) && bsgPlayer.getRank().getMultiplier() >= 3)) {
                        BlitzSG.send((Player) e.getWhoClicked(), BlitzSG.CORE_NAME + "&cYou don't have this kit!");
                        return;
                    }
                }
                BlitzSG.send((Player) e.getWhoClicked(), BlitzSG.CORE_NAME + "&eYou have chosen the &a" + kit.getName() + " &ekit, You will get your items 60 seconds after the game starts.");
                bsgPlayer.setSelectedKit(kit);
                //if(e.isLeftClick())
                //	bsgPlayer.getGame().setVote(p, true);
                //else if(e.isRightClick())
                //	bsgPlayer.getGame().setVote(p, false);
                //p.getOpenInventory().setItem(13, ItemUtils.buildItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), "§eEnable Player Heads?"
                //		, Arrays.asList("§7Left-Click to vote §aTrue", "§7Right-Click to vote §cFalse"
                //		, "§7", "§7Status:", "§a" + bsgPlayer.getGame().getTrueVotes()
                //		+ " §7/ §c" + bsgPlayer.getGame().getFalseVotes() + " §8("
                //				+ bsgPlayer.getGame().getVotingPercentage() + "%)")));
                //p.closeInventory();
            }
        }
        if (e.getInventory().getName() == "§8Blitz Shop") {
            e.setCancelled(true);
            if (bsgPlayer.isInGame())
                return;
            if (BlitzSG.getInstance().getKitManager().getKit(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()) == null)
                return;
            Kit kit = BlitzSG.getInstance().getKitManager().getKit(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
            if (kit.getPrice(bsgPlayer.getKitLevel(kit)) == -1) {
                p.sendMessage("§cYou already have this kit at max level!!");
                return;
            }
            if (bsgPlayer.getCoins() < kit.getPrice(bsgPlayer.getKitLevel(kit))) {
                p.sendMessage("§cYou don't have enough coins to purchase this upgrade!");
                return;
            }
            p.sendMessage(ChatColor.GOLD + "You purchased " + ChatColor.GREEN + kit.getName() + KitUtils.getKitTag(bsgPlayer.getKitLevel(kit) + 1) + "");
            p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

            bsgPlayer.removeCoins(kit.getPrice(bsgPlayer.getKitLevel(kit)));
            bsgPlayer.setKitLevel(kit, bsgPlayer.getKitLevel(kit) + 1);
            p.closeInventory();
        }
        if (e.getInventory().getName() == "§8Star Selector") {
            e.setCancelled(true);
            if (!bsgPlayer.isInGame())
                return;
            if (BlitzSG.getInstance().getStarManager().getStar(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()) == null) {
                return;
            }
            Star star = BlitzSG.getInstance().getStarManager().getStar(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
            bsgPlayer.getGame().msgAll(BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + p.getName() + " &6BLITZ! &e" + star.getName());
            star.run(p);
        }

    }

}
