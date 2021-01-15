package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.game.Game;
import me.syesstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Wobbuffet extends Star {
    public Wobbuffet() {
        super("Wobbuffet", Material.SPONGE, "Reflects all damage onto an opponent for 30 seconds!", 10000);
    }
    private Game game;
    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        user.setWobbuffet(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(game != null && game == user.getGame())
                    BlitzSG.send(p, BlitzSG.CORE_NAME+"&aWobbuffet wore off!");
                user.setWobbuffet(false);
            }
        }.runTaskLater(BlitzSG.getInstance(), 30 * 20);
    }
}