package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.ItemBuilder;
import me.hardstyles.blitz.util.ItemUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedDragon extends Kit {


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

    ItemStack pot(int i) {
        return ItemUtils.buildPotion(PotionEffectType.FIRE_RESISTANCE, 10 * 20, 1, (short) 8227, i);
    }

    @Override
    public void initialize() {
        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(1).make());
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(1).make(),
                pot(1));
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(1).make(),
                pot(1),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRedDragon's Leather Helmet §7(III)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make());

        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(2).make(),
                pot(1),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRedDragon's Leather Helmet §7(IV)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rRedDragon's Wooden Axe §7(IV)").make());
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(2).make(),
                pot(1),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRedDragon's Leather Helmet §7(V)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRedDragon's Leather Leggings §7(V)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rRedDragon's Wooden Axe §7(V)").make());
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(2).make(),
                pot(1),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRedDragon's Leather Helmet §7(VI)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRedDragon's Leather Leggings §7(VI)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rRedDragon's Iron Boots §7(VI)").make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rRedDragon's Wooden Axe §7(VI)").make());
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(2).make(),
                pot(2),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRedDragon's Leather Helmet §7(VII)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRedDragon's Leather Leggings §7(VII)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRedDragon's Leather Chestplate §7(VII)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rRedDragon's Iron Boots §7(VII)").make(),
                new ItemBuilder(Material.STONE_AXE).name("&rRedDragon's Stone Axe §7(VII)").make(),
                new ItemStack(Material.COOKED_FISH, 3));
        // 3 x potion slowness 4 15 seconds
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(3).make(),
                pot(2),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rRedDragon's Leather Helmet §7(VIII)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRedDragon's Leather Leggings §7(VIII)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRedDragon's Leather Chestplate §7(VIII)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rRedDragon's Iron Boots §7(VIII)").make(),
                new ItemBuilder(Material.STONE_AXE).name("&rRedDragon's Stone Axe §7(VIII)").make(),
                new ItemStack(Material.COOKED_FISH, 5),
                new ItemBuilder(Material.FLINT_AND_STEEL).name("&rRedDragon's Flint And Steel §7(VIII)").durability(Material.FLINT_AND_STEEL.getMaxDurability() - 2).make());
        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(3).make(),
                pot(2),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rRedDragon's Diamond Helmet §7(IX)").enchantment(Enchantment.PROTECTION_FIRE, 3).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRedDragon's Leather Leggings §7(IX)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rRedDragon's Leather Chestplate §7(IX)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rRedDragon's Iron Boots §7(IX)").make(),
                new ItemBuilder(Material.STONE_AXE).name("&rRedDragon's Stone Axe §7(IX)").make(),
                new ItemStack(Material.COOKED_FISH, 7),
                new ItemBuilder(Material.FLINT_AND_STEEL).name("&rRedDragon's Flint And Steel §7(IX)").durability(Material.FLINT_AND_STEEL.getMaxDurability() - 2).make());

        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(62).name("&rMagma Cube Spawn Egg").amount(3).make(),
                pot(2),
                new ItemBuilder(Material.DIAMOND_HELMET).name("&rRedDragon's Diamond Helmet §7(X)").enchantment(Enchantment.PROTECTION_FIRE, 3).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rRedDragon's Leather Leggings §7(X)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rRedDragon's Chainmail Chestplate §7(X)").enchantment(Enchantment.PROTECTION_FIRE, 3).color(Color.fromRGB(255, 0, 0)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rRedDragon's Iron Boots §7(X)").make(),
                new ItemBuilder(Material.STONE_AXE).name("&rRedDragon's Stone Axe §7(X)").make(),
                new ItemStack(Material.COOKED_FISH, 9),
                new ItemBuilder(Material.FLINT_AND_STEEL).name("&rRedDragon's Flint And Steel §7(X)").durability(Material.FLINT_AND_STEEL.getMaxDurability() - 2).make());

        //5 x potion slowness 4 15 seconds
        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public RedDragon() {

        super(
                "RedDragon", "You saw it here first!", new ItemBuilder(Material.MONSTER_EGG).durability(62).make()
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
