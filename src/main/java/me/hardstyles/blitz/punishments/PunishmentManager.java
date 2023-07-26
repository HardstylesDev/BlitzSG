package me.hardstyles.blitz.punishments;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class PunishmentManager {

    public void handlePreLogin(AsyncPlayerPreLoginEvent e) {
        try {
            PlayerBan ban = BlitzSG.getInstance().getDb().getBan(e.getUniqueId());

            if (ban != null) {
                if(!ban.isBanned()){
                    return;
                }

                long now = System.currentTimeMillis();
                if(ban.getEndTime() < now){
                    BlitzSG.getInstance().getDb().removeBan(e.getUniqueId());
                    return;
                }
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ban.getReason());
                Bukkit.getLogger().info("Banned player " + e.getName() + " tried to join.");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}