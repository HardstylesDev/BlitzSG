package me.hardstyles.blitz.command.taunt;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TauntCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public TauntCommand() {
        super("taunt", ImmutableList.of("t"), null);


    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());

        if(iPlayer.getGame() == null && !player.hasPermission("blitz.admin")){
            player.sendMessage(ChatUtil.color("&cYou must be in a game to use this command!"));
            return;
        }
        BlitzSG.getInstance().getGameManager().taunt(player);





    }
}