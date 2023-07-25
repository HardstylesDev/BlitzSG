package me.hardstyles.blitz.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Aura;
import me.hardstyles.blitz.cosmetic.Taunt;
import me.hardstyles.blitz.gamestar.Star;
import me.hardstyles.blitz.kit.Kit;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.punishments.PlayerMute;
import me.hardstyles.blitz.rank.Rank;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.*;

public class StatisticsManager {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type kitsType = new TypeToken<Map<String, Integer>>(){}.getType();

    public void saveAll() {
        BlitzSG.getInstance().getIPlayerManager().getBlitzPlayers().values().forEach(this::savePlayer);
    }

    public void savePlayer(Player p) {
        savePlayer(BlitzSG.getInstance().getIPlayerManager().getPlayer(p.getUniqueId()));
    }

    public void savePlayer(IPlayer p) {
        MongoDatabase database = BlitzSG.getInstance().getDb().getDatabase();
        MongoCollection<Document> collection = database.getCollection("stats");

        Document document = new Document();
        document.put("uuid", p.getUuid().toString());
        document.put("coins", p.getCoins());
        document.put("kills", p.getKills());
        document.put("deaths", p.getDeaths());
        document.put("wins", p.getWins());

        Rank rank = p.getRank();
        document.put("rank", rank != null ? rank.getRank() : null);

        String nickName = p.getNickName();
        document.put("nickname", nickName != null ? nickName : null);

        document.put("stars", starsToString(p));
        document.put("kits", kitsToJson(p));
        document.put("elo", p.getElo());

        Taunt taunt = p.getTaunt();
        document.put("taunt", taunt != null ? taunt.getName() : null);

        Kit selectedKit = p.getSelectedKit();
        document.put("selectedKit", selectedKit != null ? selectedKit.getName() : null);

        Aura aura = p.getAura();
        document.put("aura", aura != null ? aura.getName() : null);

        collection.replaceOne(new BasicDBObject("uuid", p.getUuid().toString()), document, new com.mongodb.client.model.ReplaceOptions().upsert(true));
    }

    private String starsToString(IPlayer p) {
        StringBuilder sb = new StringBuilder();
        for (Star star : p.getStars()) {
            sb.append(star.getName()).append("|");
        }
        return sb.toString();
    }

    private String kitsToJson(IPlayer p) {
        Map<String, Integer> kits = new HashMap<>();
        p.getKits().forEach((kit, integer) -> {
            if (kit != null && kit.getName() != null) {
                kits.put(kit.getName(), integer);
            }
        });
        return gson.toJson(kits, kitsType);
    }

    public void loadPlayer(UUID uuid) {
        MongoDatabase database = BlitzSG.getInstance().getDb().getDatabase();
        MongoCollection<Document> collection = database.getCollection("stats");

        Document query = new Document("uuid", uuid.toString());
        Document result = collection.find(query).first();

        if (result != null) {
            IPlayer player = new IPlayer(uuid);

            player.setCoins(result.getInteger("coins", 0));
            player.setKills(result.getInteger("kills", 0));
            player.setDeaths(result.getInteger("deaths", 0));
            player.setWins(result.getInteger("wins", 0));

            String rank = result.getString("rank");
            if (rank != null) {
                player.setRank(BlitzSG.getInstance().getRankManager().getRankByName(rank));
            }

            String aura = result.getString("aura");
            if (aura != null) {
                Aura a = BlitzSG.getInstance().getCosmeticsManager().getAuraByName(aura);
                if (a != null) {
                    player.setAura(a);
                }
            }

            player.getStars().clear();
            String starsString = result.getString("stars");
            if (starsString != null) {
                for (String starName : starsString.split("\\|")) {
                    if (!starName.isEmpty()) {
                        player.getStars().add(BlitzSG.getInstance().getStarManager().getStar(starName));
                    }
                }
            }

            player.getKits().clear();
            String kitsJson = result.getString("kits");
            if (kitsJson != null) {
                Map<String, Integer> kits = gson.fromJson(kitsJson, kitsType);
                for (Map.Entry<String, Integer> entry : kits.entrySet()) {
                    Kit kit = BlitzSG.getInstance().getKitManager().getKit(entry.getKey());
                    if (kit != null) {
                        int level = entry.getValue();
                        if (kit.getRequiredRank() == BlitzSG.getInstance().getRankManager().getRankByName("Default")) {
                            if (level == 0) {
                                level = 1;
                            }
                        }
                        player.getKits().put(kit, level);
                    }
                }
            }

            player.setElo(result.getInteger("elo", 0));

            String selectedKitName = result.getString("selectedKit");
            if (selectedKitName != null) {
                Kit selectedKit = BlitzSG.getInstance().getKitManager().getKit(selectedKitName);
                if (selectedKit != null) {
                    player.setSelectedKit(selectedKit);
                }
            }

            String tauntName = result.getString("taunt");
            if (tauntName != null) {
                Taunt t = BlitzSG.getInstance().getCosmeticsManager().getTauntByName(tauntName);
                if (t != null) {
                    player.setTaunt(t);
                }
            }

            BlitzSG.getInstance().getIPlayerManager().addPlayer(uuid, player);
        } else {
            BlitzSG.getInstance().getIPlayerManager().addPlayer(uuid, new IPlayer(uuid));
        }

        muteCheck(uuid);
    }

    public void muteCheck(UUID uuid) {
        // Your mute check logic using MongoDB
        // Implement this part based on your MongoDB setup and data structure
        // You can use MongoDB queries to check for active mutes for the player
    }
}


