package me.syesstyles.blitz.blitzsgplayer;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.gui.ShopGUI;

public class BlitzSGPlayerHandler implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer;
		if(BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId()) == null)
			uhcPlayer = new BlitzSGPlayer(e.getPlayer().getUniqueId());
		uhcPlayer = BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		p.setGameMode(GameMode.SURVIVAL);
		p.teleport(BlitzSG.lobbySpawn);
		p.setPlayerListName(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix()
				+ "[" + uhcPlayer.getElo() + "] " + p.getName());
		BlitzSG.getInstance().getSpeedUHCPlayerManager().setLobbyInventory(p);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		//if(!uhcPlayer.isInGame())
			//e.setRespawnLocation(SpeedUHC.lobbySpawn);
	}
	
	@EventHandler
	public void onAsyncChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		e.setFormat(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
				+ e.getPlayer().getName() + ": �f" + e.getMessage());
	}
	
	@EventHandler
	public void playerLobbyInteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		if(uhcPlayer.isInGame())
			return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		e.setCancelled(true);
		if(e.getItem().getType() == Material.EMERALD)
			ShopGUI.openGUI(p);
		else if(e.getItem().getType() == Material.IRON_SWORD) {
			if(BlitzSG.getInstance().getGameManager().getAvailableGame() == null) {
				p.sendMessage("�cCouldn't find any available games");
				return;
			}
			BlitzSG.getInstance().getGameManager().getAvailableGame().addPlayer(p);
		}
			
	}

}
