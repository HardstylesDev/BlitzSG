package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
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
        I = Arrays.asList(new ItemBuilder(Material.WOOD_PICKAXE).name("&rSpeleologist's Wooden Pickaxe (I)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemStack(Material.POISONOUS_POTATO, 2),
                new ItemStack(Material.STICK, 1));
        II = Arrays.asList(new ItemBuilder(Material.WOOD_PICKAXE).name("&rSpeleologist's Wooden Pickaxe (II)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemStack(Material.BAKED_POTATO, 1),
                new ItemStack(Material.STICK, 1));
        III = Arrays.asList(new ItemBuilder(Material.WOOD_PICKAXE).name("&rSpeleologist's Wooden Pickaxe (III)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemStack(Material.BAKED_POTATO, 3),
                new ItemStack(Material.STICK, 2));
        IV = Arrays.asList(new ItemBuilder(Material.STONE_PICKAXE).name("&rSpeleologist's Stone Pickaxe (IV)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet (IV)").make(),
                new ItemStack(Material.BAKED_POTATO, 4),
                new ItemStack(Material.STICK, 2));
        V = Arrays.asList(new ItemBuilder(Material.STONE_PICKAXE).name("&rSpeleologist's Stone Pickaxe (V)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet (V)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemStack(Material.BOWL, 1),
                new ItemStack(Material.BAKED_POTATO, 4),
                new ItemStack(Material.STICK, 2));
        VI = Arrays.asList(new ItemBuilder(Material.STONE_PICKAXE).name("&rSpeleologist's Stone Pickaxe (VI)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet (VI)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots (VI)").make(),
                new ItemStack(Material.BOWL, 1),
                new ItemStack(Material.BAKED_POTATO, 5),
                new ItemStack(Material.STICK, 2));
        VII = Arrays.asList(new ItemBuilder(Material.IRON_PICKAXE).name("&rSpeleologist's Iron Pickaxe (VII)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSpeleologist's Iron Helmet (VII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate (VII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots (VII)").make(),
                new ItemStack(Material.BOWL, 3),
                new ItemStack(Material.BAKED_POTATO, 7),
                new ItemStack(Material.STICK, 2));
        VIII = Arrays.asList(new ItemBuilder(Material.IRON_PICKAXE).name("&rSpeleologist's Iron Pickaxe (VIII)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rSpeleologist's Iron Helmet (VIII)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate (VIII)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSpeleologist's Leather Leggings (VIII)").make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots (VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 1).make(),
                new ItemStack(Material.BOWL, 3),
                new ItemStack(Material.BAKED_POTATO, 9),
                new ItemStack(Material.STICK, 2));
        IX = Arrays.asList(new ItemBuilder(Material.IRON_PICKAXE).name("&rSpeleologist's Iron Pickaxe (IX)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rSpeleologist's Iron Helmet (IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate (IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSpeleologist's Leather Leggings (IX)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots (IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 1).make(),
                new ItemStack(Material.BOWL, 5),
                new ItemStack(Material.BAKED_POTATO, 12),
                new ItemStack(Material.STICK, 2));
        X = Arrays.asList(new ItemBuilder(Material.DIAMOND_PICKAXE).name("&rSpeleologist's Diamond Pickaxe (X)").enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(96).name("&rMushroom Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rSpeleologist's Iron Helmet (X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rSpeleologist's Leather Chestplate (X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rSpeleologist's Leather Leggings (X)").enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSpeleologist's Iron Boots (X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
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
