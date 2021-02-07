package me.syesstyles.blitz.blitzsgplayer;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.utils.nickname.Nickname;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class BlitzSGPlayerHandler implements Listener {

    // @EventHandler
    // public void banCheck(AsyncPlayerPreLoginEvent e) {
    //     BlitzSG.getInstance().getPunishmentManager().handlePreLogin(e);
    // }

    @EventHandler
    public void onConnect(AsyncPlayerPreLoginEvent e) {


        // if(BlitzSG.getInstance().getGameManager().getRunningGames().size() != 0){
        //     e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_FULL);
        //     e.setKickMessage("sorry");
        //     return;
        // }
        //   Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
        BlitzSG.getInstance().getStatisticsManager().load(e.getUniqueId());
        BlitzSG.getInstance().getBlitzSGPlayerManager().addBsgPlayer(e.getUniqueId(), BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getUniqueId()));
        //});

        System.out.println("Loaded players: " + BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayers().size());



    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        e.setQuitMessage("");
        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
            BlitzSG.getInstance().getStatisticsManager().save(BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId()));
            BlitzSG.getInstance().getBlitzSGPlayerManager().removeBsgPlayer(e.getPlayer().getUniqueId());
        });
    }

    @EventHandler

    public void onJoin(PlayerJoinEvent e) {


        Player p = e.getPlayer();
        p.teleport(new Location(Bukkit.getWorld("world"),0,100,0));
        e.setJoinMessage("");
        p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
        BlitzSGPlayer uhcPlayer;
        if (BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()) == null)
            uhcPlayer = new BlitzSGPlayer(e.getPlayer().getUniqueId());
        uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        uhcPlayer.setName(p.getDisplayName());
        uhcPlayer.setIp(p.getAddress().toString().split(":")[0].replaceAll("/", ""));
        if (uhcPlayer.getAura() != null) BlitzSG.getInstance().getCosmeticsManager().add(p);
        p.setGameMode(GameMode.SURVIVAL);
        //p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)); //todo change back
        //if (!(uhcPlayer.getRank() instanceof Default)) {
        //    p.setAllowFlight(true);
        //    p.setFlying(true);
        //}
        if (uhcPlayer.getNick() != null && uhcPlayer.getNick().isNicked()) {
            //e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
            Nickname nickname = new Nickname();
            if (uhcPlayer.getNick().getSkinSignature() == null) {
                //e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
                uhcPlayer.getNick().setNicked(true);
                p.kickPlayer(ChatColor.GREEN + "Re-applied nick, please rejoin");
                String[] skin = nickname.prepareSkinTextures(p, uhcPlayer.getNick().getNickName());
                uhcPlayer.getNick().setNicked(true);
                uhcPlayer.getNick().setSkinValue(skin[0]);
                uhcPlayer.getNick().setSkinSignature(skin[1]);
            } else {
                nickname.setNick(p, uhcPlayer.getNick().getNickName(), true);
            }
        } else
           // e.setJoinMessage((ChatColor.YELLOW + p.getName() + " joined the game").replaceAll("  ", " "));

        if (uhcPlayer.getRank() == null)
            uhcPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName("Default"));

        Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
            BlitzSG.getInstance().getGameManager().getAvailableGame().addPlayer(p);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                p.showPlayer(onlinePlayer);
                onlinePlayer.showPlayer(p);
            }

        }, 2L);

        BlitzSG.getInstance().getNametagManager().update();



    }


    @EventHandler
    public void onAsyncChat(PlayerChatEvent e) {
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId());
        e.setFormat(uhcPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (uhcPlayer.getRank(true).getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage().replaceAll("%", "%%"));
    }

    //BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
    //e.setFormat(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
    //        + e.getPlayer().getName() + ": §f" + e.getMessaFge());
    //Bukkit.broadcastMessage(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
    //		+ e.getPlayer().getName() + ": §f" + e.getMessage());


    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        if (!(e.getItem().getItemStack().getType() == Material.POTION)) {
            return;
        }
        if (e.getPlayer().getInventory().firstEmpty() == -1) {
            return;
        }
        e.setCancelled(true);
        e.getPlayer().getInventory().addItem(e.getItem().getItemStack());
        e.getItem().remove();
        e.getPlayer().playSound(e.getItem().getLocation(), Sound.ITEM_PICKUP, (float) 0.1, (float) 1.5);
    }


}
