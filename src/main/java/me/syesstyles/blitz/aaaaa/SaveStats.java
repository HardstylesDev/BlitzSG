package me.syesstyles.blitz.aaaaa;

import com.google.gson.GsonBuilder;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import me.syesstyles.blitz.gamestar.Star;
import me.syesstyles.blitz.kit.Kit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SaveStats {

    public void saveAll() {
       // for (BlitzSGPlayer uhcPlayer : BlitzSG.getInstance().getBlitzSGPlayerManager().getUhcPlayers().values())
       //     save(uhcPlayer);
    }

    public void save(Player p) {
        save(BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId()));
    }

    public void save(BlitzSGPlayer p) {
      // try {
      //     Connection connection = BlitzSG.getInstance().getData().getConnection();
      //     String command = String.format("REPLACE INTO `stats`(`uuid`, `coins`, `kills`, `deaths`, `wins`, `rank`, `nickname`, `stars`, `kits`, `elo`, `taunt`, `selectedKit`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

      //     PreparedStatement preparedStatement = connection.prepareStatement(command);
      //     preparedStatement.setString(1, p.getUuid().toString());
      //     preparedStatement.setInt(2, p.getCoins());
      //     preparedStatement.setInt(3, p.getKills());
      //     preparedStatement.setInt(4, p.getDeaths());
      //     preparedStatement.setInt(5, p.getWins());
      //     if (p == null || p.getRank() == null || p.getRank().getPrefix() == null)
      //         preparedStatement.setString(6, null);
      //     else
      //         preparedStatement.setString(6, p.getRank().getRank());

      //     if (p == null || p.getNickName() == null)
      //         preparedStatement.setString(7, null);
      //     else
      //         preparedStatement.setString(7, p.getNickName());
      //     //       preparedStatement.setString(7, p.getNick().getNickName());
      //     preparedStatement.setString(8, starsToString(p));
      //     preparedStatement.setString(9, kitsToJson(p));
      //     preparedStatement.setInt(10, p.getElo());
      //     preparedStatement.setInt(11, 0);

      //     if (p.getSelectedKit() != null)
      //         preparedStatement.setString(12, p.getSelectedKit().getName());
      //     else preparedStatement.setString(12, null);


//p.ge//Uuid(), p.getCoins(), p.getKills(), p.getDeaths(),p.getWins(),p.getRank().getRank(), p.getNick().getNickName(), starsToString(p), kitsToJson(p), p.getElo(), null );

      //     preparedStatement.execute();

      //     connection.close();
      // } catch (SQLException e) {
      //     e.printStackTrace();

      // }
    }

    private String starsToString(BlitzSGPlayer p) {
        String s = "";
        for (Star star : p.getStars()) {
            s = s + star.getName() + "|";
        }
        return s;
    }

    private String kitsToJson(BlitzSGPlayer p) {
        HashMap<String, Integer> kits = new HashMap<>();

        for (Map.Entry<Kit, Integer> v : p.getKits().entrySet()) {
            if (v == null || v.getKey() == null || v.getKey().getName() == null) continue;
            // System.out.println(v.getKey().getName() + " / " + v.getValue());
            kits.put(v.getKey().getName(), v.getValue());
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(kits);
    }
}
