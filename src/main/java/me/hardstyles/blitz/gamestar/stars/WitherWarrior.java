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

public class WitherWarrior extends Star {
    public WitherWarrior() {
        super("Wither Warrior", Material.ENDER_STONE, "Nearby players take 10 hearts of ender damage.", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        List<Entity> a = p.getNearbyEntities(20, 20, 20);
        for (Entity entity : a) {
            if (!(entity instanceof Player) || entity == p)
                continue;
            if (user.getGame().getAlivePlayers().contains(entity)) {
                ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 20, 1));
            }
            for (int t = 0; t < 30; ++t) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME)
                            return;
                        else if (!user.getGame().getAlivePlayers().contains(p))
                            return;

                        for (Entity entity : a) {
                            if (!(entity instanceof Player))
                                continue;
                            if (user.getGame().getAlivePlayers().contains(entity)) {
                                if (((Player) entity).getHealth() <= 10)
                                    ((Player) entity).removePotionEffect(PotionEffectType.WITHER);
                            }
                        }
                    }
                }.runTaskLater(BlitzSG.getInstance(), t * 20);
            }

        }
    }
}