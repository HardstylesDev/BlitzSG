package org.example.SpigotServer.games.kitpvp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class Damage implements Listener {
    public static ArrayList<Player> fall = new ArrayList<>();

    @EventHandler
    public void fallDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player p = (Player) e.getEntity();
            if (KitPVP.isInKitPVP(p))
                if (fall.contains(p)) {
                    e.setCancelled(true);
                    fall.remove(p);
                }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if(KitPVP.isInKitPVP((Player) event.getEntity())){
            if(event.getDamager() instanceof Player){
                Player attacker = ((Player) event.getDamager());
                attacker.setExp(attacker.getExp() + 0.1F);
            }
        }
    }

    @EventHandler
    public void death(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (KitPVP.isInKitPVP(p)) {
                // if(e.getEntity().getLocation().getY() >= 80){
                //     e.setCancelled(true);
                //     return;
                // }
                if ((((Player) e.getEntity()).getHealth() - e.getFinalDamage()) <= 0) {
                    e.setCancelled(true);
                    KitPVP.death((Player) e.getEntity());
                }
            }
        }
    }
}

