package me.syesstyles.blitz.aaaaa;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;

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
        for (BlitzSGPlayer bsgPlayer : BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayers().values()) {
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
        if (bsgPlayer.getAura() != null)
            jsonObject.addProperty("selected_aura", bsgPlayer.getAura().getName());
        jsonObject.add("kits", kitsToJson(bsgPlayer));
        jsonObject.add("stars", starsToJson(bsgPlayer));
        bsgPlayer.setJsonObject(jsonObject);
        insert(bsgPlayer.getUuid().toString(), jsonObject);

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
                if (jsonObject.has("selected_kit"))
                    blitzSGPlayer.setSelectedKit(BlitzSG.getInstance().getKitManager().getKit(jsonObject.get("selected_kit").getAsString()));
                if (jsonObject.has("selected_aura"))
                    blitzSGPlayer.setAura(BlitzSG.getInstance().getCosmeticsManager().getAuraByName(jsonObject.get("selected_aura").getAsString()));

                blitzSGPlayer.setKitLevels(getKitsFromJson(jsonObject.get("kits").getAsJsonObject().toString()));
                //if (rs.getString("nickname") != null && !rs.getString("nickname").equalsIgnoreCase("")) {
                //    blitzSGPlayer.setNick(new Nick(rs.getString("nickname"), null, null, !rs.getString("nickname").equalsIgnoreCase("")));
                //}
                if (jsonObject.has("stars"))
                    blitzSGPlayer.setStars(getStarsFromJsonArray(jsonObject.get("stars").getAsJsonArray()));

            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
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
