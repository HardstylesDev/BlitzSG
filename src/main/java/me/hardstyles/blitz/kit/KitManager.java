package me.hardstyles.blitz.kit;

import me.hardstyles.blitz.kit.kits.*;

import java.util.ArrayList;

public class KitManager {

    public ArrayList<Kit> kits;


    public KitManager() {
        this.kits = new ArrayList<Kit>();
        loadKits();
    }


    private void loadKits() {
        addKit(new Armorer());
        addKit(new Knight());
        addKit(new Fisherman());
        addKit(new Speleologist());
        addKit(new Archer());
        addKit(new Meatmaster());
        addKit(new Scout());
        addKit(new Baker());

        addKit(new Astronaut());
        addKit(new Troll());
        addKit(new Creepertamer());
        addKit(new Wolftamer());
        addKit(new Rogue());
        addKit(new SlimeySlime());
        addKit(new Blaze());
        addKit(new Toxicologist());


        addKit(new Arachnologist());
        addKit(new RedDragon());
        addKit(new HorseTamer());
        addKit(new Tim());
        addKit(new Necromancer());
        addKit(new Snowman());

    }

    public ArrayList<Kit> getKits() {
        return kits;
    }

    public Kit getKit(String kitName) {
        return kits.stream().filter(p -> kitName.toLowerCase().contains(p.getName().toLowerCase())).findFirst().orElse(null);
    }

    public void addKit(Kit kit) {
        kit.initialize();
        this.kits.add(kit);
    }
}
