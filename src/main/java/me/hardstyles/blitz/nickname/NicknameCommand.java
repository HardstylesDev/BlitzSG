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
            p.sendMessage(BlitzSG.CORE_NAME + "missing permiss2ion.");
            return;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("unnick") || args[0].equalsIgnoreCase("reset")) {
                BlitzSG.send(p, "&aUnnicking");
                new Nickname().unnick(p);
                return;
            }
            BlitzSG.send(p, "&aChanging your nickname to &e" + args[0]);
            new Nickname().setNick(p, args[0]);
            return;
        }
        p.sendMessage(BlitzSG.CORE_NAME + "&e");
    }
}

