package me.syesstyles.blitz.game;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game.GameMode;
import me.syesstyles.blitz.gui.KitGUI;
import me.syesstyles.blitz.rank.ranks.Admin;
import me.syesstyles.blitz.utils.ChestUtils;
import me.syesstyles.blitz.utils.FireworkCommand;
import me.syesstyles.blitz.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class GameHandler implements Listener {
    ChestUtils chestUtils = new ChestUtils();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (bsgPlayer.isInGame()) {
            if (bsgPlayer.getGame().getGameMode() == GameMode.INGAME) {
                bsgPlayer.getGame().killPlayer(p);
                bsgPlayer.getGame().msgAll("§c" + p.getName() + " left the game and was killed!");
                for (ItemStack i : p.getInventory().getContents()) {
                    if (i != null)
                        if (i.getType() != Material.AIR)
                            p.getWorld().dropItemNaturally(p.getLocation(), i);
                }
                for (ItemStack i : p.getInventory().getArmorContents()) {
                    if (i != null)
                        if (i.getType() != Material.AIR)
                            p.getWorld().dropItemNaturally(p.getLocation(), i);
                }
            } else if (bsgPlayer.getGame().getGameMode() == GameMode.WAITING || bsgPlayer.getGame().getGameMode() == GameMode.STARTING) {
                bsgPlayer.getGame().removePlayer(p);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        Player victim = e.getEntity();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(victim.getUniqueId());
        if (!bsgPlayer.isInGame())
            return;
        //e.setDeathMessage("§c" + e.getDeathMessage());
        e.setDeathMessage("");
        victim.getWorld().strikeLightningEffect(victim.getLocation());
        bsgPlayer.getGame().msgAll(e.getDeathMessage());
        bsgPlayer.getGameEntities().forEach(entity -> entity.remove());
        bsgPlayer.getGameEntities().clear();
        bsgPlayer.getGame().killPlayer(victim);
        bsgPlayer.addDeath();
        if (victim.getKiller() != null) {
            //p.getKiller().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 2));
            BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(victim.getKiller().getUniqueId());
            blitzSGPlayer.addGameKill();
            int coins = 7 * blitzSGPlayer.getRank().getMultiplier();
            blitzSGPlayer.addCoins(coins);
            for (Player p : blitzSGPlayer.getGame().getAllPlayers()) {
                BlitzSG.send(p, BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + victim.getName() + " &ewas killed by " + blitzSGPlayer.getRank(true).getChatColor() + victim.getKiller().getName() + " &eand everyone got a speed buff!");
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 15, 1));
            }
            BlitzSG.send(victim.getKiller(), "§6+" + coins + " Coins (Kill)");
            BlitzSG.getInstance().getBlitzSGPlayerManager().handleKillElo(victim, victim.getKiller());
            return;
        }
        BlitzSG.getInstance().getBlitzSGPlayerManager().handleDeathElo(victim);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame())
            return;
        if (!bsgPlayer.getGame().isDead(p))
            return;
        p.setGameMode(org.bukkit.GameMode.SPECTATOR);
        if (p.getKiller() != null)
            e.setRespawnLocation(p.getKiller().getLocation());
        else
            e.setRespawnLocation(bsgPlayer.getGame().getArena().getArenaWorld().getSpawnLocation());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame())
            return;
        if (bsgPlayer.getGame().getGameMode() == GameMode.STARTING)
            if (e.getTo().getBlockX() != p.getLocation().getBlockX() || e.getTo().getBlockZ() != p.getLocation().getBlockZ()) {
                Location spawn = bsgPlayer.getGameSpawn();
                spawn.setY(e.getPlayer().getLocation().getY());
                spawn.setDirection(e.getTo().getDirection());
                e.getPlayer().teleport(spawn);
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
    public void onBlockExplode(BlockExplodeEvent e) {
        for (Game runningGame : BlitzSG.getInstance().getGameManager().getRunningGames()) {
            if (runningGame.getArena().getArenaWorld() == e.getBlock().getWorld())
                e.blockList().clear();
        }
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {
        for (Game runningGame : BlitzSG.getInstance().getGameManager().getRunningGames()) {
            if (runningGame.getArena().getArenaWorld() == e.getEntity().getWorld())
                e.blockList().clear();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) {
            if (!(bsgPlayer.getRank() instanceof Admin) && !(p.getGameMode() == org.bukkit.GameMode.CREATIVE))

                e.setCancelled(true);
            return;
        }
        Block b = e.getBlock();
        if (b.getType() == Material.CAKE_BLOCK || b.getType() == Material.WEB) {
            return;
        }
        if (bsgPlayer.getGame().getGameMode() != GameMode.INGAME)
            e.setCancelled(true);

        e.setCancelled(true);
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
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (e.getBlockPlaced().getType() == Material.CAKE_BLOCK || e.getBlockPlaced().getType() == Material.WEB)
            return;
        if (!bsgPlayer.isInGame()) {
            if (!(bsgPlayer.getRank() instanceof Admin) && !(p.getGameMode() == org.bukkit.GameMode.CREATIVE))
                e.setCancelled(true);
            return;
        }
        if (bsgPlayer.getGame().getGameMode() != GameMode.INGAME) {
            if (e.getBlockPlaced().getType() == Material.CAKE_BLOCK || e.getBlockPlaced().getType() == Material.WEB)
                return;
            e.setCancelled(true);
            return;
        }
        if (e.getBlockPlaced().getType() == Material.CAKE_BLOCK || e.getBlockPlaced().getType() == Material.WEB)
            return;
        if (e.getBlockPlaced().getType() == Material.TNT) {
            e.getBlockPlaced().setType(Material.AIR);
            Entity tnt = e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
            //Entity horse = e.getPlayer().getWorld().spawn(e.getBlock().getLocation(), Horse.class);
            return;
        } else if (e.getBlockPlaced().getType() == Material.CAKE_BLOCK || e.getBlockPlaced().getType() == Material.WEB)
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) {
            if (!(bsgPlayer.getRank() instanceof Admin) && !(p.getGameMode() == org.bukkit.GameMode.CREATIVE))
                e.setCancelled(true);
            return;
        }

        if (bsgPlayer.getGame().getGameMode() == GameMode.INGAME)
            return;
        if (bsgPlayer.getGame().getGameMode() != GameMode.STARTING && bsgPlayer.getGame().getGameMode() != GameMode.WAITING) {
            System.out.println("cancelled for no reason1!!");
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getWorld() == BlitzSG.lobbySpawn.getWorld()) {
            e.setCancelled(true);
            return;
        }

        if (!(e.getEntity() instanceof Player))
            return;
        Player p = (Player) e.getEntity();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        if (bsgPlayer.getGame().getGameMode() == GameMode.WAITING || bsgPlayer.getGame().getGameMode() == GameMode.STARTING || bsgPlayer.getGame().getGameMode() == GameMode.RESETING)
            e.setCancelled(true);
        if (!bsgPlayer.isInGame()) {
            e.setCancelled(true);
            return;
        }

        //e.setCancelled(true);
    }

    @EventHandler
    public void playerPortalEvent(PlayerPortalEvent e) {
        Player p = (Player) e.getPlayer();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) {
            p.sendMessage("§cThe Nether is disabled.");
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;

        Player p = (Player) e.getEntity();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame())
            return;
        if (bsgPlayer.getGame().getGameMode() == GameMode.INGAME) {
            if (bsgPlayer.getGameEntities().contains(e.getDamager()) || (e.getDamager() instanceof Snowball && bsgPlayer.getGameEntities().contains(((Snowball) e.getDamager()).getShooter()))) {
                e.setCancelled(true);
            }
            if (!(e.getDamager() instanceof Player)) {
                if (e.getDamager() instanceof Arrow)
                    if ((Arrow) ((Arrow) e.getDamager()).getShooter() instanceof Player)
                        return;
                e.setDamage(e.getDamage() / 10);
            }
        }

        if (bsgPlayer.getGame().getGameTime() >= 60)
            return;
        //if(e.getDamager() instanceof Player) { //todo do later!
        //	e.setCancelled(true);
        //	((Player)e.getDamager()).sendMessage("§cYou can't damage other players during the grace period!");
        //}else
        // if(e.getDamager() instanceof Projectile) {
        //	if(((Projectile)e.getDamager()).getShooter() instanceof Player) {
        //		e.setCancelled(true);
        //		((Player)((Projectile)e.getDamager()).getShooter()).sendMessage("§cYou can't damage other players during the grace period!");
        //	}
        //}
    }

    @EventHandler
    public void onHealthRegain(EntityRegainHealthEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        if (e.getRegainReason() == RegainReason.SATIATED)
            e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        if (((Player) e.getEntity()).getFoodLevel() < e.getFoodLevel())
            return;
        if (new Random().nextInt(50) != 0)
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
    public void playerOpenVotingMenu(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame())
            return;
        if (bsgPlayer.getGame().getGameMode() != GameMode.WAITING && bsgPlayer.getGame().getGameMode() != GameMode.STARTING)
            return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getItem() == null)
            return;
        if (e.getItem().getType() == Material.BOW)
            KitGUI.openGUI(p);
        if (e.getItem().getType() == Material.BARRIER)
            bsgPlayer.getGame().removePlayer(p);
    }

    @EventHandler
    public void playerEatHead(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getItem() == null)
            return;
        if (e.getItem().getType() != Material.SKULL_ITEM)
            return;
        if (e.getItem().getDurability() != 3)
            return;
        e.setCancelled(true);
        p.sendMessage("§aYou ate a player head and gained Regeneration III and Speed II for 4 seconds!");
        p.playSound(p.getLocation(), Sound.EAT, 2, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1));
        p.getInventory().setItemInHand((ItemStack) null);
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        BlitzSGPlayer bsgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId());
        if (!bsgPlayer.isInGame())
            return;
        if (!(bsgPlayer.getGame().getGameMode() == GameMode.INGAME))
            return;
        if (e.getClickedBlock() != null)
            if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                //e.setCancelled(true);
                if (!bsgPlayer.getGame().getOpenedChests().contains(e.getClickedBlock().getLocation())) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    chest.getInventory().clear();
                    chestUtils.generateChestLoot(chest.getInventory(), 3);
                    chest.update();
                    bsgPlayer.getGame().getOpenedChests().add(e.getClickedBlock().getLocation());
                }
                if (bsgPlayer.getGame().canFindStar() && !(bsgPlayer.getGame().getStarChests().contains(e.getClickedBlock().getLocation()))) {
                    int foo = (int) (Math.random() * 100);
                    if (foo < 12) {
                        Chest chest = (Chest) e.getClickedBlock().getState();
                        chest.getInventory().addItem(new ItemBuilder(Material.NETHER_STAR).name("&6Blitz Star").lores(new String[]{"&7Plase in hotbar and right click", "&7to activate your Blitz!", "&7A Blitz is a super-powerful", "&7ability that can change the", "&7course of the game.", "&7Unlock more in the shop", "&7with coins."}).enchantment(Enchantment.LOOT_BONUS_BLOCKS, 1).make());
                        bsgPlayer.getGame().msgAll(BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + e.getPlayer().getName() + " &efound the &6Blitz Star&e!");
                        bsgPlayer.getGame().setFindStar(false);
                        new FireworkCommand().launchFirework(chest.getLocation());
                        bsgPlayer.getGame().getStarChests().clear();
                    }
                    bsgPlayer.getGame().getStarChests().add(e.getClickedBlock().getLocation());
                }
            }
    }
}