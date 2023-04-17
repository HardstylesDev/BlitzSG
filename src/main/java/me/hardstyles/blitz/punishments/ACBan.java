package me.hardstyles.blitz.punishments;

import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.BlitzSG;
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
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getNick() == null || !iPlayer.getNick().isNicked())
            BlitzSG.broadcast("&8[&c&l!&8] &cRemoved " + iPlayer.getRank().getPrefix() + player.getDisplayName() + " &cfrom the server");
        else
            BlitzSG.broadcast("&8[&c&l!&8] &cRemoved " + iPlayer.getRank().getPrefix() + player.getDisplayName() + " &cfrom the server &7(nick: " + player.getName() +")");

        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = BlitzSG.getInstance().db().getConnection();
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
//  BlitzSGPlayer p = BlitzSG.getInstance().getIPlayerManager().getBsgPlayer(((Player) sender).getUniqueId());
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
