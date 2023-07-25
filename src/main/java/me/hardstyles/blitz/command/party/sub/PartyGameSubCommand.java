package me.hardstyles.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.map.Map;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;

public class PartyGameSubCommand extends SubCommand {
    public PartyGameSubCommand() {
        super("game", ImmutableList.of(), null, "/party game <map>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    private boolean canStart = true;

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getParty() == null) {
            player.sendMessage(ChatUtil.color("&cYou are not in a party!"));
            return;
        }
        Party party = iPlayer.getParty();

        if(party.getOnlineMembers().size() < 2) {
            party.message(ChatUtil.color("&cYou need at least 2 players to start a game!"));
            return;
        }

        if (args.length == 1) {
            player.sendMessage(ChatUtil.color("&cUsage: /party game <map>"));
            return;
        }
        if(party.getOwner() != iPlayer.getUuid()){
            player.sendMessage(ChatUtil.color("&cYou are not the owner of this party!"));
            return;
        }

        String gameName = args[1];

        File f = new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + gameName + ".yml");
        if (!f.exists()) {
            sender.sendMessage(ChatUtil.color("&cThat map doesn't exist."));
            return;
        }
        if (BlitzSG.getInstance().getGameManager().getRunningGames().size() > 5) {
            sender.sendMessage(ChatUtil.color("&cThere are too many games running at the moment."));
            return;
        }
        long remainingTime = (iPlayer.getLastGameRequested() + 15000 - System.currentTimeMillis()) / 1000;
        if (remainingTime > 0) {
            party.message(ChatUtil.color("&cYou must wait " + remainingTime + " seconds before requesting a new game!"));
            return;
        }
        iPlayer.setLastGameRequested(System.currentTimeMillis());

        if (!canStart) {
            party.message("&cThere is already a game being created.");
            return;
        }

        party.message(ChatUtil.color("&9Party > &6Creating a new game instance!"));
        Game game = new Game(new Map(gameName));
        canStart = false;
        new BukkitRunnable() {
            int attempts = 10;
            public void run() {
                World world = Bukkit.getWorld(game.getMap().getMapId());
                if (world == null || game.getMap().getLobby() == null) {
                    attempts = attempts - 1;
                    party.message(ChatUtil.color(BlitzSG.CORE_NAME + "&eLoading map... &c(" + attempts + "/10)"));
                    if (attempts == 0) {
                        party.message(ChatUtil.color(BlitzSG.CORE_NAME + "&cFailed to load the map."));
                        cancel();
                    }
                } else {
                    party.joinPrivate(game);
                    cancel();
                    canStart = true;
                }
            }
        }.runTaskTimer(BlitzSG.getInstance(), 0, 20);


    }
}
