package me.syesstyles.blitz.kit.kits;

import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Snowman extends Kit {


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

    ItemStack horse_egg = new ItemBuilder(Material.MONSTER_EGG).durability(100).name("&rHorse Spawn Egg").make();

    @Override
    public void initialize() {


        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").make());
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").make(),
                new ItemStack(Material.SNOW_BALL, 8));
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").make(),
                new ItemStack(Material.SNOW_BALL, 16));
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 2));
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 4),
                new ItemBuilder(Material.WOOD_AXE).name("&rSnowman's Wooden Axe (V)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rSnowman's Leather Helmet (V)").color(Color.fromRGB(255, 255, 255)).make());
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(2).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 6),
                new ItemBuilder(Material.WOOD_AXE).name("&rSnowman's Wooden Axe (VI)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rSnowman's Leather Helmet (VI)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSnowman's Leather Leggings (VI)").color(Color.fromRGB(255, 255, 255)).make());
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(2).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 6),
                new ItemBuilder(Material.STONE_AXE).name("&rSnowman's Wooden Axe (VII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rSnowman's Leather Helmet (VII)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSnowman's Leather Leggings (VII)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSnowman's Iron Boots (VII)").make());
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(2).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 6),
                new ItemBuilder(Material.STONE_AXE).name("&rSnowman's Stone Axe (VIII)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSnowman's Iron Helmet (VIII)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSnowman's Leather Leggings (VIII)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSnowman's Iron Boots (VIII)").make());

        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(3).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 8),
                new ItemBuilder(Material.IRON_AXE).name("&rSnowman's Iron Axe (IX)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSnowman's Iron Helmet (IX)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSnowman's Leather Chestplate (IX)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSnowman's Leather Leggings (IX)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSnowman's Iron Boots (IX)").enchantment(Enchantment.PROTECTION_FALL,1).make());

        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(4).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 10),
                new ItemBuilder(Material.DIAMOND_AXE).name("&rSnowman's Diamond Axe (X)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSnowman's Iron Helmet (X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSnowman's Leather Chestplate (X)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSnowman's Leather Leggings (X)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSnowman's Iron Boots (X)").enchantment(Enchantment.PROTECTION_FALL,1).make());

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Snowman() {

        super(
                "Snowman", "Full of festive spirit.", new ItemBuilder(Material.MONSTER_EGG).durability(999).amount(1).make()
                , Arrays.asList(400000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
