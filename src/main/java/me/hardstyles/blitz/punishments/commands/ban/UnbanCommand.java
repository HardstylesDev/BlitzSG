package me.hardstyles.blitz.punishments.commands.ban;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnbanCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public UnbanCommand() {
        super("unban", ImmutableList.of(), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
            if(!(p.getRank().canBan())) {
                sender.sendMessage(ChatUtil.color("&cYou do not have permission to use this command!"));
                return;
            }
        }


        if (args.length == 0) {
            help(sender);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtil.color("&cThat player could not be found!"));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
            try (Connection connection = BlitzSG.getInstance().getDb().getConnection();
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM bans WHERE uuid = ?")) {
                    statement.setString(1, target.getUniqueId().toString());
                    statement.executeUpdate();
                    sender.sendMessage(ChatUtil.color("&aSuccessfully unbanned " + target.getName() + "!"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.color("&cUsage: /unban <player>"));
    }
}