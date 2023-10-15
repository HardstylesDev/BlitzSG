package me.hardstyles.blitz.menu;


import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MenuContainer {
    private static Map<String, MenuContainer> windows = new HashMap<>();

    private Inventory inv;
    private Map<Integer, MenuItem> items;
    private Consumer<InventoryOpenEvent> onOpen = null;
    private Consumer<InventoryCloseEvent> onClose = null;
    private int rows;

    private boolean registered;

    public MenuContainer(String name, int rows) {
        name = getValidName(name);

        this.inv = Bukkit.createInventory(null, rows * 9, name);
        this.items = new HashMap<>(rows * 9);
        this.rows = rows;


        windows.put(name, this);

        this.registered = true;
    }

    public void setItem(int slot, MenuItem item) {
        this.items.put(slot, item);
        this.inv.setItem(slot, item.getBukkitItem());
    }

    public void setItem(int x, int y, MenuItem item) {
        setItem(x + y * 9, item);
    }

    public void addItem(MenuItem menuItem) {
        for (int i = 0; i < this.inv.getSize(); i++) {
            if (this.inv.getItem(i) == null) {
                setItem(i, menuItem);
                return;
            }
        }
    }

    public void border(MenuItem menuItem) {
        for (int i = 0; i < this.inv.getSize(); i++) {
            if (i < 9 || i > this.inv.getSize() - 9 || i % 9 == 0 || i % 9 == 8) {
                setItem(i, menuItem);
            }
        }
    }


    public MenuItem getItem(int slot) {
        return this.items.get(slot);
    }

    public MenuItem getItem(int x, int y) {
        return getItem(x * 9 + y);
    }

    public void onOpen(Consumer<InventoryOpenEvent> e) {
        this.onOpen = e;
    }

    @Deprecated
    void callOpen(InventoryOpenEvent e) {
        if (onOpen != null) onOpen.accept(e);
    }

    public void onClose(Consumer<InventoryCloseEvent> e) {
        this.onClose = e;
    }

    @Deprecated
    void callClosed(InventoryCloseEvent e) {
        if (onClose != null) onClose.accept(e);
    }

    @Deprecated
    public Inventory getBukkitInventory() {
        return this.inv;
    }

    public void show(HumanEntity h) {
        Inventory inv = Bukkit.createInventory(h, getBukkitInventory().getSize(), getBukkitInventory().getTitle());
        inv.setContents(getBukkitInventory().getContents());
        h.openInventory(inv);
    }

    public void open(HumanEntity h) {
        show(h);
    }

    public void unregister() {
        windows.remove(this.getBukkitInventory().getTitle());
        this.items.clear();
        this.registered = false;
    }

    static MenuContainer getWindow(String inv) {
        return windows.get(inv);
    }

    private String getValidName(String name) {
        if (windows.containsKey(name)) return getValidName(name + ChatColor.RESET);
        else return name;
    }

    public int getRows() {
        return rows;
    }

    public int getBottomLeft() {
        return rows * 9 - 9;
    }
}