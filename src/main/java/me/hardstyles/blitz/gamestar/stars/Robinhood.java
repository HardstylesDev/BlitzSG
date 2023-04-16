package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.blitzsgplayer.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Robinhood extends Star {
    public Robinhood() {
        super("Robinhood", Material.BOW, "1 shot kill!", 10000);
    }
    private Game game;
    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

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
