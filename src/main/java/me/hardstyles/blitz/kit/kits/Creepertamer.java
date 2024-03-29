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

public class Creepertamer extends Kit {


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


        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(1).make());
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rCreepertamer's Leather Chestplate §7(II)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 1).make());
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rCreepertamer's Wood Axe §7(III)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rCreepertamer's Leather Chestplate §7(III)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 2).make());
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rCreepertamer's Wood Axe §7(IV)").make(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).name("&rCreepertamer's Gold Chestplate §7(IV)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 1).make(),
                new ItemStack(Material.TNT, 1));
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rCreepertamer's Wood Axe §7(V)").make(),
                new ItemBuilder(Material.IRON_CHESTPLATE).name("&rCreepertamer's Iron Chestplate §7(V)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 1).make(),
                new ItemStack(Material.TNT, 2));
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rCreepertamer's Wood Sword §7(VI)").make(),
                new ItemBuilder(Material.IRON_CHESTPLATE).name("&rCreepertamer's Iron Chestplate §7(VI)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 1).make(),
                new ItemStack(Material.TNT, 3));
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rCreepertamer's Wood Sword §7(VII)").make(),
                new ItemBuilder(Material.IRON_CHESTPLATE).name("&rCreepertamer's Iron Chestplate §7(VII)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 2).make(),
                new ItemStack(Material.TNT, 4));
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_SWORD).name("&rCreepertamer's Wood Sword §7(VIII)").make(),
                new ItemBuilder(Material.IRON_CHESTPLATE).name("&rCreepertamer's Iron Chestplate §7(VIII)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 3).make(),
                new ItemStack(Material.TNT, 5));
        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rCreepertamer's Stone Sword §7(IX)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.IRON_CHESTPLATE).name("&rCreepertamer's Iron Chestplate §7(IX)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).make(),
                new ItemStack(Material.TNT, 6));
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(50).name("&rCreeper Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rCreepertamer's Stone Sword §7(X)").enchantment(Enchantment.DURABILITY, 1).make(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).name("&rCreepertamer's Diamond Chestplate §7(X)").enchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).make(),
                new ItemStack(Material.TNT, 8));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Creepertamer() {

        super(
                "Creepertamer", "Explosions are tasty!", new ItemBuilder(Material.MONSTER_EGG).durability(50).amount(1).make()
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
