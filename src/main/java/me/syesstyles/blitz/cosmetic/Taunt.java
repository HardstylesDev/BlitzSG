package me.syesstyles.blitz.cosmetic;

import me.syesstyles.blitz.rank.Rank;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Taunt {
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public ItemStack getIcon() {
        return icon;
    }
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

    public void go(Player p) {
    }


}
