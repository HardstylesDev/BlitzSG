package me.hardstyles.blitz.command.broadcast;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BroadcastCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public BroadcastCommand() {
        super("bc", ImmutableList.of("broadcast"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if(iPlayer.getRank().canBan()){
            if(args.length == 0){
                sender.sendMessage(ChatColor.RED + "Usage: /bc <message>");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String message = sb.toString();
            String finalMessage = ChatUtil.color("&6[BROADCAST]: " + iPlayer.getRank().getPrefix() + " " + player.getName() + "&f: " + message);
            Bukkit.broadcastMessage(finalMessage);
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