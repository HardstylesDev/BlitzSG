package me.syesstyles.blitz.command.rank.sub;

import com.google.common.collect.ImmutableList;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.command.SubCommand;
import me.syesstyles.blitz.rank.Rank;
import me.syesstyles.blitz.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class RankSetCommand extends SubCommand {

    public RankSetCommand() {
        super("set", ImmutableList.of("setrank"), "blitz.rank.setrank", "/rank set <player> <rank>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
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