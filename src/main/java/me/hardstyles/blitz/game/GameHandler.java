package me.hardstyles.blitz.game;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.gui.SpectatorGUI;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.command.fireworks.FireworkCommand;
import me.hardstyles.blitz.gui.KitGUI;
import me.hardstyles.blitz.gui.StarGUI;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.loot.ChestUtils;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.*;
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
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

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
                bsgPlayer.getGame().message(BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + p.getName() + " &ewas killed!");
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
        if (!bsgPlayer.isInGame()) {
            return;
        }

        bsgPlayer.getGame().applyDeathEffects();

        String deathMessage = BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + victim.getName() + " &ewas killed and everyone got a speed buff!";
        if (killer != null) {
            IPlayer killerPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(killer.getUniqueId());
            deathMessage = BlitzSG.CORE_NAME + bsgPlayer.getRank(true).getChatColor() + victim.getName() + " &ewas killed by " + killerPlayer.getRank().getChatColor() + killer.getName() + " &eand everyone got a speed buff!";
            killerPlayer.addGameKill();
            BlitzSG.getInstance().getIPlayerManager().handleKillElo(victim, killer);
            handleCoinReward(killerPlayer, killer);
        }

        String finalDeathMessage = deathMessage;
        bsgPlayer.getGame().getAllPlayers().forEach(p -> BlitzSG.send(p, finalDeathMessage));

        bsgPlayer.getGame().killPlayer(victim);
        victim.getWorld().strikeLightningEffect(victim.getLocation());
        bsgPlayer.getGameEntities().forEach(Entity::remove);
        bsgPlayer.getGameEntities().clear();
        bsgPlayer.addDeath();
        BlitzSG.getInstance().getIPlayerManager().handleDeathElo(victim);
        victim.setGameMode(GameMode.ADVENTURE);

        victim.setAllowFlight(true);
        victim.setFlying(true);
        victim.setHealth(20);
        victim.setFoodLevel(20);
        victim.setFireTicks(0);
        victim.setFallDistance(0);
        victim.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1));

        bsgPlayer.getGame().getAlivePlayers().forEach(p -> p.hidePlayer(victim));

        dropInventory(victim);

        victim.teleport(bsgPlayer.getGame().getMap().getLobby());
        victim.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).name("&aSpectate Menu").make());
    }

    private void handleCoinReward(IPlayer killerPlayer, Player killer) {
        int coins = 7 * killerPlayer.getRank().getMultiplier();
        killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);
        String coinMessage;
        if (killerPlayer.isTauntActive()) {
            coins *= 2;
            coinMessage = ChatUtil.color("&6+" + coins + " Coins (Kill) (Taunt)");
        } else {
            coinMessage = ChatUtil.color("&6+" + coins + " Coins (Kill)");
        }
        killer.sendMessage(coinMessage);
        killerPlayer.addCoins(coins);
    }


    private void dropInventory(Player victim) {
        victim.getInventory().forEach(item -> {
            if (item != null && item.getType() != Material.AIR) {
                victim.getWorld().dropItemNaturally(victim.getLocation(), item);
            }
        });
        Arrays.stream(victim.getInventory().getArmorContents())
                .filter(item -> item != null && item.getType() != Material.AIR)
                .forEach(item -> victim.getWorld().dropItemNaturally(victim.getLocation(), item));
        victim.getWorld().spawn(victim.getLocation(), ExperienceOrb.class).setExperience(victim.getTotalExperience());
        victim.getInventory().clear();
        victim.getInventory().setArmorContents(null);
        victim.setTotalExperience(0);
        victim.setLevel(0);
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame() || bsgPlayer.getGame().getGameMode() != Game.GameMode.STARTING) {
            return;
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        if (to.getBlockX() != from.getBlockX() || to.getBlockZ() != from.getBlockZ()) {
            Location spawn = bsgPlayer.getGameSpawn();
            if (spawn == null) {
                bsgPlayer.getGame().resetGame();
                Bukkit.broadcastMessage("Error occurred in game " + bsgPlayer.getGame());
                return;
            }
            spawn.setY(p.getLocation().getY());
            spawn.setDirection(to.getDirection());
            p.teleport(spawn);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        BlitzSG.getInstance().getGameManager().getRunningGames().stream().filter(runningGame -> runningGame.getMap().getWorld() == e.getBlock().getWorld()).forEach(game -> e.blockList().clear());
    }

    @EventHandler
    public void onBlockExplode(EntityExplodeEvent e) {
        BlitzSG.getInstance().getGameManager().getRunningGames().stream().filter(runningGame -> runningGame.getMap().getWorld() == e.getEntity().getWorld()).forEach(game -> e.blockList().clear());
    }

    @EventHandler
    public void onPlayerInteractFire(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (bsgPlayer.isInGame()) {
            return;
        }
        if (bsgPlayer.getRank().isManager() && player.getGameMode() == org.bukkit.GameMode.CREATIVE) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (player.getTargetBlock((Set<Material>) null, 5).getType() == Material.FIRE) {
                event.setCancelled(true);
            }
            if ((event.getClickedBlock().getType().equals(Material.FIRE)) && (event.getAction() == Action.LEFT_CLICK_AIR)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (!bsgPlayer.isInGame()) {
            if (bsgPlayer.getRank().isManager() && p.getGameMode() == org.bukkit.GameMode.CREATIVE) {
                return;
            }
            e.setCancelled(true);
            return;
        }
        if (e.getBlock().getType() == Material.CAKE_BLOCK || e.getBlock().getType() == Material.WEB || e.getBlock().getType() == Material.FIRE) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        Material blockPlacedType = e.getBlockPlaced().getType();
        if (blockPlacedType == Material.CAKE_BLOCK || blockPlacedType == Material.WEB || blockPlacedType == Material.FIRE) {
            return;
        }
        if (!iPlayer.isInGame()) {
            if (iPlayer.getRank().isManager() && p.getGameMode() == org.bukkit.GameMode.CREATIVE) {
                return;
            }
            e.setCancelled(true);
            return;
        }
        if (iPlayer.getGame().getGameMode() != Game.GameMode.INGAME) {
            e.setCancelled(true);
            return;
        }
        if (blockPlacedType == Material.TNT) {
            e.getBlockPlaced().setType(Material.AIR);
            e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
            return;
        }
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

        if (bsgPlayer.getGame().getGameMode() == Game.GameMode.WAITING || bsgPlayer.getGame().getGameMode() == Game.GameMode.STARTING) {
            e.setCancelled(true);
            return;
        }
        if (bsgPlayer.getGame().getGameMode() == Game.GameMode.INGAME) {
            if (bsgPlayer.isSpectating()) {
                e.setCancelled(true);
                return;
            }
            return;
        }
        if (bsgPlayer.getGame().getGameMode() != Game.GameMode.STARTING && bsgPlayer.getGame().getGameMode() != Game.GameMode.WAITING) {
            e.setCancelled(true);
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
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (iPlayer == null || iPlayer.getGame() == null || iPlayer.getGame().getGameMode() == null) return;
        if (iPlayer.getGame().getGameMode() == Game.GameMode.WAITING || iPlayer.getGame().getGameMode() == Game.GameMode.STARTING || iPlayer.getGame().getGameMode() == Game.GameMode.RESETING)
            e.setCancelled(true);
        if (!iPlayer.isInGame() || iPlayer.isSpectating()) {
            e.setCancelled(true);
            return;
        }

        if (iPlayer.getGame().getGameMode() == Game.GameMode.INGAME) {
            if (e instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
                if (event.getDamager() instanceof Player) {
                    Player attacker = (Player) event.getDamager();
                    IPlayer bsgAttacker = BlitzSG.getInstance().getIPlayerManager().getPlayer(attacker.getUniqueId());
                    if (bsgAttacker.isSpectating()) {
                        e.setCancelled(true);
                        return;
                    }
                    iPlayer.setLastDamage(System.currentTimeMillis());
                    iPlayer.setLastDamager(attacker);
                }
            }
            if ((e.getCause() == EntityDamageEvent.DamageCause.LAVA || e.getCause() == EntityDamageEvent.DamageCause.VOID) && iPlayer.getGame().hasDeathMatchStarted()) {
                onPlayerDeath(p, iPlayer.getLastAttacker());
            }
            if (iPlayer.isSpectating()) {
                e.setCancelled(true);
                return;
            }
            if (p.getHealth() - e.getFinalDamage() <= 0) {
                if (!e.isCancelled()) {
                    e.setCancelled(true);
                    onPlayerDeath(p, iPlayer.getLastAttacker());
                }
            }
        }
    }

    @EventHandler
    public void playerPortalEvent(PlayerPortalEvent e) {
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
                    bsgPlayer.setLastDamager((Player) ((Arrow) e.getDamager()).getShooter());
                    if (BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) ((Arrow) e.getDamager()).getShooter()).getUniqueId()).isRobinhood()) {
                        e.setDamage(1000);
                        e.getEntity().sendMessage("&aYou were taken out in a single shot!");
                    } else {
                        e.setDamage(e.getDamage() / 2);
                    }
                    return;
                }
                e.setDamage(e.getDamage() / 7.5);
            }
        }
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

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(e.getPlayer().getUniqueId());
        if (bsgPlayer.isSpectating()) {
            e.setCancelled(true);
        }
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

    @EventHandler
    public void playerOpenVotingMenu(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        if (bsgPlayer.isSpectating()) {
            e.setCancelled(true);
        }
        if (!bsgPlayer.isInGame()) return;
        if (bsgPlayer.getGame().getGameMode() != Game.GameMode.WAITING && bsgPlayer.getGame().getGameMode() != Game.GameMode.STARTING) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (e.getItem().getType() == Material.BOW) {
            KitGUI.openGUI(p);
        }
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
        } else if (e.getItem().getType() == Material.COMPASS) {
            if (bsgPlayer.isSpectating()) {
                SpectatorGUI.openGUI(p);
            }
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
        p.getInventory().setItemInHand(null);
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(e.getPlayer().getUniqueId());
        if (!bsgPlayer.isInGame()) return;
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
            if (e.getPlayer().getItemInHand().getType() == Material.COMPASS && e.getPlayer().isSneaking()) {
                BlitzSG.getInstance().getGameManager().taunt(e.getPlayer());
            }
            return;
        }
        if (!(bsgPlayer.getGame().getGameMode() == Game.GameMode.INGAME)) return;
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                if (!bsgPlayer.getGame().getOpenedChests().contains(e.getClickedBlock().getLocation())) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    chest.getInventory().clear();
                    chestUtils.generateChestLoot(chest.getInventory(), 3, e.getClickedBlock().getLocation());
                    chest.update();
                    bsgPlayer.getGame().getOpenedChests().add(e.getClickedBlock().getLocation());
                }
                if (bsgPlayer.getGame().isStarAvailable() && !(bsgPlayer.getGame().getStarChests().contains(e.getClickedBlock().getLocation()))) {
                    int foo = (int) (Math.random() * 100);
                    if (foo < 12) {
                        Chest chest = (Chest) e.getClickedBlock().getState();
                        chest.getInventory().addItem(new ItemBuilder(Material.NETHER_STAR).name("&6Blitz Star").lores(new String[]{"&7Please in hotbar and right click", "&7to activate your Blitz!", "&7A Blitz is a super-powerful", "&7ability that can change the", "&7course of the game.", "&7Unlock more in the shop", "&7with coins."}).enchantment(Enchantment.LOOT_BONUS_BLOCKS, 1).make());
                        bsgPlayer.getGame().message(String.format("%s%s%s &found the &6Blitz Star&e!", BlitzSG.CORE_NAME, bsgPlayer.getRank(true).getChatColor(), e.getPlayer().getName()));
                        bsgPlayer.getGame().setStarAvailable(false);
                        new FireworkCommand().launchFirework(chest.getLocation());
                        bsgPlayer.getGame().getStarChests().clear();
                    }
                    bsgPlayer.getGame().getStarChests().add(e.getClickedBlock().getLocation());
                }
            }
        }
    }
}