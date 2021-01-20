package me.syesstyles.blitz.kit.kits;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.ItemBuilder;
import me.syesstyles.blitz.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Toxicologist extends Kit {


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

    ItemStack poison(int i) {
        return ItemUtils.buildPotion(PotionEffectType.POISON, 8 * 20, 2, (short) 16420, i);
    }

    ItemStack regen(int i) {
        return ItemUtils.buildPotion(PotionEffectType.REGENERATION, 8 * 20, 1, (short) 8225, i);
    }

    @Override
    public void initialize() {

        I = Arrays.asList(poison(1));
        II = Arrays.asList(poison(2));
        III = Arrays.asList(poison(2), regen(1));
        IV = Arrays.asList(poison(2), regen(1),
                new ItemBuilder(Material.WOOD_AXE).name("&rToxicologist's Wooden Axe (IV)").make());
        V = Arrays.asList(poison(3), regen(1),
                new ItemBuilder(Material.WOOD_AXE).name("&rToxicologist's Wooden Axe (V)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rToxicologist's Golden Helmet (V)").make());
        VI =  Arrays.asList(poison(3), regen(1),
                new ItemBuilder(Material.WOOD_SWORD).name("&rToxicologist's Wooden Sword (VI)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rToxicologist's Golden Helmet (VI)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rToxicologist's Golden Boots (VI)").make());
        VII =  Arrays.asList(poison(3), regen(1),
                new ItemBuilder(Material.STONE_AXE).name("&rToxicologist's Stone Axe (VII)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rToxicologist's Golden Helmet (VII)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rToxicologist's Golden Boots (VII)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rToxicologist's Leather Leggings (VII)").make());
        VIII =  Arrays.asList(poison(4), regen(2),
                new ItemBuilder(Material.STONE_SWORD).name("&rToxicologist's Stone Sword (VIII)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rToxicologist's Golden Helmet (VIII)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rToxicologist's Golden Boots (VIII)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rToxicologist's Leather Leggings (VIII)").make());
        IX = Arrays.asList(poison(4), regen(2),
                new ItemBuilder(Material.STONE_SWORD).name("&rToxicologist's Stone Sword (IX)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rToxicologist's Golden Helmet (IX)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rToxicologist's Golden Boots (IX)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rToxicologist's Leather Leggings (IX)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rToxicologist's Golden Chestplate (IX)").make());
        X =  Arrays.asList(poison(5), regen(3),
                new ItemBuilder(Material.STONE_SWORD).name("&rToxicologist's Stone Sword (X)").make(),
                new ItemBuilder(Material.GOLD_HELMET).name("&rToxicologist's Golden Helmet (X)").make(),
                new ItemBuilder(Material.GOLD_BOOTS).name("&rToxicologist's Golden Boots (X)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rToxicologist's Leather Leggings (X)").make(),
                new ItemBuilder(Material.IRON_CHESTPLATE).name("&rToxicologist's Iron Chestplate (X)").make());

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Toxicologist() {

        super(
                "Toxicologist", "Make them taste their own medicine.", ItemUtils.buildPotion(PotionEffectType.POISON, 8 * 20, 2, (short) 16420, 1)
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
