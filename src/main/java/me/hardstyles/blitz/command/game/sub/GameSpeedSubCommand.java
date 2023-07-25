package me.hardstyles.blitz.command.game.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class GameSpeedSubCommand extends SubCommand {

    public GameSpeedSubCommand() {
        super("speed", ImmutableList.of("speedup"), "blood.game.speedup", "/game start");
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

        long interval = 10L;
        if (args.length == 2) {
            try {
                interval = Long.parseLong(args[1]);
            } catch (NumberFormatException e) {
                p.sendMessage(ChatUtil.color("&cInvalid number!"));
                return;
            }
        }
        game.setINTERVAL(interval);
        p.sendMessage(ChatUtil.color("&eSet the game speed to &6" + interval + " &aticks! &7or &6" + (interval / 20) + " &7seconds!"));



    }
}