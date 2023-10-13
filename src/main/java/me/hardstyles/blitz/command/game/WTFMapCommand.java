package me.hardstyles.blitz.command.game;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WTFMapCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public WTFMapCommand() {
        super("wtfmap", ImmutableList.of(), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getGame() == null) {
            sender.sendMessage(ChatUtil.color("&cYou are not in a game!"));
            return;
        }

        if (iPlayer.getGame().getMap() == null) {
            sender.sendMessage(ChatUtil.color("&cYou are not in a game!"));
            return;
        }

        sender.sendMessage(ChatUtil.color("&cThe map is " + iPlayer.getGame().getMap().getMapName() + "!"));

    }
}