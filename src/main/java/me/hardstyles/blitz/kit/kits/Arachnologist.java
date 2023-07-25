package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Arachnologist extends Kit {


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


        I = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(1).make());
        II = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(1).make(),
                new ItemStack(Material.WEB, 1));
        III = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rArachnologist's Leather Helmet §7(III)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemStack(Material.WEB, 2));
        IV = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rArachnologist's Leather Helmet §7(IV)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArachnologist's Leather Boots §7(IV)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemStack(Material.WEB, 2));
        V = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(2).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rArachnologist's Wood Axe §7(V)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rArachnologist's Leather Helmet §7(V)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArachnologist's Leather Leggings §7(V)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArachnologist's Leather Boots §7(V)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemStack(Material.WEB, 3));
        VI = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rArachnologist's Wood Axe §7(VI)").make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&rArachnologist's Leather Helmet §7(VI)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArachnologist's Leather Chestplate §7(VI)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArachnologist's Leather Leggings §7(VI)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArachnologist's Leather Boots §7(VI)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemStack(Material.WEB, 3));
        VII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(3).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rArachnologist's Stone Axe §7(VII)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rArachnologist's Iron Helmet §7(VII)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArachnologist's Leather Chestplate §7(VII)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArachnologist's Leather Leggings §7(VII)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_BOOTS).name("&rArachnologist's Leather Boots §7(VII)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemStack(Material.WEB, 4));
        VIII = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(4).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rArachnologist's Stone Axe §7(VIII)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rArachnologist's Iron Helmet §7(VIII)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArachnologist's Leather Chestplate §7(VIII)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArachnologist's Leather Leggings §7(VIII)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rArachnologist's Iron Boots §7(VIII)").make(),
                new ItemStack(Material.WEB, 5),
                new ItemStack(Material.APPLE, 1));
        IX = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rArachnologist's Stone Sword §7(IX)").make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rArachnologist's Iron Helmet §7(IX)").make(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).name("&rArachnologist's Leather Chestplate §7(IX)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArachnologist's Leather Leggings §7(IX)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rArachnologist's Iron Boots §7(IX)").make(),
                new ItemStack(Material.WEB, 5),
                new ItemStack(Material.APPLE, 2));
        X = Arrays.asList(new ItemBuilder(Material.MONSTER_EGG).durability(52).name("&rSpider Spawn Egg").amount(5).make(),
                new ItemBuilder(Material.IRON_SWORD).name("&rArachnologist's Iron Sword §7(X)").enchantment(Enchantment.DURABILITY,1).make(),
                new ItemBuilder(Material.IRON_HELMET).name("&rArachnologist's Iron Helmet §7(X)").make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rArachnologist's Chain Chestplate §7(X)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&rArachnologist's Leather Leggings §7(X)").color(Color.fromRGB(120, 120, 120)).make(),
                new ItemBuilder(Material.IRON_BOOTS).name("&rArachnologist's Iron Boots §7(X)").make(),
                new ItemStack(Material.WEB, 6),
                new ItemStack(Material.APPLE, 3));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Arachnologist() {

        super(
                "Arachnologist", "Some people might be scared", new ItemBuilder(Material.MONSTER_EGG).durability(52).amount(1).make()
                , Arrays.asList(192000, 100, 1000, 2000, 4000, 16000, 50000, 100000, 250000, 1000000)
                , Collections.emptyList()
                , Collections.singletonList(Collections.singletonList(new ItemStack(Material.AIR, 1))), BlitzSG.getInstance().getRankManager().getRankByName("VIP+")

        );
    }

}
