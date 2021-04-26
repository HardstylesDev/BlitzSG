package me.syesstyles.blitz.punishments;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ACBan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(player.getUniqueId());
        BlitzSG.broadcast("&7&m--------------------------------------------------");
        BlitzSG.broadcast("&c&l✗ &c&lCHEAT DETECTION");
        if (blitzSGPlayer.getNick() == null || !blitzSGPlayer.getNick().isNicked())
            BlitzSG.broadcast("&cRemoved" + blitzSGPlayer.getRank().getPrefix() + player.getDisplayName() + " &cfrom the server");
        else
            BlitzSG.broadcast("&cRemoved " + blitzSGPlayer.getRank().getPrefix() + player.getDisplayName() + " &7(" + player.getName() +") &cfrom the server");
        BlitzSG.broadcast("&7&m--------------------------------------------------");

        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = BlitzSG.getInstance().getData().getConnection();
                    String command = String.format("REPLACE INTO `bans`(`uuid`, `reason`, `expires`, `executor`) VALUES (?,?,?,?)");

                    PreparedStatement preparedStatement = connection.prepareStatement(command);

                    preparedStatement.setString(1, player.getUniqueId().toString());
                    preparedStatement.setString(2, "Unfair Advantage");
                    preparedStatement.setDouble(3, System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);
                    preparedStatement.setString(4, "AntiCheat");


                    preparedStatement.execute();

                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        });

        player.kickPlayer("Unfair Advantage");
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
