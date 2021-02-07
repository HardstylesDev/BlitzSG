package me.syesstyles.blitz.commands;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.rank.ranks.Admin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        Player p = (Player) sender;
        BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /tag <custom text>");
            return true;
        } else if (args[0].length() > 16) {
            sender.sendMessage(ChatColor.RED + "Error: tag can only be 16 characters long");
            return true;
        }
        blitzSGPlayer.setCustomTag(args[0]);
        if(args[0].equalsIgnoreCase("reset"))
            blitzSGPlayer.setCustomTag(null);
        if (blitzSGPlayer.getCustomTag() != null)
            p.setPlayerListName(blitzSGPlayer.getRank(true).getPrefix() + p.getName() + ChatColor.GRAY + " [" + blitzSGPlayer.getCustomTag() + "]");
        else
            p.setPlayerListName(blitzSGPlayer.getRank(true).getPrefix() + p.getName());


        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> BlitzSG.getInstance().getStatisticsManager().save(blitzSGPlayer));

        return true;

    }
}