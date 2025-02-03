package me.hardstyles.blitz.command.friend.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FriendListSubCommand extends SubCommand {

    public FriendListSubCommand() {
        super("list", ImmutableList.of("show"), null, "/friend list");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.color("&cOnly players can use this command!"));
            return;
        }

        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        if (iPlayer.getFriends().isEmpty()) {
            p.sendMessage(ChatUtil.color("&cYou have no friends added."));
            return;
        }

        p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        p.sendMessage(ChatUtil.color("&eFriends&f: "));
        for (UUID friend : iPlayer.getFriends()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(friend);
            if(offlinePlayer.isOnline()){
                IPlayer friendPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(friend);
                p.sendMessage(ChatUtil.color("&7- " + friendPlayer.getRank().getPrefix() + offlinePlayer.getName() + " &aOnline"));
            } else {
                p.sendMessage(ChatUtil.color("&7- &7" + offlinePlayer.getName() + " &cis offline."));
            }
        }
        p.sendMessage(ChatUtil.color("&9&m-------------------------------"));

    }
}
