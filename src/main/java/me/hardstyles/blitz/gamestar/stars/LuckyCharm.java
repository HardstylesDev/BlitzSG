package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LuckyCharm extends Star {
    public LuckyCharm() {
        super("Lucky Charm", Material.GOLD_INGOT, "Generate up to 600 exp!", 0);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        for (int t = 0; t < 150; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME)
                        return;
                    else if (!user.getGame().getAlivePlayers().contains(p))
                        return;
                    p.giveExp(3);
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, (float)0.1,(float)1.5);

                }
            }.runTaskLater(BlitzSG.getInstance(), t * 4);
        }

    }
}