package me.syesstyles.blitz.kit;

import me.syesstyles.blitz.kit.kits.*;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;

import java.util.ArrayList;

public class KitManager {

    public ArrayList<Kit> kits;


    public KitManager() {
        this.kits = new ArrayList<Kit>();
        loadKits();
    }


    private void loadKits() {
        addKit(new Knight());
        addKit(new Baker());
        addKit(new HorseTamer());
        addKit(new Necromancer());
        addKit(new Snowman());
        addKit(new Blaze());
        addKit(new Meatmaster());
        addKit(new Archer());
        addKit(new Speleologist());
        addKit(new Wolftamer());
        addKit(new Tim());
        addKit(new Fisherman());
        addKit(new Astronaut());
        addKit(new Troll());
        addKit(new Creepertamer());
        addKit(new Arachnologist());
        addKit(new SlimeySlime());
        addKit(new Armorer());
        addKit(new Scout());
        addKit(new Toxicologist());
        addKit(new RedDragon());
        addKit(new Rogue());
        //rogue
        //reddragon



        // this.kits.add(new Kit("Bomberman", "Test kit", Material.TNT
        //         , Arrays.asList(350, 650, 950, 1950)
        //         , Arrays.asList(Arrays.asList("§7Start the game with 3x TNT.")
        //         , Arrays.asList("§7Start the game with 5x TNT.")
        //         , Arrays.asList("§7Start the game with 8x TNT.")
        //         , Arrays.asList("§7Start the game with 13x TNT."))
        //         , Arrays.asList(Arrays.asList(new ItemStack(Material.TNT, 3))
        //         , Arrays.asList(new ItemStack(Material.TNT, 5))
        //         , Arrays.asList(new ItemStack(Material.TNT, 8))
        //         , Arrays.asList(new ItemStack(Material.TNT, 13)))));
//
//
//
        // this.kits.add(new Kit("Penis", "awesome", Material.EMERALD
        //         , Arrays.asList(350, 650, 950, 1950)
        //         , Arrays.asList(Arrays.asList("§7Start the game with 3x DICK.")
        //         , Arrays.asList("§7Start the game with 5x DICK.")
        //         , Arrays.asList("§7Start the game with 8x DICK.")
        //         , Arrays.asList("§7Start the game with 13x DICK."))
        //         , Arrays.asList(Arrays.asList(new ItemStack(Material.EMERALD, 3))
        //         , Arrays.asList(new ItemStack(Material.EMERALD, 5))
        //         , Arrays.asList(new ItemStack(Material.EMERALD, 8))
        //         , Arrays.asList(new ItemStack(Material.EMERALD, 13)))));
    }

    public ArrayList<Kit> getKits() {
        return kits;
    }

    public Kit getKit(String kitName) {
        for (Kit p : kits)
            if (kitName.toLowerCase().contains(p.getName().toLowerCase()))
                return p;
        return null;
    }

    public void addKit(Kit kit) {
        kit.initialize();
        this.kits.add(kit);
    }
}
