package org.example.SpigotServer.commands.informative;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.example.SpigotServer.utils.sendColor;

public class PlaytimeCommand implements CommandExecutor {

    private static String getDurationString(double seconds) {

        double sec = seconds % 60;
        double minutes = seconds % 3600 / 60;
        double hours = seconds % 86400 / 3600;
        double days = seconds / 86400;
        return Math.round(Math.floor(days)) + "d, " + Math.round(Math.floor(hours)) + "h, " + Math.round(Math.floor(minutes)) + "m";
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &aYour playtime: &r" + getDurationString(p.getStatistic(Statistic.PLAY_ONE_TICK) / 20)));
            return true;
        } else {
            if (p.getServer().getPlayer(args[0]) == null) {
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &cCouldn't find that player!"));
                return true;
            } else {
                p.sendMessage(sendColor.format("&8[&3Noctas&8] &a" + p.getServer().getPlayer(args[0]).getName() + "'s playtime: &r" + getDurationString(p.getServer().getPlayer(args[0]).getStatistic(Statistic.PLAY_ONE_TICK) / 20)));
                return true;
            }
        }

    }
}