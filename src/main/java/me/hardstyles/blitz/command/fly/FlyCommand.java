package me.hardstyles.blitz.command.fly;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlyCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public FlyCommand() {
        super("fly", ImmutableList.of("flight", "fl", "flying"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if(!iPlayer.getRank().isVip()){
            player.sendMessage(ChatUtil.color("&cYou must be &aVIP &cto use this command."));
            return;
        }

        if(iPlayer.isInGame() || iPlayer.getGame() != null) {
            player.sendMessage(ChatUtil.color("&cYou cannot use this command while in a game."));
            return;
        }

        boolean isFlying = player.getAllowFlight();
        player.setAllowFlight(!isFlying);
        player.setFlying(!isFlying);
        player.sendMessage(ChatUtil.color("&eFlight mode has been " + (isFlying ? "&cdisabled" : "&aenabled") + "&e."));
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