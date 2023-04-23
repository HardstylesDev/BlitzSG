package me.hardstyles.blitz.gui;


import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
        } else if (e.getInventory().getName().contains("Kit Upgrades")) {
            e.setCancelled(true);
        }
    }

}
