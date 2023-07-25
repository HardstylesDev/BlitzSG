package me.hardstyles.blitz.command.rank.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.rank.Rank;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RankInfoSubCommand extends SubCommand {

    public RankInfoSubCommand() {
        super("info", ImmutableList.of("ad"), "blitz.rank.rankad", "/rank info <player>");
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
        if(args[1] == null) {
            s.sendMessage(ChatUtil.color("&cYou must specify a player!"));
            return;
        }
        Player target = BlitzSG.getInstance().getServer().getPlayer(args[1]);
        if (target == null) {
            s.sendMessage(ChatUtil.color("&cThat player is not online!"));
            return;
        }

        ArrayList<String> messages = new ArrayList<>();
        messages.add(ChatUtil.color("&7&m----------------------------------------"));
        messages.add(ChatUtil.color("&6To get a FREE rank, follow me on GitHub!"));
        messages.add(ChatUtil.color("&6https://github.com/HardstylesDev"));
        messages.add("");
        messages.add(ChatUtil.color("&6To get some free coins, drop a star!"));
        messages.add(ChatUtil.color("&6https://github.com/HardstylesDev/BlitzSG"));
        messages.add(ChatUtil.color("&7&m----------------------------------------"));

        messages.forEach(target::sendMessage);


    }
}