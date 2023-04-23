package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.kit.KitUtils;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShopKitAdvancedGUI {

    public static void openGUI(Player p) {
        IPlayer bsgPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        //Create GUI
        Inventory inv = Bukkit.createInventory(null, (int) (((BlitzSG.getInstance().getKitManager().getKits().size() + 1) / 9) + 3) * 9 + 9, "§8Advanced Kit Upgrades");

        //Add Items
        int firstItem = 10;
        for (Kit kit : BlitzSG.getInstance().getKitManager().getKits()) {
            if (kit.getPrice(0) == 0)
                continue;
            inv.setItem(firstItem, ItemUtils.buildItem(new ItemStack(kit.getIcon())
                    , getName(bsgPlayer, kit)
                    , getFullDescription(bsgPlayer, kit)));
            if ((firstItem + 2) % 9 == 0) {
                firstItem += 3;
                continue;
            }
            firstItem++;
        }

        //Open the GUI
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }

    public static String getName(IPlayer iPlayer, Kit p) {
        if (p.getPrice(iPlayer.getKitLevel(p)) == -1)
            return "§a" + p.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(p));
        else if (iPlayer.getKitLevel(p) == 0) {
            if (iPlayer.getRank().getPosition() >= p.getRequiredRank().getPosition())
                return "§a" + p.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(p));
            else
                return "§c" + p.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(p));
        } else if (p.getPrice(iPlayer.getKitLevel(p)) <= iPlayer.getCoins())
            return "§e" + p.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(p) + 1);
        else
            return "§c" + p.getName() + KitUtils.getKitTag(iPlayer.getKitLevel(p) + 1);
    }

    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Kit p) {
        ArrayList<String> desc = new ArrayList<String>();

        getItemDescription(p, iPlayer.getKitLevel(p)).forEach(s -> desc.add(ChatColor.GRAY + s.replaceAll("" + ChatColor.RESET, "")));
        desc.add("");
        if (p.getPrice(iPlayer.getKitLevel(p)) == -1) {
            desc.add("§aMAX LEVEL!");
            return desc;
        }
        if (iPlayer.getKitLevel(p) == 0 && p.getPrice(0) > 0) {
            if (iPlayer.getRank().getPosition() >= p.getRequiredRank().getPosition()) {
                desc.add("§eCick to unlock!");
                desc.add("");
                desc.add("§7This is free because you are a " + iPlayer.getRank().getRankFormatted());
            } else
                desc.add("§7Requires " + p.getRequiredRank().getRankFormatted() + " §7or unlock it for §6" + NumberFormat.getNumberInstance(Locale.US).format(p.getPrice(iPlayer.getKitLevel(p))) + " §7coins.");
            return desc;
        }
        desc.add("§7Price: §6" + NumberFormat.getNumberInstance(Locale.US).format(p.getPrice(iPlayer.getKitLevel(p))));
        desc.add("");
        if (p.getPrice(iPlayer.getKitLevel(p)) <= iPlayer.getCoins())
            desc.add("§eClick to unlock!");
        else
            desc.add("§cNot enough coins!");
        return desc;
    }

    public static ArrayList<String> getItemDescription(Kit kit, int level) {

        List<ItemStack> items = new ArrayList<>();
        if (level == 10 || level == 0)
            items = kit.getKitItems(level);
        else items = kit.getKitItems(level + 1);
        ArrayList<String> desc = new ArrayList<String>();
        items.forEach(itemStack -> {
            if (itemStack.getType() == Material.MONSTER_EGG)
                desc.add(ChatColor.RESET + "" + itemStack.getAmount() + "x " + itemStack.getItemMeta().getDisplayName().split(" ")[0] + " Egg");
            else if (itemStack.getEnchantments().size() > 0)
                desc.add("" + ChatColor.RESET + capitalizeString(itemStack.getType().toString().replaceAll("_", " ")) + (" (" + getEnchantString(itemStack) + ")").replaceAll(" \\)", "\\)"));
            else if (itemStack.getType() == Material.POTION) {
                PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
                String pot = meta.getCustomEffects().get(0).getType().getName().toLowerCase();
                int lvl = meta.getCustomEffects().get(0).getAmplifier() + 1;
                desc.add("" + ChatColor.RESET + (itemStack.getAmount() > 1 ? itemStack.getAmount() + "x " : "") + capitalizeString(pot) + "" + KitUtils.getKitTag(lvl == 0 ? 1 : lvl) + " potion" + (itemStack.getAmount() > 1 ? "s" : ""));
            } else
                desc.add("" + ChatColor.RESET + (itemStack.getAmount() > 1 ? itemStack.getAmount() + "x " : "") + capitalizeString(itemStack.getType().toString().replaceAll("_", " ")));

        });
        return desc;

    }

    public static String capitalizeString(String string) {
        if (string.equalsIgnoreCase("Slow")) return "Slowness";
        if (string.equalsIgnoreCase("Heal")) return "Health";
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String getEnchantString(ItemStack itemStack) {
        String s = "";
        ArrayList<String> enchantmentList = new ArrayList<>();
        itemStack.getEnchantments().forEach((enchantment, integer) -> {
            if (enchantment == Enchantment.ARROW_KNOCKBACK) enchantmentList.add("Punch " + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.PROTECTION_ENVIRONMENTAL)
                enchantmentList.add("Protection" + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.PROTECTION_PROJECTILE)
                enchantmentList.add("Projectile Protection" + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.PROTECTION_FALL)
                enchantmentList.add("Feather Falling" + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.DAMAGE_ALL)
                enchantmentList.add("Sharpness" + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.DURABILITY)
                enchantmentList.add("Unbreaking" + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.KNOCKBACK)
                enchantmentList.add("Knockback" + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.PROTECTION_EXPLOSIONS)
                enchantmentList.add("Blast Protection" + KitUtils.getKitTag(integer));
            else if (enchantment == Enchantment.OXYGEN)
                enchantmentList.add("Respiration" + KitUtils.getKitTag(integer));
            else enchantmentList.add("" + enchantment.getName());
        });
        for (String enchantment : enchantmentList) {
            s = s + enchantment + " ";
        }
        return s;
    }
}
