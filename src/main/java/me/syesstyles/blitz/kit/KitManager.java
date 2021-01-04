package me.syesstyles.blitz.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class KitManager {

    public ArrayList<Kit> kits;

    public KitManager() {
        this.kits = new ArrayList<Kit>();
        loadKits();
    }

    private void loadKits() {
        this.kits.add(new Kit("Bomberman", Material.TNT
                , Arrays.asList(350, 650, 950, 1950)
                , Arrays.asList(Arrays.asList("§7Start the game with 3x TNT.")
                , Arrays.asList("§7Start the game with 5x TNT.")
                , Arrays.asList("§7Start the game with 8x TNT.")
                , Arrays.asList("§7Start the game with 13x TNT."))
                , Arrays.asList(Arrays.asList(new ItemStack(Material.TNT, 3))
                , Arrays.asList(new ItemStack(Material.TNT, 5))
                , Arrays.asList(new ItemStack(Material.TNT, 8))
                , Arrays.asList(new ItemStack(Material.TNT, 13)))));



        this.kits.add(new Kit("Penis", Material.EMERALD
                , Arrays.asList(350, 650, 950, 1950)
                , Arrays.asList(Arrays.asList("§7Start the game with 3x DICK.")
                , Arrays.asList("§7Start the game with 5x DICK.")
                , Arrays.asList("§7Start the game with 8x DICK.")
                , Arrays.asList("§7Start the game with 13x DICK."))
                , Arrays.asList(Arrays.asList(new ItemStack(Material.EMERALD, 3))
                , Arrays.asList(new ItemStack(Material.EMERALD, 5))
                , Arrays.asList(new ItemStack(Material.EMERALD, 8))
                , Arrays.asList(new ItemStack(Material.EMERALD, 13)))));
    }

    public ArrayList<Kit> getKits() {
        return kits;
    }

    public Kit getKit(String kitName) {
        for (Kit p : kits)
            if (kitName.contains(p.getName()))
                return p;
        return null;
    }

}
