package me.hardstyles.blitz.punishments;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.punishments.punishtype.PlayerBan;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PunishmentManager {



    public void handlePreLogin(AsyncPlayerPreLoginEvent e) {
        try {
            PlayerBan ban = BlitzSG.getInstance().getDb().getBan(e.getUniqueId());

            if (ban != null) {
                if(!ban.isBanned()){
                    BlitzSG.getInstance().getDb().revokeBan(e.getUniqueId());
                    return;
                }

                long now = System.currentTimeMillis();
                if(ban.getEndTime() < now){
                    BlitzSG.getInstance().getDb().revokeBan(e.getUniqueId());
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