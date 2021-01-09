package me.syesstyles.blitz.kit.kits;

import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Necromancer extends Kit {


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


        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(1).make());
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make());
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (III)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make());
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (IV)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make());
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (V)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow (V)").make(),
                new ItemBuilder(Material.ARROW).amount(3).make());
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (VI)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots (VI)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),

                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow (VI)").make(),
                new ItemBuilder(Material.ARROW).amount(3).make());
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots (VII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate (VII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),

                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (VII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow (VII)").make(),
                new ItemBuilder(Material.ARROW).amount(9).make());
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots (VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate (VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow (VIII)").enchantment(Enchantment.ARROW_KNOCKBACK, 1).make(),
                new ItemBuilder(Material.ARROW).amount(9).make());


        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots (IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rNecromancer's Chainmail Leggings (IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate (IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow (IX)").enchantment(Enchantment.ARROW_KNOCKBACK, 1).make(),
                new ItemBuilder(Material.ARROW).amount(12).make());
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots (X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rNecromancer's Chainmail Leggings (X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate (X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet (X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow (X)").enchantment(Enchantment.ARROW_KNOCKBACK, 1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rNecromancer's Wooden Sword (X)").make(),
                new ItemBuilder(Material.ARROW).amount(16).make());

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Necromancer() {

        super(
                "Necromancer", "Living dead mobs", new ItemBuilder(Material.MONSTER_EGG).durability(54).make()
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
