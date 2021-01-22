package me.syesstyles.blitz.cosmetic.cosmetics.taunt;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.cosmetic.Taunt;
import me.syesstyles.blitz.utils.FireworkCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FireworkTaunt extends Taunt {
    public FireworkTaunt(){
        super("fireworkexplosion", "Firework Explosion", "Oh, so many colors!", BlitzSG.getInstance().getRankManager().getRankByName("VIP"), new ItemStack(Material.FIREWORK,1));
    }
    public void go(Player p){
        new FireworkCommand().launchFirework(p.getLocation());
    }
}
