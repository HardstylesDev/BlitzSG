package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.utils.ChatUtil;
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
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        user.setWobbuffet(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (game != null && game == user.getGame()) {
                    p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&aWobbuffet wore off!"));
                }
                user.setWobbuffet(false);
            }
        }.runTaskLater(BlitzSG.getInstance(), 30 * 20);
    }
}