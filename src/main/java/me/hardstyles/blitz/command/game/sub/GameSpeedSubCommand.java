package me.hardstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;


public class GameSpeedSubCommand extends SubCommand {

    public GameSpeedSubCommand() {
        super("start", ImmutableList.of("new"), "blood.game.startgame", "/game start");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        Game game = iPlayer.getGame();
        if (game == null) {
            p.sendMessage(ChatUtil.color("&cYou are not in a game!"));
            return;
        }
        if (game.getGameMode() != Game.GameMode.WAITING) {
            p.sendMessage(ChatUtil.color("&cThe game has already started!"));
            return;
        }

        game.setINTERVAL(2);



    }
}