package me.hardstyles.blitz.kit.kits;

import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Astronaut extends Kit {


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

    ItemStack pot(int i){
        return ItemUtils.buildPotion(PotionEffectType.DAMAGE_RESISTANCE, 10 * 20, 1, (short) 8193, i);
    }

    @Override
    public void initialize() {

        I = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (I)").enchantment(Enchantment.PROTECTION_FALL,2).amount(1).make());
        II = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (II)").enchantment(Enchantment.PROTECTION_FALL,2).amount(1).make(),
                pot(1));
        III = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (III)").enchantment(Enchantment.PROTECTION_FALL,2).amount(1).make(),
                pot(2));
        IV = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (IV)").enchantment(Enchantment.PROTECTION_FALL,3).amount(1).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rAstronaut's Wooden Axe (IV)").make(),
                pot(3));
        V =Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (V)").enchantment(Enchantment.PROTECTION_FALL,4).amount(1).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rAstronaut's Wooden Axe (V").make(),
                pot(3));
        VI = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (VI)").enchantment(Enchantment.PROTECTION_FALL,4).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rAstronaut's Chainmail Helmet (VI)").enchantment(Enchantment.PROTECTION_EXPLOSIONS,1).amount(1).make(),
                new ItemBuilder(Material.WOOD_AXE).name("&rAstronaut's Wooden Axe (VI").make(),
                pot(3));
        VII = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (VII)").enchantment(Enchantment.PROTECTION_FALL,4).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rAstronaut's Chainmail Helmet (VII)").enchantment(Enchantment.PROTECTION_EXPLOSIONS,2).amount(1).make(),
                new ItemBuilder(Material.STONE_AXE).name("&rAstronaut's Stone Axe (VII").make(),
                pot(3));
        VIII = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (VIII)").enchantment(Enchantment.PROTECTION_FALL,5).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rAstronaut's Chainmail Helmet (VIII)").enchantment(Enchantment.PROTECTION_EXPLOSIONS,3).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rAstronaut's Chainmail Chestplate (VIII)").make(),
                new ItemBuilder(Material.STONE_AXE).name("&rAstronaut's Stone Axe (VIII").make(),
                pot(3));
        IX = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (IX)").enchantment(Enchantment.PROTECTION_FALL,7).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rAstronaut's Chainmail Helmet (IX)").enchantment(Enchantment.PROTECTION_EXPLOSIONS,3).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rAstronaut's Chainmail Chestplate (IX)").make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rAstronaut's Chainmail Leggings (IX)").make(),
                new ItemBuilder(Material.STONE_AXE).name("&rAstronaut's Stone Axe (IX").make(),
                pot(3));
        X = Arrays.asList(new ItemBuilder(Material.CHAINMAIL_BOOTS).name("&rAstronaut's Chainmail Boots (X)").enchantment(Enchantment.PROTECTION_FALL,10).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).name("&rAstronaut's Chainmail Helmet (X)").enchantment(Enchantment.PROTECTION_EXPLOSIONS,3).amount(1).make(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name("&rAstronaut's Chainmail Chestplate (X)").make(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).name("&rAstronaut's Chainmail Leggings (X)").make(),
                new ItemBuilder(Material.STONE_SWORD).name("&rAstronaut's Stone Sword (X").make(),
                pot(4));

        setKitItems(Arrays.asList(I, II, III, IV, V, VI, VII, VIII, IX, X));
    }

    public Astronaut() {

        super(
                "Astronaut", "One small step for man.", new ItemBuilder(Material.CHAINMAIL_BOOTS).enchantment(Enchantment.PROTECTION_FALL, 2).amount(1).make()
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
