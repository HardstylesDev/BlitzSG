package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meatmaster extends Kit {


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
        int r = 255;
        int g = 153;
        int b = 51;

        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (I)").make(),
                new ItemStack(Material.COOKED_BEEF, 2));
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (II)").make(),
                new ItemStack(Material.COOKED_BEEF, 4));
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (III)").make(),
                new ItemStack(Material.COOKED_BEEF, 6));
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (IV)").make(),
                new ItemStack(Material.COOKED_BEEF, 8));
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (V)").make(),
                new ItemStack(Material.COOKED_BEEF, 10));
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(6).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rMeatmaster's Iron Helmet (VI)").make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatmaster's Leather Chestplate (VI)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (VI)").make(),
                new ItemStack(Material.COOKED_BEEF, 12));
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(7).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rMeatmaster's Iron Helmet (VII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatmaster's Iron Boots (VII)").make(),

                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatmaster's Leather Chestplate (VII)").make(),
                // new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatmaster's Leather Leggings (VII)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (VII)").make(),
                new ItemStack(Material.COOKED_BEEF, 16));
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(7).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rMeatmaster's Diamond Helmet (VIII)").make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatmaster's Leather Chestplate (VIII)").make(),
                // new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatmaster's Leather Leggings (VIII)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatmaster's Wood Sword (VIII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatmaster's Iron Boots (VIII)").make(),
                new ItemStack(Material.COOKED_BEEF, 20));
        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(8).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rMeatmaster's Diamond Helmet (IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatmaster's Leather Chestplate (IX)").make(),
                // new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatmaster's Leather Leggings (IX)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatmaster's Iron Boots (IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rMeatmaster's Stone Sword (IX)").make(),
                new ItemStack(Material.COOKED_BEEF, 24));
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(8).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rMeatmaster's Diamond Helmet (X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatmaster's Leather Chestplate (X)").make(),
                //  new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatmaster's Leather Leggings (X)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatmaster's Iron Boots (X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemBuilder(Material.IRON_SWORD).name("&rMeatmaster's Iron Sword (X)").enchantment(Enchantment.DURABILITY,1).make(),
                new ItemStack(Material.COOKED_BEEF, 28));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Meatmaster() {

        super(
                "Meatmaster", "Harvest those mobs!", new ItemBuilder(Material.COOKED_BEEF).make()
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
