package me.hardstyles.blitz.utils.loot;

import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

public class StaticLootGenerator {

    HashSet<StaticLoot> chests;

    public StaticLootGenerator() {
        chests = new HashSet<>();

        /*
         *   StaticLoot(String worldName, int x, int y, int z, ItemStack item)
         *   Please add any static loot chests that you know of to this list.
         *   The world name is the name of the world that the chest is in, in lowercase.
         *  The x, y, and z are the coordinates of the chest.
         *  The itemstack is the item that will be in the chest.
         *
         *  Example:
         *             chests.add(new StaticLoot("winter", -2385, 15, 663, new ArrayList<ItemStack>() {{
         *                add(new ItemStack(Material.IRON_CHESTPLATE, 1));
         *             }})); // Winter Iron Chestplate chest
         *
         *  Don't hesitate to make a pull request if you know of any static loot chests,
         *  Even if you don't know how to code, you can still & I will fix any errors.
         */

        chests.add(new StaticLoot("winter", -2385, 15, 663, new ArrayList<ItemStack>() {{
            add(new ItemStack(Material.IRON_CHESTPLATE, 1));
        }})); // Winter Iron Chestplate chest
        chests.add(new StaticLoot("winter", -2639, 12, 783, new ArrayList<ItemStack>() {{
            add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        }})); // Winter Chain Leggings chest
        chests.add(new StaticLoot("citadel", -2341, 67, 730, new ArrayList<ItemStack>() {{
            add(new ItemStack(Material.GOLD_LEGGINGS, 1));
        }})); // Citadel Gold Leggings chest
        chests.add(new StaticLoot("citadel", -2358, 59, 694, new ArrayList<ItemStack>() {{
            add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
        }})); // Citadel Gold Leggings chest
        chests.add(new StaticLoot("docks", -2308, 94, 953, new ArrayList<ItemStack>() {{
            add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        }})); // Docks Chain Chestplate chest
        chests.add(new StaticLoot("egypt", 346, 19, 302, new ArrayList<ItemStack>() {{
            add(new ItemStack(Material.DIAMOND, 2));
        }})); // Egypt Secret Diamonds chest
        chests.add(new StaticLoot("citadel", -1494, 57, 825, new ArrayList<ItemStack>() {{
            add (new ItemStack(Material.SNOW_BALL, 2));
        }})); // Citadel Spawn Snowball chest
        chests.add(new StaticLoot("winter", -2390, 23, 664, new ArrayList<ItemStack>() {{
            add (new ItemStack(Material.LEATHER_CHESTPLATE, 1));
            add (new ItemStack(Material.LEATHER_BOOTS, 1));
            add (new ItemStack(Material.LEATHER_HELMET, 1));
        }})); // Winter Hillside Leather Gear chest

    }

    public ArrayList<ItemStack> isStatic(Location location) {
        for (StaticLoot chest : chests) {
            boolean worldMatch = location.getWorld().getName().contains(chest.getWorldName());

            // Fully aware that the statement above is potentially unsafe due to multiple maps having the same name e.g. caelum, caelum2,
            // but due to the extremely low chance of a chest being in the same location on two different maps, I am not going to fix it.

            boolean xMatch = location.getBlockX() == chest.getX();
            boolean yMatch = location.getBlockY() == chest.getY();
            boolean zMatch = location.getBlockZ() == chest.getZ();
            if (worldMatch && xMatch && yMatch && zMatch) {
                return chest.getItemStack();
            }
        }
        return null;
    }
}
