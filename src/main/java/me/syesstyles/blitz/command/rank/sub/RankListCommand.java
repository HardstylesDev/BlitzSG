package me.syesstyles.blitz.command.rank.sub;


import com.google.common.collect.ImmutableList;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.command.SubCommand;
import me.syesstyles.blitz.rank.Rank;
import me.syesstyles.blitz.utils.ChatUtil;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Set;

public class RankListCommand extends SubCommand {

    public RankListCommand() {
        super("list", ImmutableList.of("l"), "blood.rank.list", "/rank list");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        StringBuilder builder = new StringBuilder("&6&lAll Ranks &7»");
        Set<Rank> ranks = BlitzSG.getInstance().getRankManager().getRanks();
        for (Rank rank : ranks) {
            builder.append(" &7- &r").append(rank.getRank() + "&7 - " + rank.getPrefix()).append("&7\n");
        }
        sender.sendMessage(ChatUtil.color("&7&m-------------------------------"));
        sender.sendMessage(ChatUtil.color(builder.toString()));
        sender.sendMessage(ChatUtil.color("&7&m-------------------------------"));
    }
}