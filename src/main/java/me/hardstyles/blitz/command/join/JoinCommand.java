package me.hardstyles.blitz.command.join;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.command.game.sub.GameListSubCommand;
import me.hardstyles.blitz.command.game.sub.GameStartDeathmatchSubCommand;
import me.hardstyles.blitz.command.game.sub.GameStartSubCommand;
import me.hardstyles.blitz.command.game.sub.GameStopAllSubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JoinCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public JoinCommand() {
        super("j", ImmutableList.of("join"), null);


    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        for (Game allWaitingGame : BlitzSG.getInstance().getGameManager().getAllWaitingGames()) {
            completions.add(allWaitingGame.getMap().getMapName());
        }
        return completions;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if(args.length == 0){
            Game availableGame = BlitzSG.getInstance().getGameManager().getAvailableGame();
            if(availableGame == null){
                sender.sendMessage(ChatUtil.color("&cThere are no available games at the moment."));
                return;
            }
            availableGame.addPlayer((Player) sender);
            return;
        }
        String gameName = args[0];
        for (Game allWaitingGame : BlitzSG.getInstance().getGameManager().getAllWaitingGames()) {
            if(allWaitingGame.getMap().getMapName().equalsIgnoreCase(gameName)){
                allWaitingGame.addPlayer((Player) sender);
                return;
            }
        }
        // check if the map exists
        File f = new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + gameName + ".yml");
        if(!f.exists()){
            sender.sendMessage(ChatUtil.color("&cThat map doesn't exist."));
            return;
        }
        // create a new game
        if(BlitzSG.getInstance().getGameManager().getRunningGames().size() > 5){
            sender.sendMessage(ChatUtil.color("&cThere are too many games running at the moment."));
            return;
        }
        sender.sendMessage(ChatUtil.color("&aCreating a new game..."));
        Game game = new Game(new Map(gameName));
        new BukkitRunnable() {
            int attempts = 10;
            public void run() {
                World world = Bukkit.getWorld(game.getMap().getMapId());
                if(world == null || game.getMap().getLobby() == null){
                    attempts = attempts - 1;
                    sender.sendMessage(ChatUtil.color("&aAttempting to load the map... &7(" + attempts + " attemps remaining)"));
                    if(attempts == 0){
                        sender.sendMessage(ChatUtil.color("&cFailed to load the map."));
                        cancel();
                    }
                } else {
                    game.addPlayer((Player) sender);
                    cancel();
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, 20);

    }

}