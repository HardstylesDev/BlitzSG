package me.hardstyles.blitz.nickname;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.player.IPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NicknameCommand extends Command {
    public NicknameCommand() {
        super("nick", ImmutableList.of("nickname"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!(iPlayer.getRank().getPosition() > 5)) {
            p.sendMessage(BlitzSG.CORE_NAME + "missing permissions");
            return;
        }

        if(iPlayer.getGame() != null){
            p.sendMessage(BlitzSG.CORE_NAME + "You can't use this command while in game");
            return;
        }


        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("unnick") || args[0].equalsIgnoreCase("reset")) {
                BlitzSG.getInstance().getNickManager().resetNick(p);
                return;
            }

            String skinName = null;
            if (args.length == 2) {
                skinName = args[1];
            }

            BlitzSG.getInstance().getNickManager().setNick(p, args[0], skinName);

            return;
        }

        p.sendMessage(BlitzSG.CORE_NAME + "Usage: /nick <name> or /nick reset");
    }
}

