package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.ItemBuilder;
import me.hardstyles.blitz.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlimeySlime extends Kit {


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
        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(1).make());
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rSlimeySlime's Wood Sword §7(II)").enchantment(Enchantment.KNOCKBACK, 1).make());
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rSlimeySlime's Wood Sword §7(III)").enchantment(Enchantment.KNOCKBACK, 1).make());
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rSlimeySlime's Wood Sword §7(IV)").enchantment(Enchantment.KNOCKBACK, 1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rSlimeySlime's Chain Helmet §7(IV)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                ItemUtils.buildPotion(PotionEffectType.SLOW, 15 * 20, 4, (short) 16426, 1));
        // 1 x potion slowness 4 15 seconds
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rSlimeySlime's Wood Sword §7(V)").enchantment(Enchantment.KNOCKBACK, 1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rSlimeySlime's Chain Helmet §7(V)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                ItemUtils.buildPotion(PotionEffectType.SLOW, 15 * 20, 4, (short) 16426, 2));
        // 2 x potion slowness 4 15 seconds
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rSlimeySlime's Wood Sword §7(VI)").enchantment(Enchantment.KNOCKBACK, 1).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSlimeySlime's Iron Helmet §7(VI)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                ItemUtils.buildPotion(PotionEffectType.SLOW, 15 * 20, 4, (short) 16426, 3));
        //  3 x potion slowness 4 15 seconds
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rSlimeySlime's Wood Sword §7(VII)").enchantment(Enchantment.KNOCKBACK, 1).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSlimeySlime's Iron Helmet §7(VII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemStack(Material.COOKED_CHICKEN, 3),
                ItemUtils.buildPotion(PotionEffectType.SLOW, 15 * 20, 4, (short) 16426, 3));
        // 3 x potion slowness 4 15 seconds
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rSlimeySlime's Stone Sword §7(VIII)").enchantment(Enchantment.KNOCKBACK, 1).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSlimeySlime's Iron Helmet §7(VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSlimeySlime's Iron Boots §7(VIII)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemStack(Material.COOKED_CHICKEN, 5),
                ItemUtils.buildPotion(PotionEffectType.SLOW, 15 * 20, 4, (short) 16426, 4));
        //  4 x potion slowness 4 15 seconds
        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rSlimeySlime's Stone Sword §7(IX)").enchantment(Enchantment.KNOCKBACK, 2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSlimeySlime's Iron Helmet §7(IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSlimeySlime's Iron Boots §7(IX)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemStack(Material.COOKED_CHICKEN, 5),
                ItemUtils.buildPotion(PotionEffectType.SLOW, 15 * 20, 4, (short) 16426, 4));
        // 4 x potion slowness 4 15 seconds
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(55).name("&rSlime Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rSlimeySlime's Stone Sword §7(X)").enchantment(Enchantment.KNOCKBACK, 2).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rSlimeySlime's Iron Helmet §7(X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rSlimeySlime's Iron Boots §7(X)").enchantment(Enchantment.PROTECTION_PROJECTILE, 4).make(),
                new ItemStack(Material.CARROT, 5),
                new ItemStack(Material.COOKED_CHICKEN, 5),
                new ItemStack(Material.DIAMOND, 1),
                ItemUtils.buildPotion(PotionEffectType.SLOW, 15 * 20, 4, (short) 16426, 5));
        //5 x potion slowness 4 15 seconds
        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public SlimeySlime() {

        super(
                "SlimeySlime", "It's kinda Slimey", new ItemBuilder(Material.MONSTER_EGG).durability(55).make()
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
