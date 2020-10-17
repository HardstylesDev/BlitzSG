package org.example.SpigotServer.test;

import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.example.SpigotServer.utils.sendColor;

public class BuildStuff implements Listener {


    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onSignChange(final SignChangeEvent e) {
        final String[] lineArray = e.getLines();
        for (int lineArrayLength = lineArray.length, i = 0; i < lineArrayLength; ++i) {
            final String oldLine = lineArray[i];
            final String newLine = sendColor.format(oldLine);
            e.setLine(i, newLine);
        }
    }

    @EventHandler

    public void onWeather(WeatherChangeEvent e)
    {
        boolean rain = e.toWeatherState();
        if(rain)
            e.setCancelled(true);
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getPlayer() != null){
            if (!event.getPlayer().hasPermission("admin")){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(event.getPlayer() != null){
            if (!event.getPlayer().hasPermission("admin")){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockDamage(BlockDamageEvent event) {
        if(event.getPlayer() != null){
            if (!event.getPlayer().hasPermission("admin")){
                event.setCancelled(true);
            }
        }
    }

}
