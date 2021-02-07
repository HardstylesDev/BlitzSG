package me.syesstyles.blitz.utils;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.rank.ranks.Admin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartDMCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(BlitzSG.getInstance().getRankManager().getRank((Player) sender) instanceof Admin))
            return true;
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(((Player)sender).getUniqueId());
        bsgPlayer.getGame().startDeathmatchCounter(bsgPlayer.getGame().getGameTime());
        return true;
    }
}
