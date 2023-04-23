package me.hardstyles.blitz.gui;


import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {
    private Consumer<InventoryClickEvent> invClick;
    private ItemStack item;

    public MenuItem(ItemStack item, Consumer<InventoryClickEvent> toRun) {
        this.invClick = toRun;
        this.item = item;
    }

    ItemStack getBukkitItem() {
        return this.item;
    }

    void invClick(InventoryClickEvent e) {
        this.invClick.accept(e);
    }
}