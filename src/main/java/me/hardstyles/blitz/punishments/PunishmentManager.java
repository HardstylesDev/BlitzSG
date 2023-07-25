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
            MongoDatabase database = BlitzSG.getInstance().getDb().getDatabase();
            MongoCollection<Document> collection = database.getCollection("bans");

            Document query = new Document("uuid", e.getUniqueId().toString());
            Document result = collection.find(query).first();

            if (result != null) {
                String message = "";
                double expireDate = result.getDouble("expires");
                if (expireDate != -1 && System.currentTimeMillis() > expireDate) {
                    collection.deleteOne(query);
                    return;
                }

                if (expireDate != -1) {
                    message = ChatColor.RED + "You're currently banned for " + ChatColor.WHITE + result.getString("reason") + ChatColor.RED + ".\n" + ChatColor.RED + "Expires in " + ChatColor.WHITE + ChatUtil.formatDate(expireDate);
                } else {
                    message = "Permanent ban";
                }

                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message);
                Bukkit.getLogger().info("Banned player " + e.getName() + " tried to join.");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}