package me.syesstyles.blitz.utils;

import me.syesstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ImportantCommandModifier implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent paramPlayerCommandPreprocessEvent) {

        String str = paramPlayerCommandPreprocessEvent.getMessage().toLowerCase();
        if (!str.startsWith("/restart ") &&
                !str.equals("/restart"))
            return;
        Player player = paramPlayerCommandPreprocessEvent.getPlayer();
        if (player == null || !player.hasPermission("minecraft.command.restart"))
            return;

        paramPlayerCommandPreprocessEvent.setCancelled(true);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.kickPlayer("Restarting, we'll be right back!");
        }
        Bukkit.getScheduler().runTaskLater(BlitzSG.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop"), 10L);


    }
}
