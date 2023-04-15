package me.syesstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.command.SubCommand;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.rank.Rank;
import me.syesstyles.blitz.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;


public class GameStartDeathmatchCommand extends SubCommand {

    public GameStartDeathmatchCommand() {
        super("dm", ImmutableList.of("startdm"), "blood.game.startdm", "/game startdm");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(player.getUniqueId());

        if(blitzSGPlayer.getGame() == null) {
            player.sendMessage(ChatUtil.color("&cYou are not in a game!"));
            return;
        }

        Game game = blitzSGPlayer.getGame();
        game.startDeathmatchCounter(15);

        sender.sendMessage(ChatUtil.color("&aStarted Deathmatch timer!"));


    }
}