package me.hardstyles.blitz.player;

import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gui.ShopGUI;
import me.hardstyles.blitz.rank.ranks.*;
import me.hardstyles.blitz.utils.BookUtility;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.nickname.Nickname;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.logging.Level;

public class IPlayerHandler implements Listener {

    @EventHandler
    public void banCheck(AsyncPlayerPreLoginEvent e) {
        BlitzSG.getInstance().getPunishmentManager().handlePreLogin(e);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        BlitzSG.getInstance().getStatisticsManager().loadPlayer(e.getPlayer().getUniqueId());

        Player p = e.getPlayer();

        p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
        IPlayer sgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)); //todo change back
        if (sgPlayer.getRank().isVip()) {
            p.setAllowFlight(true);
            p.setFlying(true);
        }
        if (sgPlayer.getNick() != null && sgPlayer.getNick().isNicked()) {
            Nickname nickname = new Nickname();
            if (sgPlayer.getNick().getSkinSignature() == null) {
                if (sgPlayer.getRank().getPosition() > 4) {
                    for (Player member : Bukkit.getWorld("world").getPlayers()) {
                        member.sendMessage(ChatUtil.color("&e" + sgPlayer.getRank().getPrefix() + p.getName() + "&6 joined the lobby!"));
                    }
                }

                sgPlayer.getNick().setNicked(true);
                p.kickPlayer(ChatColor.GREEN + "Re-applied nick, please rejoin");
                String[] skin = nickname.prepareSkinTextures(p, sgPlayer.getNick().getNickName());
                sgPlayer.getNick().setNicked(true);
                sgPlayer.getNick().setSkinValue(skin[0]);
                sgPlayer.getNick().setSkinSignature(skin[1]);
            } else {
                nickname.setNick(p, sgPlayer.getNick().getNickName(), true);
            }
        } else {
            Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
                p.setPlayerListName(sgPlayer.getRank(true).getPrefix() + p.getName());
                if (sgPlayer.getRank().getPosition() > 4) {
                    for (Player member : Bukkit.getWorld("world").getPlayers()) {
                        member.sendMessage(ChatUtil.color("&e" + sgPlayer.getRank(true).getPrefix() + p.getName() + "&6 joined the lobby!"));
                    }
                }
            }, 10L);

        }
        if (sgPlayer.getRank() == null) {
            sgPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName("Default"));
        }
        p.setPlayerListName(sgPlayer.getRank(true).getPrefix() + p.getName());
        BlitzSG.getInstance().getIPlayerManager().toLobby(p);

        p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));

        Bukkit.getOnlinePlayers().forEach(player -> {
            IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
            iPlayer.getNametag().sendPacket(p);
        });
        //if (BlitzSG.getInstance().getGameManager().getAllWaitingGames().size() == 0) {
            //if (BlitzSG.getInstance().getStartTime() + 30000 > System.currentTimeMillis()) {
                //new Game();
                //Bukkit.getLogger().log(Level.FINEST, "New Game instance created by IPlayerHandler");
            //}//
        //}

        World world = Bukkit.getWorld("world");
        for (Player player : world.getPlayers()) {
            IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
            if (iPlayer.isVisibilityEnabled()) {
                continue;
            }
            player.hidePlayer(p);
        }

    }


    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e) {
        IPlayer sgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(e.getPlayer().getUniqueId());
        if (sgPlayer.getMute() != null) {
            if (sgPlayer.getMute().isMuted()) {
                e.getPlayer().sendMessage(ChatUtil.color("&cYou are currently muted for \"" + sgPlayer.getMute().getReason() + "\""));
                e.getPlayer().sendMessage(ChatUtil.color("&cThis mute will expire in " + ChatUtil.formatDate(sgPlayer.getMute().getEndTime())));
                e.setCancelled(true);
                return;
            }
        }
        String format = ChatUtil.color("&r[&7" + sgPlayer.getKills() + "&r] " + sgPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (sgPlayer.getRank(true).getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage());
        if(sgPlayer.isSpectating()){
            format = ChatUtil.color("&7[SPECTATOR] &r[&7" + sgPlayer.getKills() + "&r] " + sgPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (sgPlayer.getRank(true).getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage());

        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().equals(e.getPlayer().getWorld())) {
                p.sendMessage(format);
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void voidDamage(EntityDamageEvent e) {
        if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            if (e.getEntity() instanceof Player) {
                e.getEntity().teleport(BlitzSG.lobbySpawn);
            }
        }
    }

    @EventHandler
    public void playerLobbyInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        IPlayer uhcPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (uhcPlayer.isInGame()) return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (uhcPlayer.getRank().isManager() && p.getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
        if (e.getItem().getType() == Material.EMERALD) ShopGUI.openGUI(p);
        if(e.getItem().getType() == Material.PAINTING) {
            BookUtility bookUtility = new BookUtility();
            bookUtility.title("" + uhcPlayer.getRank(true).getPrefix() + p.getName() + "'s Profile");
            bookUtility.author("BlitzSG");
            ArrayList<String> pages = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append("&7Kills: &a" + uhcPlayer.getKills());
            sb.append("\n&7Wins: &a" + uhcPlayer.getWins());
            sb.append("\n&7Deaths: &a" + uhcPlayer.getDeaths());
            double kdr = (double) uhcPlayer.getKills() / uhcPlayer.getDeaths();
            kdr = Math.round(kdr * 100.0) / 100.0;
            sb.append("\n&7K/D: &a" + kdr);
            sb.append("\n");
            sb.append("\n&7Coins: &a" + uhcPlayer.getCoins());
            sb.append("\n");
            sb.append("\n&7Rank: &a" + uhcPlayer.getRank(true).getPrefix());
            sb.append("\n&7Kit: &a" + (uhcPlayer.getSelectedKit() == null ? "None" : uhcPlayer.getSelectedKit().getName()));
            sb.append("\n&7Aura: &a" + (uhcPlayer.getAura() == null ? "None" : uhcPlayer.getAura().getName()));
            if(uhcPlayer.getMute() != null) {
                if(uhcPlayer.getMute().isMuted()) {
                    sb.append("\n");
                    sb.append("\n&7Mute: &cMuted");
                    sb.append("\n&7Reason: &c" + uhcPlayer.getMute().getReason());
                    sb.append("\n&7Expires: &c" + ChatUtil.formatDate(uhcPlayer.getMute().getEndTime()));
                }
            }
            pages.add(ChatUtil.color(sb.toString()));
            bookUtility.pages(pages);
            bookUtility.open(p);
        }
        if (e.getItem().getType() == Material.WATCH) {
            if (uhcPlayer.isVisibilityEnabled()) {
                uhcPlayer.setVisibilityEnabled(false);
                p.sendMessage(ChatUtil.color("&cYou have disabled visibility"));
                World world = Bukkit.getWorld("world");
                for (Player player : world.getPlayers()) {
                    if (player.getUniqueId() != p.getUniqueId()) {
                        p.hidePlayer(player);
                    }
                }
            } else {
                uhcPlayer.setVisibilityEnabled(true);
                p.sendMessage(ChatUtil.color("&aYou have enabled visibility"));
                World world = Bukkit.getWorld("world");
                for (Player player : world.getPlayers()) {
                    if (player.getUniqueId() != p.getUniqueId()) {
                        p.showPlayer(player);
                    }
                }
            }
        } else if (e.getItem().getType() == Material.IRON_SWORD) {
            if (BlitzSG.getInstance().getGameManager().getAvailableGame() == null) {
                p.sendMessage("§cCouldn't find any available games (0x1)");
                return;
            }
            BlitzSG.getInstance().getGameManager().getAvailableGame().addPlayer(p);
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
        if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }

        if (!(e.getEntity() instanceof Player && e.getDamager() instanceof Player)) {
            return;
        }

        IPlayer victim = BlitzSG.getInstance().getIPlayerManager().getPlayer(e.getEntity().getUniqueId());
        if (victim.getGame() != null) return;
        if (!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)) return;

        if (!(victim.getRank().isStaff())) {
            return;
        }

        IPlayer attacker = BlitzSG.getInstance().getIPlayerManager().getPlayer(e.getDamager().getUniqueId());

        if (!(attacker.getRank() instanceof Admin) && !(attacker.getRank() instanceof Moderator) && !(attacker.getRank() instanceof Helper) && !(attacker.getRank() instanceof MvpPlus) && !(attacker.getRank() instanceof Youtuber))
            return;
        if (victim.isPunched()) return;
        victim.setPunched(true);
        BlitzSG.broadcast(attacker.getRank().getPrefix() + e.getDamager().getName() + " &7punched " + victim.getRank().getChatColor() + e.getEntity().getName() + " &7into the sky!", e.getDamager().getWorld());
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
