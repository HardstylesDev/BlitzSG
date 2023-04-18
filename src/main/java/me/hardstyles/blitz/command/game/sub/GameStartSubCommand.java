package me.hardstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;


public class GameStartSubCommand extends SubCommand {

    public GameStartSubCommand() {
        super("start", ImmutableList.of("new"), "blood.game.startgame", "/game start");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length == 1){
            new Game();
            sender.sendMessage(ChatUtil.color("&aStarted a new game!"));
            Bukkit.getLogger().log(Level.FINEST, "New Game instance created through command");

            return;
        }

        new Game(new Map(args[1]));
        sender.sendMessage(ChatUtil.color("&aStarted a new game! Name: " + args[1] + ""));






    }
}