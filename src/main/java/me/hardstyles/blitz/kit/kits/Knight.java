package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Kit {


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

    @Override
    public void initialize() {
        I = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rKnight's Wooden Sword (I)").make());
        II = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Wooden Sword (II)").make());
        III = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Wooden Sword (III)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Wooden Helmet (III)").make());
        IV = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Wooden Sword (IV)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet (IV)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 1));
        V = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Golden Sword (V)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet (V)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots (V)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 2));
        VI = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Golden Sword (VI)").enchantment(Enchantment.DURABILITY, 2).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet (VI)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings (VI)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots (VI)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 2));
        VII = Arrays.asList(new ItemBuilder(Material.STONE_SWORD).name("&rKnight's Stone Sword (VII)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet (VII)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings (VII)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots (VII)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 2));
        VIII = Arrays.asList(new ItemBuilder(Material.STONE_SWORD).name("&rKnight's Stone Sword (VIII)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet (VIII)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings (VIII)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rKnight's Golden Chestplate (VIII)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots (VIII)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 3));
        IX = Arrays.asList(new ItemBuilder(Material.STONE_SWORD).name("&rKnight's Stone Sword (IX)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet (IX)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings (IX)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rKnight's Golden Chestplate (IX)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots (IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),

                new ItemStack(Material.COOKED_CHICKEN, 3));
        X = Arrays.asList(new ItemBuilder(Material.IRON_SWORD).name("&rKnight's Iron Sword (X)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet (X)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rKnight's Golden Chestplate (X)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings (X)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots (X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemStack(Material.COOKED_CHICKEN, 10));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Knight() {

        super(
                "Knight", "Idk, some shit here", new ItemStack(Material.WOOD_SWORD, 1)
                , Arrays.asList(0, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
                , Arrays.asList(Arrays.asList(new ItemStack(Material.AIR, 1)))

        );
    }

}
