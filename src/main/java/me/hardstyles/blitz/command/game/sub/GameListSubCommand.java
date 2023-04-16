package me.hardstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class GameListSubCommand extends SubCommand {

    public GameListSubCommand() {
        super("list", ImmutableList.of("l"), "blood.game.list", "/game list");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        BlitzSG.getInstance().getGameManager().getRunningGames().forEach(game -> {
            player.sendMessage(ChatUtil.color("&7- &a" + game.getArena().getName() + " &7- &aPlayers: " + game.getAlivePlayers().size() + "/" + game.getArena().getSpawns().size()));
        });

        Game availableGame = BlitzSG.getInstance().getGameManager().getAvailableGame();
        if(availableGame != null) {
            player.sendMessage(ChatUtil.color("&7- &a" + availableGame.getArena().getName() + " &7- &aPlayers: " + availableGame.getAlivePlayers().size() + "/" + availableGame.getArena().getSpawns().size()));
        } else {
            player.sendMessage(ChatUtil.color("&7- &cNo new available games."));
        }



    }
}