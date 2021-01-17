package me.syesstyles.blitz.punishments;

import me.syesstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PunishmentManager {


    public PunishmentManager() {

    }

    public void handlePreLogin(AsyncPlayerPreLoginEvent e) {
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "");
        System.out.println("yup");

        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = BlitzSG.getInstance().getData().getConnection();
                    String sql = "select * from bans;";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        if (rs.getString("uuid").equalsIgnoreCase(e.getUniqueId().toString())) {
                            String message = "";
                            if (rs.getDouble("expires") != -1) {
                                double expireDate = rs.getDouble("expires");
                                if (System.currentTimeMillis() > expireDate)
                                    //todo unban
                                    return;
                                message = ChatColor.RED + "You're currently banned for " + ChatColor.WHITE + rs.getString("reason") + ChatColor.RED + ".\n" + ChatColor.RED + "Expires in " + ChatColor.WHITE + formatDate(expireDate);

                            } else
                                message = "Permanent ban";
                            e.disallow(PlayerPreLoginEvent.Result.KICK_BANNED, message);
                            System.out.println("User connected but is banned.");
                            if (Bukkit.getPlayer(e.getUniqueId()) != null)
                                Bukkit.getPlayer(e.getUniqueId()).kickPlayer(message);
                        }
                    }
                    rs.close();
                    ps.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        });
        return;
    }

    private String formatDate(double milis) {
        Date start = new Date(System.currentTimeMillis()); // JANUARY_1_2007
        Date end = new Date((long) milis); // APRIL_1_2007


        long diffInSeconds = (end.getTime() - start.getTime()) / 1000;

        long diff[] = new long[]{0, 0, 0, 0};
        /* sec */
        diff[3] = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
        /* min */
        diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
        /* hours */
        diff[1] = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
        /* days */
        diff[0] = (diffInSeconds = (diffInSeconds / 24));

        return (String.format(
                "%sd, %sh, %sm, %ss",
                diff[0],
                diff[0] > 1 ? "s" : "",
                diff[1],
                diff[1] > 1 ? "s" : "",
                diff[2],
                diff[2] > 1 ? "s" : "",
                diff[3],
                diff[3] > 1 ? "s" : ""));
    }
}
