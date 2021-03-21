package me.syesstyles.blitz.blitzsgplayer;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.gui.ShopGUI;
import me.syesstyles.blitz.rank.ranks.*;
import me.syesstyles.blitz.utils.nickname.Nickname;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import redis.clients.jedis.Jedis;

public class BlitzSGPlayerHandler implements Listener {



    @EventHandler
    public void banCheck(AsyncPlayerPreLoginEvent e) {
        BlitzSG.getInstance().getPunishmentManager().handlePreLogin(e);
    }

    @EventHandler
    public void onConnect(PlayerPreLoginEvent e) {
        // Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
        BlitzSG.getInstance().getStatisticsManager().load(e.getUniqueId());
        BlitzSG.getInstance().getBlitzSGPlayerManager().addBsgPlayer(e.getUniqueId(), BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getUniqueId()));
        //  });

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
        p.sendMessage("");
        e.setJoinMessage("");
        p.sendTitle(ChatColor.RESET + "", ChatColor.RESET + "");
        p.setFireTicks(0);
        p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
        BlitzSG.getInstance().getNametagManager().update();
        BlitzSGPlayer uhcPlayer;
        if (BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()) == null)
            uhcPlayer = new BlitzSGPlayer(e.getPlayer().getUniqueId());
        uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        uhcPlayer.setName(p.getDisplayName());
        uhcPlayer.setIp(p.getAddress().toString().split(":")[0].replaceAll("/", ""));
        if (uhcPlayer.getRank() != null && uhcPlayer.getRank(true).getPosition() > 3)
            e.setJoinMessage(uhcPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + ChatColor.GOLD + " joined the lobby!");
        if (uhcPlayer.getAura() != null) BlitzSG.getInstance().getCosmeticsManager().add(p);
        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)); //todo change back

      // if (uhcPlayer.getNick() != null && uhcPlayer.getNick().isNicked()) {

      //     //e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
      //     Nickname nickname = new Nickname();
      //     if (uhcPlayer.getNick().getSkinSignature() == null) {
      //         // e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
      //         uhcPlayer.getNick().setNicked(true);
      //         p.kickPlayer(ChatColor.GREEN + "Re-applied nick, please rejoin");
      //         String[] skin = nickname.prepareSkinTextures(p, uhcPlayer.getNick().getNickName());
      //         uhcPlayer.getNick().setNicked(true);
      //         uhcPlayer.getNick().setSkinValue(skin[0]);
      //         uhcPlayer.getNick().setSkinSignature(skin[1]);
      //     } else {
      //         nickname.setNick(p, uhcPlayer.getNick().getNickName(), true);
      //     }
      // } else
            // e.setJoinMessage((ChatColor.YELLOW + p.getName() + " joined the game").replaceAll("  ", " "));

            if (uhcPlayer.getRank() == null)
                uhcPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName("Default"));




        BlitzSGPlayer finalUhcPlayer1 = uhcPlayer;
        Bukkit.getScheduler().runTaskLaterAsynchronously(BlitzSG.getInstance(), () -> {
            BlitzSG.getInstance().getBlitzSGPlayerManager().setLobbyInventoryAndNameTag(p);
            p.setPlayerListName(finalUhcPlayer1.getRank(true).getPrefix() + p.getName());
            Bukkit.getOnlinePlayers().forEach(player -> updateHide(player));
            if (finalUhcPlayer1.getCustomTag() != null)
                p.setPlayerListName(finalUhcPlayer1.getRank(true).getPrefix() + p.getName() + ChatColor.GRAY + " [" + finalUhcPlayer1.getCustomTag() + "]");
            else
                p.setPlayerListName(finalUhcPlayer1.getRank(true).getPrefix() + p.getName());
        }, 1);
        BlitzSGPlayer finalUhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        Bukkit.getScheduler().runTaskLaterAsynchronously(BlitzSG.getInstance(), () -> {
            if (finalUhcPlayer.getRank().getPosition() > 0) {
                p.setAllowFlight(true);
                p.setFlying(true);
            }
            BlitzSG.getInstance().getNametagManager().update();
        }, 10);


    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

    }

    @EventHandler
    public void onRespawn(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }


    @EventHandler
    public void onFirePunch(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock().getLocation().add(0, 1, 0).getBlock().getType().equals(Material.FIRE)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAsyncChat(PlayerChatEvent e) {
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId());
        e.setFormat(uhcPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (uhcPlayer.getRank().getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage().replaceAll("%", "%%"));
    }

    //BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
    //e.setFormat(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
    //        + e.getPlayer().getName() + ": §f" + e.getMessage());
    //Bukkit.broadcastMessage(BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix() + "[" + uhcPlayer.getElo() + "] "
    //		+ e.getPlayer().getName() + ": §f" + e.getMessage());

    @EventHandler
    public void voidDamage(EntityDamageEvent e) {
        if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world"))
            return;
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            if (e.getEntity() instanceof Player)
                e.getEntity().teleport(BlitzSG.lobbySpawn);
        } else if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK))
            e.getEntity().setFireTicks(0);
    }

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
        if (e.getItem().getType() == Material.COMPASS)
            return;
        if (e.getItem().getType() == Material.WATCH) {
            uhcPlayer.setHideOthers(!uhcPlayer.doesHideOthers());
            p.sendMessage(ChatColor.GREEN + (uhcPlayer.doesHideOthers() ? "Other players won't be visible anymore!" : "You can now see other players!"));
            this.updateHide(p);
        }

        if (e.getItem().getType() == Material.EMERALD)
            ShopGUI.openGUI(p);
        if (e.getItem().getType() == Material.NETHER_STAR)
            return;
        else if (e.getItem().getType() == Material.IRON_SWORD) {
            if (BlitzSG.getInstance().getGameManager().getAvailableGame() == null) {
                p.sendMessage("§cCouldn't find any available games");
                return;
            }
            BlitzSG.getInstance().getGameManager().getAvailableGame().addPlayer(p);
        }

    }


    @EventHandler
    public void playerLobbySignEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (uhcPlayer.isInGame())
            return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getClickedBlock().getType() != Material.WALL_SIGN && e.getClickedBlock().getType() != Material.SIGN_POST)
            return;
        e.setCancelled(true);
        if (!(e.getClickedBlock().getState() instanceof Sign))
            return;
        Sign sign = (Sign) e.getClickedBlock().getState();
        if (sign.getLine(0).contains("[Join]"))
            Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
                try {
                    Jedis jedisResource = BlitzSG.getInstance().getJedisPool().getResource();
                    String canJoin = jedisResource.get("canJoin");
                    jedisResource.close();
                    if (Boolean.parseBoolean(canJoin) == false) {
                        p.sendMessage(ChatColor.RED + "No games available at the moment!");
                        return;
                    }
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF("hub");

                    p.sendPluginMessage(BlitzSG.getInstance(), "BungeeCord", out.toByteArray());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    p.sendMessage(ChatColor.RED + "No games available at the moment! " + ChatColor.GRAY + "(e)");

                }
            });
    }


    private void updateHide(Player p) {
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        if (uhcPlayer.doesHideOthers()) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer != p) {
                    p.hidePlayer(onlinePlayer);

                }
            }
        } else {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer != p) {
                    p.showPlayer(onlinePlayer);
                }
            }
        }
    }

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

    @EventHandler
    public void onLobbyPunch(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player && e.getDamager() instanceof Player))
            return;

        BlitzSGPlayer victim = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getEntity().getUniqueId());
        if (victim.getGame() != null) return;
        if (!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK))
            return;

        if (!(victim.getRank(true) instanceof Admin) && !(victim.getRank(true) instanceof Moderator) && !(victim.getRank(true) instanceof Helper))
            return;

        BlitzSGPlayer attacker = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getDamager().getUniqueId());

        if (!(attacker.getRank() instanceof Admin) && !(attacker.getRank() instanceof Moderator) && !(attacker.getRank() instanceof Helper) && !(attacker.getRank() instanceof MvpPlus) && !(attacker.getRank() instanceof Youtuber))
            return;
        if (victim.getPunched())
            return;
        victim.setPunched(true);
        BlitzSG.broadcast(attacker.getRank(true).getPrefix() + e.getDamager().getName() + " &7punched " + victim.getRank().getChatColor() + e.getEntity().getName() + " &7into the sky!", e.getDamager().getWorld());
        e.getEntity().getLocation().getWorld().createExplosion(e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY(), e.getEntity().getLocation().getZ(), 2, false, false);
        ((Player) e.getEntity()).setAllowFlight(true);
        ((Player) e.getEntity()).setFlying(true);
        e.getEntity().setVelocity(new Vector(0, 3, 0));
        new BukkitRunnable() {
            public void run() {
                victim.setPunched(false);
            }
        }.runTaskLater(BlitzSG.getInstance(), 20 * 20);
    }
}
