package me.hardstyles.blitz.menu.impl.game;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.game.Game;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
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
        MenuContainer gui = new MenuContainer("§8Spectator Menu", 3);
        for (Player alivePlayer : game.getAlivePlayers()) {
            IPlayer iAlive = BlitzSG.getInstance().getIPlayerManager().getPlayer(alivePlayer.getUniqueId());
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setDisplayName(iAlive.getRank().getPrefix() + alivePlayer.getName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatUtil.color("&fHealth: &a" + alivePlayer.getHealth() + "&4❤"));
            lore.add(ChatUtil.color("&fKills: &a" + iAlive.getGameKills()));
            meta.setLore(lore);
            meta.setOwner(alivePlayer.getName());
            skull.setItemMeta(meta);
            MenuItem menuItem = new MenuItem(skull, e -> {
                e.setCancelled(true);
                if (alivePlayer.isOnline()) {
                    p.teleport(alivePlayer);
                    p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&eYou teleported to &f" + alivePlayer.getName() + "&e!"));
                } else {
                    p.sendMessage(ChatUtil.color(BlitzSG.CORE_NAME + "&cThat player is no longer online!"));
                }
                p.closeInventory();
            });
            gui.addItem(menuItem);
        }
        gui.show(p);
    }
}
