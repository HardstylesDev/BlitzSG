package org.example.SpigotServer.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.SpigotServer.worldgeneration.VoidGenerator;

public class SCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(!sender.hasPermission("admin"))
            return true;
        if(args.length == 0)
            return true;
        if(args[0].equalsIgnoreCase("new")){
            new WorldCreator(args[0]).generator(new VoidGenerator()).createWorld();
            sender.sendMessage("created!");
        }
        if(args[0].equalsIgnoreCase("tp")){
            sender.sendMessage("teleporting you");
            World world = Bukkit.getWorld(args[0]);
            ((Player)sender).teleport(new Location(world, 0.5, 50, 0.5));
        }
        return true;
    }
}