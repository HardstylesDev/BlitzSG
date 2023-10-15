package me.hardstyles.blitz.cosmetic;

import lombok.Getter;
import me.hardstyles.blitz.rank.Rank;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class Taunt {

    private final ItemStack icon;
    private final String id;
    private final String name;
    private final String description;
    private final Rank requiredRank;
    public Taunt(String id, String name, String description, Rank requiredRank, ItemStack icon) {

        this.id = id;
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.requiredRank = requiredRank;

    }

    public abstract void run(Player p);


}
