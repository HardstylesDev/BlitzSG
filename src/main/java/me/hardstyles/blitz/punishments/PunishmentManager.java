package me.hardstyles.blitz.punishments;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.utils.ChatUtil;
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
        try {
            Connection conn = BlitzSG.getInstance().db().getConnection();
            String sql = "select * from bans where uuid = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, e.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String message = "";
                if (rs.getDouble("expires") != -1) {
                    double expireDate = rs.getDouble("expires");
                    if (System.currentTimeMillis() > expireDate) {
                        String command = "DELETE FROM bans WHERE uuid = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(command);
                        preparedStatement.setString(1, e.getUniqueId().toString());
                        preparedStatement.execute();
                        return;
                    }
                    message = ChatColor.RED + "You're currently banned for " + ChatColor.WHITE + rs.getString("reason") + ChatColor.RED + ".\n" + ChatColor.RED + "Expires in " + ChatColor.WHITE + ChatUtil.formatDate(expireDate);

                } else
                    message = "Permanent ban";
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
                Bukkit.getLogger().info("Banned player " + e.getName() + " tried to join.");
                rs.close();
                ps.close();
                conn.close();
                return;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }



}
