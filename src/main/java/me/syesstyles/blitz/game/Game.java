package me.syesstyles.blitz.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.arena.Arena;
import me.syesstyles.blitz.perk.Perk;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.utils.ItemUtils;

public class Game {

	public static enum GameMode {
		LOADING, INACTIVE, WAITING,
		STARTING, INGAME, RESETING
	}

	private ArrayList<Player> allPlayers;
	private ArrayList<Player> alivePlayers;
	private ArrayList<Player> deadPlayers;

	private GameMode gameMode;

	private Arena arena;
	private Player winner;

	private HashMap<Player, Boolean> votes;

	private HashSet<Location> spawnUsed;

	private int countdownTime, gameTime;

	private double borderSize;
	private double borderShrinkBy;

	public Game() {
		gameMode = GameMode.LOADING;
		arena = BlitzSG.getInstance().getArenaManager().getRandomArena();
		if(arena == null) {
			return;
		}
		arena.setInUse(true);
		BlitzSG.getInstance().getGameManager().addGame(this);
		allPlayers = new ArrayList<Player>();
		alivePlayers = new ArrayList<Player>();
		deadPlayers = new ArrayList<Player>();
		votes = new HashMap<Player, Boolean>();
		spawnUsed = new HashSet<Location>();
		gameMode = GameMode.WAITING;
		for(Location loc : arena.getSpawns()) {
			loc.getBlock().setType(Material.AIR);
		}
		borderSize = Math.max(arena.getArenaMaxCorner().getBlockX(), arena.getArenaMaxCorner().getBlockZ()) - Math.max(arena.getCenter().getBlockX(), arena.getCenter().getBlockZ());
		borderShrinkBy = -0.00625;
		arena.getArenaWorld().getWorldBorder().setSize(borderSize * 2);
		arena.getArenaWorld().getWorldBorder().setCenter(arena.getCenter().clone().add(0.5, 0, 0.5));
		arena.getArenaWorld().getWorldBorder().setSize(borderSize * 2);
	}

	public void addPlayer(Player p) {
		//if(arena == null) {
		//p.sendMessage("&cCouldn't find an available arena!");
		//return;
		//}
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(uhcPlayer.isInGame()) {

			BlitzSG.send(p,BlitzSG.CORE_NAME + "&cYou are already in a game!");

			return;
		}
		if(arena.getSpawns().size() < alivePlayers.size() + 1) {
			BlitzSG.send(p,BlitzSG.CORE_NAME + "&cThe game is already full!");
			return;
		}
		uhcPlayer.setGame(this);
		allPlayers.add(p);
		alivePlayers.add(p);
		Location playerSpawn = null;
		for(Location l : arena.getSpawns())
			if(!spawnUsed.contains(l)) {
				playerSpawn = l;
				break;
			}
		//createCage(playerSpawn);
		spawnUsed.add(playerSpawn);
		p.teleport(playerSpawn.clone().add(0.5, 1.0, 0.5));
		msgAll(BlitzSG.CORE_NAME + "&7" + p.getName() + " &ehas joined (&b" + alivePlayers.size() + "&e/&b" + arena.getSpawns().size() + "&e)!");
		if(alivePlayers.size() >= 2 && gameMode.equals(GameMode.WAITING)) {
			startCountDown();
		}
		resetPlayer(p);
		setPregameInventory(p);
	}

