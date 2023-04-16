package me.hardstyles.blitz.utils;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.rank.ranks.Admin;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(BlitzSG.getInstance().getRankManager().getRank((Player) sender) instanceof Admin)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GRAY + "Teleport to a world '/world tp <name>'");
            sender.sendMessage(ChatColor.GRAY + "Generate new empty world '/world new <name>'");
            sender.sendMessage(ChatColor.GRAY + "List all worlds '/world list'");
            return true;
        } else if (args[0].equalsIgnoreCase("new")) {
            new WorldCreator(args[1]).generator(new VoidGenerator()).createWorld();
            sender.sendMessage("&aWorld generated! Type '&7/world tp " + args[1] + "&a'");
            return true;
        } else if (args[0].equalsIgnoreCase("tp")) {
            World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                sender.sendMessage("&cLooks like that world doesn't exist!");

                return true;
            }
            ((Player) sender).teleport(new Location(world, 0.5, 100, 0.5));
            sender.sendMessage("&aTeleporting you to '&7" + args[1] + "&a'!");
            return true;
        } else if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage("&7&m----------------------------------------");
            //Worlds.worlds.forEach((o, aBoolean) -> sender.sendMessage(o + " : " + aBoolean));

            Bukkit.getWorlds().stream().forEach(str ->
                    sender.sendMessage(true ? ("&7- &a" + str.getName() + " &e" + str.getLoadedChunks().length + " chunk(s)" + " &6" + str.getEntities().size() + " entities &c" + str.getPlayers().size() + " player(s)") : ("&7- " + str.getName())));

            // Worlds.worlds.forEach((o, aBoolean) -> {
            //     if (!aBoolean)
            //         sender.sendMessage(sendColor.format("&7- " + o + " (not loaded)"));
            // });

            sender.sendMessage("&7----------------------------------------");
            return true;
        }
        if (args[0].equalsIgnoreCase("load")) {
            World world = Bukkit.getWorld(args[1]);
            if (world != null) {
                sender.sendMessage("&aWorld is already loaded!");
                return true;
            }
            new WorldCreator(args[1]).generator(new VoidGenerator()).createWorld();
            sender.sendMessage("World loaded succesfully! Type /world tp " + args[1]);
            return true;
        }
        if (args[0].equalsIgnoreCase("unload")) {
            if (Bukkit.getWorld(args[1]) == null) {
                sender.sendMessage("&cIt appears that world doesn't exist!");
                return true;
            }
            Bukkit.getWorld(args[1]).getPlayers().stream().forEach(player -> player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 50, 0.5)));
            Bukkit.getServer().unloadWorld(args[1], true);
            sender.sendMessage("World unloaded!");
            return true;
        }
        ((Player) sender).chat("/world");
        return true;
    }
}