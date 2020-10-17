package org.example.SpigotServer.games.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.example.SpigotServer.SpigotServer;
import org.example.SpigotServer.hub.Hub;



public class KitPVP implements Listener {
    public static void join(Player p){
        Hub.resetPlayer(p);
        Damage.fall.add(p);
        p.teleport(new Location(Bukkit.getWorld("kitpvp"), .5, 80, .5));
    }
    public static boolean isInKitPVP(Player p) {
        if (p.getWorld().getName().equalsIgnoreCase("kitpvp"))
            return true;
        return false;
    }
    @EventHandler
    public void fallDamage(final PlayerRespawnEvent e) {
        if(isInKitPVP(e.getPlayer())){
           death(e.getPlayer());
        }
    }
    @EventHandler
    public void fallDamage(final FoodLevelChangeEvent e) {
        if(isInKitPVP((Player) e.getEntity())){
           e.setFoodLevel(20);
        }
    }


    public static void death(Player p){
        p.sendMessage("you dead");
        p.teleport(new Location(Bukkit.getWorld("kitpvp"), .5, 80, .5));
        Hub.resetPlayer(p);
        Damage.fall.add(p);

    }
}
