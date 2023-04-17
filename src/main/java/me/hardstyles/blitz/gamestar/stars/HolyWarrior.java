package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HolyWarrior extends Star {
    public HolyWarrior() {
        super("Holy Warrior", Material.BREWING_STAND_ITEM, "Strike nearby enemies with lightning for 30s.", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30 * 20, 2));
        for (int t = 0; t < 30; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME)
                        return;
                    else if (!user.getGame().getAlivePlayers().contains(p))
                        return;
                    List<Entity> a = p.getNearbyEntities(20, 20, 20);
                    for (Entity entity : a) {
                        if (!(entity instanceof Player)) {
                            continue;
                        }
                        if (user.getGame().getAlivePlayers().contains(entity)) {
                            entity.getWorld().strikeLightningEffect(entity.getLocation());
                            Player target = (Player) entity;
                            if (target.getHealth() - 2 <= 0) {
                                IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
                                BlitzSG.getInstance().getGameHandler().onPlayerDeath(target, iPlayer.getLastAttacker());
                            } else {
                                target.damage(2);
                            }
                        }
                    }
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 20);
        }

    }
}