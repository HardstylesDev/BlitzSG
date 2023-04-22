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
import me.hardstyles.blitz.player.IPlayer;
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

    private boolean canStart = true;

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

        if(gameName.equalsIgnoreCase("list")){
            sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&eAvailable games:"));
            for (Game allWaitingGame : BlitzSG.getInstance().getGameManager().getAllWaitingGames()) {
                sender.sendMessage(ChatUtil.color("&e- &a" + allWaitingGame.getMap().getMapName()));
            }
            return;
        }

        for (Game allWaitingGame : BlitzSG.getInstance().getGameManager().getAllWaitingGames()) {
            if(allWaitingGame.getMap().getMapName().equalsIgnoreCase(gameName)){
                allWaitingGame.addPlayer((Player) sender);
                return;
            }
        }
        File f = new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + gameName + ".yml");
        if(!f.exists()){
            sender.sendMessage(ChatUtil.color("&cThat map doesn't exist."));
            return;
        }
        if(BlitzSG.getInstance().getGameManager().getRunningGames().size() > 5){
            sender.sendMessage(ChatUtil.color("&cThere are too many games running at the moment."));
            return;
        }
        Player p = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        long remainingTime = (iPlayer.getLastGameRequested() + 15000 - System.currentTimeMillis()) / 1000;
        if (remainingTime > 0) {
            p.sendMessage(ChatUtil.color("&cYou must wait " + remainingTime + " seconds before requesting a new game!"));
            return;
        }
        iPlayer.setLastGameRequested(System.currentTimeMillis());

        if(!canStart){
            sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cThere is already a game being created."));
            return;
        }

        sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&aRequest successful. &eCreating a new game..."));
        Game game = new Game(new Map(gameName));
        canStart = false;
        new BukkitRunnable() {
            int attempts = 10;
            public void run() {
                World world = Bukkit.getWorld(game.getMap().getMapId());
                if(world == null || game.getMap().getLobby() == null){
                    attempts = attempts - 1;
                    sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&eLoading map... &c" + attempts + "&e attempts remaining."));
                    if(attempts == 0){
                        sender.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME +"&cFailed to load the map."));
                        cancel();
                    }
                } else {
                    game.addPlayer((Player) sender);
                    cancel();
                    canStart = true;
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, 20);

    }

}