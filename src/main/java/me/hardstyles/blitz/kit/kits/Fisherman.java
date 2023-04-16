package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fisherman extends Kit {


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

        I = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (I)").amount(1).make(),
                new ItemStack(Material.COOKED_FISH, 2));
        II = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (II)").amount(1).make(),
                new ItemStack(Material.COOKED_FISH, 2),
                new ItemStack(Material.RAW_FISH, 2));
        III = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (III)").amount(1).make(),
                new ItemStack(Material.COOKED_FISH, 3),
                new ItemStack(Material.RAW_FISH, 3));
        IV = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (IV)").enchantment
                        (Enchantment.DURABILITY, 1).amount(1).make(),
                new ItemStack(Material.COOKED_FISH, 3),
                new ItemStack(Material.RAW_FISH, 3));
        V = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (V)").enchantment
                        (Enchantment.DURABILITY, 1).enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemStack(Material.COOKED_FISH, 4),
                new ItemStack(Material.RAW_FISH, 4));
        VI = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (VI)").enchantment
                        (Enchantment.DURABILITY, 1).enchantment(Enchantment.DAMAGE_ALL, 1).amount(1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&Fisherman's Leather Helmet (VI)").make(),
                new ItemStack(Material.COOKED_FISH, 4),
                new ItemStack(Material.RAW_FISH, 4));
        VII = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (VII)").enchantment
                        (Enchantment.DURABILITY, 1).enchantment(Enchantment.DAMAGE_ALL, 2).amount(1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&Fisherman's Leather Helmet (VII)").make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&Fisherman's Chain Chestplate (VII)").make(),
                new ItemStack(Material.COOKED_FISH, 4),
                new ItemStack(Material.RAW_FISH, 4));
        VIII = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (VIII)").enchantment
                        (Enchantment.DURABILITY, 1).enchantment(Enchantment.DAMAGE_ALL, 3).amount(1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&Fisherman's Leather Helmet (VIII)").make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&Fisherman's Chain Chestplate (VIII)").make(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).name("&Fisherman's Leather Leggings (VIII)").make(),
                new ItemStack(Material.COOKED_FISH, 4),
                new ItemStack(Material.RAW_FISH, 4));
        IX = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (IX)").enchantment
                        (Enchantment.DURABILITY, 1).enchantment(Enchantment.DAMAGE_ALL, 3).amount(1).make(),
                new ItemBuilder(Material.LEATHER_HELMET).name("&Fisherman's Leather Helmet (IX)").make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&Fisherman's Chain Chestplate (IX)").make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&Fisherman's Chain Leggings (IX)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&Fisherman's Chain Boots (IX)").make(),
                new ItemStack(Material.COOKED_FISH, 4),
                new ItemStack(Material.RAW_FISH, 4));
        X = Arrays.asList(new ItemBuilder(Material.FISHING_ROD).name("&rFisherman's Fishing Rod (X)").enchantment
                        (Enchantment.DURABILITY, 1).enchantment(Enchantment.DAMAGE_ALL, 4).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&Fisherman's Chain Helmet (X)").enchantment(Enchantment.OXYGEN, 1).
                        make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&Fisherman's Chain Chestplate (X)").make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&Fisherman's Chain Leggings (X)").make(),
                new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&Fisherman's Chain Boots (X)").make(),
                new ItemStack(Material.COOKED_FISH, 4),
                new ItemStack(Material.RAW_FISH, 4));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Fisherman() {

        super(
                "Fisherman", "Here - fishy fishy.", new ItemBuilder(Material.FISHING_ROD).amount(1).make()
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
