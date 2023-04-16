package me.hardstyles.blitz.command.rank.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.rank.Rank;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.nametag.Nametag;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RankSetSuffixCommand extends SubCommand {

    public RankSetSuffixCommand() {
        super("suffix", ImmutableList.of("suf"), "blitz.rank.prefix", "/rank suffix <prefix>");
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
        if (args.length == 1 ) {
            s.sendMessage(ChatUtil.color("&cUsage: /rank suffix <player> <rank>"));
            return;
        }
        IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(Bukkit.getPlayer(((Player) sender).getUniqueId()).getUniqueId());

        String all = "";
        for (int i = 1; i < args.length; i++) {
            all += args[i] + " ";
        }

        p.setSuffix(ChatUtil.color(all));
    }
}