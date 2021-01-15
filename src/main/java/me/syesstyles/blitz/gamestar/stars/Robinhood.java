package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import sun.java2d.loops.Blit;

public class Robinhood extends Star {
    public Robinhood() {
        super("Robinhood", Material.BOW, "1 shot kill!", 10000);
    }
    private Game game;
    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        user.setRobinhood(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(game != null && game == user.getGame())
                    BlitzSG.send(p, BlitzSG.CORE_NAME+"&aRobinhood wore off!");
                user.setRobinhood(false);
            }
        }.runTaskLater(BlitzSG.getInstance(), 30 * 20);
    }
}
