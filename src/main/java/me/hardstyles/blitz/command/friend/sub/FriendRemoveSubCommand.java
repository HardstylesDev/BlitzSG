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

public class FriendRemoveSubCommand extends SubCommand {

    public FriendRemoveSubCommand() {
        super("remove", ImmutableList.of("unfriend", "delete"), null, "/friend remove <player>");
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

        if (args.length < 2) {
            sender.sendMessage(ChatUtil.color("&cUsage: /friend remove <player>"));
            return;
        }

        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        Player target = Bukkit.getPlayer(args[1]);
        UUID targetUUID;

        if (target != null && target.isOnline()) {
            targetUUID = target.getUniqueId();
        } else {
            OfflinePlayer player = BlitzSG.getInstance().getServer().getOfflinePlayer(args[1]);
            if (player != null) {
                targetUUID = player.getUniqueId();
            } else {
                target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
                p.sendMessage(ChatUtil.color("&cThat player is not online!"));
                target.sendMessage(ChatUtil.color("&9&m-------------------------------"));

                return;
            }
        }


        IPlayer targetPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(targetUUID);

        if (!iPlayer.getFriends().contains(targetUUID)) {
            target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            target.sendMessage(ChatUtil.color("&cYou are not friends with &e" + targetPlayer.getRank().getPrefix() + target.getName() + "&c!"));
            target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            return;
        }

        iPlayer.getFriends().remove(targetUUID);

        if (target != null && target.isOnline()) {
            targetPlayer.getFriends().remove(p.getUniqueId());

            target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
            target.sendMessage(ChatUtil.color("&c" + iPlayer.getRank().getPrefix() + p.getName()+ " &chas removed you from their friends list!"));
            target.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        }

        BlitzSG.getInstance().getDb().removeFriend(p.getUniqueId(), targetUUID);


        sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
        sender.sendMessage(ChatUtil.color("&e" + targetPlayer.getRank().getPrefix() + target.getName() + " &chas been removed from your friends list!"));
        sender.sendMessage(ChatUtil.color("&9&m-------------------------------"));
    }
}
