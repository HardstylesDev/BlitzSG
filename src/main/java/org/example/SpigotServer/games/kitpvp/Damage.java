package org.example.SpigotServer.games.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.example.SpigotServer.utils.sendColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Damage implements Listener {
    public static ArrayList<Player> fall = new ArrayList<>();
    public static HashMap<Player, Long> perun = new HashMap<>();
    public static HashMap<Player, Player> lastDamager = new HashMap<>();

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
                lastDamager.put((Player) event.getEntity(),(Player) event.getDamager());
                Player attacker = ((Player) event.getDamager());
                if(attacker.getInventory().getItemInHand().getItemMeta().getDisplayName().contains("Axe of Perun")){
                    if(!perun.containsKey(attacker) || (perun.containsKey(attacker) && System.currentTimeMillis() > perun.get(attacker) + 5000 )){
                        event.setDamage(0);
                        if(((Player) event.getEntity()).getHealth() > 8)
                            ((Player) event.getEntity()).damage(8);
                        else
                            ((Player) event.getEntity()).damage(((Player) event.getEntity()).getHealth() - 1);
                        event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                        perun.put(attacker, System.currentTimeMillis());
                    }
                }
                //attacker.setExp(attacker.getExp() + 0.1F);
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

                    e.getEntity().getWorld().getPlayers().forEach(player -> player.sendMessage(sendColor.format("&c" + p.getName() + " &ewas killed by &c" + lastDamager.get(e.getEntity()).getName())));

                }
            }
        }
    }
}

