package me.syesstyles.blitz.cosmetic;

import me.syesstyles.blitz.rank.Rank;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Aura {
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
    public int getLocation(){return location;}
    private final ItemStack icon;
    private final String id;
    private final String name;
    private final String description;
    private final Rank requiredRank;
    private final int location;
    public Aura(String id, String name, String description, Rank requiredRank, ItemStack icon, int location) {

        this.id = id;
        this.icon = icon;
        this.name = name;
        this.description = description;
        this.requiredRank = requiredRank;
        this.location = location;
    }

    public void tick(Player p) {
    }


}
