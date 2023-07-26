package me.hardstyles.blitz.punishments.commands.mute;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

public class UnmuteCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public UnmuteCommand() {
        super("unmute", ImmutableList.of(), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        }
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
            if (!p.getRank().isStaff()) {
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
            try {
                BlitzSG.getInstance().getDb().removeMute(target.getUniqueId());
                sender.sendMessage(ChatUtil.color("&aSuccessfully unmuted " + target.getName() + "!"));
                if (target.isOnline()) {
                    IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
                    target.getPlayer().sendMessage(ChatUtil.color("&aYour previous mute has been revoked!"));
                    p.setMute(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.color("&cUsage: /unmute <player>"));
    }
}
