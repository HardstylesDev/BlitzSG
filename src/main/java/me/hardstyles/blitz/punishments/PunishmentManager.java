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

                String reason = ban.getReason();
                String duration = ChatUtil.formatDate(ban.getEndTime());
                String message = String.format(ChatUtil.color("&cYou are currently banned for &f%s &cfrom this server!\n&7Reason: &f%s\n" + "&7Find out more: &bhttps://discord.gg/idk"), duration, reason);
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message );
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}