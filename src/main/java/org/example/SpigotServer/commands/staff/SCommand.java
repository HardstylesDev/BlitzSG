package org.example.SpigotServer.commands.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player) sender;
        String message = "";
        for (String part : args) {
            if (message != "") message += " ";
            message += part;
        }
        p.chat(message);
        return true;
    }
}