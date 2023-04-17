package me.hardstyles.blitz.game;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.command.fireworks.FireworkCommand;
import me.hardstyles.blitz.gui.KitGUI;
import me.hardstyles.blitz.gui.StarGUI;
import me.hardstyles.blitz.utils.ChestUtils;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class GameHandler implements Listener {
    ChestUtils chestUtils = new ChestUtils();

    @EventHandler
    public void onExpBottle(ExpBottleEvent e) {
        e.setExperience(e.getExperience() / 2);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (bsgPlayer.isInGame()) {
            if (bsgPlayer.getGame().getGameMode() == Game.GameMode.INGAME) {
                bsgPlayer.getGame().killPlayer(p);
                bsgPlayer.getGame().msgAll(BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + p.getName() + " &ewas killed!");
                dropInventory(p);
            } else if (bsgPlayer.getGame().getGameMode() == Game.GameMode.WAITING || bsgPlayer.getGame().getGameMode() == Game.GameMode.STARTING) {
                bsgPlayer.getGame().removePlayer(p);
            }
        }
        BlitzSG.getInstance().getStatisticsManager().savePlayer(bsgPlayer);
        BlitzSG.getInstance().getIPlayerManager().removePlayer(p.getUniqueId());
    }


    public void onPlayerDeath(Player victim, Player killer) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(victim.getUniqueId());
        if (!bsgPlayer.isInGame()) return;
        bsgPlayer.getGame().applyDeathEffects();
        BlitzSG.getInstance().getIPlayerManager().handleDeathElo(victim);
        String deathMessage = BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + victim.getName() + " &ewas killed and everyone got a speed buff!";
        if (killer != null) {
            IPlayer killerPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(killer.getUniqueId());
            deathMessage = BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + victim.getName() + " &ewas killed by " + killerPlayer.getRank().getChatColor() + killer.getName() + " &eand everyone got a speed buff!";
            killerPlayer.addGameKill();
            BlitzSG.getInstance().getIPlayerManager().handleKillElo(victim, killer);
        }
        for (Player p : bsgPlayer.getGame().getAllPlayers()) {
            BlitzSG.send(p, deathMessage);
        }

        if (killer != null) {
            IPlayer killerPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(killer.getUniqueId());
            int coins = 7 * killerPlayer.getRank().getMultiplier();
            killer.sendMessage("§6+" + coins + " Coins (Kill)");
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);
            killerPlayer.addCoins(coins);
        }


        bsgPlayer.getGame().killPlayer(victim);
        victim.getWorld().strikeLightningEffect(victim.getLocation());
        bsgPlayer.getGameEntities().forEach(Entity::remove);
        bsgPlayer.getGameEntities().clear();
        bsgPlayer.addDeath();
        BlitzSG.getInstance().getIPlayerManager().handleDeathElo(victim);

        victim.setGameMode(org.bukkit.GameMode.SPECTATOR);

        CraftPlayer craftPlayer = (CraftPlayer) victim;
        craftPlayer.getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange(3, 0));


        victim.setAllowFlight(true);
        victim.setFlying(true);
        victim.setHealth(20);
        victim.setFoodLevel(20);
        victim.setFireTicks(0);
        victim.setFallDistance(0);
        victim.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1));
        dropInventory(victim);
    }


    private void dropInventory(Player victim) {
        for (ItemStack i : victim.getInventory().getContents()) {
            if (i != null)
                if (i.getType() != Material.AIR) victim.getWorld().dropItemNaturally(victim.getLocation(), i);
        }
        for (ItemStack i : victim.getInventory().getArmorContents()) {
            if (i != null) {
                if (i.getType() != Material.AIR) {
                    victim.getWorld().dropItemNaturally(victim.getLocation(), i);
                }
            }
        }
        ExperienceOrb orb = victim.getWorld().spawn(victim.getLocation(), ExperienceOrb.class);
        orb.setExperience(victim.getTotalExperience());
        victim.getInventory().clear();
        victim.getInventory().setArmorContents(null);
        victim.setTotalExperience(0);
        victim.setLevel(0);
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        if (!bsgPlayer.isInGame()) {
            return;
        }
        if (bsgPlayer.getGame().getGameMode() == Game.GameMode.STARTING) {
            if (e.getTo().getBlockX() != p.getLocation().getBlockX() || e.getTo().getBlockZ() != p.getLocation().getBlockZ()) {
                Location spawn = bsgPlayer.getGameSpawn();
                if (spawn == null) {
                    bsgPlayer.getGame().resetGame();
                    Bukkit.broadcastMessage("Error occured in game " + bsgPlayer.getGame());
                }
                spawn.setY(e.getPlayer().getLocation().getY());
                spawn.setDirection(e.getTo().getDirection());
                e.getPlayer().teleport(spawn);
            }
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        for (Game runningGame : BlitzSG.getInstance().getGameManager().getRunningGames()) {
            if (runningGame.getMap().getWorld() == e.getBlock().getWorld()) e.blockList().clear();
        }
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {
        for (Game runningGame : BlitzSG.getInstance().getGameManager().getRunningGames()) {
            if (runningGame.getMap().getWorld() == e.getEntity().getWorld()) e.blockList().clear();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) {
            if ((bsgPlayer.getRank().isManager() && (p.getGameMode() == org.bukkit.GameMode.CREATIVE))) return;
            e.setCancelled(true);
            return;
        }
        Block b = e.getBlock();
        if (b.getType() == Material.CAKE_BLOCK || b.getType() == Material.WEB) {
            return;
        }
        if (bsgPlayer.getGame().getGameMode() != Game.GameMode.INGAME) e.setCancelled(true);

        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (e.getBlockPlaced().getType() == Material.CAKE_BLOCK || e.getBlockPlaced().getType() == Material.WEB) return;
        if (!bsgPlayer.isInGame()) {

            if ((bsgPlayer.getRank().isManager()) && (p.getGameMode() == org.bukkit.GameMode.CREATIVE)) {
                return;
            }
            e.setCancelled(true);
            return;
        }
        if (bsgPlayer.getGame().getGameMode() != Game.GameMode.INGAME) {
            if (e.getBlockPlaced().getType() == Material.CAKE_BLOCK || e.getBlockPlaced().getType() == Material.WEB)
                return;
            e.setCancelled(true);
            return;
        }
        if (e.getBlockPlaced().getType() == Material.CAKE_BLOCK || e.getBlockPlaced().getType() == Material.WEB) return;
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
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) {
            if (!(bsgPlayer.getRank().isManager()) && !(p.getGameMode() == org.bukkit.GameMode.CREATIVE))
                e.setCancelled(true);
            return;
        }

        if (bsgPlayer.getGame().getGameMode() == Game.GameMode.INGAME) return;
        if (bsgPlayer.getGame().getGameMode() != Game.GameMode.STARTING && bsgPlayer.getGame().getGameMode() != Game.GameMode.WAITING) {
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

        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (bsgPlayer == null || bsgPlayer.getGame() == null || bsgPlayer.getGame().getGameMode() == null) return;
        if (bsgPlayer.getGame().getGameMode() == Game.GameMode.WAITING || bsgPlayer.getGame().getGameMode() == Game.GameMode.STARTING || bsgPlayer.getGame().getGameMode() == Game.GameMode.RESETING)
            e.setCancelled(true);
        if (!bsgPlayer.isInGame()) {
            e.setCancelled(true);
        }

        if (bsgPlayer.getGame().getGameMode() == Game.GameMode.INGAME) {
            if (e instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
                if (event.getDamager() instanceof Player) {
                    Player attacker = (Player) event.getDamager();
                    bsgPlayer.setLastDamage(System.currentTimeMillis());
                    bsgPlayer.setLastDamager(attacker);
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.LAVA && bsgPlayer.getGame().hasDeathMatchStarted()) {
                e.setDamage(99);
            }

            // check if this damage will kill the player
            if (p.getHealth() - e.getFinalDamage() <= 0) {
                if (!e.isCancelled()) {
                    e.setCancelled(true);
                    onPlayerDeath(p, bsgPlayer.getLastAttacker());
                }

            }

        }


    }

    @EventHandler
    public void playerPortalEvent(PlayerPortalEvent e) {
        Player p = (Player) e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) {
            p.sendMessage("§cThe Nether is disabled.");
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) return;
        if (bsgPlayer.getGame().getDeathmatchStartTime() >= 45 && bsgPlayer.getGame().getDeathmatchStartTime() <= 60) {
            e.setCancelled(true);
            return;
        }
        if (bsgPlayer.isWobbuffet()) {
            e.setCancelled(true);
            if ((e.getDamager() instanceof Player)) {
                ((Player) e.getDamager()).damage(e.getDamage());
                e.getDamager().sendMessage(ChatColor.AQUA + "Damage was bounced back!");
            } else if (e.getDamager() instanceof Arrow) {
                if (((Arrow) e.getDamager()).getShooter() instanceof Player) {
                    ((Player) ((Arrow) e.getDamager()).getShooter()).sendMessage("&bDamage was bounced back!");
                }
            }
            e.getEntity().getWorld().playSound(e.getDamager().getLocation(), Sound.IRONGOLEM_HIT, 1, 1);

        }
        if (bsgPlayer.getGame().getGameMode() == Game.GameMode.INGAME || bsgPlayer.getGame().getGameMode() == Game.GameMode.RESETING) {
            if (bsgPlayer.getGameEntities().contains(e.getDamager()) || (e.getDamager() instanceof Snowball && bsgPlayer.getGameEntities().contains(((Snowball) e.getDamager()).getShooter())) || (e.getDamager() instanceof Arrow && bsgPlayer.getGameEntities().contains(((Arrow) e.getDamager()).getShooter()))) {
                e.setCancelled(true);
            }

            if (!(e.getDamager() instanceof Player)) {
                if (e.getDamager() instanceof Arrow) if (((Arrow) e.getDamager()).getShooter() instanceof Player) {
                    if (BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) ((Arrow) e.getDamager()).getShooter()).getUniqueId()).isRobinhood()) {
                        e.setDamage(1000);
                        e.getEntity().sendMessage("&aYou were taken out in a single shot!");
                    } else e.setDamage(e.getDamage() / 2);

                    return;
                }
                e.setDamage(e.getDamage() / 10);
            }
        }

        if (bsgPlayer.getGame().getGameTime() >= 60) return;
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
        //if (!(e.getEntity() instanceof Player))
        //    return;
        //if (e.getRegainReason() == RegainReason.SATIATED)
        //    e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (((Player) e.getEntity()).getFoodLevel() < e.getFoodLevel()) return;
        if (new Random().nextInt(50) != 0) e.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {

        boolean rain = event.toWeatherState();
        if (rain) event.setCancelled(true);
    }

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {

        boolean storm = event.toThunderState();
        if (storm) event.setCancelled(true);
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
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        if (bsgPlayer.isSpectating()) {
            e.setCancelled(true);
        }


        if (!bsgPlayer.isInGame()) return;
        if (bsgPlayer.getGame().getGameMode() != Game.GameMode.WAITING && bsgPlayer.getGame().getGameMode() != Game.GameMode.STARTING)
            return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (e.getItem().getType() == Material.BOW) KitGUI.openGUI(p);
    }

    @EventHandler
    public void playerOpenStarMenu(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) return;
        if (bsgPlayer.getGame().getGameMode() != Game.GameMode.INGAME) return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (e.getItem().getType() == Material.NETHER_STAR) {
            if (bsgPlayer.getGame().getDeathmatchStartTime() >= 15) {
                p.sendMessage(BlitzSG.CORE_NAME + ChatColor.RED + "The Blitz Star has been disabled!");
                e.setCancelled(true);
                return;
            }
            StarGUI.openGUI(p);
        }

    }

    @EventHandler
    public void playerEatHead(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (e.getItem().getType() != Material.SKULL_ITEM) return;
        if (e.getItem().getDurability() != 3) return;
        e.setCancelled(true);
        p.sendMessage("§aYou ate a player head and gained Regeneration III and Speed II for 4 seconds!");
        p.playSound(p.getLocation(), Sound.EAT, 2, 1);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1));
        p.getInventory().setItemInHand((ItemStack) null);
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(e.getPlayer().getUniqueId());
        if (!bsgPlayer.isInGame()) return;
        if (!(bsgPlayer.getGame().getGameMode() == Game.GameMode.INGAME)) return;
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