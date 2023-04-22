package me.hardstyles.blitz.cosmetic.cosmetics.taunt;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Taunt;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IRefuseTaunt extends Taunt {
    public IRefuseTaunt() {
        super("irefuse", "I Refuse!", "While taunting, you will be grumpy.", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.REDSTONE, 1));
    }

    public void run(Player p) {
        p.getWorld().getPlayers().forEach(player -> player.playSound(p.getLocation(), Sound.VILLAGER_HIT, 1, 1));
    }
}
