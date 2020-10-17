package org.example.SpigotServer.commands.informative;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.example.SpigotServer.utils.sendColor;

import java.util.HashMap;

public class BackCommand implements CommandExecutor, Listener {
    public static HashMap<Player, Location> backloc;

    static {
        BackCommand.backloc = new HashMap<Player, Location>();
    }

    public boolean onCommand(final CommandSender cs, final Command arg1, final String arg2, final String[] args) {
        if (!(cs instanceof Player)) {
            return true;
        }
        final Player p = (Player) cs;
        if (args.length == 0) {
            if (BackCommand.backloc.containsKey(p)) {
                p.teleport((Location) BackCommand.backloc.get(p));
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &aTeleported you back to your previous location!"));
                return true;
            } else {
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &cYou don't have a previous location!"));
                return true;
            }
        }
        return true;
    }

    @EventHandler
    public void onBackDeath(final PlayerDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = e.getEntity();
        final Location loc = p.getLocation();
        BackCommand.backloc.put(p, loc);
    }

    @EventHandler
    public void onTeleport(final PlayerTeleportEvent e) {
        BackCommand.backloc.put(e.getPlayer(), e.getPlayer().getLocation());
    }
}