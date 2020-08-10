package org.example.SpigotServer.utils;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.util.Collection;

public class NametagEdit {

    @SuppressWarnings("deprecation")
    public static void setScoreboard() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("TabPrefix", "dummy");

            Team owner = board.registerNewTeam("010Owner");
            Team spieler = board.registerNewTeam("011Spieler");

            owner.setPrefix("§4Owner §8> §4");


            spieler.setPrefix("§7");


            Bukkit.getOnlinePlayers().forEach(p -> {

                if(p.hasPermission("owner.prefix")) {
                    owner.addEntry(p.getName());
                } else {
                    spieler.addEntry(p.getName());
                }

            });

            player.setScoreboard(board);

        }
    }


}

