package org.example.SpigotServer.test;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.SpigotServer.system.RankManager;

public class BuildStuff implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getPlayer() != null){
            if (!RankManager.hasRank(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(event.getPlayer() != null){
            if (!RankManager.hasRank(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockDamage(BlockDamageEvent event) {
        if(event.getPlayer() != null){
            if (!RankManager.hasRank(event.getPlayer())){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onBreak(EntityDamageByEntityEvent event) {
        if(event.getDamager().getType() == EntityType.PLAYER){
            if (!RankManager.hasRank((Player) event.getDamager())){
                event.setCancelled(true);
            }
        }
    }

}
