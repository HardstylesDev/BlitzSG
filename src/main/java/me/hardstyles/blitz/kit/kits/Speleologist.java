package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Speleologist extends Kit {


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
        I = Arrays.asList(new ItemBuilder(Material.WOOD_PICKAXE).name("&rSpeleologist's Wooden Pickaxe §7(I)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemStack(Material.POISONOUS_POTATO, 2),
                new ItemStack(Material.STICK, 1));
        II = Arrays.asList(new ItemBuilder(Material.WOOD_PICKAXE).name("&rSpeleologist's Wooden Pickaxe §7(II)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemStack(Material.BAKED_POTATO, 1),
                new ItemStack(Material.STICK, 1));
        III = Arrays.asList(new ItemBuilder(Material.WOOD_PICKAXE).name("&rSpeleologist's Wooden Pickaxe §7(III)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemStack(Material.BAKED_POTATO, 3),
                new ItemStack(Material.STICK, 2));
        IV = Arrays.asList(new ItemBuilder(Material.STONE_PICKAXE).name("&rSpeleologist's Stone Pickaxe §7(IV)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet §7(IV)").make(),
                new ItemStack(Material.BAKED_POTATO, 4),
                new ItemStack(Material.STICK, 2));
        V = Arrays.asList(new ItemBuilder(Material.STONE_PICKAXE).name("&rSpeleologist's Stone Pickaxe §7(V)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet §7(V)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemStack(Material.BOWL, 1),
                new ItemStack(Material.BAKED_POTATO, 4),
                new ItemStack(Material.STICK, 2));
        VI = Arrays.asList(new ItemBuilder(Material.STONE_PICKAXE).name("&rSpeleologist's Stone Pickaxe §7(VI)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet §7(VI)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots §7(VI)").make(),
                new ItemStack(Material.BOWL, 1),
                new ItemStack(Material.BAKED_POTATO, 5),
                new ItemStack(Material.STICK, 2));
        VII = Arrays.asList(new ItemBuilder(Material.IRON_PICKAXE).name("&rSpeleologist's Iron Pickaxe §7(VII)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet §7(VII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate §7(VII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots §7(VII)").make(),
                new ItemStack(Material.BOWL, 3),
                new ItemStack(Material.BAKED_POTATO, 7),
                new ItemStack(Material.STICK, 2));
        VIII = Arrays.asList(new ItemBuilder(Material.IRON_PICKAXE).name("&rSpeleologist's Iron Pickaxe §7(VIII)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rSpeleologist's Iron Helmet §7(VIII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate §7(VIII)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSpeleologist's Leather Leggings §7(VIII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots §7(VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 1).make(),
                new ItemStack(Material.BOWL, 3),
                new ItemStack(Material.BAKED_POTATO, 9),
                new ItemStack(Material.STICK, 2));
        IX = Arrays.asList(new ItemBuilder(Material.IRON_PICKAXE).name("&rSpeleologist's Iron Pickaxe §7(IX)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rSpeleologist's Iron Helmet §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSpeleologist's Leather Leggings §7(IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots §7(IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 1).make(),
                new ItemStack(Material.BOWL, 5),
                new ItemStack(Material.BAKED_POTATO, 12),
                new ItemStack(Material.STICK, 2));
        X = Arrays.asList(new ItemBuilder(Material.DIAMOND_PICKAXE).name("&rSpeleologist's Diamond Pickaxe §7(X)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rSpeleologist's Iron Helmet §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSpeleologist's Leather Leggings §7(X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots §7(X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemStack(Material.BOWL, 5),
                new ItemStack(Material.BAKED_POTATO, 15),
                new ItemStack(Material.STICK, 2));


        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Speleologist() {

        super(
                "Speleologist", "It means you like caves", new ItemStack(Material.WOOD_PICKAXE, 1)
                , Arrays.asList(0, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
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
