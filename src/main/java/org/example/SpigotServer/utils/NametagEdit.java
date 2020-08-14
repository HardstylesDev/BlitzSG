package org.example.SpigotServer.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NametagEdit {

    @SuppressWarnings("deprecation")
    public static void setScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective obj = board.registerNewObjective("TabPrefix", "dummy");

            Team owner = board.registerNewTeam("08Owner");
            Team builder = board.registerNewTeam("010Builder");
            Team police = board.registerNewTeam("09Police");
            Team guest = board.registerNewTeam("011Spieler");

            owner.setPrefix("§4Owner §8> §4");
            builder.setPrefix("§3Builder §8> §3");
            police.setPrefix("§9Police §8> §9");


            guest.setPrefix("§7Guest §8> §7");


            Bukkit.getOnlinePlayers().forEach(p -> {
                if (p.getName().equalsIgnoreCase("hardstyles"))
                    police.addEntry(p.getName());
                else if (p.getName().equalsIgnoreCase("itscon") || p.getName().equalsIgnoreCase("typae"))
                    owner.addEntry(p.getName());
                else
                    builder.addEntry(p.getName());
            });

            player.setScoreboard(board);

        }
    }


}

