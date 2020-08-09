package org.example.SpigotServer.test;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.example.SpigotServer.utils.SendCenteredMessages;
import org.example.SpigotServer.utils.sendColor;

public class Test implements Listener
{

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        event.getPlayer().sendMessage(sendColor.format("&7&m----------------------------------------"));
        event.getPlayer().sendMessage("");
        SendCenteredMessages.sendCenteredMessage(event.getPlayer(), sendColor.format("&3&lWelcome to Noctas"));
        SendCenteredMessages.sendCenteredMessage(event.getPlayer(), "This is the build server");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage(sendColor.format("&7&m----------------------------------------"));

        event.getPlayer().setPlayerListName("Nigger " + event.getPlayer());
    }

}