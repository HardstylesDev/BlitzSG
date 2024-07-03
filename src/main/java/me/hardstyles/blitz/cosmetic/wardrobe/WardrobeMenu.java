package me.hardstyles.blitz.cosmetic.wardrobe;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WardrobeMenu {
    private final BlitzSG blitz;

    public WardrobeMenu() {
        this.blitz = BlitzSG.getInstance();
    }


    private ArrayList<ItemStack> getArmorSet(String type) {
        ArrayList<ItemStack> armor = new ArrayList<>();
        String[] pieces = {"HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS"};
        for (String piece : pieces) {
            armor.add(new ItemBuilder(Material.valueOf(type + "_" + piece.toUpperCase())).make());
        }
        return armor;
    }

    public void open(Player p) {
        IPlayer iPlayer = blitz.getIPlayerManager().getPlayer(p.getUniqueId());
        MenuContainer gui = new MenuContainer(ChatUtil.color("&7Wardrobe"), 6);
        gui.border(new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").make(), e -> {
            e.setCancelled(true);
        }));

        if (iPlayer.getWardrobeStorage() == null) {
            iPlayer.setWardrobeStorage(new WardrobeStorage());
        }


        String[] types = {"CHAINMAIL", "IRON", "GOLD", "DIAMOND"};
        int a = 1;
        for (String type : types) {
            ArrayList<ItemStack> diamond = getArmorSet(type);
            int i = 0;
            for (ItemStack item : diamond) {
                gui.setItem(a, i + 1, new MenuItem(item, e -> {
                    iPlayer.getWardrobeStorage().add(new WardrobeItem(item, false, null));
                    p.playSound(p.getLocation(), "gui.button.press", 0.4f, 1);
                    iPlayer.getWardrobeStorage().apply(p);
                }));
                i++;
            }
            a++;
        }

        ArrayList<ItemStack> leather = getArmorSet("LEATHER");
        int index = 0;
        for (ItemStack item : leather) {
            gui.setItem(6, index + 1, new MenuItem(item, e -> {
                iPlayer.getWardrobeStorage().add(new WardrobeItem(item, false, null));
                p.playSound(p.getLocation(), "gui.button.press", 0.4f, 1);
                iPlayer.getWardrobeStorage().apply(p);
            }));
            index++;
        }
        String[] sections = new String[]{"HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS"};
        index = 0;
        for (String section : sections) {
            gui.setItem(5, index + 1, new MenuItem(new ItemBuilder(Material.BOOK).name("&aEnchant Armor").enchantment(Enchantment.DURABILITY).addItemFlag(ItemFlag.HIDE_ENCHANTS).make(), e -> {
                if (iPlayer.getWardrobeStorage().getSection(section.toLowerCase()) != null) {
                    WardrobeItem item = iPlayer.getWardrobeStorage().getSection(section.toLowerCase());
                    System.out.println("Item: " + item.getItem().getType().name());
                    item.setEnchanted(!item.isEnchanted());
                    System.out.println("Enchanted: " + item.isEnchanted());
                    p.playSound(p.getLocation(), "gui.button.press", 0.4f, 1);
                    iPlayer.getWardrobeStorage().apply(p);
                }
            }));
            gui.setItem(7, index + 1, new MenuItem(new ItemBuilder(Material.INK_SACK).durability(15).name("&aDye Armor").make(), e -> {
                MenuContainer dyeMenu = new MenuContainer(ChatUtil.color("&aDye Armor"), 3);
                dyeMenu.border(new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").make(), e1 -> {
                    e1.setCancelled(true);
                }));
                ArrayList<Color> colors = new ArrayList<>();
                for (int i = 0; i <= 256; i += 128) {
                    for (int j = 0; j <= 256; j += 128) {
                        for (int k = 0; k <= 256; k += 128) {

                            colors.add(Color.fromRGB(Math.min(i, 255), Math.min(j, 255), Math.min(k, 255)));
                        }
                    }
                }
                int i = 0;
                for (Color color : colors) {
                    dyeMenu.setItem(i, new MenuItem(new ItemBuilder(Material.valueOf("LEATHER_" + section.toUpperCase())).color(color).make(), e1 -> {
                        WardrobeItem item = new WardrobeItem(new ItemStack(Material.valueOf("LEATHER_" + section)), false, color);
                        iPlayer.getWardrobeStorage().add(item);
                        p.playSound(p.getLocation(), "gui.button.press", 0.4f, 1);
                        iPlayer.getWardrobeStorage().apply(p);
                        gui.show(p);
                    }));
                    i++;
                }
                dyeMenu.open(p);
            }));
            index++;
        }

        gui.setItem(4, 5, new MenuItem(new ItemBuilder(Material.REDSTONE_BLOCK).name("&cClose").make(), e -> {
            e.setCancelled(true);
            p.closeInventory();
        }));

        gui.setItem(8, 5, new MenuItem(new ItemBuilder(Material.WOOL).durability(14).name("&cClear").make(), e -> {
            e.setCancelled(true);
            iPlayer.getWardrobeStorage().clear();
            iPlayer.getWardrobeStorage().apply(p);
        }));

        gui.show(p);
    }
}
