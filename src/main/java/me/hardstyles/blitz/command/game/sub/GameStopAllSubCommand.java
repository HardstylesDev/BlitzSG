package me.hardstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;

import java.util.List;


public class GameStopAllSubCommand extends SubCommand {

    public GameStopAllSubCommand() {
        super("stopall", ImmutableList.of("stopallgames"), "blood.game.startgame", "/game stopall");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        BlitzSG.getInstance().getGameManager().stopWaitingGames();
        sender.sendMessage(ChatUtil.color("&aStopped all games!"));
    }
}