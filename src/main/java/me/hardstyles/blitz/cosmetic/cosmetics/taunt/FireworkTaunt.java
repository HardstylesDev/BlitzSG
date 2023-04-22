package me.hardstyles.blitz.cosmetic.cosmetics.taunt;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.fireworks.FireworkCommand;
import me.hardstyles.blitz.cosmetic.Taunt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FireworkTaunt extends Taunt {
    public FireworkTaunt(){
        super("fireworkexplosion", "Firework Explosion", "Oh, so many colors!", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.FIREWORK,1));
    }
    public void run(Player p){
        new FireworkCommand().launchFirework(p.getLocation());
    }
}
