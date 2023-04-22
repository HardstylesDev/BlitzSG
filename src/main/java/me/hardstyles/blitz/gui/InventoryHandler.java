package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.kit.KitUtils;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.cosmetic.Aura;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class InventoryHandler implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        BlitzSG.getInstance().getGuiManager().setInGUI((Player)
                e.getPlayer(), false);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!iPlayer.isInGame())
            if (!(iPlayer.getRank().isManager())) {
                e.setCancelled(true);
            }
        if (BlitzSG.getInstance().getGuiManager().isInGUI(p)) {
            e.setCancelled(true);
        }
        if (e.getSlot() == 39 && p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() == Material.TNT) {
            e.setCancelled(true);
            return;
        }

        if (e.getRawSlot() >= e.getInventory().getSize() || e.getRawSlot() <= -1) {
            return;
        }
        if (e.getInventory().getItem(e.getSlot()) == null) {
            return;
        }
        if (Objects.equals(e.getInventory().getName(), "§8Kit Selector")) {
            e.setCancelled(true);
            if (e.getInventory().getItem(e.getSlot()).getType() != Material.AIR) {
                if (!iPlayer.isInGame())
                    return;
                Kit kit = BlitzSG.getInstance().getKitManager().getKit(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
                if (kit == null)
                    return;

                //e.getWhoClicked().sendMessage("Selected: " + kit.getName());
                if (iPlayer.getKitLevel(kit) == 0) {
                    if (!(kit.getPrice(0) == 0) && !((kit.getPrice(0) == 125000) && iPlayer.getRank().getMultiplier() >= 2) && !((kit.getPrice(0) == 250000) && iPlayer.getRank().getMultiplier() >= 3)) {
                        BlitzSG.send((Player) e.getWhoClicked(), BlitzSG.CORE_NAME + "&cYou don't have this kit!");
                        return;
                    }
                }
                BlitzSG.send((Player) e.getWhoClicked(), BlitzSG.CORE_NAME + "&eYou have chosen the &a" + kit.getName() + " &ekit, You will get your items 60 seconds after the game starts.");
                iPlayer.setSelectedKit(kit);
            }
        } else if (e.getInventory().getName() == "§8Blitz Shop") {
            e.setCancelled(true);
            if (iPlayer.isInGame())
                return;
            if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().contains("Basic Kit"))
                ShopKitBasicGUI.openGUI(p);
            if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().contains("Auras"))
                AuraGUI.openGUI(p);
            if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().contains("Advanced Kit"))
                ShopKitAdvancedGUI.openGUI(p);
            if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().contains("Blitz Powerups"))
                ShopStarGUI.openGUI(p);
            if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().contains("Taunt"))
                TauntGUI.openGUI(p);
            return;
        } else if (e.getInventory().getName().contains("Kit Upgrades")) {
            e.setCancelled(true);
            if (iPlayer.isInGame())
                return;
            if (BlitzSG.getInstance().getKitManager().getKit(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()) == null)
                return;
            Kit kit = BlitzSG.getInstance().getKitManager().getKit(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
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
                p.sendMessage("§cYou already have this kit at max level!!");
                return;
            }
            if (iPlayer.getCoins() < kit.getPrice(iPlayer.getKitLevel(kit))) {
                p.sendMessage("§cYou don't have enough coins to purchase this upgrade!");
                return;
            }
            p.sendMessage(ChatColor.GOLD + "You purchased " + ChatColor.GREEN + kit.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(kit) + 1) + "");
            p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

            iPlayer.removeCoins(kit.getPrice(iPlayer.getKitLevel(kit)));
            iPlayer.setKitLevel(kit, iPlayer.getKitLevel(kit) + 1);
            p.closeInventory();
        } else if (e.getInventory().getName() == "§8Blitz Star Shop") {
            e.setCancelled(true);
            if (iPlayer.isInGame()) {
                return;
            }
            if (BlitzSG.getInstance().getStarManager().getStar(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()) == null) {
                return;
            }
            Star star = BlitzSG.getInstance().getStarManager().getStar(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
            if (star.getPrice() == 0) {
                p.sendMessage("§cYou already have this star unlocked!");
                return;
            }
            if (iPlayer.getCoins() < star.getPrice()) {
                p.sendMessage("§cYou don't have enough coins to purchase this star!");
                return;
            }
            if (iPlayer.getStars().contains(star)) {
                p.sendMessage("§cYou already have this star unlocked!");
                return;
            }
            p.sendMessage(ChatColor.GOLD + "You purchased star " + ChatColor.GREEN + star.getName());
            p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

            iPlayer.removeCoins(star.getPrice());
            iPlayer.addStar(star);
            p.closeInventory();
        } else if (e.getInventory().getName() == "§8Auras") {
            e.setCancelled(true);
            if (iPlayer.isInGame())
                return;
            if (BlitzSG.getInstance().getCosmeticsManager().getAuraByName(ChatColor.stripColor(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName())) == null) {
                return;
            }
            Aura aura = BlitzSG.getInstance().getCosmeticsManager().getAuraByName(ChatColor.stripColor(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()));

            if (iPlayer.getRank().getPosition() < aura.getRequiredRank().getPosition()) {
                p.sendMessage("§cYou must be " + aura.getRequiredRank().getRankFormatted() + " §cor higher to purchase that!");
                return;
            }
            p.sendMessage(ChatColor.GREEN + "You selected " + ChatColor.GOLD + aura.getName());
            p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

            iPlayer.setAura(aura);
            p.closeInventory();
        } else if (e.getInventory().getName() == "§8Taunts") {
            e.setCancelled(true);
            if (iPlayer.isInGame())
                return;
            if (BlitzSG.getInstance().getCosmeticsManager().getTauntByName(ChatColor.stripColor(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName())) == null) {
                return;
            }
            Taunt aura = BlitzSG.getInstance().getCosmeticsManager().getTauntByName(ChatColor.stripColor(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()));

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
            p.sendMessage(ChatColor.GREEN + "You selected the " + ChatColor.GOLD + aura.getName() + " Taunt " + ChatColor.GREEN + "!");
            p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

            iPlayer.setTaunt(aura);
            p.closeInventory();

        } else if (e.getInventory().getName() == "§8Spectator Menu") {
            e.setCancelled(true);
            ItemStack clickedItem = e.getInventory().getItem(e.getSlot());
            // check if item is a skull and if it is a player skull
            if (clickedItem.getType() == Material.SKULL_ITEM && clickedItem.getDurability() == 3) {
                // get the skull meta
                SkullMeta skullMeta = (SkullMeta) clickedItem.getItemMeta();
                // get the owner of the skull
                String owner = skullMeta.getOwner();
                // get the player from the owner
                Player target = Bukkit.getPlayer(owner);
                // check if the player is online
                if (target != null) {
                    // teleport the player to the target
                    p.teleport(target);
                    p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&eYou teleported to &f" + target.getName() + "&e!"));

                } else {
                    p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cThat player is no longer online!"));
                }
            }
            p.closeInventory();
        }
        if (e.getInventory().getName() == "§8Star Selector") {
            if (iPlayer.getGame().isDeathmatchStarting())
                if (iPlayer.getGame().getDeathmatchStartTime() >= 15) {
                    p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "The Blitz Star has been disabled!");
                    e.setCancelled(true);
                    return;
                }
            e.setCancelled(true);
            if (!iPlayer.isInGame())
                return;
            if (BlitzSG.getInstance().getStarManager().getStar(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()) == null) {
                return;
            }
            Star star = BlitzSG.getInstance().getStarManager().getStar(ChatColor.stripColor(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName()));
            if (star.getPrice() > 0 && !(iPlayer.getStars().contains(star))) {
                BlitzSG.send(p, "&cYou don't have this star unlocked!");
                return;
            }

            // check if p has a nether star in inventory
            if (!p.getInventory().contains(Material.NETHER_STAR)) {
                p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "HELL NAWH! You need a Nether Star to use this star!");
                return;
            }

            iPlayer.getGame().msgAll(BlitzSG.CORE_NAME + iPlayer.getRank(true).getChatColor() + p.getName() + " &6BLITZ! &e" + star.getName());
            p.closeInventory();
            star.run(p);
        }

    }

}
