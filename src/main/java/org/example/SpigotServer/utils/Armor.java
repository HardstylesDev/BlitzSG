package org.example.SpigotServer.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Armor {

    public static void equip(Player p, ItemStack i, Enchantment e, int lvl){
        i.addEnchantment(e, lvl);
        if(i.getType().name().contains("_HELMET"))
            p.getInventory().setHelmet(i);
        if(i.getType().name().contains("_CHESTPLATE"))
            p.getInventory().setLeggings(i);
        if(i.getType().name().contains("_LEGGINGS"))
            p.getInventory().setLeggings(i);
        if(i.getType().name().contains("_BOOTS"))
            p.getInventory().setBoots(i);
    }
}
