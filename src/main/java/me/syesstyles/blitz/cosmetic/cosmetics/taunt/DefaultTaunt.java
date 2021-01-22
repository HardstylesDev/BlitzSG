package me.syesstyles.blitz.cosmetic.cosmetics.taunt;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.cosmetic.Taunt;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DefaultTaunt extends Taunt {
    public DefaultTaunt(){
        super("default", "Default", "The default taunt", BlitzSG.getInstance().getRankManager().getRankByName("Default"), new ItemStack(Material.MAGMA_CREAM,1));
    }
    public void uh(){

    }
}
