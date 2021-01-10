package me.syesstyles.blitz.gamestar.stars;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Assassin extends Star {
    public Assassin(){
        super("Assassin", Material.BONE,"Teleport to a random player and do 10 hearts damage.", 10000);
    }

    @Override
    public void run(Player p) {
        p.getInventory().remove(Material.NETHER_STAR);
        BlitzSGPlayer user = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        List<Player> targets = new ArrayList<>(user.getGame().getAlivePlayers());

        targets.remove(p);
        int index = new Random().nextInt(targets.size());
        Player target = targets.get(index);

        p.teleport(target);
        target.damage(target.getHealth()/2, p);
        target.sendMessage(ChatColor.RED + "You were assassined by " + p.getName());
        p.sendMessage(ChatColor.RED + "You assassined " + target.getName());

    }
}
