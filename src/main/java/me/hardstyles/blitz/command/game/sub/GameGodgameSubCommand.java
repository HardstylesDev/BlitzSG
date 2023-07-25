package me.hardstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;

import java.util.List;


public class GameGodgameSubCommand extends SubCommand {

    public GameGodgameSubCommand() {
        super("godgame", ImmutableList.of("god"), "blood.game.god", "/game god <map>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {

        Game g = new Game(new Map(args[1]));
        g.setGodGame(true);
        sender.sendMessage(ChatUtil.color("&aStarted a GOD game! Name: " + args[1] + ""));






    }
}