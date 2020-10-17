package org.example.SpigotServer.commands.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.SpigotServer.utils.sendColor;

public class FlySpeedCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        Player p = (Player) sender;
        if(args.length == 0 ){
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &cPlease enter a number!"));
            return true;
        }
        if (Double.parseDouble(args[0]) < 0 || Double.parseDouble(args[0]) > 10) {
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &cPlease enter a number between 0 and 10!"));
            return true;
        }
        p.setFlySpeed(Float.parseFloat(args[0]) / 10);

        p.sendMessage(sendColor.format("&8[&3Noctas&8] &aFly speed set to " + Float.parseFloat(args[0]) + "!"));

        return true;
    }

}
