package me.syesstyles.blitz.kit.kits;

import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Blaze extends Kit {


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
        int b = 153;
        int g = 51;

        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").make());
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (II)").make());
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (III)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (III)").color(Color.fromRGB(r, b, g)).make());
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (IV)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (IV)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 1).make(),
                new ItemStack(Material.GRILLED_PORK, 2));
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (V)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (V)").enchantment(Enchantment.FIRE_ASPECT,1).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (V)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemStack(Material.GRILLED_PORK, 2));
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (VI)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (VI)").enchantment(Enchantment.FIRE_ASPECT,1).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (VI)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBlaze's Leather Helmet (VI)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 1).make(),
                new ItemStack(Material.GRILLED_PORK, 2));
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (VII)").make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (VII)").enchantment(Enchantment.FIRE_ASPECT,1).durability(Material.WOOD_SWORD.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (VII)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rBlaze's Leather Leggings (VII)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBlaze's Leather Helmet (VII)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 1).make(),
                new ItemStack(Material.GRILLED_PORK, 2));
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rBlaze's Wooden Sword (VIII)").make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rBlaze's Stone Sword (VIII)").enchantment(Enchantment.FIRE_ASPECT,1).durability(Material.STONE.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (VIII)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rBlaze's Leather Leggings (VIII)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBlaze's Leather Helmet (VIII)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 1).make(),
                new ItemStack(Material.GRILLED_PORK, 2));

        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rBlaze's Stone Sword (IX)").make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rBlaze's Stone Sword (IX)").enchantment(Enchantment.FIRE_ASPECT,1).durability(Material.STONE.getMaxDurability() - 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (IX)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rBlaze's Leather Leggings (IX)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBlaze's Leather Helmet (IX)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rBlaze's Leather Boots (IX)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemStack(Material.GRILLED_PORK, 3));
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(61).name("&rBlaze Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rBlaze's Stone Sword (X)").make(),
                new ItemBuilder(Material.DIAMOND_AXE).name("&rBlaze's Diamond Axe (X)").enchantment(Enchantment.FIRE_ASPECT,1).durability(Material.DIAMOND_AXE.getMaxDurability() - 6).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rBlaze's Leather Chestplate (X)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rBlaze's Leather Leggings (X)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 3).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rBlaze's Leather Helmet (X)").color(Color.fromRGB(r, b, g)).enchantment(Enchantment.PROTECTION_FIRE, 2).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rBlaze's Iron Boots (X)").enchantment(Enchantment.PROTECTION_FIRE, 3).make(),
                new ItemStack(Material.GRILLED_PORK, 6));
        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Blaze() {

        super(
                "Blaze", "Burn, baby burn.", new ItemBuilder(Material.MONSTER_EGG).durability(61).amount(1).make()
                , Arrays.asList(250000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
