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

	private HashMap<UUID, BlitzSGPlayer> bsgPlayers;
	private HashMap<BlitzSGPlayer, Game> playerGames;
	
	public BlitzSGPlayerManager() {
		bsgPlayers = new HashMap<UUID, BlitzSGPlayer>();
		playerGames = new HashMap<BlitzSGPlayer, Game>();
	}

	public HashMap<UUID, BlitzSGPlayer> getBsgPlayers() {
		return bsgPlayers;
	}

	public BlitzSGPlayer getBsgPlayer(UUID uuid) {
		return bsgPlayers.get(uuid);
	}

	public void addBsgPlayer(UUID uuid, BlitzSGPlayer uhcPlayer) {
		bsgPlayers.put(uuid, uhcPlayer);
	}

	public void removeBsgPlayer(UUID uuid) {
		bsgPlayers.remove(uuid);
	}

	public Game getUhcPlayerGame(BlitzSGPlayer uhcPlayer) {
		if(playerGames.get(uhcPlayer) == null)
			return null;
		return playerGames.get(uhcPlayer);
	}

	public void setUhcPlayerGame(BlitzSGPlayer uhcPlayer, Game game) {
		playerGames.put(uhcPlayer, game);
	}

	public void removeUhcPlayerGame(BlitzSGPlayer uhcPlayer) {
		playerGames.remove(uhcPlayer);
	}
	
	//Elo Management
	/*public void handleKillElo(Player victim, Player killer) {
		SpeedUHCPlayer victimUhc = this.getUhcPlayer(victim.getUniqueId());
		SpeedUHCPlayer killerUhc = this.getUhcPlayer(killer.getUniqueId());
		if(killerUhc.getElo() > 0) {
			double eloChange = (victimUhc.getElo()/killerUhc.getElo())*0.1 + 1;
			victimUhc.removeElo((int) eloChange);
			victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
			killerUhc.addElo((int) eloChange);
			killer.sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + killerUhc.getElo() + ")");
			return;
		}
		double eloChange = victimUhc.getElo()*0.1 + 1;
		victimUhc.removeElo((int) eloChange);
		victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
		killerUhc.addElo((int) eloChange);
		killer.sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + killerUhc.getElo() + ")");
	}
	
	public void handleDeathElo(Player victim) {
		SpeedUHCPlayer victimUhc = this.getUhcPlayer(victim.getUniqueId());
		double eloToRemove = victimUhc.getElo()*0.1 + 1;
		victimUhc.removeElo((int)eloToRemove);
		victim.sendMessage("§c-" + (int)eloToRemove + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
	}*/
	
	public void setLobbyInventoryAndNameTag(Player p) {
		p.getInventory().clear();
		p.getInventory().setItem(1, ItemUtils.buildItem(new ItemStack(Material.IRON_SWORD), "&b&lJoin a Game &7(Right-Click)", Arrays.asList("§7Right-Click to join a Blitz game")));
		p.getInventory().setItem(3, ItemUtils.buildItem(new ItemStack(Material.EMERALD), "&a&lOpen the Shop &7(Right-Click)", Arrays.asList("§7Right-Click to open the shop")));
		p.getInventory().setItem(5, ItemUtils.buildItem(new ItemStack(Material.PAINTING), "&e&lYour Stats &7(Right-Click)", Arrays.asList("§7Right-Click to view your stats")));
		p.getInventory().setItem(7, ItemUtils.buildItem(new ItemStack(Material.SKULL_ITEM), "&c???", Arrays.asList("§7Coming soon...")));

		BlitzSG.getInstance().getNametagManager().update();


		//ArrayList<NametagEdit> nametagEdits = BlitzSG.getInstance().getRankManager().getNametags();
		//for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//
		//	for (NametagEdit nametagEdit : nametagEdits) {
		//		if (nametagEdit.getRequiredRank() == bsgPlayer.getRank())
		//			nametagEdit.addPlayer(onlinePlayer);
		//	}
//
		//}
		//for (NametagEdit nametagEdit : nametagEdits) {
		//	nametagEdit.updateAll();
		//}
	}



	public void handleKillElo(Player victim, Player killer) {
		BlitzSGPlayer victimUhc = this.getBsgPlayer(victim.getUniqueId());
		BlitzSGPlayer killerUhc = this.getBsgPlayer(killer.getUniqueId());
		
		double eloChange = 0;
		if(killerUhc.getElo() > 0)
			eloChange = Math.sqrt(victimUhc.getElo()/killerUhc.getElo()) + 1;
		else if(killerUhc.getElo() == 0)
			eloChange = Math.sqrt(victimUhc.getElo()/1) + 1;
		
		victimUhc.removeElo((int) eloChange);
		//victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
		killerUhc.addElo((int) eloChange);
		//killer.sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + killerUhc.getElo() + ")");
	}
	
	public void handleDeathElo(Player victim) {
		BlitzSGPlayer victimUhc = this.getBsgPlayer(victim.getUniqueId());
		Game g = victimUhc.getGame();
		
		double allPlayerElo = 0;
		for(Player pl : g.getAllPlayers())
			if(pl.getUniqueId() != victim.getUniqueId())
				allPlayerElo += BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(pl.getUniqueId()).getElo();
		double eloChange = 0;
		if(allPlayerElo > 0)
			eloChange = ((victimUhc.getElo() * 0.1)/((allPlayerElo/(g.getAllPlayers().size()-1)))) * 4 + 1;
		else if(allPlayerElo == 0)
			eloChange = (victimUhc.getElo() * 0.1) * 4 + 1;
		
		victimUhc.removeElo((int) eloChange);
		//victim.sendMessage("§c-" + (int)eloChange + " §7ELO §c(\u25bc" + victimUhc.getElo() + ")");
	}
	
	/*public void handleWinElo(Game g) {
		SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(g.getWinner().getUniqueId());
		double allPlayerElo = 0;
		for(Player pl : g.getAllPlayers())
			allPlayerElo += SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(pl.getUniqueId()).getElo();
		double eloToAdd = (allPlayerElo/(uhcPlayer.getElo() ^ g.getAllPlayers().size())) * 10 + 1;
		if(uhcPlayer.getElo() == 0)
			eloToAdd = (allPlayerElo/g.getAllPlayers().size()) * 10 + 1;
		uhcPlayer.addElo((int) eloToAdd);
		g.getWinner().sendMessage("§a+" + (int)eloToAdd + " §7ELO §a(\u25b2" + uhcPlayer.getElo() + ")");
	}*/
	
	public void handleWinElo(Game g) {
		//Get Player
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(g.getWinner().getUniqueId());
		
		//Calculate ELO
		double allPlayerElo = 0;
		for(Player pl : g.getAllPlayers())
			if(pl.getUniqueId() != g.getWinner().getUniqueId())
				allPlayerElo += BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(pl.getUniqueId()).getElo();
		double eloChange = (((allPlayerElo * 0.5)/(g.getAllPlayers().size()-1))/uhcPlayer.getElo()) * 4 + 1;
		if(uhcPlayer.getElo() == 0)
			eloChange = 1;
		
		//Apply ELO + Message Player
		uhcPlayer.addElo((int) eloChange);
		//g.getWinner().sendMessage("§a+" + (int)eloChange + " §7ELO §a(\u25b2" + uhcPlayer.getElo() + ")");
	}
	
}
