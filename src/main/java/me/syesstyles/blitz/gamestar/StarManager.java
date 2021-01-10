package me.syesstyles.blitz.gamestar;

import me.syesstyles.blitz.gamestar.stars.Assassin;
import me.syesstyles.blitz.gamestar.stars.HolyWarrior;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.kit.kits.*;

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