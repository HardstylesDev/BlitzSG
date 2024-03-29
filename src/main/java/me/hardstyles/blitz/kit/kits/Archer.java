package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Archer extends Kit {


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

    Color color = Color.fromRGB(0, 255, 0);

    @Override
    public void initialize() {
        int r = 255;
        int g = 153;
        int b = 51;

        I = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(I)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemStack(Material.ARROW, 6));
        II = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(II)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemStack(Material.ARROW, 9));
        III = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(III)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(III)").color(color).make(),
                new ItemStack(Material.ARROW, 16));
        IV = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(IV)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(IV)").color(color).make(),
                new ItemStack(Material.ARROW, 24));
        V = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(V)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(V)").color(color).make(),
                new ItemStack(Material.ARROW, 32));
        VI = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(VI)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rArcher's Gold Helmet §7(VI)").make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(VI)").color(color).make(),
                new ItemStack(Material.ARROW, 32));
        VII = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(VII)").make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rArcher's Diamond Helmet §7(VII)").make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(VII)").color(color).make(),
                new ItemStack(Material.ARROW, 32));
        VIII = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(VIII)").make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rArcher's Diamond Helmet §7(VIII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArcher's Leather Leggings §7(VIII)").color(color).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(VIII)").color(color).make(),
                new ItemStack(Material.ARROW, 32));
        IX = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(IX)").enchantment(Enchantment.ARROW_DAMAGE, 1).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rArcher's Diamond Helmet §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArcher's Leather Chestplate §7(IX)").color(color).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArcher's Leather Leggings §7(IX)").color(color).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(IX)").color(color).make(),
                new ItemStack(Material.ARROW, 48));
        X = Arrays.asList(new ItemBuilder(Material.BOW).name("&rArcher's Bow §7(X)").enchantment(Enchantment.ARROW_DAMAGE, 2).enchantment(Enchantment.ARROW_KNOCKBACK, 1).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rArcher's Diamond Helmet §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArcher's Leather Chestplate §7(X)").color(color).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArcher's Leather Leggings §7(X)").color(color).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArcher's Leather Boots §7(X)").color(color).make(),
                new ItemStack(Material.ARROW, 64));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Archer() {

        super(
                "Archer", "Ranged attacks.", new ItemBuilder(Material.BOW).amount(1).make()
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
