package me.hardstyles.blitz.gui;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class SpectatorGUI {

    public static void openGUI(Player p) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId());
        if (iPlayer.getGame() == null) {
            p.sendMessage(ChatColor.RED + "You are not in a game!");
            return;
        }

        if (!iPlayer.isSpectating()) {
            p.sendMessage(ChatColor.RED + "You are not spectating!");
            return;
        }
        Game game = iPlayer.getGame();
        Inventory inv = Bukkit.createInventory(null, 27, "§8Spectator Menu");
        for (Player alivePlayer : game.getAlivePlayers()) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setDisplayName(alivePlayer.getName());
            meta.setOwner(alivePlayer.getName());
            skull.setItemMeta(meta);
            inv.addItem(skull);
        }
        BlitzSG.getInstance().getGuiManager().setInGUI(p, true);
        p.openInventory(inv);
    }
}
