package me.hardstyles.blitz.gamestar.stars;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Kamikaze extends Star {
    public Kamikaze() {
        super("Kamikazi", Material.EXPLOSIVE_MINECART, "Places a primed TNT on your head.", 10000);
    }

    int time = 0;

    @Override
    public void run(Player p) {
        time = 0;
        p.getInventory().remove(Material.NETHER_STAR);
        IPlayer user = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        p.getInventory().setHelmet(new ItemStack(Material.TNT));

        for (int t = 0; t < 31; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {

                    // p.sendMessage("aaa");
                    if (user.getGame() == null || user.getGame().getGameMode() == null || user.getGame().getGameMode() != Game.GameMode.INGAME) {
                        //   p.sendMessage("niggeddddr");
                        return;
                    } else if (!user.getGame().getAlivePlayers().contains(p)) {
                        //    p.sendMessage("nigger");
                        return;
                    }
                    if (time >= 24 && time < 29)
                        user.getGame().msgAll(BlitzSG.CORE_NAME + "&eExploding in " + (29 - time) + " second" + (((29 - time) == 1) ? "" : "s") + "!");
                    if (time == 29) {
                        user.getGame().msgAll(BlitzSG.CORE_NAME + "&6BOOM!");
                        p.getWorld().createExplosion(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 20, true, false);
                        List<Entity> a = p.getNearbyEntities(10, 10, 10);
                        for (Entity entity : a) {
                            if (!(entity instanceof Player))
                                continue;
                            if (user.getGame().getAlivePlayers().contains(entity)) {
                                if (entity != p)
                                    ((Player) entity).damage(100);
                            }
                        }
                    }
                    if (time == 30)
                        p.damage(100);
                    time++;
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 20);
        }

    }
}