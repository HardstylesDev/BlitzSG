package me.hardstyles.blitz.menu;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

@SuppressWarnings("deprecation")
public class MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {

        MenuContainer window = MenuContainer.getWindow(e.getInventory().getTitle());
        if (window != null) {
            if (e.getRawSlot() >= 54) {
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);
                return;
            }
            MenuItem item = window.getItem(e.getSlot());
            if (item != null) {
                item.invClick(e);
            }
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        MenuContainer window = MenuContainer.getWindow(e.getInventory().getTitle());
        if (window != null) {
            window.callOpen(e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        MenuContainer window = MenuContainer.getWindow(e.getInventory().getTitle());
        if (window != null) {
            window.callClosed(e);
        }
    }

    @EventHandler
    public void onInteract(InventoryInteractEvent e) {
        if (MenuContainer.getWindow(e.getInventory().getTitle()) != null) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }
}