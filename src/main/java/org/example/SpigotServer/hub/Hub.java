package org.example.SpigotServer.hub;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.example.SpigotServer.utils.sendColor;

public class Hub implements Listener {

    public static boolean isInHub(Player p) {
        if (p.getWorld().getName().equalsIgnoreCase("world"))
            return true;
        return false;
    }

    @EventHandler
    public void join(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (isInHub((Player) e.getEntity()))
                e.setCancelled(true);
        }
    }

    public static void resetPlayer(Player p){
        p.setHealth(20);
        p.setSaturation(20);
        p.setFoodLevel(20);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setFireTicks(0);
        p.getActivePotionEffects().stream().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
        p.sendTitle("","");
        p.getInventory().clear();
        p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
        p.setFallDistance(0);
        p.setWalkSpeed(0.2F);
        p.setExp(0);
        p.setLevel(0);
    }


}
