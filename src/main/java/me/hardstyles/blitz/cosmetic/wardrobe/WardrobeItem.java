package me.hardstyles.blitz.cosmetic.wardrobe;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@Getter
@Setter
public class WardrobeItem {
    private Color color = null;
    private boolean isEnchanted;
    private final ItemStack item;

    public WardrobeItem(ItemStack item, boolean isEnchanted, Color color) {
        this.item = item;
        this.isEnchanted = isEnchanted;
        this.color = color;
    }

    public static WardrobeItem of(ItemStack item, boolean isEnchanted, Color color) {
        return new WardrobeItem(item, isEnchanted, color);
    }

    public static WardrobeItem deserialize(JsonObject serialized) {
        Material material = Material.valueOf(serialized.get("item").getAsString());
        boolean isEnchanted = serialized.get("isEnchanted").getAsBoolean();
        Color color = null;
        if(serialized.has("color")){
            color = Color.fromRGB(serialized.get("color").getAsInt());
        }
        return new WardrobeItem(new ItemStack(material), isEnchanted, color);
    }

    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty("item", item.getType().name());
        object.addProperty("isEnchanted", isEnchanted);
        if(color != null){
            object.addProperty("color", color.asRGB());
        }
        return object;
    }

    public void apply(Player player) {
        item.setItemMeta(null);
        item.removeEnchantment(Enchantment.DURABILITY);
        if (color != null) {
            if (item.getType().name().toLowerCase().contains("leather")) {
                LeatherArmorMeta lMeta = (LeatherArmorMeta) item.getItemMeta();
                lMeta.setColor(color);
                item.setItemMeta(lMeta);
            }
        }
        if (isEnchanted) {
            item.addEnchantment(Enchantment.DURABILITY, 1);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }


        Material type = item.getType();
        String name = type.name();
        if (name.toLowerCase().contains("helmet")) {
            player.getInventory().setHelmet(item);
        } else if (name.toLowerCase().contains("chestplate")) {
            player.getInventory().setChestplate(item);
        } else if (name.toLowerCase().contains("leggings")) {
            player.getInventory().setLeggings(item);
        } else if (name.toLowerCase().contains("boots")) {
            player.getInventory().setBoots(item);
        } else {
            throw new IllegalArgumentException("Item must be a piece of armor!");
        }
    }
}
