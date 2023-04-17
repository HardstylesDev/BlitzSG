package me.hardstyles.blitz.command.message;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReplyCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public ReplyCommand() {
        super("reply", ImmutableList.of("r"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length > 0) {

            IPlayer player = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
            if(player.getLastMessaged() == null) {
                sender.sendMessage(ChatUtil.color("&cYou have not messaged anyone recently!"));
                return;
            }
            Player target = Bukkit.getPlayer(player.getLastMessaged());
            if (target == null) {
                sender.sendMessage(ChatUtil.color("&cThat player is not online!"));
                return;
            }
            if (target == sender) {
                sender.sendMessage(ChatUtil.color("&cYou can't message yourself!"));
                return;
            }

            IPlayer iTarget = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }
            target.sendMessage(ChatUtil.color("&dFrom " + player.getRank().getPrefix() + sender.getName() + "&f: " + message.toString()));
            sender.sendMessage(ChatUtil.color("&dTo " + iTarget.getRank().getPrefix() + target.getName() + "&f: " + message.toString()));

            iTarget.setLastMessaged(player.getUuid());
            return;
        }

        help(sender);

    }

    private void help(CommandSender sender) {
        sender.sendMessage("&cUsage: /reply <message>");
    }

    private List<SubCommand> getAvailableSubs(CommandSender sender) {
        return subcommands.stream()
                .filter(c -> sender.hasPermission(c.getPermission()))
                .collect(Collectors.toList());
    }

    private SubCommand getSubCommand(String name) {
        String a = name.toLowerCase();
        return subcommands.stream()
                .filter(sub -> (sub.getName().equals(a) || sub.getAliases().contains(a)))
                .findFirst().orElse(null);
    }
}