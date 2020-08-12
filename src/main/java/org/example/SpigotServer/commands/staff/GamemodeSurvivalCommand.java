package org.example.SpigotServer.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.SpigotServer.utils.sendColor;

public class GamemodeSurvivalCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player) sender;
        p.setGameMode(GameMode.SURVIVAL);
        p.sendMessage(sendColor.format("&8[&3Noctas&8] &aGamemode set to Survival!"));
        return true;
    }
}
