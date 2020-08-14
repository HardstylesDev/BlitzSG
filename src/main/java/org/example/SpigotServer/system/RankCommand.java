package org.example.SpigotServer.system;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.example.SpigotServer.utils.NametagEdit;

public class RankCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        NametagEdit.setScoreboard();
        return true;

    }
}