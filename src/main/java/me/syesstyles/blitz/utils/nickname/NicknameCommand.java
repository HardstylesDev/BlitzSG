package me.syesstyles.blitz.utils.nickname;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.rank.ranks.Admin;
import me.syesstyles.blitz.rank.ranks.Default;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if ((BlitzSG.getInstance().getRankManager().getRank((Player) sender) instanceof Default)) {
            sender.sendMessage(BlitzSG.CORE_NAME + "missing permission.");

            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("unnick") || args[0].equalsIgnoreCase("reset")){
                BlitzSG.send(p, "&aUnnicking");
                new Nickname().unnick(p);
                return true;
            }
            BlitzSG.send(p, "&aChanging your nickname to &e" + args[0]);
            new Nickname().setNick(p, args[0]);
            return true;
        }
        p.sendMessage(BlitzSG.CORE_NAME + "&e");
        return true;
    }
}
