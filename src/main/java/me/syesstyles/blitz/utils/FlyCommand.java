package me.syesstyles.blitz.utils;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.rank.ranks.Default;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if ((BlitzSG.getInstance().getRankManager().getRank((Player) sender) instanceof Default)) {
            BlitzSG.send((Player) sender, "&cThis command requires " + BlitzSG.getInstance().getRankManager().getRankByName("vip").getRankFormatted() + " &cor higher!");
            return true;
        }
        Player p = (Player) sender;
        p.setAllowFlight(!p.getAllowFlight());
        BlitzSG.send(p, "&aYou have " + (p.getAllowFlight() ? "enabled" : "disabled") + " flight");
        return true;
    }
}