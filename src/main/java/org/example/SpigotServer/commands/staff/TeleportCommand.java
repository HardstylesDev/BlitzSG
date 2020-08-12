package org.example.SpigotServer.commands.staff;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.SpigotServer.utils.sendColor;

public class TeleportCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        Player p = (Player) sender;
        Location location = null;
        if (args.length == 0) {
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &aUsage:"));
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &7- /" + alias + " <player>: Teleport to a player"));
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &7- /" + alias + " <player> <player>: Teleport a player to another player"));
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &7- /" + alias + " <x> <y> <z> [<p> <y>] Teleport to given coordinates"));

            return true;
        }
        if (args.length == 1) {
            if (p.getServer().getPlayer(args[0]) == null) {
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &cCouldn't find that player!"));
                return true;
            }
            p.teleport(p.getServer().getPlayer(args[0]));
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &aTeleported to " + p.getServer().getPlayer(args[0]).getDisplayName() + "."));
            return true;
        }
        if (args.length == 2) {
            if (p.getServer().getPlayer(args[1]) == null) {
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &cCouldn't find that player!"));
                return true;
            }


            p.getServer().getPlayer(args[0]).teleport(p.getServer().getPlayer(args[1]));
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &aTeleported " + p.getServer().getPlayer(args[0]).getDisplayName() + " to " + p.getServer().getPlayer(args[1]) + "'s location."));
            return true;
        }
        if (args.length == 3) {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            location = new Location(p.getWorld(), x, y, z);
            p.sendMessage(String.format(sendColor.format("&8[&3Noctas&8] &aTeleported to %s, %s, %s (%s)"), location.getX(), location.getY(), location.getZ(), location.getWorld().getName()));


        }
        if (args.length == 5) {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            float yaw = Float.parseFloat(args[3]);
            float pitch = Float.parseFloat(args[4]);
            location = new Location(p.getWorld(), x, y, z, yaw, pitch);
            p.sendMessage(String.format(sendColor.format("&8[&3Noctas&8] &aTeleported to %s, %s, %s (%s)"), location.getX(), location.getY(), location.getZ(), location.getWorld()));

        }

        p.teleport(location);
        return true;
    }
}