	public void removePlayer(Player p) {
		if(gameMode == GameMode.INGAME) {
			BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou can't leave while the game is running!");
			//killPlayer(p);
			return;
		}
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		uhcPlayer.setGame(null);
		spawnUsed.remove(arena.getSpawns().get(alivePlayers.indexOf(p)));
		alivePlayers.remove(p);
		allPlayers.remove(p);
		msgAll(BlitzSG.CORE_NAME + "&7" + p.getName() + " &ehas left (&b" + alivePlayers.size() + "&e/&b" + arena.getSpawns().size() + "&e)!");
		resetPlayer(p);
		p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)); //todo change back
		BlitzSG.getInstance().getBlitzSGPlayerManager().setLobbyInventory(p);
		BlitzSG.send(p, BlitzSG.CORE_NAME + "&cYou have left the game!");
	}

	public void startCountDown() {
		gameMode = GameMode.STARTING;
		countdownTime = 31;
		new BukkitRunnable() {
			public void run() {
				countdownTime--;
				if(countdownTime == 30) {
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("&eA BSG &egame on the map &a" + arena.getName() + " &eis bound to start in &b" + countdownTime + " &eseconds. Use &6/bsg join &eto enter the game.");
					Bukkit.broadcastMessage("");
				}
				if(alivePlayers.size() < 2) {
					this.cancel();
					gameMode = GameMode.WAITING;
					msgAll("&cWe don't have enough players! Countdown cancelled.");
					return;
				}
				if(countdownTime == 0) {
					startGame();
					this.cancel();
					return;
				}
				if(countdownTime % 10 == 0 || countdownTime <= 5) {
					if(countdownTime <= 5)
						msgAll(BlitzSG.CORE_NAME + "&eGame starting in &c" + countdownTime + " &eseconds!");
					else if(countdownTime <= 15)
						msgAll(BlitzSG.CORE_NAME + "&eGame starting in &6" + countdownTime + " &eseconds!");
					else if(countdownTime <= 25)
						msgAll(BlitzSG.CORE_NAME + "&eGame starting in &e" + countdownTime + " &eseconds!");
					else if(countdownTime <= 30)
						msgAll(BlitzSG.CORE_NAME + "&eGame starting in &a" + countdownTime + " &eseconds!");
				}
			}
		}.runTaskTimer(BlitzSG.getInstance(), 0, 20);
	}

	public void startGame() {
		gameMode = GameMode.INGAME;
		arena.getArenaWorld().setTime(0);
		for(Player p : alivePlayers) {
			p.closeInventory();
			resetPlayer(p);
			p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
			p.sendMessage("&aThe game has started, Good Luck!");
			if(isHeadGame())
				p.sendMessage("&ePlayer heads are &aEnabled&e!");
			else
				p.sendMessage("&ePlayer heads are &cDisabled&e!");
			p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60 * 20, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60 * 20, 0));
			p.getInventory().addItem(ItemUtils.getGracefulItem(Material.DIAMOND_PICKAXE, "Graceful Pickaxe"));
			p.getInventory().addItem(ItemUtils.getGracefulItem(Material.DIAMOND_AXE, "Graceful Axe"));
			for(Perk perk : BlitzSG.getInstance().getPerkManager().getGameStartPerks())
				perk.givePerk(p, BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()).getPerkLevel(perk));
		}
		for(Location l : arena.getSpawns())
			if(spawnUsed.contains(l))
				//removeCage(l);
		gameTime = 0;
		new BukkitRunnable() {
			public void run() {
				if(alivePlayers.size() < 2) {
					this.cancel();
					endGame(false);
					return;
				}
				if((gameTime+1)%60 == 0)
					winner.sendMessage("&6+10 Coins (Survived + " + gameTime + " seconds)");
				BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(winner.getUniqueId()).addCoins(10);
				if(gameTime == 59) {
					msgAll("&eThe grace period has ended, PvP is now enabled!");
					for(Player p : alivePlayers)
						for(Perk perk : BlitzSG.getInstance().getPerkManager().getPvpStartPerks())
							perk.givePerk(p, BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()).getPerkLevel(perk));
				}
				if(gameTime == 119) {
					msgAll("&eThe border will now start shrinking!");
					startBorder();
				}
				if(gameTime >= 119 && alivePlayers.size() < 4 && borderShrinkBy > -0.025) {
					msgAll("&cLess than 4 players remaining, the border will now shrink faster.");
					borderShrinkBy = -0.025;
				}
				if(gameTime == 479) {
					//msgAll("&cSudden Death has begun, all players will now take continuous damage until the Game End.");
					msgAll(BlitzSG.CORE_NAME + "&cSudden Death has begun, all players health has been capped at 0.5\u2764.");
					for(Player p : alivePlayers)
						p.setMaxHealth(1);
				}
				/*if(gameTime >= 479) {
					for(Player p : players)
						p.damage(1);
				}*/
				if(gameTime == 599)
					endGame(false);
				gameTime++;
			}
		}.runTaskTimer(BlitzSG.getInstance(), 20, 20);
	}

	public void startBorder() {
		new BukkitRunnable() {
			public void run() {
				if(gameMode == GameMode.RESETING)
					this.cancel();
				if(borderSize > 10) {
					borderSize += borderShrinkBy;
					arena.getArenaWorld().getWorldBorder().setSize(borderSize * 2);
				}
			}
		}.runTaskTimer(BlitzSG.getInstance(), 1, 1);
	}

	public void killPlayer(Player p) {
		alivePlayers.remove(p);
		deadPlayers.add(p);
		//SpeedUHCPlayer uhcPlayer = SpeedUHC.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
	}

	public void endGame(boolean draw) {
		gameMode = GameMode.RESETING;
		msgAll("&7&m------------------------------");
		msgAll("               &f&lSpeed UHC      ");
		msgAll("&7");
		if(draw) {
			msgAll("                  &e&lDRAW! ");
		}else {
			winner = alivePlayers.get(0);
			BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(winner.getUniqueId()).addWin();
			msgAll("           &e&lWinner &7- " + winner.getName());
		}
		msgAll("&7");
		msgAll("      &e&l1st Killer &7- " + Bukkit.getOfflinePlayer(BlitzSG.getInstance().getGameManager().getTopKillers(this).get(1).getUuid()).getName() + " &7- "
				+ BlitzSG.getInstance().getGameManager().getTopKillers(this).get(1).getGameKills());
		if(BlitzSG.getInstance().getGameManager().getTopKillers(this).size() >= 2)
			msgAll("      &6&l2nd Killer &7- " + Bukkit.getOfflinePlayer(BlitzSG.getInstance().getGameManager().getTopKillers(this).get(2).getUuid()).getName() + " &7- "
					+ BlitzSG.getInstance().getGameManager().getTopKillers(this).get(2).getGameKills());
		if(BlitzSG.getInstance().getGameManager().getTopKillers(this).size() >= 3)
			msgAll("      &c&l3rd Killer &7- " + Bukkit.getOfflinePlayer(BlitzSG.getInstance().getGameManager().getTopKillers(this).get(3).getUuid()).getName() + " &7- "
					+ BlitzSG.getInstance().getGameManager().getTopKillers(this).get(3).getGameKills());
		msgAll("&7&m------------------------------");
		if(!draw) {
			BlitzSG.getInstance().getBlitzSGPlayerManager().handleWinElo(this);
			winner.sendMessage("&6+400 Coins (Win)");
			BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(winner.getUniqueId()).addCoins(400);
		}
		new BukkitRunnable() {
			public void run() {
				resetGame();
			}
		}.runTaskLater(BlitzSG.getInstance(), 200);
	}

	public void resetGame() {
		BlitzSG.getInstance().getGameManager().removeGame(this);
		for(Player p : allPlayers) {
			BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
			uhcPlayer.setGame(null);
			resetPlayer(p);
			p.teleport(BlitzSG.lobbySpawn);
			BlitzSG.getInstance().getBlitzSGPlayerManager().setLobbyInventory(p);
			p.setPlayerListName(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix()
					+ "[" + uhcPlayer.getElo() + "] " + p.getName());
		}
		allPlayers.clear();
		alivePlayers.clear();
		deadPlayers.clear();
		arena.resetArena();
	}

	//private void createCage(Location location) {
	//	for(int x = -2; x <= 2; x = x +1) {
	//		for(int z = -2; z <= 2; z = z +1) {
	//			for(int y = 0; y <= 4; y = y +1) {
	//				Location loc = location.clone();
	//				loc.add(x, y, z).getBlock().setType(Material.GLASS);
	//			}
	//		}
	//	}
	//	for(int x = -1; x <= 1; x = x +1) {
	//		for(int z = -1; z <= 1; z = z +1) {
	//			for(int y = 1; y <= 3; y = y +1) {
	//				Location loc = location.clone();
	//				loc.add(x, y, z).getBlock().setType(Material.AIR);
	//			}
	//		}
	//	}
	//}
