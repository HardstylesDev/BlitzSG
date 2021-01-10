package me.syesstyles.blitz.kit.kits;

import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.ItemBuilder;
import me.syesstyles.blitz.utils.ItemUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Troll extends Kit {


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

    Color color = Color.fromRGB(255, 0, 255);
    @Override
    public void initialize() {


        I = Arrays.asList(new ItemBuilder(Material.WEB).amount(2).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(3).make());
        II = Arrays.asList(new ItemBuilder(Material.WEB).amount(3).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(9).make(),
                new ItemBuilder(Material.STICK).amount(1).make());
        III = Arrays.asList(new ItemBuilder(Material.WEB).amount(3).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(16).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(2).make());
        IV = Arrays.asList(new ItemBuilder(Material.WEB).amount(5).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(16).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(4).make());
        V = Arrays.asList(new ItemBuilder(Material.WEB).amount(5).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(32).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTroll's Leather Chestplate (V)").color(color).enchantment(Enchantment.LOOT_BONUS_BLOCKS, 3).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(7).make());
        VI = Arrays.asList(new ItemBuilder(Material.WEB).amount(6).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(64).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTroll's Leather Chestplate (VI)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(10).make(),
                new ItemBuilder(Material.FIREBALL).amount(2).make());
        VII = Arrays.asList(new ItemBuilder(Material.WEB).amount(6).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(64).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTroll's Leather Chestplate (VII)").color(color).enchantment(Enchantment.LOOT_BONUS_BLOCKS, 3).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTroll's Leather Leather (VII)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(10).make(),
                new ItemBuilder(Material.FIREBALL).amount(2).make());
        VIII = Arrays.asList(new ItemBuilder(Material.WEB).amount(6).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(64).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTroll's Leather Helmet (VIII)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTroll's Leather Chestplate (VIII)").color(color).enchantment(Enchantment.LOOT_BONUS_BLOCKS, 3).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rTroll's Leather Leggings (VIII)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(12).make(),
                new ItemBuilder(Material.FIREBALL).amount(3).make());
        IX = Arrays.asList(new ItemBuilder(Material.WEB).amount(6).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(64).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTroll's Leather Helmet (IX)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTroll's Leather Chestplate (IX)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rTroll's Leather Leggings (IX)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rTroll's Leather Boots (IX)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(12).make(),
                new ItemBuilder(Material.FIREBALL).amount(3).make());
        X = Arrays.asList(new ItemBuilder(Material.WEB).amount(6).make(),
                new ItemBuilder(Material.SNOW_BALL).amount(64).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rTroll's Leather Helmet (X)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rTroll's Leather Chestplate (X)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rTroll's Leather Leggings (X)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rTroll's Leather Boots (X)").color(color).enchantment(Enchantment.LOOT_BONUS_MOBS, 3).make(),
                new ItemBuilder(Material.STICK).amount(64).make(),
                new ItemBuilder(Material.FIREWORK).amount(12).make(),
                new ItemBuilder(Material.FIREBALL).amount(3).make(),
                ItemUtils.buildPotion(PotionEffectType.INVISIBILITY, 20 * 20, 0, (short) 8238, 1));

        //1x invisibility potion for 20 seconds

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Troll() {

        super(
                "Troll", "Trololol", new ItemBuilder(Material.WEB).amount(2).make()
                , Arrays.asList(125000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
