package me.syesstyles.blitz.game;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GameMobHandler implements Listener {


    @EventHandler
    public void mobSpawnEvent(PlayerInteractEvent e) {
        if (e.getItem() == null || e.getItem().getType() == null)
            return;
        BlitzSGPlayer sgPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(e.getPlayer().getUniqueId());
        if (!sgPlayer.isInGame())
            return;
        if (!(sgPlayer.getGame().getGameMode() == GameMode.INGAME))
            return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem().getType() == Material.MONSTER_EGG) {
            EntityType entityType = ((SpawnEgg) e.getPlayer().getItemInHand().getData()).getSpawnedType();
            if (entityType != null) {
                e.setCancelled(true);
                ItemStack itemInHand = e.getPlayer().getItemInHand();
                itemInHand.setAmount(itemInHand.getAmount() - 1);
                e.getPlayer().setItemInHand(itemInHand);


                Entity entity = e.getClickedBlock().getWorld().spawn(e.getClickedBlock().getLocation().add(0, 1, 0), entityType.getEntityClass());
                if (entity instanceof Horse) {
                    ((Horse) entity).setColor(Horse.Color.BLACK);
                    ((Horse) entity).setTamed(true);
                    ((Horse) entity).setAdult();
                    ((Horse) entity).getInventory().setSaddle(new ItemStack(Material.SADDLE));
                    ((Horse) entity).setMaxHealth(30);
                    ((Horse) entity).setJumpStrength(0.2);
                }
                entity.setCustomName(null);
                entity.setCustomNameVisible(false);
                sgPlayer.getGameEntities().add(entity);
                for (Entity entityList : entity.getNearbyEntities(15, 15, 15))
                    if (entityList instanceof Player) {
                        Player potentialTarget = (Player) entityList;
                        if (!sgPlayer.getGame().isDead(potentialTarget) && potentialTarget != e.getPlayer())
                            ((Monster) entity).setTarget(potentialTarget);

                    }
            } else {


                //it's a snowman.

                e.setCancelled(true);
                ItemStack itemInHand = e.getPlayer().getItemInHand();
                itemInHand.setAmount(itemInHand.getAmount() - 1);
                e.getPlayer().setItemInHand(itemInHand);


                Entity entity = e.getClickedBlock().getWorld().spawn(e.getClickedBlock().getLocation().add(0, 1, 0), EntityType.SNOWMAN.getEntityClass());
                ((Snowman) entity).setHealth(2);
                entity.setCustomName(null);
                entity.setCustomNameVisible(false);
                ((Snowman) entity).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1, false, false));
                sgPlayer.getGameEntities().add(entity);
                for (Entity entityList : entity.getNearbyEntities(15, 15, 15))
                    if (entityList instanceof Player) {
                        Player potentialTarget = (Player) entityList;
                        if (!sgPlayer.getGame().isDead(potentialTarget) && potentialTarget != e.getPlayer()) {
                            ((Golem) entity).setTarget(potentialTarget);
                            ((Golem) entity).launchProjectile(Snowball.class);
                        }

                    }
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player) {
            Player target = (Player) e.getTarget();
            BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(target.getUniqueId());
            if (blitzSGPlayer.getGameEntities().contains(e.getEntity())) {
                e.setCancelled(true);
                for (Entity entity : e.getEntity().getNearbyEntities(15, 15, 15))
                    if (entity instanceof Player) {
                        Player potentialTarget = (Player) entity;
                        if (!blitzSGPlayer.getGame().isDead(potentialTarget) && potentialTarget != target)
                            e.setTarget(potentialTarget);

                    }
            }
        }
    }

    @EventHandler
    public void onBurn(EntityCombustEvent e) {
        if (e.getEntity() instanceof Zombie || e.getEntity() instanceof Skeleton)
            e.setCancelled(true);
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Snowman) {
            Vector v = e.getEntity().getVelocity();

            for (int a = 0; a < 2; ++a)
                new BukkitRunnable() {
                    @Override
                    public void run() {
                            shoot((Entity) e.getEntity().getShooter());

                    }
                }.runTaskLater(BlitzSG.getInstance(), a * 8);
        }
    }

    public void shoot(Entity shooter) {
        Vector vector = ((Snowman) shooter).getTarget().getLocation().toVector().subtract(shooter.getLocation().toVector()).normalize();


        Entity snowball = shooter.getWorld().spawn(((Snowman) shooter).getEyeLocation().add(Math.random() - 0.5, Math.random() - 0.2, Math.random() - 0.5), Snowball.class);
        ((Snowball) snowball).setShooter((ProjectileSource) shooter);
        snowball.setVelocity(vector);

    }

}