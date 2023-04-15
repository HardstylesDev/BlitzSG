package me.syesstyles.blitz.blitzsgplayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.utils.ItemUtils;

public class BlitzSGPlayerManager {

    private HashMap<UUID, BlitzSGPlayer> blitzPlayers;
    private HashMap<BlitzSGPlayer, Game> playerGames;

    public BlitzSGPlayerManager() {
        blitzPlayers = new HashMap<>();
        playerGames = new HashMap<>();
    }

    public HashMap<UUID, BlitzSGPlayer> getBlitzPlayers() {
        return blitzPlayers;
    }

    public BlitzSGPlayer getBsgPlayer(UUID uuid) {
        return blitzPlayers.get(uuid);
    }

    public void addPlayer(UUID uuid, BlitzSGPlayer uhcPlayer) {
        blitzPlayers.put(uuid, uhcPlayer);
    }

    public void removePlayer(UUID uuid) {
        blitzPlayers.remove(uuid);
    }

    public Game getUhcPlayerGame(BlitzSGPlayer uhcPlayer) {
        if (playerGames.get(uhcPlayer) == null)
            return null;
        return playerGames.get(uhcPlayer);
    }

    public void setUhcPlayerGame(BlitzSGPlayer uhcPlayer, Game game) {
        playerGames.put(uhcPlayer, game);
    }


    public void setLobbyInventoryAndNameTag(Player p) {
        p.getInventory().clear();
        p.getInventory().setItem(1, ItemUtils.buildItem(new ItemStack(Material.IRON_SWORD), "&b&lJoin a Game &7(Right-Click)", Arrays.asList("§7Right-Click to join a Blitz game")));
        p.getInventory().setItem(3, ItemUtils.buildItem(new ItemStack(Material.EMERALD), "&a&lOpen the Shop &7(Right-Click)", Arrays.asList("§7Right-Click to open the shop")));
        p.getInventory().setItem(5, ItemUtils.buildItem(new ItemStack(Material.PAINTING), "&e&lYour Stats &7(Right-Click)", Arrays.asList("§7Right-Click to view your stats")));
        p.getInventory().setItem(7, ItemUtils.buildItem(new ItemStack(Material.SKULL_ITEM), "&c???", Arrays.asList("§7Coming soon...")));

        BlitzSG.getInstance().getNametagManager().update();
    }


    public void handleKillElo(Player victim, Player killer) {
        BlitzSGPlayer victimBsg = this.getBsgPlayer(victim.getUniqueId());
        BlitzSGPlayer killerBsg = this.getBsgPlayer(killer.getUniqueId());

        double eloChange = 0;
        if (killerBsg.getElo() > 0) {
            eloChange = Math.sqrt(victimBsg.getElo() / killerBsg.getElo()) + 1;
        } else if (killerBsg.getElo() == 0) {
            eloChange = Math.sqrt(victimBsg.getElo() / 1) + 1;
        }
        victimBsg.removeElo((int) eloChange);
        killerBsg.addElo((int) eloChange);
    }

    public void handleDeathElo(Player victim) {
        BlitzSGPlayer blitzVictim = this.getBsgPlayer(victim.getUniqueId());
        Game g = blitzVictim.getGame();

        double allPlayerElo = 0;
        for (Player pl : g.getAllPlayers())
            if (pl.getUniqueId() != victim.getUniqueId())
                allPlayerElo += BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(pl.getUniqueId()).getElo();
        double eloChange = 0;
        if (allPlayerElo > 0)
            eloChange = ((blitzVictim.getElo() * 0.1) / ((allPlayerElo / (g.getAllPlayers().size() - 1)))) * 4 + 1;
        else if (allPlayerElo == 0)
            eloChange = (blitzVictim.getElo() * 0.1) * 4 + 1;

        blitzVictim.removeElo((int) eloChange);
    }

    public void handleWinElo(Game g) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(g.getWinner().getUniqueId());
        double allPlayerElo = 0;
        for (Player pl : g.getAllPlayers())
            if (pl.getUniqueId() != g.getWinner().getUniqueId())
                allPlayerElo += BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(pl.getUniqueId()).getElo();
        double eloChange = (((allPlayerElo * 0.5) / (g.getAllPlayers().size() - 1)) / bsgPlayer.getElo()) * 4 + 1;
        if (bsgPlayer.getElo() == 0)
            eloChange = 1;
        bsgPlayer.addElo((int) eloChange);
    }

}
