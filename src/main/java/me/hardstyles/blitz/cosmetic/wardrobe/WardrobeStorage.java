package me.hardstyles.blitz.cosmetic.wardrobe;

import com.google.gson.JsonObject;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Getter
public class WardrobeStorage {
    private ArrayList<WardrobeItem> items = new ArrayList<>();

    public void add(WardrobeItem item) {
        String type = item.getItem().getType().name().split("_")[1].toLowerCase();
        items.removeIf(i -> i.getItem().getType().name().split("_")[1].toLowerCase().equals(type));
        items.add(item);
    }

    public void clear() {
        items.clear();
    }


    public void remove(WardrobeItem item) {
        items.remove(item);
    }

    public void apply(Player player) {
        player.getInventory().setArmorContents(null);
        for (WardrobeItem item : items) {
            item.apply(player);
        }
    }

    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        for (WardrobeItem item : items) {
            String key = item.getItem().getType().name().split("_")[1].toLowerCase();
            object.add(key, item.serialize());
        }
        return object;
    }

    public static WardrobeStorage deserialize(JsonObject object) {
        WardrobeStorage storage = new WardrobeStorage();
        String[] keys = {"helmet", "chestplate", "leggings", "boots"};
        for (String key : keys) {
            if (object.has(key)) {
                System.out.println("Found: " + key);
                System.out.println("It's located: " + key + " " + object.get(key).getAsJsonObject());
                storage.add(WardrobeItem.deserialize(object.get(key).getAsJsonObject()));
            }
        }
        return storage;
    }

    public WardrobeItem getSection(String section) {
        for (WardrobeItem item : items) {
            if (item.getItem().getType().name().split("_")[1].toLowerCase().equals(section)) {
                return item;
            }
        }
        return null;
    }
}
