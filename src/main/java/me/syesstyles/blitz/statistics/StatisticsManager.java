package me.syesstyles.blitz.statistics;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.nickname.Nick;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatisticsManager {

    public StatisticsManager() {
    }

    public void save() {
        for (BlitzSGPlayer bsgPlayer : BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayers().values()) {
            save(bsgPlayer);
        }
    }

    public void save(BlitzSGPlayer bsgPlayer) {
        JsonObject jsonObject = bsgPlayer.getJsonObject();
        jsonObject.addProperty("uuid", bsgPlayer.getUuid().toString());
        jsonObject.addProperty("name", bsgPlayer.getName());
        jsonObject.addProperty("ip_adress", bsgPlayer.getIp());
        if (bsgPlayer.getRank() != null)
            jsonObject.addProperty("rank", bsgPlayer.getRank().getRank());
        jsonObject.addProperty("kills", bsgPlayer.getKills());
        jsonObject.addProperty("wins", bsgPlayer.getWins());
        jsonObject.addProperty("deaths", bsgPlayer.getDeaths());
        jsonObject.addProperty("coins", bsgPlayer.getCoins());
        jsonObject.addProperty("elo", bsgPlayer.getElo());
        if (bsgPlayer.getSelectedKit() != null)
            jsonObject.addProperty("selected_kit", bsgPlayer.getSelectedKit().getName());
        if(bsgPlayer.doesHideOthers())
            jsonObject.addProperty("hide_others",bsgPlayer.doesHideOthers());
        if (bsgPlayer.getTaunt() != null)
            jsonObject.addProperty("selected_taunt", bsgPlayer.getTaunt().getName());
        if (bsgPlayer.getAura() != null)
            jsonObject.addProperty("selected_aura", bsgPlayer.getAura().getName());

        if (bsgPlayer.getCustomTag() != null)
            jsonObject.addProperty("custom_tag", bsgPlayer.getCustomTag());

        jsonObject.add("kits", kitsToJson(bsgPlayer));
        jsonObject.add("stars", starsToJson(bsgPlayer));
        if (bsgPlayer.getNick() != null) {
            jsonObject.add("nick", nickToJson(bsgPlayer));

        }

        bsgPlayer.setJsonObject(jsonObject);
        insert(bsgPlayer.getUuid().toString(), jsonObject);

    }


    private JsonObject nickToJson(BlitzSGPlayer p) {
        JsonObject j = new JsonObject();
        j.addProperty("name", p.getNick().getNickName());
        j.addProperty("value", p.getNick().getSkinValue());
        j.addProperty("signature", p.getNick().getSkinSignature());
        j.addProperty("nicked", p.getNick().isNicked());
        return j;

    }

    private JsonObject kitsToJson(BlitzSGPlayer p) {
        HashMap<String, Integer> kits = new HashMap<>();
        for (Map.Entry<Kit, Integer> v : p.getKits().entrySet()) {
            if (v == null || v.getKey() == null || v.getKey().getName() == null) continue;
            kits.put(v.getKey().getName(), v.getValue());
        }
        return new Gson().toJsonTree(kits).getAsJsonObject();
    }

    private JsonArray starsToJson(BlitzSGPlayer p) {
        ArrayList<String> stars = new ArrayList<>();
        for (Star v : p.getStars()) {
            if (v == null) continue;
            stars.add(v.getName());
        }
        return new Gson().toJsonTree(stars).getAsJsonArray();
    }


    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private void insert(String uuid, JsonObject jsonObject) {
        try {
            Connection connection = BlitzSG.getInstance().getData().getConnection();
            String command = String.format("REPLACE INTO `data`(`uuid`, `data`) VALUES (?,?)");
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, gson.toJson(jsonObject));
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public void load() {
        try {
            Connection conn = BlitzSG.getInstance().getData().getConnection();
            String sql = "select * from data;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BlitzSGPlayer blitzSGPlayer = new BlitzSGPlayer(UUID.fromString(rs.getString("uuid")));
                if (blitzSGPlayer == null)
                    continue;
                JsonObject jsonObject = new JsonParser().parse(rs.getString("data")).getAsJsonObject();

                if (jsonObject.has("name")) blitzSGPlayer.setName(jsonObject.get("name").getAsString());
                if (jsonObject.has("rank"))
                    blitzSGPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName(jsonObject.get("rank").getAsString()));
                blitzSGPlayer.setKills(jsonObject.get("kills").getAsInt());
                blitzSGPlayer.setDeaths(jsonObject.get("deaths").getAsInt());
                blitzSGPlayer.setWins(jsonObject.get("wins").getAsInt());
                blitzSGPlayer.setCoins(jsonObject.get("coins").getAsInt());
                blitzSGPlayer.setElo(jsonObject.get("elo").getAsInt());
                if(jsonObject.has("hide_others"))
                    blitzSGPlayer.setHideOthers(jsonObject.get("hide_others").getAsBoolean());
                if(jsonObject.has("custom_tag"))
                    blitzSGPlayer.setCustomTag(jsonObject.get("custom_tag").getAsString());
                if (jsonObject.has("selected_kit"))
                    blitzSGPlayer.setSelectedKit(BlitzSG.getInstance().getKitManager().getKit(jsonObject.get("selected_kit").getAsString()));
                if (jsonObject.has("selected_aura"))
                    blitzSGPlayer.setAura(BlitzSG.getInstance().getCosmeticsManager().getAuraByName(jsonObject.get("selected_aura").getAsString()));
                if (jsonObject.has("selected_taunt"))
                    blitzSGPlayer.setTaunt(BlitzSG.getInstance().getCosmeticsManager().getTauntByName(jsonObject.get("selected_aura").getAsString()));

                blitzSGPlayer.setKitLevels(getKitsFromJson(jsonObject.get("kits").getAsJsonObject().toString()));
                //if (rs.getString("nickname") != null && !rs.getString("nickname").equalsIgnoreCase("")) {
                //    blitzSGPlayer.setNick(new Nick(rs.getString("nickname"), null, null, !rs.getString("nickname").equalsIgnoreCase("")));
                //}
                if (jsonObject.has("stars"))
                    blitzSGPlayer.setStars(getStarsFromJsonArray(jsonObject.get("stars").getAsJsonArray()));
                if (jsonObject.has("nick") && jsonObject.get("nick") != null && jsonObject.get("nick").getAsJsonObject() != null && jsonObject.get("nick").getAsJsonObject().has("name") && jsonObject.get("nick").getAsJsonObject() != null && jsonObject.get("nick").getAsJsonObject().get("value") != null && jsonObject.get("nick").getAsJsonObject().get("signature") != null) {
                    blitzSGPlayer.setNick(new Nick(jsonObject.get("nick").getAsJsonObject().get("name").getAsString(), jsonObject.get("nick").getAsJsonObject().get("value").getAsString(), jsonObject.get("nick").getAsJsonObject().get("signature").getAsString(), jsonObject.get("nick").getAsJsonObject().get("nicked").getAsBoolean()));
                }

            }
            rs.close();
            ps.close();
            conn.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void load(UUID uuid) {
        try {
            Connection conn = BlitzSG.getInstance().getData().getConnection();
            String sql = "select * from data WHERE uuid = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BlitzSGPlayer blitzSGPlayer = new BlitzSGPlayer(UUID.fromString(rs.getString("uuid")));
                if (blitzSGPlayer == null)
                    continue;
                JsonObject jsonObject = new JsonParser().parse(rs.getString("data")).getAsJsonObject();


                if (jsonObject.has("name")) blitzSGPlayer.setName(jsonObject.get("name").getAsString());
                if (jsonObject.has("rank"))
                    blitzSGPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName(jsonObject.get("rank").getAsString()));
                blitzSGPlayer.setKills(jsonObject.get("kills").getAsInt());
                blitzSGPlayer.setDeaths(jsonObject.get("deaths").getAsInt());
                blitzSGPlayer.setWins(jsonObject.get("wins").getAsInt());
                blitzSGPlayer.setCoins(jsonObject.get("coins").getAsInt());
                blitzSGPlayer.setElo(jsonObject.get("elo").getAsInt());
                if(jsonObject.has("custom_tag"))
                    blitzSGPlayer.setCustomTag(jsonObject.get("custom_tag").getAsString());
                if(jsonObject.has("hide_others"))
                    blitzSGPlayer.setHideOthers(jsonObject.get("hide_others").getAsBoolean());
                if (jsonObject.has("selected_kit"))
                    blitzSGPlayer.setSelectedKit(BlitzSG.getInstance().getKitManager().getKit(jsonObject.get("selected_kit").getAsString()));
                if (jsonObject.has("selected_aura"))
                    blitzSGPlayer.setAura(BlitzSG.getInstance().getCosmeticsManager().getAuraByName(jsonObject.get("selected_aura").getAsString()));
                if (jsonObject.has("selected_taunt"))
                    blitzSGPlayer.setTaunt(BlitzSG.getInstance().getCosmeticsManager().getTauntByName(jsonObject.get("selected_taunt").getAsString()));

                blitzSGPlayer.setKitLevels(getKitsFromJson(jsonObject.get("kits").getAsJsonObject().toString()));
                if (jsonObject.has("stars"))
                    blitzSGPlayer.setStars(getStarsFromJsonArray(jsonObject.get("stars").getAsJsonArray()));
                if (jsonObject.has("nick") && jsonObject.get("nick") != null && jsonObject.get("nick").getAsJsonObject() != null && jsonObject.get("nick").getAsJsonObject().has("name") && jsonObject.get("nick").getAsJsonObject() != null && jsonObject.get("nick").getAsJsonObject().get("value") != null && jsonObject.get("nick").getAsJsonObject().get("signature") != null) {
                    blitzSGPlayer.setNick(new Nick(jsonObject.get("nick").getAsJsonObject().get("name").getAsString(), jsonObject.get("nick").getAsJsonObject().get("value").getAsString(), jsonObject.get("nick").getAsJsonObject().get("signature").getAsString(), jsonObject.get("nick").getAsJsonObject().get("nicked").getAsBoolean()));
                }

            }
            rs.close();
            ps.close();
            conn.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Star> getStarsFromJsonArray(JsonArray jsonElement) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> list = new Gson().fromJson(jsonElement, listType);
        HashSet<Star> stars = new HashSet<>();
        for (String string : list) {
            Star star = BlitzSG.getInstance().getStarManager().getStar(string);
            if (star != null)
                stars.add(star);
        }
        return stars;

    }

    public HashMap<Kit, Integer> getKitsFromJson(String jsonString) {
        try {
            HashMap<Kit, Integer> kits = new HashMap<>();
            HashMap<String, Object> map = new HashMap<>();
            HashMap<String, String> myMap = new Gson().fromJson(jsonString, map.getClass());
            //myMap.keySet().forEach(s -> System.out.println(s));
            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
            Iterator it = myMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                it.remove(); // avoids a ConcurrentModificationException
                Kit kit = BlitzSG.getInstance().getKitManager().getKit(pair.getKey().toString());
                int level = jsonObject.get(pair.getKey().toString()).getAsInt();
                kits.put(kit, level);
            }
            return kits;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
