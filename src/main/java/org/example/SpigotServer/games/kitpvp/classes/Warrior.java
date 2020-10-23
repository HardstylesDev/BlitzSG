package org.example.SpigotServer.games.kitpvp.classes;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.example.SpigotServer.utils.Armor;

import java.util.List;

import static org.example.SpigotServer.games.kitpvp.KitPVP.isInKitPVP;

public class Warrior implements Listener {
    public static void receive(Player p){
        Armor.equip(p, new ItemStack(Material.DIAMOND_HELMET), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));

        ItemStack compass = new ItemStack(Material.DIAMOND_AXE, 1);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName(ChatColor.YELLOW + "§aAxe of Perun");
        compass.setItemMeta(compassMeta);

        p.getInventory().addItem(compass);

    }
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {

        Player p = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() != null && event.getItem().getType() == Material.IRON_SWORD) {
                if(event.getPlayer().getExp() >= 1){
                    event.getPlayer().setExp(0);
                    event.getPlayer().setLevel(0);
                    ability(event.getPlayer());
                }
            }
        }
    }

    public static void ability(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 0));
        p.setHealth(p.getHealth() < 14 ? (p.getHealth() + 6) : 20);
        p.playSound(p.getLocation(), "mob.guardian.curse", 1F, 1.3F);
        List<Entity> nearby = p.getNearbyEntities(6, 5, 6);
        for(Entity near : nearby){
            if(near instanceof Player){
                ((Player)near).playSound(p.getLocation(), "mob.guardian.curse", 1F, 1.3F);
            }
        }
        for (int i = 0; i < 500; i++) {
            p.getWorld().playEffect(p.getLocation(), Effect.CLOUD, 1, 999);
        }
    }
}
