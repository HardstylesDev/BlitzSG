package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Baker extends Kit {


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

    Color color = Color.fromRGB(255, 255, 255);

    ItemStack getPot(int i) {
        return ItemUtils.buildPotion(PotionEffectType.DAMAGE_RESISTANCE, 13 * 20, 2, (short) 16385, i);
    }

    @Override
    public void initialize() {
        int SL = 10;

        I = Arrays.asList(new ItemStack(Material.BREAD, 2));
        II = Arrays.asList(new ItemStack(Material.BREAD, 2),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (II)").color(color).make());
        III = Arrays.asList(new ItemStack(Material.BREAD, 2),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (III)").color(color).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBaker's Wooden Sword (III)").make(),
                new ItemStack(Material.CAKE));


        IV = Arrays.asList(new ItemStack(Material.BREAD, 2),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (IV)").color(color).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBaker's Leather Chestplate (IV)").color(color).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBaker's Wooden Sword (IV)").make(),
                new ItemStack(Material.CAKE, 2));

        V = Arrays.asList(new ItemStack(Material.BREAD, 3),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (V)").color(color).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBaker's Leather Chestplate (V)").color(color).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBaker's Wooden Sword (V)").make(),
                new ItemStack(Material.CAKE, 3));

        VI = Arrays.asList(new ItemStack(Material.BREAD, 3),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (VI)").color(color).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBaker's Leather Chestplate (VI)").color(color).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rBaker's Stone Axe (VI)").make(),
                new ItemStack(Material.CAKE, 3),
                getPot(1));
        VII = Arrays.asList(new ItemStack(Material.BREAD, 3),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (VII)").color(color).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBaker's Leather Chestplate (VII)").color(color).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rBaker's Stone Axe (VII)").make(),
                new ItemStack(Material.CAKE, 3),
                new ItemStack(Material.GOLDEN_APPLE, 1),
                getPot(1));
        VIII = Arrays.asList(new ItemStack(Material.BREAD, 4),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (VIII)").color(color).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBaker's Leather Chestplate (VIII)").color(color).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rBaker's Stone Sword (VIII)").make(),
                new ItemStack(Material.CAKE, 4),
                new ItemStack(Material.GOLDEN_APPLE, 1),
                getPot(2));
        IX = Arrays.asList(new ItemStack(Material.BREAD, 4),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (IX)").color(color).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBaker's Leather Chestplate (IX)").color(color).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rBaker's Stone Sword (IX)").make(),
                new ItemStack(Material.CAKE, 5),
                new ItemStack(Material.GOLDEN_APPLE, 2),
                getPot(2));
        X = Arrays.asList(new ItemStack(Material.BREAD, 4),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBaker's Leather Helmet (X)").color(color).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBaker's Leather Chestplate (X)").color(color).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rBaker's Stone Sword (X)").make(),
                new ItemStack(Material.CAKE, 5),
                new ItemStack(Material.GOLDEN_APPLE, 3),
                getPot(3));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Baker() {

        super(
                "Baker", "Mmm, food!", new ItemStack(Material.CAKE, 1)
                , Arrays.asList(0, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
                , Arrays.asList(Arrays.asList("§7Start the gameith 3x TNT.")
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
