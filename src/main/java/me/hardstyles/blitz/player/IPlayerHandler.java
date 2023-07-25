package me.hardstyles.blitz.player;

import me.hardstyles.blitz.menu.impl.shop.ShopGUI;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.util.BookUtility;
import me.hardstyles.blitz.util.ChatUtil;
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
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)); //todo change back
        if (iPlayer.getRank().isVip()) {
            p.setAllowFlight(true);
            p.setFlying(true);
        }
        if (iPlayer.getNick() != null && iPlayer.getNick().isNicked()) {
            Nickname nickname = new Nickname();
            if (iPlayer.getNick().getSkinSignature() == null) {
                if (iPlayer.getRank().getPosition() > 4) {
                    for (Player member : Bukkit.getWorld("world").getPlayers()) {
                        member.sendMessage(ChatUtil.color("&e" + iPlayer.getRank().getPrefix() + p.getName() + "&6 joined the lobby!"));
                    }
                }

                iPlayer.getNick().setNicked(true);
                p.kickPlayer(ChatColor.GREEN + "Re-applied nick, please rejoin");
                String[] skin = nickname.prepareSkinTextures(p, iPlayer.getNick().getNickName());
                iPlayer.getNick().setNicked(true);
                iPlayer.getNick().setSkinValue(skin[0]);
                iPlayer.getNick().setSkinSignature(skin[1]);
            } else {
                nickname.setNick(p, iPlayer.getNick().getNickName(), true);
            }
        } else {
            Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
                p.setPlayerListName(iPlayer.getRank(true).getPrefix() + p.getName());
                if (iPlayer.getRank().getPosition() > 4) {
                    for (Player member : Bukkit.getWorld("world").getPlayers()) {
                        member.sendMessage(ChatUtil.color("&e" + iPlayer.getRank(true).getPrefix() + p.getName() + "&6 joined the lobby!"));
                    }
                }
            }, 10L);

        }
        if (iPlayer.getRank() == null) {
            iPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName("Default"));
        }
        p.setPlayerListName(iPlayer.getRank(true).getPrefix() + p.getName());
        BlitzSG.getInstance().getIPlayerManager().toLobby(p);

        p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));

        Bukkit.getOnlinePlayers().forEach(player -> {
            IPlayer oPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
            oPlayer.getNametag().sendPacket(p);
        });

        for (Party party : BlitzSG.getInstance().getPartyManager().getParties()) {
            if(party.getMembers().contains(p.getUniqueId()) || party.getOwner() == iPlayer.getUuid()){
                iPlayer.setParty(party);
                party.message(ChatUtil.color("&9Party > " + iPlayer.getRank().getPrefix() + p.getName() + " §6has returned!"));
            }
        }
    }


    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(e.getPlayer().getUniqueId());
        if (iPlayer.getMute() != null) {
            if (iPlayer.getMute().isMuted()) {
                e.getPlayer().sendMessage(ChatUtil.color("&cYou are currently muted for \"" + iPlayer.getMute().getReason() + "\""));
                e.getPlayer().sendMessage(ChatUtil.color("&cThis mute will expire in " + ChatUtil.formatDate(iPlayer.getMute().getEndTime())));
                e.setCancelled(true);
                return;
            }
        }
        String format = ChatUtil.color("&r[&7" + iPlayer.getKills() + "&r] " + iPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (iPlayer.getRank(true).getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage());
        if(iPlayer.isSpectating()){
            format = ChatUtil.color("&7[SPECTATOR] &r[&7" + iPlayer.getKills() + "&r] " + iPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (iPlayer.getRank(true).getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage());

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
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (iPlayer.isInGame()) return;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getItem() == null) return;
        if (iPlayer.getRank().isManager() && p.getGameMode() == GameMode.CREATIVE) return;
        e.setCancelled(true);
        if (e.getItem().getType() == Material.EMERALD) ShopGUI.openGUI(p);
        if(e.getItem().getType() == Material.PAINTING) {
            BookUtility bookUtility = new BookUtility();
            bookUtility.title("" + iPlayer.getRank(true).getPrefix() + p.getName() + "'s Profile");
            bookUtility.author("BlitzSG");
            ArrayList<String> pages = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append("&7Kills: &a").append(iPlayer.getKills());
            sb.append("\n&7Wins: &a").append(iPlayer.getWins());
            sb.append("\n&7Deaths: &a").append(iPlayer.getDeaths());
            double kdr = (double) iPlayer.getKills() / iPlayer.getDeaths();
            kdr = Math.round(kdr * 100.0) / 100.0;
            sb.append("\n&7K/D: &a").append(kdr);
            sb.append("\n");
            sb.append("\n&7Coins: &a").append(iPlayer.getCoins());
            sb.append("\n");
            sb.append("\n&7Rank: &a").append(iPlayer.getRank(true).getPrefix());
            sb.append("\n&7Kit: &a").append(iPlayer.getSelectedKit() == null ? "None" : iPlayer.getSelectedKit().getName());
            sb.append("\n&7Aura: &a").append(iPlayer.getAura() == null ? "None" : iPlayer.getAura().getName());
            if(iPlayer.getMute() != null) {
                if(iPlayer.getMute().isMuted()) {
                    sb.append("\n");
                    sb.append("\n&7Mute: &cMuted");
                    sb.append("\n&7Reason: &c").append(iPlayer.getMute().getReason());
                    sb.append("\n&7Expires: &c").append(ChatUtil.formatDate(iPlayer.getMute().getEndTime()));
                }
            }
            pages.add(ChatUtil.color(sb.toString()));
            bookUtility.pages(pages);
            bookUtility.open(p);
        }
        if (e.getItem().getType() == Material.WATCH) {
            if (iPlayer.isVisibilityEnabled()) {
                iPlayer.setVisibilityEnabled(false);
                BlitzSG.getInstance().getIPlayerManager().applyVisibility(p);
            } else {
                iPlayer.setVisibilityEnabled(true);
                BlitzSG.getInstance().getIPlayerManager().applyVisibility(p);
            }
            p.sendMessage(ChatUtil.color("&eYou have toggled player visibility. If you want to change your view level, right click the watch again."));

        } else if (e.getItem().getType() == Material.IRON_SWORD) {
            if (BlitzSG.getInstance().getGameManager().getAvailableGame() == null) {
                p.sendMessage("§cCouldn't find any available games (0x1)");
                return;
            }
            BlitzSG.getInstance().getGameManager().getAvailableGame().addPlayer(p);
        }

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

        if (!(attacker.getRank().isMvpPlus())) {
            return;
        }
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
