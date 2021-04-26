package me.syesstyles.blitz.utils.leaderboard;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.syesstyles.blitz.BlitzSG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Leaderboard {
    public void update() {
        HashMap<String, Integer> top = new HashMap<>();
        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
            try {
                Connection conn = BlitzSG.getInstance().getData().getConnection();
                String sql = "select * from data;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    JsonObject jsonObject = new JsonParser().parse(rs.getString("data")).getAsJsonObject();
                    top.put(Bukkit.getOfflinePlayer(UUID.fromString(jsonObject.get("uuid").getAsString())).getName(), jsonObject.get("wins").getAsInt());
                }
                rs.close();
                ps.close();
                conn.close();
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }

            List<String> keys = top.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
            System.out.println("1st: " + keys.get(0) + " with " + top.get(keys.get(0)));
            System.out.println("1st: " + keys.get(1) + " with " + top.get(keys.get(1)));
            System.out.println("1st: " + keys.get(2) + " with " + top.get(keys.get(2)));


            Block sign3 = Bukkit.getWorld("world").getBlockAt(-42, 103, 14);
            Block sign2 = Bukkit.getWorld("world").getBlockAt(-44, 103, 14);
            Block sign1 = Bukkit.getWorld("world").getBlockAt(-43, 104, 14);


            BlockState state3 = sign3.getState();
            if (!(state3 instanceof Sign)) {
                return;
            }
            Sign xsign3 = (Sign) state3;
            xsign3.setLine(1, keys.get(2));
            xsign3.setLine(2, top.get(keys.get(2)) + "");
            xsign3.update();
            Skull skull3 = (Skull)state3.getBlock().getLocation().clone().add(0, 1, 1).getBlock().getState();
            if (skull3.getType() != Material.SKULL) {
                return;
            }
            skull3.setSkullType(SkullType.PLAYER);
            skull3.setOwner(keys.get(2));
            skull3.update();



            BlockState state2 = sign2.getState();
            if (!(state2 instanceof Sign)) {
                return;
            }
            Sign xsign2 = (Sign) state2;
            xsign2.setLine(1, keys.get(1));
            xsign2.setLine(2, top.get(keys.get(1)) + "");
            xsign2.update();

            Skull skull2 = (Skull)state2.getBlock().getLocation().clone().add(0, 1, 1).getBlock().getState();
            if (skull2.getType() != Material.SKULL) {
                return;
            }
            skull2.setSkullType(SkullType.PLAYER);
            skull2.setOwner(keys.get(1));
            skull2.update();


            BlockState state1 = sign1.getState();
            if (!(state1 instanceof Sign)) {
                return;
            }
            Sign xsign1 = (Sign) state1;
            xsign1.setLine(1, keys.get(0));
            xsign1.setLine(2, top.get(keys.get(0)) + "");
            xsign1.update();

            Skull skull1 = (Skull)state1.getBlock().getLocation().clone().add(0, 1, 1).getBlock().getState();
            if (skull1.getType() != Material.SKULL) {
                return;
            }
            skull1.setSkullType(SkullType.PLAYER);
            skull1.setOwner(keys.get(0));
            skull1.update();



        });
    }
}
