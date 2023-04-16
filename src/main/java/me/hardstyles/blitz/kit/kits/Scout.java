package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scout extends Kit {


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
        int SL = 10;

        I = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 2, (short) 8226, 2));
        II = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 2, (short) 8226, 3));
        III = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 2, (short) 8226, 3));
        IV = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 3, (short) 8226, 4),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rScout's Leather Chestplate (IV)").make());
        V = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 3, (short) 8226, 5),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rScout's Leather Chestplate (V)").make(),
                ItemUtils.buildPotion(PotionEffectType.SLOW, SL * 20, 3, (short) 16426, 1),
                ItemUtils.buildPotion(PotionEffectType.INVISIBILITY, 5 * 20, 1, (short) 16430, 1));
        VI = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 3, (short) 8226, 5),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rScout's Leather Chestplate (VI)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rScout's Leather Helmet (VI)").enchantment(Enchantment.OXYGEN, 1).make(),

                ItemUtils.buildPotion(PotionEffectType.SLOW, SL * 20, 3, (short) 16426, 1),
                ItemUtils.buildPotion(PotionEffectType.INVISIBILITY, 5 * 20, 1, (short) 16430, 1));
        VII = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 3, (short) 8226, 5),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rScout's Leather Chestplate (VII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rScout's Leather Helmet (VII)").enchantment(Enchantment.OXYGEN, 1).make(),

                ItemUtils.buildPotion(PotionEffectType.SLOW, SL * 20, 3, (short) 16426, 2),
                ItemUtils.buildPotion(PotionEffectType.INVISIBILITY, 5 * 20, 1, (short) 16430, 1));
        VIII = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 3, (short) 8226, 5),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rScout's Leather Chestplate (VIII)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rScout's Leather Helmet (VIII)").enchantment(Enchantment.OXYGEN, 2).make(),
                ItemUtils.buildPotion(PotionEffectType.SLOW, SL * 20, 3, (short) 16426, 2),
                ItemUtils.buildPotion(PotionEffectType.INVISIBILITY, 5 * 20, 1, (short) 16430, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL, 1, 3, (short) 8261, 1));
        IX = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 3, (short) 8226, 5),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rScout's Leather Chestplate (IX)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rScout's Leather Helmet (IX)").enchantment(Enchantment.OXYGEN, 2).make(),
                ItemUtils.buildPotion(PotionEffectType.SLOW, SL * 20, 3, (short) 16426, 3),
                ItemUtils.buildPotion(PotionEffectType.INVISIBILITY, 5 * 20, 1, (short) 16430, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL, 1, 3, (short) 8261, 2));
        X = Arrays.asList(ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 3, (short) 8226, 5),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rScout's Leather Chestplate (X)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rScout's Leather Helmet (X)").enchantment(Enchantment.OXYGEN, 2).make(),
                ItemUtils.buildPotion(PotionEffectType.SLOW, SL * 20, 3, (short) 16426, 5),
                ItemUtils.buildPotion(PotionEffectType.INVISIBILITY, 5 * 20, 1, (short) 16430, 1),
                ItemUtils.buildPotion(PotionEffectType.HEAL, 1, 3, (short) 8261, 2));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Scout() {

        super(
                "Scout", "Keep running!", ItemUtils.buildPotion(PotionEffectType.SPEED, 15 * 20, 1, (short) 8226, 2)
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
