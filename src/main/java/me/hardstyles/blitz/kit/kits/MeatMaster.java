package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeatMaster extends Kit {


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
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(I)").make(),
                new ItemStack(Material.COOKED_BEEF, 2));
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(II)").make(),
                new ItemStack(Material.COOKED_BEEF, 4));
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(III)").make(),
                new ItemStack(Material.COOKED_BEEF, 6));
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(IV)").make(),
                new ItemStack(Material.COOKED_BEEF, 8));
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(V)").make(),
                new ItemStack(Material.COOKED_BEEF, 10));
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(6).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rMeatMaster's Iron Helmet §7(VI)").make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatMaster's Leather Chestplate §7(VI)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(VI)").make(),
                new ItemStack(Material.COOKED_BEEF, 12));
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(7).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rMeatMaster's Iron Helmet §7(VII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatMaster's Iron Boots §7(VII)").make(),

                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatMaster's Leather Chestplate §7(VII)").make(),
                // new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatMaster's Leather Leggings §7(VII)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(VII)").make(),
                new ItemStack(Material.COOKED_BEEF, 16));
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(7).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rMeatMaster's Diamond Helmet §7(VIII)").make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatMaster's Leather Chestplate §7(VIII)").make(),
                // new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatMaster's Leather Leggings §7(VIII)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rMeatMaster's Wood Sword §7(VIII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatMaster's Iron Boots §7(VIII)").make(),
                new ItemStack(Material.COOKED_BEEF, 20));
        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(8).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMooshroom Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rMeatMaster's Diamond Helmet §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatMaster's Leather Chestplate §7(IX)").make(),
                // new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatMaster's Leather Leggings §7(IX)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatMaster's Iron Boots §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rMeatMaster's Stone Sword §7(IX)").make(),
                new ItemStack(Material.COOKED_BEEF, 24));
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(90).name("&rPig Spawn Egg").amount(8).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rMeatMaster's Diamond Helmet §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).make(),
                // new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rMeatMaster's Leather Chestplate §7(X)").make(),
                //  new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rMeatMaster's Leather Leggings §7(X)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rMeatMaster's Iron Boots §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemBuilder(Material.IRON_SWORD).name("&rMeatMaster's Iron Sword §7(X)").enchantment(Enchantment.DURABILITY,1).make(),
                new ItemStack(Material.COOKED_BEEF, 28));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public MeatMaster() {

        super(
                "MeatMaster", "Harvest those mobs!", new ItemBuilder(Material.WOOD_SWORD).make()
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
