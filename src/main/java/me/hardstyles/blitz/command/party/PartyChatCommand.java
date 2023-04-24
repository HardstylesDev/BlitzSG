package me.hardstyles.blitz.command.party;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.command.party.sub.*;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PartyChatCommand extends Command {
    public PartyChatCommand() {
        super("pc", ImmutableList.of("partychat"), null);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getParty() == null) {
            player.sendMessage(ChatUtil.color("&cYou are not in a party!"));
            return;
        }

        if (args.length > 0) {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }
           iPlayer.getParty().message(ChatUtil.color("&9Party > " + iPlayer.getRank().getPrefix() + player.getName() + "&f: " + message.toString()));


        } else {
            sender.sendMessage(ChatUtil.color("&cUsage: /pc <message>"));
        }
    }
}