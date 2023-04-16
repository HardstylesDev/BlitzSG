package me.hardstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class GameStartDeathmatchSubCommand extends SubCommand {

    public GameStartDeathmatchSubCommand() {
        super("dm", ImmutableList.of("startdm"), "blood.game.startdm", "/game startdm");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());

        if(iPlayer.getGame() == null) {
            player.sendMessage(ChatUtil.color("&cYou are not in a game!"));
            return;
        }

        Game game = iPlayer.getGame();
        game.startDeathmatchCounter(15);

        sender.sendMessage(ChatUtil.color("&aForced the deathmatch timer to initiate."));


    }
}