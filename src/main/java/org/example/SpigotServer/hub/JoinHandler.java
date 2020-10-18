package org.example.SpigotServer.hub;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.example.SpigotServer.utils.NametagEdit;

public class JoinHandler implements Listener {
    @EventHandler
    public void join(final PlayerJoinEvent e) {

        Hub.resetPlayer(e.getPlayer());
        World world = Bukkit.getWorld("world");
        e.getPlayer().teleport(new Location(world, 0.5, 50, 0.5));
        e.getPlayer().getInventory().clear();
        e.getPlayer().setHealth(20);
        e.getPlayer().setSaturation(20);


        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = compass.getItemMeta();


        compassMeta.setDisplayName(ChatColor.YELLOW + "§aGame Selector §7(right-click)");
        compass.setItemMeta(compassMeta);

        e.getPlayer().getInventory().setItem(4, compass);
        NametagEdit nametagEdit = new NametagEdit("Test", "Hardstyles", "eeee");
        nametagEdit.sendToPlayer(e.getPlayer());
        nametagEdit.updateAll();


    }


}