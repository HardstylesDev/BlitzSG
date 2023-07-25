package me.hardstyles.blitz.util.loot;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

@Getter

public class StaticLoot {
    private final String worldName;
    private final int x;
    private final int y;
    private final int z;
    private final ArrayList<ItemStack> itemStack;

    public StaticLoot(String worldName, int x, int y, int z, ArrayList<ItemStack> itemStack) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemStack = itemStack;
    }
}
