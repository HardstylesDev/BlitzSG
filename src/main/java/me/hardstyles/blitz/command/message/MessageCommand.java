package me.hardstyles.blitz.command.message;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.command.rank.sub.RankListSubCommand;
import me.hardstyles.blitz.command.rank.sub.RankSetPrefixCommand;
import me.hardstyles.blitz.command.rank.sub.RankSetSubCommand;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public MessageCommand() {
        super("message", ImmutableList.of("msg", "tell", "w", "whisper"), null);
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
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatUtil.color("&cThat player is not online!"));
                return;
            }
            if (target == sender) {
                sender.sendMessage(ChatUtil.color("&cYou can't message yourself!"));
                return;
            }
            if (args.length > 1) {
                IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
                IPlayer iTarget = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
                StringBuilder message = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }
                target.sendMessage(ChatUtil.color("&dFrom " + iPlayer.getRank().getPrefix() + sender.getName() + "&f: " + message.toString()));
                sender.sendMessage(ChatUtil.color("&dTo " + iTarget.getRank().getPrefix() + target.getName() + "&f: " + message.toString()));
                iTarget.setLastMessaged(iPlayer.getUuid());
                return;
            }
            sender.sendMessage(ChatUtil.color("&cUsage: /message <player> <message>"));
            return;
        }

        help(sender);

    }

    private void help(CommandSender sender) {
        sender.sendMessage("&cUsage: /message <player> <message>");
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