//
	//private void removeCage(Location location) {
	//	for(int x = -2; x <= 2; x = x +1) {
	//		for(int z = -2; z <= 2; z = z +1) {
	//			for(int y = 0; y <= 4; y = y +1) {
	//				Location loc = location.clone();
	//				loc.add(x, y, z).getBlock().setType(Material.AIR);
	//			}
	//		}
	//	}
	//}

	private void setPregameInventory(Player p) {
		//p.getInventory().setItem(0, ItemUtils.buildItem(new ItemStack(Material.BOW), "&aKit Selector &7(Right Click)"
		//, Arrays.asList("&7Right Click to select a kit")));
		p.getInventory().setItem(0, ItemUtils.buildItem(new ItemStack(Material.PAPER), "&aVote Options &7(Right Click)"
				, Arrays.asList("&7Right Click to vote for the game settings")));
		p.getInventory().setItem(8, ItemUtils.buildItem(new ItemStack(Material.BARRIER), "&cExit Game &7(Right Click)"
				, Arrays.asList("&7Right Click to leave the game")));
	}

	private void resetPlayer(Player p) {
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		uhcPlayer.resetGameKills();
		p.getInventory().clear();
		p.getInventory().setArmorContents((ItemStack[]) null);
		p.setFoodLevel(20);
		p.setSaturation(10);
		p.setMaxHealth(20);
		p.setHealth(20);
		p.setExp(0);
		p.setLevel(0);
		p.setGameMode(org.bukkit.GameMode.SURVIVAL);
		for(PotionEffect pe : p.getActivePotionEffects())
			p.removePotionEffect(pe.getType());
	}

	public void msgAll(String msg) {
		for(Player p : allPlayers)
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	public ArrayList<Player> getAllPlayers() {
		return allPlayers;
	}

	public ArrayList<Player> getAlivePlayers() {
		return alivePlayers;
	}

	public ArrayList<Player> getDeadPlayers() {
		return deadPlayers;
	}

	public boolean isHeadGame() {
		if(getTrueVotes() > getFalseVotes())
			return true;
		return false;
	}

	public HashMap<Player, Boolean> getVotes() {
		return votes;
	}

	public int getTrueVotes() {
		int v = 0;
		for(boolean b : votes.values())
			if(b)
				v++;
		return v;
	}

	public int getFalseVotes() {
		int v = 0;
		for(boolean b : votes.values())
			if(!b)
				v++;
		return v;
	}

	public int getVotingPercentage() {
		if(votes.size() == 0)
			return 0;
		return (int) ((double)getTrueVotes()*100/(double)votes.size());
	}

	public boolean getVote(Player p) {
		return this.votes.get(p);
	}

	public void setVote(Player p, boolean vote) {
		this.votes.put(p, vote);
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public Arena getArena() {
		return arena;
	}

	public Player getWinner() {
		return winner;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}

	public int getCountdownTime() {
		return countdownTime;
	}

	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
	}

	public boolean isDead(Player p) {
		if(deadPlayers.contains(p))
			return true;
		return false;
	}


}
