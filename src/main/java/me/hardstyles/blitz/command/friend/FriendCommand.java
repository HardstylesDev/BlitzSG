package me.hardstyles.blitz.command.friend;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.command.friend.sub.*;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public FriendCommand() {
        super("friend", ImmutableList.of("f"), null);
        subcommands.add(new FriendAddSubCommand());
        subcommands.add(new FriendRemoveSubCommand());
        subcommands.add(new FriendListSubCommand());
        subcommands.add(new FriendRequestsSubCommand());
        subcommands.add(new FriendAcceptSubCommand());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return subcommands.stream()
                    .map(SubCommand::getName)
                    .filter(name -> name.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            help(sender);
            return;
        }

        SubCommand sub = getSubCommand(args[0].toLowerCase());
        if (sub != null) {
            sub.execute(sender, args);
        } else {
            Player player = (Player) sender;
            player.performCommand("friend add " + args[0]);
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        sender.sendMessage(ChatUtil.color("&eFriend Commands:"));
        subcommands.forEach(sub -> sender.sendMessage(ChatUtil.color("&7- &e" + sub.getUsage())));
        sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
    }

    private SubCommand getSubCommand(String name) {
        return subcommands.stream()
                .filter(sub -> sub.getName().equalsIgnoreCase(name) || sub.getAliases().contains(name))
                .findFirst().orElse(null);
    }
}
