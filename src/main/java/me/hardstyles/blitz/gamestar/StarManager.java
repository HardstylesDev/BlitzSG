package me.hardstyles.blitz.gamestar;

import me.hardstyles.blitz.gamestar.stars.*;


import java.util.ArrayList;

public class StarManager {
    public ArrayList<Star> stars;


    public StarManager() {
        this.stars = new ArrayList<Star>();
        registerStars();
    }


    private void registerStars() {
        addKit(new Assassin());
        addKit(new HolyWarrior());
        addKit(new Timelord());
        addKit(new Kamikaze());
        addKit(new Firebreather());
        addKit(new Ninja());
        addKit(new Robinhood());
        addKit(new Wobbuffet());
        addKit(new Apocalypse());
        addKit(new Vampire());
        addKit(new LuckyCharm());
        addKit(new WitherWarrior());
        addKit(new Gremlin());
        addKit(new Dragonborn());
        addKit(new DarkKnight());
        addKit(new Imprison());
        addKit(new Explosion());
    }

    public ArrayList<Star> getStars() {
        return stars;
    }

    public Star getStar(String starName) {
        for (Star p : stars)
            if (starName.contains(p.getName()))
                return p;
        return null;
    }

    public void addKit(Star star) {

        this.stars.add(star);
    }
}