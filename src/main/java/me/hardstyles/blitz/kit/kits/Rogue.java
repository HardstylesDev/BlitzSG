package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rogue extends Kit {


    List<ItemStack> I = new ArrayList<>();
    List<ItemStack> II = new ArrayList<>();
    List<ItemStack> III = new ArrayList<>();
    List<ItemStack> IV = new ArrayList<>();
    List<ItemStack> V = new ArrayList<>();
    List<ItemStack> VI = new ArrayList<>();
    List<ItemStack> VII = new ArrayList<>();
    List<ItemStack> VIII = new ArrayList<>();
    List<ItemStack> IX = new ArrayList<>();
    List<ItemStack> X = new ArrayList<>();

    ItemStack pot(int i) {
        return ItemUtils.buildPotion(PotionEffectType.FIRE_RESISTANCE, 10 * 20, 1, (short) 8227, i);
    }

    @Override
    public void initialize() {
        I = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (I)").enchantment(Enchantment.KNOCKBACK, 3).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make());
        II = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (II)").enchantment(Enchantment.KNOCKBACK, 4).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make());
        III = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (III)").enchantment(Enchantment.KNOCKBACK, 4).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (III)").durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make());

        IV = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (IV)").enchantment(Enchantment.KNOCKBACK, 4).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (IV)").enchantment(Enchantment.KNOCKBACK, 1).durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make());

        V = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (VI)").enchantment(Enchantment.KNOCKBACK, 4).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (VI)").enchantment(Enchantment.KNOCKBACK, 2).durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make());
        VI = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (VI)").enchantment(Enchantment.KNOCKBACK, 4).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (VI)").enchantment(Enchantment.KNOCKBACK, 3).durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRogue's Leather Chestplate (VI)").color(Color.fromRGB(0, 0, 0)).make());


        VII = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (VII)").enchantment(Enchantment.KNOCKBACK, 4).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (VII)").enchantment(Enchantment.KNOCKBACK, 3).durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRogue's Leather Chestplate (VII)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRogue's Leather Leggings (VII)").color(Color.fromRGB(0, 0, 0)).make());

        VIII = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (VIII)").enchantment(Enchantment.KNOCKBACK, 4).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (VIII)").enchantment(Enchantment.KNOCKBACK, 3).durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRogue's Leather Chestplate (VIII)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRogue's Leather Leggings (VIII)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rRogue's Leather Boots (VIII)").color(Color.fromRGB(0, 0, 0)).make());

        IX = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (IX)").enchantment(Enchantment.KNOCKBACK, 5).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (IX)").enchantment(Enchantment.KNOCKBACK, 3).durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRogue's Leather Helmet (IX)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRogue's Leather Chestplate (IX)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRogue's Leather Leggings (IX)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rRogue's Leather Boots (IX)").color(Color.fromRGB(0, 0, 0)).make());
        X =Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rRogue's Wooden Sword (X)").enchantment(Enchantment.KNOCKBACK, 5).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.DIAMOND_SWORD).name("&rRogue's Diamond Sword (X)").enchantment(Enchantment.KNOCKBACK, 3).durability(Material.DIAMOND_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRogue's Leather Helmet (X)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRogue's Leather Chestplate (X)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRogue's Leather Leggings (X)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rRogue's Leather Boots (X)").color(Color.fromRGB(0, 0, 0)).make(),
                new ItemBuilder(Material.BOW).name("&rRogue's Bow (X)").enchantment(Enchantment.ARROW_KNOCKBACK, 3).enchantment(Enchantment.ARROW_DAMAGE, 3).durability(Material.BOW.getMaxDurability() - 2).make());
                new ItemBuilder(Material.ARROW).amount(3).make();

        //5 x potion slowness 4 15 seconds
        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Rogue() {

        super(
                "Rogue", "Sneak attack!", new ItemBuilder(Material.WOOD_SWORD).durability(Material.WOOD_SWORD.getMaxDurability() - 2).enchantment(Enchantment.KNOCKBACK, 3).make()
                , Arrays.asList(96000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
                , Arrays.asList(Arrays.asList("§7Start the game with 3x TNT.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit.")
                        , Arrays.asList("§7Start the game with some bullshit."))
                , Arrays.asList(Arrays.asList(new ItemStack(Material.AIR, 1))), BlitzSG.getInstance().getRankManager().getRankByName("VIP")

        );
    }

}
