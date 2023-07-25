package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.ItemBuilder;
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
                new ItemBuilder(Material.WOOD_AXE).name("&rSnowman's Wooden Axe §7(V)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rSnowman's Leather Helmet §7(V)").color(Color.fromRGB(255, 255, 255)).make());
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(2).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 6),
                new ItemBuilder(Material.WOOD_AXE).name("&rSnowman's Wooden Axe §7(VI)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rSnowman's Leather Helmet §7(VI)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSnowman's Leather Leggings §7(VI)").color(Color.fromRGB(255, 255, 255)).make());
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(2).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 6),
                new ItemBuilder(Material.STONE_AXE).name("&rSnowman's Wooden Axe §7(VII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rSnowman's Leather Helmet §7(VII)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSnowman's Leather Leggings §7(VII)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rSnowman's Chain Boots §7(VII)").make());
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(2).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 6),
                new ItemBuilder(Material.STONE_AXE).name("&rSnowman's Stone Axe §7(VIII)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSnowman's Iron Helmet §7(VIII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1).make(),
                new ItemBuilder(Material.GOLD_LEGGINGS).name("&rSnowman's Gold Leggings §7(VIII)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rSnowman's Chain Boots §7(VIII)").make());

        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(3).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 8),
                new ItemBuilder(Material.IRON_AXE).name("&rSnowman's Iron Axe §7(IX)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSnowman's Iron Helmet §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSnowman's Leather Chestplate §7(IX)").color(Color.fromRGB(255, 255, 255)).make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rSnowman's Chain Leggings §7(IX)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rSnowman's Chain Boots §7(IX)").enchantment(Enchantment.PROTECTION_FALL,1).make());

        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(999).name("&rSnowman Spawn Egg").amount(4).make(),
                new ItemStack(Material.SNOW_BALL, 16),
                new ItemStack(Material.CARROT, 10),
                new ItemBuilder(Material.DIAMOND_AXE).name("&rSnowman's Diamond Axe §7(X)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSnowman's Iron Helmet §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,4).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rSnowman's Chain Chestplate §7(X)").make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rSnowman's Chain Leggings §7(X)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rSnowman's Chain Boots §7(X)").enchantment(Enchantment.PROTECTION_FALL,1).make());

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Snowman() {

        super(
                "Snowman", "Full of festive spirit.", new ItemBuilder(Material.MONSTER_EGG).durability(999).amount(1).make()
                , Arrays.asList(385000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
                , Arrays.asList(Arrays.asList(new ItemStack(Material.AIR, 1))), BlitzSG.getInstance().getRankManager().getRankByName("MVP+")

        );
    }

}
