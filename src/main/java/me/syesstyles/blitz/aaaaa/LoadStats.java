package me.syesstyles.blitz.aaaaa;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import me.syesstyles.blitz.utils.nickname.Nick;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class LoadStats {
    public void load() {

        System.out.println("attempting to load");
        try {
            System.out.println("attempting to loaddddd");
            Connection conn = BlitzSG.getInstance().getData().getConnection();
            String sql = "select * from stats;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            HashSet<String> uuids = new HashSet<>();
            while (rs.next()) {

                //uuids.add(rs.getString(1));
                // System.out.println(rs.getInt("kills"));
                BlitzSGPlayer blitzSGPlayer = new BlitzSGPlayer(UUID.fromString(rs.getString("uuid")));
                if (blitzSGPlayer == null)
                    continue;
                // System.out.println("Nickname: " + rs.getString("nickname"));
                blitzSGPlayer.setKills(rs.getInt("kills"));
                blitzSGPlayer.setDeaths(rs.getInt("deaths"));
                blitzSGPlayer.setCoins(rs.getInt("coins"));
                blitzSGPlayer.setElo(rs.getInt("elo"));
                blitzSGPlayer.setWins(rs.getInt("wins"));
                blitzSGPlayer.setKitLevels(getKitsFromJson(rs.getString("kits")));
                if (rs.getString("nickname") != null && !rs.getString("nickname").equalsIgnoreCase("")) {
                    blitzSGPlayer.setNick(new Nick(rs.getString("nickname"), null, null, !rs.getString("nickname").equalsIgnoreCase("")));
                }

                blitzSGPlayer.setStars(getStarsFromString(rs.getString("stars")));
                blitzSGPlayer.setRank(BlitzSG.getInstance().getRankManager().getRankByName(rs.getString("rank")));
                //  blitzSGPlayer.setNickName(rs.getString("nickname").length() > 3 ? rs.getString("nickname") : null);
                if (rs.getString("selectedKit") != null && BlitzSG.getInstance().getKitManager().getKit(rs.getString("selectedKit")) != null)
                    blitzSGPlayer.setSelectedKit(BlitzSG.getInstance().getKitManager().getKit(rs.getString("selectedKit")));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Star> getStarsFromString(String starString) {
        HashSet<Star> stars = new HashSet<>();
        String[] strings = starString.split("\\|");
        for (String string : strings) {
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

            // myMap.forEach((s, l) -> {
            //     System.out.println("s == " + s + " | s2 == " + l);
            //     Kit kit = BlitzSG.getInstance().getKitManager().getKit(s);
            //     int level = Integer.parseInt(l);
//
            //     kits.put(kit, level);
//
//
            // });
            return kits;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
