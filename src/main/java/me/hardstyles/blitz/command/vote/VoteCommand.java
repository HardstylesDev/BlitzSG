package me.hardstyles.blitz.command.vote;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VoteCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public VoteCommand() {
        super("vote", ImmutableList.of("startgame"), null);


    }

    private boolean canStart = true;

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        completions.add("yes");
        completions.add("no");
        return completions;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {

        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        if(iPlayer.getGame() == null) {
            sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cYou are not in a game."));
            return;
        }

        Game game = iPlayer.getGame();
        if(game.getGameMode() != Game.GameMode.WAITING) {
            sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cVotes can only be casted in the waiting lobby."));
            return;
        }

        if(game.getVotes().containsKey(p.getUniqueId())) {
            sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cYou have already voted."));
            return;
        }

        if(args.length == 0){
            sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cUsage: /vote <yes/no>"));
            return;
        }
        String vote = args[0];
        if(vote.equalsIgnoreCase("yes")) {
            game.castVote(p, true);
        } else if(vote.equalsIgnoreCase("no")) {
            game.castVote(p, false);
        } else {
            sender.sendMessage(ChatUtil.color("&cUsage: /vote <yes/no>"));
        }

    }

}