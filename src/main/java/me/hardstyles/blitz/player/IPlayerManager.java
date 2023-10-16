package me.hardstyles.blitz.player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.util.ItemBuilder;
import me.hardstyles.blitz.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IPlayerManager {

    private HashMap<UUID, IPlayer> blitzPlayers;
    private HashMap<IPlayer, Game> playerGames;

    public IPlayerManager() {
        blitzPlayers = new HashMap<>();
        playerGames = new HashMap<>();
    }

    public HashMap<UUID, IPlayer> getBlitzPlayers() {
        return blitzPlayers;
    }

    public IPlayer getPlayer(UUID uuid) {
        return blitzPlayers.get(uuid);
    }

    public void addPlayer(UUID uuid, IPlayer iPlayer) {
        blitzPlayers.put(uuid, iPlayer);
    }

    public void removePlayer(UUID uuid) {
        blitzPlayers.remove(uuid);
    }

    public Game getGame(IPlayer iPlayer) {
        if (playerGames.get(iPlayer) == null)
            return null;
        return playerGames.get(iPlayer);
    }

    public static IPlayerManager get() {
        return BlitzSG.getInstance().getIPlayerManager();
    }

    public void setGame(IPlayer iPlayer, Game game) {
        playerGames.put(iPlayer, game);
    }


    public void toLobby(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.getInventory().setItem(0, ItemUtils.buildItem(new ItemStack(Material.COMPASS), "&aGame Selector &7(Right Click)", Arrays.asList("§7Right-Click to open the Game Selector")));
        p.getInventory().setItem(1, ItemUtils.buildItem(new ItemStack(Material.WATCH), "&aPlayer Visibility &7(Right Click)", Arrays.asList("§7Right-Click to hide or show players")));
        p.getInventory().setItem(2, ItemUtils.buildItem(new ItemStack(Material.EMERALD), "&aShop &7(Right Click)", Arrays.asList("§7Right-Click to open the shop")));
        p.getInventory().setItem(4, new ItemBuilder(Material.SKULL_ITEM).name("&aPlayer Menu &7(Right Click)").durability(3).skullOwner(p.getName()).make());
        p.getInventory().setItem(7, ItemUtils.buildItem(new ItemStack(Material.PAINTING), "&aYour Stats &7(Right Click)", Arrays.asList("§7Right-Click to view your stats")));
        p.getInventory().setItem(8, ItemUtils.buildItem(new ItemStack(Material.NETHER_STAR), "&aLobby Selector &7(Right Click)", Arrays.asList("§7Right-Click to open the Lobby Selector.")));

        resetPlayerStatus(p);
        IPlayer iPlayer = this.getPlayer(p.getUniqueId());
        if(iPlayer.getGadget() != null){
            p.getInventory().setItem(6, iPlayer.getGadget().getItem());
        }
        iPlayer.setGame(null);
        iPlayer.resetGameKills();
        p.teleport(BlitzSG.lobbySpawn);
        if (iPlayer.getRank() == null) {
            return;
        }
        if (iPlayer.getRank().getPosition() > 0) {
            p.setAllowFlight(true);
            p.setFlying(true);
            if (iPlayer.getRank().getPosition() > 2) {
                if (iPlayer.getWardrobeStorage() != null) {
                    iPlayer.getWardrobeStorage().apply(p);
                }
            }
        }

        iPlayer.setPrefix(iPlayer.getRank().getPrefix());
        p.setPlayerListName(iPlayer.getRank().getPrefix() + p.getName());

        iPlayer.setTauntUsed(false);

        applyVisibility(p);


    }


    public void applyVisibility(Player p) {
        IPlayer iPlayer = this.getPlayer(p.getUniqueId());
        Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getWorld().getName().equalsIgnoreCase("world")).forEach(player -> {
            IPlayer iLobby = this.getPlayer(player.getUniqueId());
            if (iLobby == null) {
                return;
            }
            if (!iLobby.isVisibilityEnabled()) {
                player.hidePlayer(p);
            } else {
                player.showPlayer(p);
            }
            if (!iPlayer.isVisibilityEnabled()) {
                p.hidePlayer(player);
            } else {
                p.showPlayer(player);
            }
        });
        Bukkit.getServer().getOnlinePlayers().stream().filter(player -> !player.getWorld().getName().equalsIgnoreCase("world")).forEach(player -> {
            p.hidePlayer(player);
            player.hidePlayer(p);

        });
    }

    public void resetPlayerStatus(Player p) {

        p.setGameMode(org.bukkit.GameMode.SURVIVAL);
        p.setMaxHealth(21);
        p.setHealth(p.getMaxHealth());
        p.setMaxHealth(20);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
        p.setSaturation(20);
        p.setFireTicks(0);
        p.setExp(0);
        p.setLevel(0);
        p.setFallDistance(0);
        p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));
        ((CraftPlayer) p).getHandle().getDataWatcher().watch(9, (byte) 0);
    }

    public void handleKillElo(Player victim, Player killer) {
        IPlayer victimBsg = this.getPlayer(victim.getUniqueId());
        IPlayer killerBsg = this.getPlayer(killer.getUniqueId());

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
        if (victim == null) {
            return;
        }

        IPlayer blitzVictim = this.getPlayer(victim.getUniqueId());
        Game g = blitzVictim.getGame();
        if (g == null) {
            return;
        }

        double allPlayerElo = 0;
        for (Player pl : g.getAllPlayers()) {
            if (pl.isOnline()) {
                if (pl.getUniqueId() != victim.getUniqueId()) {
                    allPlayerElo += BlitzSG.getInstance().getIPlayerManager().getPlayer(pl.getUniqueId()).getElo();
                }
            }
        }
        double eloChange = 0;
        if (allPlayerElo > 0)
            eloChange = ((blitzVictim.getElo() * 0.1) / ((allPlayerElo / (g.getAllPlayers().size() - 1)))) * 4 + 1;
        else if (allPlayerElo == 0)
            eloChange = (blitzVictim.getElo() * 0.1) * 4 + 1;

        blitzVictim.removeElo((int) eloChange);
    }

    public void handleWinElo(Game g) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(g.getWinner().getUniqueId());
        double allPlayerElo = 0;
        for (Player pl : g.getAllPlayers())
            if (pl.getUniqueId() != g.getWinner().getUniqueId()) {
                if (BlitzSG.getInstance().getIPlayerManager().getPlayer(pl.getUniqueId()) != null) {
                    allPlayerElo += BlitzSG.getInstance().getIPlayerManager().getPlayer(pl.getUniqueId()).getElo();
                }
            }
        double eloChange = (((allPlayerElo * 0.5) / (g.getAllPlayers().size() - 1)) / iPlayer.getElo()) * 4 + 1;
        if (iPlayer.getElo() == 0)
            eloChange = 1;
        iPlayer.addElo((int) eloChange);
    }

}
