package me.hardstyles.blitz.utils.loot;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter

public class StaticLoot {
    private final String worldName;
    private final int x;
    private final int y;
    private final int z;
    private final ItemStack itemStack;

    public StaticLoot(String worldName, int x, int y, int z, ItemStack itemStack) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemStack = itemStack;
    }
}
