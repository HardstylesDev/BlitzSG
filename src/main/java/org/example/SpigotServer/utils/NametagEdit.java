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
            Team guest = board.registerNewTeam("011Player");

            owner.setPrefix("§cADMIN §8> §c");



            guest.setPrefix("§7");


            Bukkit.getOnlinePlayers().forEach(p -> {
                if (p.getName().equalsIgnoreCase("hardstyles"))
                    owner.addEntry(p.getName());

                else
                    guest.addEntry(p.getName());
            });

            player.setScoreboard(board);

        }
    }


}

