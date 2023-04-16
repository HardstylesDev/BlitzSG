package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Armorer extends Kit {


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


        I = Arrays.asList(new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (I)").color(Color.fromRGB(0, 128, 0)).make());
        II = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (II)").color(Color.fromRGB(255, 165, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (II)").color(Color.fromRGB(0, 128, 0)).make());
        III = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (III)").color(Color.fromRGB(255, 165, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (III)").color(Color.fromRGB(0, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (III)").color(Color.fromRGB(128, 128, 128)).make());
        IV = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (IV)").color(Color.fromRGB(255, 165, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (IV)").color(Color.fromRGB(0, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArmorer's Leather Leggings (IV)").color(Color.fromRGB(0, 0, 255)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (IV)").color(Color.fromRGB(128, 128, 128)).make());
        V = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (V)").color(Color.fromRGB(255, 165, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (V)").color(Color.fromRGB(0, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArmorer's Leather Leggings (V)").enchantment(Enchantment.PROTECTION_FIRE, 1).color(Color.fromRGB(0, 0, 255)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (V)").color(Color.fromRGB(128, 128, 128)).make(),
                new ItemStack(Material.COOKIE, 1));
        VI = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (VI)").color(Color.fromRGB(255, 165, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (VI)").color(Color.fromRGB(0, 128, 0)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArmorer's Leather Leggings (VI)").color(Color.fromRGB(0, 0, 255)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 1).enchantment(Enchantment.PROTECTION_FIRE, 3).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (VI)").color(Color.fromRGB(128, 128, 128)).enchantment
                        (Enchantment.PROTECTION_EXPLOSIONS, 1).make(),
                new ItemStack(Material.COOKIE, 2));
        VII = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (VII)").color(Color.fromRGB(255, 165, 0)).enchantment
                        (Enchantment.PROTECTION_PROJECTILE, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (VII)").color(Color.fromRGB(0, 128, 0)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 1).enchantment(Enchantment.THORNS, 1).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArmorer's Leather Leggings (VII)").enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(0, 0, 255)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (VII)").color(Color.fromRGB(128, 128, 128)).enchantment
                        (Enchantment.PROTECTION_EXPLOSIONS, 2).make(),
                new ItemStack(Material.COOKIE, 3));
        VIII = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (VIII)").color(Color.fromRGB(255, 165, 0)).enchantment
                        (Enchantment.PROTECTION_PROJECTILE, 2).enchantment(Enchantment.OXYGEN, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (VIII)").color(Color.fromRGB(0, 128, 0)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchantment(Enchantment.THORNS, 2).enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArmorer's Leather Leggings (VIII)").enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchantment(Enchantment.PROTECTION_FIRE, 4).color(Color.fromRGB(0, 0, 255)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (VIII)").color(Color.fromRGB(128, 128, 128)).enchantment
                        (Enchantment.PROTECTION_EXPLOSIONS, 3).make(),
                new ItemStack(Material.COOKIE, 4));
        IX = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (IX)").color(Color.fromRGB(255, 165, 0)).enchantment
                        (Enchantment.PROTECTION_PROJECTILE, 3).enchantment(Enchantment.OXYGEN, 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (IX)").color(Color.fromRGB(0, 128, 0)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchantment(Enchantment.THORNS, 3).enchantment(Enchantment.DURABILITY, 2).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArmorer's Leather Leggings (IX)").color(Color.fromRGB(0, 0, 255)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 4).enchantment(Enchantment.PROTECTION_FIRE, 5).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (IX)").color(Color.fromRGB(128, 128, 128)).enchantment
                        (Enchantment.PROTECTION_EXPLOSIONS, 4).make(),
                new ItemStack(Material.COOKIE, 5));
        X = Arrays.asList(new ItemBuilder(Material.LEATHER_HELMET).name("&rArmorer's Leather Helmet (X)").color(Color.fromRGB(255, 165, 0)).enchantment
                        (Enchantment.PROTECTION_PROJECTILE, 4).enchantment(Enchantment.OXYGEN, 3).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArmorer's Leather Chestplate (X)").color(Color.fromRGB(0, 128, 0)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchantment(Enchantment.THORNS, 3).enchantment(Enchantment.DURABILITY, 3).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArmorer's Leather Leggings (X)").color(Color.fromRGB(0, 0, 255)).enchantment
                        (Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchantment(Enchantment.PROTECTION_FIRE, 10).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArmorer's Leather Boots (X)").color(Color.fromRGB(128, 128, 128)).enchantment
                        (Enchantment.PROTECTION_EXPLOSIONS, 5).make(),
                new ItemStack(Material.COOKIE, 6));
        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Armorer() {

        super(
                "Armorer", "Not quite a bodyguard", new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.fromRGB(0, 128, 0)).make()
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
