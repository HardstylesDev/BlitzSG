package org.example.SpigotServer.system;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class RankManager
{
    static HashMap<String, String> ranks = null;


    public static String getRank(String uuid) {
        return ranks.getOrDefault(uuid, null);
    }

    public static boolean hasRank(OfflinePlayer p){
       // if(!ranks.containsKey(p.getUniqueId().toString()))
       //     return false;
       // if(ranks.get(p.getUniqueId().toString()) != null){
       //     return true;
       // }
        return true;
    }
    public static void setRank(OfflinePlayer p, String rank){
        if(rank != null)
            ranks.put(p.getUniqueId().toString(), rank);
        else
            ranks.remove(p.getUniqueId().toString());
    }
}
