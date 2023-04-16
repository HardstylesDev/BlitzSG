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
                new ItemBuilder(Material.WOOD_AXE).name("&rHorseTamer's Wood Axe (II)").make());
        III = Arrays.asList(horse_egg,
                new ItemBuilder(Material.WOOD_AXE).name("&rHorseTamer's Wood Axe (III)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (III)").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemStack(Material.IRON_BARDING, 1));
        IV = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe (IV)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (IV").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemStack(Material.IRON_BARDING, 1));
        V = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe (V)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (V").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemStack(Material.GOLD_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,1));
        VI = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe (VI)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (VI").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots (VI").make(),
                new ItemStack(Material.GOLD_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,2));
        VII = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe (VII)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (VII").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings (VII").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots (VII").make(),
                new ItemStack(Material.DIAMOND_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,2));
        VIII = Arrays.asList(horse_egg,
                new ItemBuilder(Material.STONE_AXE).name("&rHorseTamer's Stone Axe (VIII)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (VIII").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rHorseTamer's Leather Chestplate (VIII").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings (VIII").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots (VIII").make(),
                new ItemStack(Material.DIAMOND_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,3));
        IX = Arrays.asList(horse_egg,
                new ItemBuilder(Material.IRON_AXE).name("&rHorseTamer's Iron Axe (IX)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (IX").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rHorseTamer's Leather Chestplate (IX").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings (IX").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rHorseTamer's Iron Boots (IX").make(),
                new ItemStack(Material.DIAMOND_BARDING, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL,1,1, (short) 8261 ,3));
        X = Arrays.asList(horse_egg,
                new ItemBuilder(Material.DIAMOND_AXE).name("&rHorseTamer's Diamond Axe (X)").enchantment(Enchantment.DAMAGE_ALL, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rHorseTamer's Leather Helmet (X").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rHorseTamer's Leather Chestplate (X").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rHorseTamer's Leather Leggings (X").color(Color.fromRGB(128, 128, 0)).make(),
                new ItemBuilder(Material.DIAMOND_BOOTS).name("&rHorseTamer's Diamond Boots (X").make(),
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
