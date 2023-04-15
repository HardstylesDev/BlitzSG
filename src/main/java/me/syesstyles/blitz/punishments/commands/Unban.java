package me.syesstyles.blitz.punishments.commands;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.rank.ranks.Admin;
import me.syesstyles.blitz.rank.ranks.Moderator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Unban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (!(BlitzSG.getInstance().getRankManager().getRank((Player) sender) instanceof Admin) && !(BlitzSG.getInstance().getRankManager().getRank((Player) sender) instanceof Moderator))
                return true;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        if (player == null){
            sender.sendMessage(ChatColor.RED + "Can't find that player!");
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = BlitzSG.getInstance().db().getConnection();
                    String command = String.format("DELETE FROM bans WHERE uuid = ?");
                    PreparedStatement preparedStatement = connection.prepareStatement(command);
                    preparedStatement.setString(1, player.getUniqueId().toString());
                    preparedStatement.execute();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        sender.sendMessage(ChatColor.RED + "" + player.getName() + " was unbanned.");

        return true;


    }
}
//  }
//  BlitzSGPlayer p = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(((Player) sender).getUniqueId());
//  if (args.length == 0 || args.length == 1) {

//      return true;
//  }

//  String format = args[1].substring(args[1].length() - 1, args[1].length());

//  int duration = Integer.valueOf(args[1].substring(0, args[1].length() - 1));
//  long time = 0;
//  switch (format) {
//      case "s":
//          time = duration * 1000;
//          break;
//      case "m":
//          time = duration * 1000 & 60;
//          break;
//      case "h":
//          time = duration * 1000 & 60 * 60;
//          break;
//      case "d":
//          time = duration * 1000 & 60 * 60 * 24;
//          break;
//      case "w":
//          time = duration * 1000 & 60 * 60 * 24 * 7;
//          break;
//      default:
//          sender.sendMessage(ChatColor.RED + "Please give a valid duration.");
//  }
//  sender.sendMessage("Time is " + time);


//  return true;
//}
//}
