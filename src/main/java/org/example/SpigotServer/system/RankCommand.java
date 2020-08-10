package org.example.SpigotServer.system;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        {

            if (args[0].equalsIgnoreCase("police"))
            {
                sender.sendMessage("police");
                RankManager.setRank((Player) sender, "police");
                return false;
            } else if (args[0].equalsIgnoreCase("none"))
            {
                sender.sendMessage("none");
                RankManager.setRank((Player) sender, null);
                return false;
            }

            return true;
        }
    }
}