package me.hardstyles.blitz.kit;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.rank.Rank;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    private String name;
    private ItemStack icon;
    private List<List<String>> levelDescriptions;
    private List<Integer> kitPrices;
    private String description;
    private List<List<ItemStack>> kitItems;
    private int selectorLocation;
    private int shopLocation;
    private Rank requiredRank;

	public Kit(String name, String description, ItemStack icon, List<Integer> kitPrices, List<List<String>> levelDescriptions, List<List<ItemStack>> kitItems) {
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.levelDescriptions = levelDescriptions;
		this.kitPrices = kitPrices;
		if (kitItems == null)
			this.kitItems = new ArrayList<List<ItemStack>>();
		else
			this.kitItems = kitItems;
		this.requiredRank = BlitzSG.getInstance().getRankManager().getRankByName("Default");
    }

    public Kit(String name, String description, ItemStack icon, List<Integer> kitPrices, List<List<String>> levelDescriptions, List<List<ItemStack>> kitItems, Rank requiredRank) {
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.levelDescriptions = levelDescriptions;
        this.kitPrices = kitPrices;
        if (kitItems == null)
            this.kitItems = new ArrayList<List<ItemStack>>();
        else
            this.kitItems = kitItems;
        this.requiredRank = requiredRank;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public Rank getRequiredRank() {
        return requiredRank;
    }

    public List<String> getDescription(int level) {
        if (levelDescriptions.size() <= level)
            return levelDescriptions.get(levelDescriptions.size() - 1);
        return levelDescriptions.get(level);
    }

    public void setKitItems(List<List<ItemStack>> x) {
        kitItems = x;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrice(int level) {
        if (kitPrices.size() <= level)
            return -1;
        return kitPrices.get(level);
    }

    public List<ItemStack> getKitItems(int level) {
        if (level == 0)
            level = level + 1;
        if (kitItems.size() > 0)
            return kitItems.get(level - 1);

        return null;
    }

    public void giveKit(Player p, int level) {
        if (level == 0)
            level = level + 1;
        if (kitItems.size() > 0)
            for (ItemStack is : kitItems.get(level - 1))
                if (p.getInventory().firstEmpty() == -1)
                    p.getWorld().dropItemNaturally(p.getLocation(), is.clone());
                else
                    p.getInventory().addItem(is.clone());
    }

    public void initialize() {
    }

}
