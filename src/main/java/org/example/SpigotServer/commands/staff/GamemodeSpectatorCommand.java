package org.example.SpigotServer.commands.staff;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.SpigotServer.utils.sendColor;

public class GamemodeSpectatorCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player) sender;
        p.setGameMode(GameMode.SPECTATOR);
        p.sendMessage(sendColor.format("&8[&3Noctas&8] &aGamemode set to Spectator!"));
        return true;
    }
}
