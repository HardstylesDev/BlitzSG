package me.hardstyles.blitz.cosmetic;

import lombok.Getter;
import lombok.Setter;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.rank.Rank;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public abstract class Gadget {
    private String name;
    private ItemStack item;
    private String description;
    private long cooldown;
    private long lastUsed;
    private Rank rank;

    public Gadget(String name, ItemStack item, String description) {
        this.name = name;
        this.item = item;
        this.description = description;
        this.cooldown = 5000;
        this.rank = BlitzSG.getInstance().getRankManager().getRankByName("VIP+");

    }

    public boolean canUse(Player player) {
        return System.currentTimeMillis() - lastUsed >= cooldown;
    }
    public abstract void onUse(Player player);

}
