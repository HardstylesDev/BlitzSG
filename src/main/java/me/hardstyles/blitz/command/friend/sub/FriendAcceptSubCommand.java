package me.hardstyles.blitz.command.friend.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FriendAcceptSubCommand extends SubCommand {

    public FriendAcceptSubCommand() {
        super("accept", ImmutableList.of(), null, "/friend accept <player>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        }
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.color("&cOnly players can use this command!"));
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatUtil.color("&cUsage: /friend accept <player>"));
            return;
        }

        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            p.sendMessage(ChatUtil.color("&cThat player is not online!"));
            sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));

            return;
        }

        IPlayer targetPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());

        if (!iPlayer.getFriendRequests().contains(target.getUniqueId())) {
            p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            p.sendMessage(ChatUtil.color("&cYou do not have a friend request from &e" + targetPlayer.getRank().getPrefix() + target.getName() + "&c!"));
            p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            return;
        }

        iPlayer.getFriendRequests().remove(target.getUniqueId());
        iPlayer.getFriends().add(target.getUniqueId());
        targetPlayer.getFriends().add(p.getUniqueId());

        BlitzSG.getInstance().getDb().addFriend(p.getUniqueId(), target.getUniqueId());

        p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        p.sendMessage(ChatUtil.color("&e" + targetPlayer.getRank().getPrefix() + target.getName() + " &ais now on your friends list!"));
        p.sendMessage(ChatUtil.color("&9&m-------------------------------"));


        target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        target.sendMessage(ChatUtil.color("&e" + iPlayer.getRank().getPrefix() + p.getName() + " &eis now on your friends list!"));
        target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
    }
}
