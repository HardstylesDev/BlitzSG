package me.hardstyles.blitz.menu.impl.hub;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class LobbySelectorGUI {

    private LobbySelectorGUI() {
    }

    public static void open(Player player) {
        BlitzSG plugin = BlitzSG.getInstance();
        plugin.requestHubPlayerCounts(player);

        List<String> hubs = BlitzSG.getConfiguredHubServers();
        int rows = hubs.size() > 9 ? 2 : 1;
        MenuContainer menu = new MenuContainer("§8Lobby Selector", rows);

        String current = plugin.getRuntimeServerName() == null
                ? ""
                : plugin.getRuntimeServerName().trim().toLowerCase(Locale.ROOT);

        int slot = 0;
        for (String rawHub : hubs) {
            if (rawHub == null || rawHub.trim().isEmpty()) {
                continue;
            }

            final String hubServer = rawHub.trim();
            final String lobbyLabel = resolveLobbyLabel(hubServer, slot + 1);
            String normalized = hubServer.toLowerCase(Locale.ROOT);
            boolean isCurrent = normalized.equals(current);
            int playerCount = plugin.getHubPlayerCount(hubServer);

            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatUtil.color("&7Server: &f" + lobbyLabel + " &7(" + hubServer + ")"));
            lore.add(ChatUtil.color("&7Players: &a" + (playerCount >= 0 ? playerCount : "...") ));
            lore.add("");
            if (isCurrent) {
                lore.add(ChatUtil.color("&eYou are currently on this lobby."));
            } else {
                lore.add(ChatUtil.color("&aClick to connect."));
            }

            ItemBuilder builder = new ItemBuilder(Material.NETHER_STAR)
                    .name("&a" + lobbyLabel)
                    .lores(lore);
            MenuItem item = new MenuItem(builder.make(), e -> {
                e.setCancelled(true);
                if (isCurrent) {
                    player.sendMessage(ChatUtil.color("&eYou are already in this lobby."));
                    return;
                }
                BlitzSG.sendPlayerToServer(player, hubServer);
                player.sendMessage(ChatUtil.color("&eConnecting you to &f" + lobbyLabel + "&e..."));
                player.closeInventory();
            });

            menu.setItem(slot++, item);
        }

        menu.open(player);
    }

    private static String resolveLobbyLabel(String hubServer, int fallbackIndex) {
        String normalized = hubServer == null ? "" : hubServer.trim().toLowerCase(Locale.ROOT);
        if (normalized.startsWith("hub-")) {
            String suffix = normalized.substring(4);
            try {
                int parsed = Integer.parseInt(suffix);
                if (parsed > 0) {
                    return "Lobby " + parsed;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        return "Lobby " + Math.max(1, fallbackIndex);
    }
}
