package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.ItemBuilder;
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
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(III)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make());
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(IV)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make());
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(V)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow §7(V)").make(),
                new ItemBuilder(Material.ARROW).amount(3).make());
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(VI)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots §7(VI)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),

                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow §7(VI)").make(),
                new ItemBuilder(Material.ARROW).amount(3).make());
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots §7(VII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate §7(VII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),

                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(VII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow §7(VII)").make(),
                new ItemBuilder(Material.ARROW).amount(9).make());
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots §7(VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate §7(VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 2).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow §7(VIII)").enchantment(Enchantment.ARROW_KNOCKBACK, 1).make(),
                new ItemBuilder(Material.ARROW).amount(9).make());


        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots §7(IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rNecromancer's Chainmail Leggings §7(IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate §7(IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 3).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow §7(IX)").enchantment(Enchantment.ARROW_KNOCKBACK, 1).make(),
                new ItemBuilder(Material.ARROW).amount(12).make());
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(54).name("&rZombie Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.MONSTER_EGG).durability(51).name("&rSkeleton Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rNecromancer's Chainmail Boots §7(X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rNecromancer's Chainmail Leggings §7(X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rNecromancer's Chainmail Chestplate §7(X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rNecromancer's Chainmail Helmet §7(X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.BOW).name("&rNecromancer's Bow §7(X)").enchantment(Enchantment.ARROW_KNOCKBACK, 1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rNecromancer's Wooden Sword §7(X)").make(),
                new ItemBuilder(Material.ARROW).amount(16).make());

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Necromancer() {

        super(
                "Necromancer", "Living dead mobs", new ItemBuilder(Material.MONSTER_EGG).durability(54).make()
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
