package me.syesstyles.blitz.blitzsgplayer;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.gui.ShopKitGUI;
import me.syesstyles.blitz.rank.ranks.*;
import me.syesstyles.blitz.utils.nickname.Nickname;
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

public class BlitzSGPlayerHandler implements Listener {

    @EventHandler
    public void banCheck(AsyncPlayerPreLoginEvent e) {
        BlitzSG.getInstance().getPunishmentManager().handlePreLogin(e);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        p.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        p.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        p.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        p.getInventory().setBoots(new ItemStack(Material.AIR, 1));
        BlitzSGPlayer uhcPlayer;
        if (BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()) == null)
            uhcPlayer = new BlitzSGPlayer(e.getPlayer().getUniqueId());
        uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0)); //todo change back
        if (!(uhcPlayer.getRank() instanceof Default)) {
            p.setAllowFlight(true);
            p.setFlying(true);
        }
        if (uhcPlayer.getNick() != null && uhcPlayer.getNick().isNicked()) {
            e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
            Nickname nickname = new Nickname();
            if (uhcPlayer.getNick().getSkinSignature() == null) {
                e.setJoinMessage((ChatColor.YELLOW + uhcPlayer.getNick().getNickName() + " joined the game").replaceAll("  ", " "));
                uhcPlayer.getNick().setNicked(true);
                p.kickPlayer(ChatColor.GREEN + "Re-applied nick, please rejoin");
                String[] skin = nickname.prepareSkinTextures(p, uhcPlayer.getNick().getNickName());
                uhcPlayer.getNick().setNicked(true);
                uhcPlayer.getNick().setSkinValue(skin[0]);
                uhcPlayer.getNick().setSkinSignature(skin[1]);
            } else {
                nickname.setNick(p, uhcPlayer.getNick().getNickName(), true);
            }
        } else
            e.setJoinMessage((ChatColor.YELLOW + p.getName() + " joined the game").replaceAll("  ", " "));

        if (uhcPlayer.getRank() == null)
            uhcPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName("Default"));

        p.setPlayerListName(uhcPlayer.getRank(true).getPrefix() + p.getName() + BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix()
                + " [" + uhcPlayer.getElo() + "]");

        BlitzSG.getInstance().getBlitzSGPlayerManager().setLobbyInventoryAndNameTag(p);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

    }

    @EventHandler
    public void onAsyncChat(PlayerChatEvent e) {
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId());
        e.setFormat(uhcPlayer.getRank(true).getPrefix() + e.getPlayer().getName() + (uhcPlayer.getRank(true).getPrefix().equalsIgnoreCase(ChatColor.GRAY + "") ? ChatColor.GRAY + ": " : ChatColor.WHITE + ": ") + e.getMessage());
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
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID))
            if (e.getEntity() instanceof Player)
                e.getEntity().teleport(BlitzSG.lobbySpawn);
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
        if (e.getItem().getType() == Material.EMERALD)
            ShopKitGUI.openGUI(p);
        else if (e.getItem().getType() == Material.IRON_SWORD) {
            if (BlitzSG.getInstance().getGameManager().getAvailableGame() == null) {
                p.sendMessage("§cCouldn't find any available games");
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
        System.out.println("1");
        if (!(e.getEntity() instanceof Player && e.getDamager() instanceof Player))
            return;
        System.out.println("2");
        if (!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK))
            return;
        System.out.println("3");
        BlitzSGPlayer victim = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getEntity().getUniqueId());
        if (!(victim.getRank() instanceof Admin) && !(victim.getRank() instanceof Moderator) && !(victim.getRank() instanceof Helper))
            return;
        System.out.println("4");
        BlitzSGPlayer attacker = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getDamager().getUniqueId());

        if (!(attacker.getRank() instanceof Admin) && !(attacker.getRank() instanceof Moderator) && !(attacker.getRank() instanceof Helper) && !(attacker.getRank() instanceof MvpPlus) && !(attacker.getRank() instanceof Youtuber))
            return;
        System.out.println("5");
        if (victim.getPunched())
            return;
        System.out.println("6");
        //todo punching
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
