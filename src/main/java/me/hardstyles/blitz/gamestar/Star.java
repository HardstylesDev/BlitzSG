package me.hardstyles.blitz.gamestar;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class Star {


    private String name;
    private String description;
    private int price;
    private Material icon;

    public Star(String name, Material icon, String description, int price) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.price = price;
    }

    public void run(Player p){
    }

    public String getName() {
        return name;
    }
    public Material getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
