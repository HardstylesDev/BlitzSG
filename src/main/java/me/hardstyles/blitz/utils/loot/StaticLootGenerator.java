package me.hardstyles.blitz.utils.loot;

import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class StaticLootGenerator {

    HashSet<StaticLoot> chests;

    public StaticLootGenerator() {
        chests = new HashSet<>();

        /*
         *   StaticLoot(String worldName, int x, int y, int z, ItemStack itemStack)
         *   Please add any static loot chests that you know of to this list.
         *   The world name is the name of the world that the chest is in, in lowercase.
         *  The x, y, and z are the coordinates of the chest.
         *  The itemstack is the item that will be in the chest.
         *
         *  Example:
         *  chests.add(new StaticLoot("winter", -2385, 15, 663, new ItemBuilder(Material.IRON_CHESTPLATE).make()));
         *  Don't hesitate to make a pull request if you know of any static loot chests,
         *  Even if you don't know how to code, you can still & I will fix any errors.
         */

        chests.add(new StaticLoot("winter", -2385, 15, 663, new ItemBuilder(Material.IRON_CHESTPLATE).make())); // Winter Iron Chestplate chest
    }

    public ItemStack isStatic(Location location) {
        for (StaticLoot chest : chests) {
            boolean worldMatch = location.getWorld().getName().contains(chest.getWorldName());
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
