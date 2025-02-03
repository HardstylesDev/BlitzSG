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

public class FriendAddSubCommand extends SubCommand {

    public FriendAddSubCommand() {
        super("add", ImmutableList.of("invite"), null, "/friend add <player>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        sender.sendMessage("args.length: " + args.length);
        if (args.length == 1) {
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
            sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            sender.sendMessage(ChatUtil.color("&cUsage: /friend add <player>"));
            sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            return;
        }

        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null || !target.isOnline()) {
            p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            p.sendMessage(ChatUtil.color("&cThat player is not online!"));
            p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            return;
        }

        IPlayer targetPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());

        if (iPlayer.getFriends().contains(target.getUniqueId())) {
            p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            p.sendMessage(ChatUtil.color("&7" + targetPlayer.getRank().getPrefix() + target.getName() +" &cis already on your friends list!"));
            p.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            return;
        }


        if (iPlayer.getFriendRequests().contains(target.getUniqueId())) {
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





            return;
        }

        targetPlayer.addFriendRequest(p.getUniqueId());

        sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        p.sendMessage(ChatUtil.color("&aYou have sent a friend request to &7" + targetPlayer.getRank().getPrefix() + target.getName() + "&a!"));
        sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));

        target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        target.sendMessage(ChatUtil.color("&a" + iPlayer.getRank().getPrefix() + p.getName() + " &ahas sent you a friend request!"));
        target.sendMessage(ChatUtil.color("&aUse &7/friend accept " + p.getName() + " &ato accept."));
        target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
    }
}
