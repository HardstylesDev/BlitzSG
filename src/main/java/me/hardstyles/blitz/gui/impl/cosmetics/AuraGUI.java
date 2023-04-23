package me.hardstyles.blitz.gui.impl.cosmetics;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.gui.MenuContainer;
import me.hardstyles.blitz.gui.MenuItem;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.cosmetic.Aura;
import me.hardstyles.blitz.utils.ChatUtil;
import me.hardstyles.blitz.utils.ItemBuilder;
import me.hardstyles.blitz.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class AuraGUI {

    public static int get(int a) {
        if (a >= 1 && a <= 7) {
            return a + 9;
        }
        if (a >= 8 && a <= 14) {
            return a + 11;
        } else return a + 13;
    }

    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());

        //Add Items

        MenuContainer gui = new MenuContainer(ChatUtil.color("&8Auras"), 5);
        int index = 10;

        Set<Aura> auras = BlitzSG.getInstance().getCosmeticsManager().getAuras();
        for (Aura aura : auras) {
            ItemStack icon = new ItemBuilder(aura.getIcon()).name(ChatColor.GREEN + aura.getName()).lores(getFullDescription(iPlayer, aura)).make();
            MenuItem item = new MenuItem(icon, e -> {
                if (iPlayer.getRank().getPosition() < aura.getRequiredRank().getPosition()) {
                    p.sendMessage("§cYou must be " + aura.getRequiredRank().getRankFormatted() + " §cor higher to purchase that!");
                    return;
                }
                p.sendMessage(ChatColor.GREEN + "You selected " + ChatColor.GOLD + aura.getName());
                p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                iPlayer.setAura(aura);
                p.closeInventory();
            });
            gui.setItem(index, item);
            if ((index + 2) % 9 == 0) {
                index += 3;
                continue;
            }
            index++;
        }

        gui.show(p);
    }


    public static ArrayList<String> getFullDescription(IPlayer iPlayer, Aura p) {
        ArrayList<String> desc = new ArrayList<String>();
        desc.add(ChatColor.GRAY + p.getDescription());
        desc.add("");
        if (iPlayer.getAura() == p) {
            desc.add("§aSELECTED!");
            return desc;
        }
        desc.add(p.getRequiredRank().getRankFormatted() + " §7Exclusive");

        return desc;
    }


}
