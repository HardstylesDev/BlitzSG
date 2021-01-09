package me.syesstyles.blitz.blitzsgplayer;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.gui.ShopGUI;
import me.syesstyles.blitz.rank.ranks.Admin;
import me.syesstyles.blitz.utils.nickname.Nickname;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class BlitzSGPlayerHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer uhcPlayer;
        if (BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()) == null)
            uhcPlayer = new BlitzSGPlayer(e.getPlayer().getUniqueId());
        uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)); //todo change back
        if (uhcPlayer.getNick() != null && uhcPlayer.getNick().isNicked()) {
            e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
            Nickname nickname = new Nickname();
            if (uhcPlayer.getNick().getSkinSignature() == null) {
                e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
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
            e.setJoinMessage((ChatColor.YELLOW + p.getName() + " joined the game").replaceAll("  ", " "));

        if (uhcPlayer.getRank() == null)
            uhcPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName("Default"));

        System.out.println("--------");
        System.out.println("-" + uhcPlayer.getElo());
        System.out.println("-" + uhcPlayer.getRank());
        System.out.println("-" + uhcPlayer.getRank(true).getPrefix());

        System.out.println("--------");
        p.setPlayerListName(uhcPlayer.getRank(true).getPrefix() + p.getName() + BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix()
                + " [" + uhcPlayer.getElo() + "]");

        BlitzSG.getInstance().getBlitzSGPlayerManager().setLobbyInventoryAndNameTag(p);
    }

    // @EventHandler
    // public void onKick(PlayerKickEvent e){
    //     BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId());
    //     System.out.println("kick handler: " + uhcPlayer.getNick().isNicked());
    //     if(uhcPlayer.getNick().isNicked())
    //         e.setLeaveMessage("bye");
    // }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

    }

    @EventHandler
    public void onAsyncChat(PlayerChatEvent e) {
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId());
        e.setFormat(uhcPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (uhcPlayer.getRank(true).getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage());
    }

    //BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
    //e.setFormat(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
    //        + e.getPlayer().getName() + ": §f" + e.getMessage());
    //Bukkit.broadcastMessage(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
    //		+ e.getPlayer().getName() + ": §f" + e.getMessage());


    @EventHandler
    public void playerLobbyInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (uhcPlayer.isInGame())
            return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getItem() == null)
            return;
        if (uhcPlayer.getRank() instanceof Admin && p.getGameMode() == GameMode.CREATIVE)
            return;
        e.setCancelled(true);
        if (e.getItem().getType() == Material.EMERALD)
            ShopGUI.openGUI(p);
        else if (e.getItem().getType() == Material.IRON_SWORD) {
            if (BlitzSG.getInstance().getGameManager().getAvailableGame() == null) {
                p.sendMessage("§cCouldn't find any available games");
                return;
            }
            BlitzSG.getInstance().getGameManager().getAvailableGame().addPlayer(p);
        }

    }

}
