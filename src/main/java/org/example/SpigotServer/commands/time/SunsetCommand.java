package org.example.SpigotServer.commands.time;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.SpigotServer.utils.sendColor;

public class SunsetCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player) sender;
        p.setPlayerTime(12500, false);
        p.sendMessage(sendColor.format("&8[&3Noctas&8] &eTime set to SUNSET"));
        return true;
    }
}