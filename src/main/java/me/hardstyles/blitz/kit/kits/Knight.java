package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.util.ItemBuilder;
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
        I = Arrays.asList(new ItemBuilder(Material.WOOD_SWORD).name("&rKnight's Wooden Sword §7(I)").make());
        II = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Wooden Sword §7(II)").make());
        III = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Wooden Sword §7(III)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Wooden Helmet §7(III)").make());
        IV = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Wooden Sword §7(IV)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet §7(IV)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 1));
        V = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Golden Sword §7(V)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet §7(V)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots §7(V)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 2));
        VI = Arrays.asList(new ItemBuilder(Material.GOLD_SWORD).name("&rKnight's Golden Sword §7(VI)").enchantment(Enchantment.DURABILITY, 2).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet §7(VI)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings §7(VI)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots §7(VI)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 2));
        VII = Arrays.asList(new ItemBuilder(Material.STONE_SWORD).name("&rKnight's Stone Sword §7(VII)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet §7(VII)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings §7(VII)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots §7(VII)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 2));
        VIII = Arrays.asList(new ItemBuilder(Material.STONE_SWORD).name("&rKnight's Stone Sword §7(VIII)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet §7(VIII)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings §7(VIII)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rKnight's Golden Chestplate §7(VIII)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots §7(VIII)").make(),
                new ItemStack(Material.COOKED_CHICKEN, 3));
        IX = Arrays.asList(new ItemBuilder(Material.STONE_SWORD).name("&rKnight's Stone Sword §7(IX)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet §7(IX)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings §7(IX)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rKnight's Golden Chestplate §7(IX)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),

                new ItemStack(Material.COOKED_CHICKEN, 3));
        X = Arrays.asList(new ItemBuilder(Material.IRON_SWORD).name("&rKnight's Iron Sword §7(X)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rKnight's Golden Helmet §7(X)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rKnight's Golden Chestplate §7(X)").make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rKnight's Golden Leggings §7(X)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rKnight's Golden Boots §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemStack(Material.COOKED_CHICKEN, 10));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Knight() {

        super(
                "Knight", "Keep fighting!", new ItemStack(Material.WOOD_SWORD, 1)
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
