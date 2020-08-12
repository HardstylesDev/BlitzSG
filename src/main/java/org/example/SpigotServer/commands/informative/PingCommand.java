package org.example.SpigotServer.commands.informative;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.example.SpigotServer.utils.sendColor;

public class PingCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &aYour ping: &r" + ((CraftPlayer) p).getHandle().ping));
            return true;
        } else {
            if (p.getServer().getPlayer(args[0]) == null) {
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &cCouldn't find that player!"));
                return true;
            } else {
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &a" + p.getServer().getPlayer(args[0]).getName() + "'s ping: &r" + ((CraftPlayer) p.getServer().getPlayer(args[0])).getHandle().ping));
                return true;
            }
        }

    }
}