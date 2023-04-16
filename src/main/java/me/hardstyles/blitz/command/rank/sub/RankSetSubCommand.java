package me.hardstyles.blitz.command.rank.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.rank.Rank;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RankSetSubCommand extends SubCommand {

    public RankSetSubCommand() {
        super("set", ImmutableList.of("setrank"), "blitz.rank.setrank", "/rank set <player> <rank>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        } else if (args.length == 3) {
            return ImmutableList.copyOf(BlitzSG.getInstance().getRankManager().getRanks().stream().map(Rank::getRank).toArray(String[]::new));
        } else {
            return ImmutableList.of();
        }
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        if (args.length == 1 || args.length == 2) {
            s.sendMessage(ChatUtil.color("&cUsage: /rank set <player> <rank>"));
            return;
        }
        Player target = BlitzSG.getInstance().getServer().getPlayer(args[1]);
        s.sendMessage(args[1]);
        if (target == null) {
            s.sendMessage(ChatUtil.color("&cThat player is not online!"));
            return;
        }
        Rank rank = BlitzSG.getInstance().getRankManager().getRankByName(args[2]);
        if (rank == null) {
            s.sendMessage(ChatUtil.color("&cThat rank does not exist!"));
            return;
        }

        BlitzSG.getInstance().getRankManager().setRank(target, rank);
        s.sendMessage(ChatUtil.color("&7&m-------------------------------"));
        s.sendMessage(ChatUtil.color("&aYou have set " + target.getName() + "'s rank to " + rank.getRank()));
        s.sendMessage(ChatUtil.color("&7&m-------------------------------"));
        target.sendMessage(ChatUtil.color("&7&m-------------------------------"));
        target.sendMessage(ChatUtil.color("&aYour rank has been set to " + rank.getRank()));
        target.sendMessage(ChatUtil.color("&7&m-------------------------------"));
    }
}