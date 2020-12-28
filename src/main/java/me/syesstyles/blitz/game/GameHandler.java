package me.syesstyles.blitz.game;

import java.util.Arrays;
import java.util.Random;

import me.syesstyles.blitz.rank.ranks.Admin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game.GameMode;
import me.syesstyles.blitz.gui.VotingGUI;
import me.syesstyles.blitz.utils.ItemUtils;

public class GameHandler implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(uhcPlayer.isInGame()) {
			if(uhcPlayer.getGame().getGameMode() == GameMode.INGAME) {
				uhcPlayer.getGame().killPlayer(p);
				uhcPlayer.getGame().msgAll("§c" + p.getName() + " left the game and was killed!");
				for(ItemStack i : p.getInventory().getContents()) {
					if(i != null)
						if(i.getType() != Material.AIR)
							p.getWorld().dropItemNaturally(p.getLocation(), i);
				}
				for(ItemStack i : p.getInventory().getArmorContents()) {
					if(i != null)
						if(i.getType() != Material.AIR)
							p.getWorld().dropItemNaturally(p.getLocation(), i);
				}
			}else if(uhcPlayer.getGame().getGameMode() == GameMode.WAITING || uhcPlayer.getGame().getGameMode() == GameMode.STARTING){
				uhcPlayer.getGame().removePlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player victim = e.getEntity();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(victim.getUniqueId());
		if(!uhcPlayer.isInGame())
			return;
		e.setDeathMessage("§c" + e.getDeathMessage());
		victim.getWorld().strikeLightningEffect(victim.getLocation());
		uhcPlayer.getGame().msgAll(e.getDeathMessage());
		uhcPlayer.getGame().killPlayer(victim);
		uhcPlayer.addDeath();
		if(victim.getKiller() != null) {
			//p.getKiller().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 2));
			BlitzSGPlayer uhcPlayerKiller = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(victim.getKiller().getUniqueId());
			uhcPlayerKiller.addGameKill();
			uhcPlayerKiller.addCoins(50);
			victim.getKiller().sendMessage("§6+50 Coins (Kill)");
			BlitzSG.getInstance().getBlitzSGPlayerManager().handleKillElo(victim, victim.getKiller());
			return;
		}
		BlitzSG.getInstance().getBlitzSGPlayerManager().handleDeathElo(victim);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame())
			return;
		if(!uhcPlayer.getGame().isDead(p))
			return;
		p.setGameMode(org.bukkit.GameMode.SPECTATOR);
		if(p.getKiller() != null)
			e.setRespawnLocation(p.getKiller().getLocation());
		else
			e.setRespawnLocation(uhcPlayer.getGame().getArena().getArenaWorld().getSpawnLocation());
	}
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if(!uhcPlayer.isInGame())
            return;
        if(uhcPlayer.getGame().getGameMode() == GameMode.STARTING || uhcPlayer.getGame().getGameMode() == GameMode.WAITING)
            if(e.getTo().getBlockX() != p.getLocation().getBlockX() || e.getTo().getBlockZ() != p.getLocation().getBlockZ()) {
                int x = e.getPlayer().getLocation().getBlockX();
                int z = e.getPlayer().getLocation().getBlockZ();
                e.getTo().setX(x + 0.5);
                e.getTo().setZ(z + 0.5);
            }
    }
	
	//@EventHandler
	//public void onPlayerEat(PlayerItemConsumeEvent e) {
	//	if(e.getItem().getType().equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 1) {
    //        new BukkitRunnable() {
    //            public void run() {
    //    			e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
    //    			e.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    //    			e.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
    //    	        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 3));
    //    	        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 120, 0));
    //    	        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 120, 0));
    //            }
    //        }.runTaskLater(BlitzSG.getInstance(), 1);
	//	}
	//}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame()) {
			if(!(uhcPlayer.getRank() instanceof Admin))

				e.setCancelled(true);
			return;
		}
		if(uhcPlayer.getGame().getGameMode() != GameMode.INGAME)
				e.setCancelled(true);
		Block b = e.getBlock();
		if(b.getType() == Material.DIAMOND_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.DIAMOND));
            p.giveExp(3);
            //((ExperienceOrb)b.getWorld().spawn(b.getLocation().add(0.5, 0.4, 0.5), (Class)ExperienceOrb.class)).setExperience(4);
		}else if(b.getType() == Material.IRON_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 2));
		}else if(b.getType() == Material.GOLD_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 2));
            p.giveExp(1);
		}else if(b.getType() == Material.COAL_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.FIREBALL, 1));
            p.getInventory().addItem(new ItemStack(Material.FLINT, 1));
            p.giveExp(1);
		}else if(b.getType() == Material.LAPIS_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 2));
            p.giveExp(2);
		}else if(b.getType() == Material.GRAVEL) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.ARROW, 4));
		}else if(b.getType() == Material.WEB) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.STRING, 2));
		}else if(b.getType() == Material.MELON_BLOCK) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            p.getInventory().addItem(new ItemStack(Material.SPECKLED_MELON));
		}else if(b.getType() == Material.SUGAR_CANE_BLOCK) {
            e.setCancelled(true);
            Location loc = b.getLocation();
            while(loc.add(0, 1, 0).getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                loc.getBlock().getState().update();
                loc = loc.getBlock().getLocation();
            }
            while(loc.add(0, -1, 0).getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                loc.getBlock().setType(Material.AIR);
                loc.getBlock().getState().update();
                if(new Random().nextInt(3) == 0)
                	p.getInventory().addItem(new ItemStack(Material.BOOK));
                else
                	p.getInventory().addItem(new ItemStack(Material.SUGAR));
                loc = loc.getBlock().getLocation();
            }
		}else if(b.getType() == Material.SAND) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            if(new Random().nextInt(3) == 0)
            	p.getInventory().addItem(new ItemStack(Material.TNT));
            else
                p.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16));
		}else if(b.getType() == Material.LEAVES || b.getType() == Material.LEAVES_2) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            if(new Random().nextInt(10) == 0)
            	p.getInventory().addItem(new ItemStack(Material.APPLE));
		}else if(b.getType() == Material.LOG || b.getType() == Material.LOG_2) {
            e.setCancelled(true);
            if(b.getType().equals(Material.LOG))
            	p.getInventory().addItem(new ItemStack(Material.LOG, 1, (short) e.getBlock().getState().getRawData()));
            else if(b.getType().equals(Material.LOG_2))
            	p.getInventory().addItem(new ItemStack(Material.LOG_2, 1, (short) e.getBlock().getState().getRawData()));
            b.setType(Material.AIR);
            b.getState().update();
		}
	}
	
	/*@EventHandler
	public void onBlockDestroy(BlockExplodeEvent e) {
		Block b = e.getBlock();
		if(b.getType() == Material.DIAMOND_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.DIAMOND));
		}else if(b.getType() == Material.IRON_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.IRON_INGOT, 2));
		}else if(b.getType() == Material.GOLD_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.GOLD_INGOT, 2));
		}else if(b.getType() == Material.COAL_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.FIREBALL));
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.FLINT));
		}else if(b.getType() == Material.LAPIS_ORE) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.EXP_BOTTLE, 2));
		}else if(b.getType() == Material.GRAVEL) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.ARROW, 4));
		}else if(b.getType() == Material.WEB) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.STRING, 2));
		}else if(b.getType() == Material.MELON_BLOCK) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.SPECKLED_MELON));
		}else if(b.getType() == Material.SUGAR_CANE_BLOCK) {
            e.setCancelled(true);
            Location loc = b.getLocation();
            while(loc.add(0, 1, 0).getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                loc.getBlock().getState().update();
                loc = loc.getBlock().getLocation();
            }
            while(loc.add(0, -1, 0).getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                loc.getBlock().setType(Material.AIR);
                loc.getBlock().getState().update();
                if(new Random().nextInt(3) == 0)
                    b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.BOOK));
                else
                    b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.SUGAR));
                loc = loc.getBlock().getLocation();
            }
		}else if(b.getType() == Material.SAND) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            if(new Random().nextInt(3) == 0)
                b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.TNT));
            else
            	b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.POTION, 1, (short) 16));
		}else if(b.getType() == Material.LEAVES || b.getType() == Material.LEAVES_2) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            b.getState().update();
            if(new Random().nextInt(10) == 0)
            	b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.APPLE));
		}else if(b.getType() == Material.LOG || b.getType() == Material.LOG_2) {
            e.setCancelled(true);
            if(b.getType().equals(Material.LOG))
            	b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.LOG, 1, (short) e.getBlock().getState().getRawData()));
            else if(b.getType().equals(Material.LOG_2))
            	b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 0.4, 0.5), new ItemStack(Material.LOG_2, 1, (short) e.getBlock().getState().getRawData()));
            b.setType(Material.AIR);
            b.getState().update();
		}
	}*/
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame()) {
			if(!(uhcPlayer.getRank() instanceof Admin))

				e.setCancelled(true);
			return;
		}
		if(uhcPlayer.getGame().getGameMode() != GameMode.INGAME) {
			e.setCancelled(true);
			return;
		}
		if(e.getBlockPlaced().getLocation().getBlockY() >= uhcPlayer.getGame().getArena().getArenaMaxCorner().getBlockY()) {
			p.sendMessage("§cYou have reached the building height limit!");
			e.setCancelled(true);
			return;
		}
		if(e.getBlockPlaced().getType() == Material.TNT) {
			e.getBlockPlaced().setType(Material.AIR);
			e.getBlockPlaced().getWorld().spawnEntity(e.getBlockPlaced().getLocation().add(0.5, 0.5, 0.5), EntityType.PRIMED_TNT);
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame()) {
			if(!(uhcPlayer.getRank() instanceof Admin))
				e.setCancelled(true);
			return;
		}
		if(uhcPlayer.getGame().getGameMode() != GameMode.INGAME) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame()) {
			e.setCancelled(true);
			return;
		}
		if(uhcPlayer.getGame().getGameTime() >= 15)
			return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void playerPortalEvent(PlayerPortalEvent e) {
		Player p = (Player) e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame()) {
			p.sendMessage("§cThe Nether is disabled.");
			return;
		}
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame())
			return;
		if(uhcPlayer.getGame().getGameTime() >= 60)
			return;
		if(e.getDamager() instanceof Player) {
			e.setCancelled(true);
			((Player)e.getDamager()).sendMessage("§cYou can't damage other players during the grace period!");
		}else if(e.getDamager() instanceof Projectile) {
			if(((Projectile)e.getDamager()).getShooter() instanceof Player) {
				e.setCancelled(true);
				((Player)((Projectile)e.getDamager()).getShooter()).sendMessage("§cYou can't damage other players during the grace period!");
			}
		}
	}
	
	@EventHandler
	public void onHealthRegain(EntityRegainHealthEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(e.getRegainReason() == RegainReason.SATIATED)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(((Player)e.getEntity()).getFoodLevel() < e.getFoodLevel())
			return;
		if(new Random().nextInt(50) != 0)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
	/*@EventHandler
	public void onPotionInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		p.sendMessage("TEST");
		if(!(e.getInventory() instanceof BrewerInventory))
			return;
		BrewerInventory inv = (BrewerInventory) e.getInventory();
		if(inv.getIngredient() == null)
			return;
		for(int slot = 0; slot <= 2; slot++)
			if(inv.getItem(slot) != null)
				if(inv.getItem(slot).getType() == Material.POTION)
					if(inv.getItem(slot).getDurability() == (short) 16) {
						p.sendMessage("UPDATE 1");
						if(inv.getIngredient().getType() == Material.SUGAR)
							inv.setItem(slot, ItemUtils.buildPotion(PotionEffectType.SPEED, 24 * 20, 2, (short) 16418));
						if(inv.getIngredient().getType() == Material.SPECKLED_MELON)
							inv.setItem(slot, ItemUtils.buildPotion(PotionEffectType.HEAL, 1, 1, (short) 16453));
						p.sendMessage("UPDATE 2");
					}	
	}*/
	
	@EventHandler
	public void onPotionBrew(BrewEvent e) {
		BrewerInventory inv = e.getContents().getHolder().getInventory();
		for(int slot = 0; slot <= 2; slot++)
			if(inv.getItem(slot) != null)
				if(inv.getItem(slot).getType() == Material.POTION)
					if(inv.getItem(slot).getDurability() == (short) 16) {
						if(inv.getIngredient().getType() == Material.SUGAR)
							inv.setItem(slot, ItemUtils.buildPotion(PotionEffectType.SPEED, 24 * 20, 2, (short) 16418));
						if(inv.getIngredient().getType() == Material.SPECKLED_MELON)
							inv.setItem(slot, ItemUtils.buildPotion(PotionEffectType.HEAL, 1, 1, (short) 16453));
					}	
	}
	
	@EventHandler
	public void playerOpenVotingMenu(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isInGame())
			return;
		if(uhcPlayer.getGame().getGameMode() != GameMode.WAITING && uhcPlayer.getGame().getGameMode() != GameMode.STARTING)
			return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() == Material.PAPER)
			VotingGUI.openGUI(p);
		if(e.getItem().getType() == Material.BARRIER)
			uhcPlayer.getGame().removePlayer(p);
	}
	
	@EventHandler
	public void playerEatHead(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() != Material.SKULL_ITEM)
			return;
		if(e.getItem().getDurability() != 3)
			return;
		e.setCancelled(true);
		p.sendMessage("§aYou ate a player head and gained Regeneration III and Speed II for 4 seconds!");
		p.playSound(p.getLocation(), Sound.EAT, 2, 1);
		p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1));
		p.getInventory().setItemInHand((ItemStack)null);
	}
	
}
