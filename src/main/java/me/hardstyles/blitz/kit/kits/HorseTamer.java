package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.ItemBuilder;
import me.hardstyles.blitz.util.ItemUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorseTamer extends Kit {


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



        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(100).name("&rHorse Spawn Egg").make());
        II = Arrays.asList(horse_egg,
                new ItemBuilder(Material.WOOD_AXE).name("&rHorseTamer's Wood Axe §7(II)").make());
        III = Arrays.asList(horse_egg,
                new ItemBuilder(Material.WOOD_AXE).name("&rHorseTamer's Wood Axe §7(III)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(III)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemStack(Material.IRON_BARDING, 1));
        IV = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe §7(IV)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(IV").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemStack(Material.IRON_BARDING, 1));
        V = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe §7(V)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(V").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemStack(Material.GOLD_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,1));
        VI = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe §7(VI)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(VI)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots §7(VI)").make(),
                new ItemStack(Material.GOLD_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,2));
        VII = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe §7(VII)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(VII)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings §7(VII)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots §7(VII").make(),
                new ItemStack(Material.DIAMOND_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,2));
        VIII = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe §7(VIII)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(VIII)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rHorseTamer's Leather Chestplate §7(VIII)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings §7(VIII)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots §7(VIII)").make(),
                new ItemStack(Material.DIAMOND_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,3));
        IX = Arrays.asList(horse_egg,
                new ItemBuilder(Material.IRON_AXE).name("&rHorseTamer's Iron Axe §7(IX)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(IX)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rHorseTamer's Leather Chestplate §7(IX)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings §7(IX)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots §7(IX)").make(),
                new ItemStack(Material.DIAMOND_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,3));
        X = Arrays.asList(horse_egg,
                new ItemBuilder(Material.DIAMOND_AXE).name("&rHorseTamer's Diamond Axe §7(X)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet §7(X)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rHorseTamer's Leather Chestplate §7(X)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings §7(X)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.DIAMOND_BOOTS).name("&rHorseTamer's Diamond Boots §7(X)").make(),
                new ItemStack(Material.DIAMOND_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,4));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public HorseTamer() {

        super(
                "HorseTamer", "It's a real horse I ride", new ItemStack(Material.SADDLE, 1)
                , Arrays.asList(192000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
                , Arrays.asList(Arrays.asList(new ItemStack(Material.AIR, 1))), BlitzSG.getInstance().getRankManager().getRankByName("VIP+")

        );
    }

}
