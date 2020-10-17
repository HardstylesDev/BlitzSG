package org.example.SpigotServer.worldgeneration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.SpigotServer.SpigotServer;
import org.example.SpigotServer.utils.sendColor;

public class WorldCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("admin"))
            return true;
        if (args.length == 0) {
            sender.sendMessage(sendColor.format("&7Teleport to a world '&7/world tp <name>&a'"));
            sender.sendMessage(sendColor.format("&7Generate new empty world '&7/world new <name>&a'"));
            sender.sendMessage(sendColor.format("&7List all worlds '&7/world list&a'"));
            return true;
        }
        if (args[0].equalsIgnoreCase("new")) {
            new WorldCreator(args[1]).generator(new VoidGenerator()).createWorld();
            sender.sendMessage(sendColor.format("&aWorld generated! Type '&7/world tp " + args[1] + "&a'"));
            sender.sendMessage("test: " + SpigotServer.plugin.getDataFolder() + "\\worlds.json");
            Worlds.addWorld(args[1]);
            return true;
        }
        if (args[0].equalsIgnoreCase("tp")) {
            if (!Worlds.exists(args[1])) {
                sender.sendMessage(sendColor.format("&cLooks like that world doesn't exist!"));
                return true;
            }
            if(!Worlds.isLoaded(args[1]))
            {
                sender.sendMessage(sendColor.format("&cWorld exists, but is not loaded!"));
                return true;
            }
            World world = Bukkit.getWorld(args[1]);
            ((Player) sender).teleport(new Location(world, 0.5, 65, 0.5));
            sender.sendMessage(sendColor.format("&aTeleporting you to '&7" + args[1] + "&a'!"));
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(sendColor.format("&7&m----------------------------------------"));
            Worlds.worlds.forEach((o, aBoolean) -> sender.sendMessage(o + " : " + aBoolean));

            Bukkit.getWorlds().stream().forEach(str ->
                    sender.sendMessage(sendColor.format(Worlds.isLoaded(str.getName()) ? ("&7- &a" + str.getName() + " &e" + str.getLoadedChunks().length + " chunk(s)" + " &6" + str.getEntities().size() + " entities &c" + str.getPlayers().size() + " player(s)") : ("&7- " + str.getName()))));

            Worlds.worlds.forEach((o, aBoolean) -> {
                if (!aBoolean)
                    sender.sendMessage(sendColor.format("&7- " + o + " (not loaded)"));
            });

            sender.sendMessage(sendColor.format("&7&m----------------------------------------"));
            return true;
        }
        if (args[0].equalsIgnoreCase("load")) {
            if (!Worlds.exists(args[1])) {
                sender.sendMessage(sendColor.format("&cIt appears that world doesn't exist!"));
                return true;
            }
            if (Worlds.isLoaded(args[1]) || Bukkit.getWorld(args[1]) != null) {
                sender.sendMessage(sendColor.format("&aWorld is already loaded!"));
                return true;
            }
            Worlds.updateWorld(args[1], true);
            new WorldCreator(args[1]).generator(new VoidGenerator()).createWorld();
            sender.sendMessage(sendColor.format("&aWorld loaded succesfully! Type '&7/world tp \" + args[1] + \"&a'"));
            return true;
        }
        if (args[0].equalsIgnoreCase("unload")) {
            if (Bukkit.getWorld(args[1]) == null) {
                sender.sendMessage(sendColor.format("&cIt appears that world doesn't exist!"));
                return true;
            }
            if (!Worlds.isLoaded(args[1])) {
                sender.sendMessage(sendColor.format("&cWorld isn't loaded!"));
                //return false;
            }

            Bukkit.getWorld(args[1]).getPlayers().stream().forEach(player -> player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 50, 0.5)));
            Worlds.updateWorld(args[1], false);
            Bukkit.getServer().unloadWorld(args[1], true);
            sender.sendMessage(sendColor.format("&aWorld unloaded!"));
            return true;
        }
        ((Player) sender).chat("/world");
        return true;
    }
}