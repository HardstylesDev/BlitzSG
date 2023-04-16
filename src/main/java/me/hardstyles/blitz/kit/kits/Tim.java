package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tim extends Kit {


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

        I = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(1).make());
        II = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(2).make());
        III = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(3).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rTim's Wood Sword (III)").make());
        IV = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(4).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rTim's Wood Sword (IV)").make(),
                new ItemStack(Material.APPLE, 2));
        V = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(5).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rTim's Wood Sword (V)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rTim's Leather Leggings (V)").make(),
                new ItemStack(Material.APPLE, 2));
        VI = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(6).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rTim's Wood Sword (VI)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTim's Leather Helmet (VI)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rTim's Leather Leggings (VI)").make(),
                new ItemStack(Material.APPLE, 3));
        VII = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(7).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rTim's Wood Sword (VII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTim's Leather Helmet (VII)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTim's Leather Chestplate (VII)").make(),
                new ItemBuilder(Material.IRON_LEGGINGS).name("&rTim's Iron Leggings (VII)").make(),
                new ItemStack(Material.APPLE, 3));
        VIII = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(8).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rTim's Wood Sword (VIII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTim's Leather Helmet (VIII)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTim's Leather Chestplate (VIII)").make(),
                new ItemBuilder(Material.IRON_LEGGINGS).name("&rTim's Iron Leggings (VIII)").make(),
                new ItemStack(Material.APPLE, 4));
        IX = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(10).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rTim's Stone Sword (IX)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTim's Leather Helmet (IX)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTim's Leather Chestplate (IX)").make(),
                new ItemBuilder(Material.IRON_LEGGINGS).name("&rTim's Iron Leggings (IX)").make(),
                new ItemStack(Material.APPLE, 5));
        X = Arrays.asList(new ItemBuilder(Material.EXP_BOTTLE).amount(12).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rTim's Stone Sword (X)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTim's Leather Helmet (X)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTim's Leather Chestplate (X)").make(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).name("&rTim's Diamond Leggings (X)").make(),
                new ItemStack(Material.APPLE, 5));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Tim() {

        super(
                "Tim", "The enchanter.", new ItemBuilder(Material.EXP_BOTTLE).amount(1).make()
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
