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

public class Wolftamer extends Kit {


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
        int r = 128;
        int g = 153;
        int b = 51;

        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(1).make(),
                new ItemStack(Material.ROTTEN_FLESH, 2));
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(2).make(),
                new ItemStack(Material.ROTTEN_FLESH, 4));
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rWolftamer's Chainmail Boots §7(III)").make(),
                new ItemStack(Material.ROTTEN_FLESH, 6));
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rWolftamer's Wood Axe §7(IV)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rWolftamer's Chainmail Boots §7(IV)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemStack(Material.ROTTEN_FLESH, 8));
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rWolftamer's Wood Axe §7(V)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rWolftamer's Chainmail Boots §7(V)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemStack(Material.ROTTEN_FLESH, 10));
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rWolftamer's Wood Axe §7(VI)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rWolftamer's Chainmail Boots §7(VI)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).make(),
                new ItemStack(Material.ROTTEN_FLESH, 12));
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rWolftamer's Stone Axe §7(VII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rWolftamer's Leather Helmet §7(VII)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.DIAMOND_BOOTS).name("&rWolftamer's Diamond Boots §7(VII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemStack(Material.ROTTEN_FLESH, 14));
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rWolftamer's Stone Axe §7(VIII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rWolftamer's Leather Helmet §7(VIII)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rWolftamer's Leather Leggings §7(VIII)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.DIAMOND_BOOTS).name("&rWolftamer's Diamond Boots §7(VIII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemStack(Material.ROTTEN_FLESH, 16));
        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rWolftamer's Stone Axe §7(IX)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rWolftamer's Leather Helmet §7(IX)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rWolftamer's Leather Chestplate §7(IX)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rWolftamer's Leather Leggings §7(IX)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.DIAMOND_BOOTS).name("&rWolftamer's Diamond Boots §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).make(),
                new ItemStack(Material.ROTTEN_FLESH, 18));
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(95).name("&rWolf Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rWolftamer's Stone Sword §7(X)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rWolftamer's Leather Helmet §7(X)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rWolftamer's Leather Chestplate §7(X)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rWolftamer's Leather Leggings §7(X)").color(Color.fromRGB(r,r,r)).make(),
                new ItemBuilder(Material.DIAMOND_BOOTS).name("&rWolftamer's Diamond Boots §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).make(),
                new ItemStack(Material.ROTTEN_FLESH, 20));


        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Wolftamer() {

        super(
                "Wolftamer", "Howl at the moon!", new ItemBuilder(Material.MONSTER_EGG).durability(95).amount(1).make()
                , Arrays.asList(96000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
                , Arrays.asList(Arrays.asList(new ItemStack(Material.AIR, 1))), BlitzSG.getInstance().getRankManager().getRankByName("VIP")

        );
    }

}
