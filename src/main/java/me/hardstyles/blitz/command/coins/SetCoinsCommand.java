package me.hardstyles.blitz.command.coins;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.player.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SetCoinsCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public SetCoinsCommand() {
        super("coins", ImmutableList.of("setcoins"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getRank().isManager()) {
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found.");
                    return;
                }
                IPlayer targetIPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
                int coins = 0;
                try {
                    coins = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Please enter a valid number.");
                    return;
                }
                targetIPlayer.setCoins(coins);
                sender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s coins to " + coins + ".");
                return;

            }
            sender.sendMessage(ChatColor.RED + "Usage: /coins <player> <amount>");
            return;
        }
        sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
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