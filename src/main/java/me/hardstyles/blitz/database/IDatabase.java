package me.hardstyles.blitz.database;

import com.mongodb.client.MongoDatabase;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.punishments.PlayerMute;
import me.hardstyles.blitz.punishments.PlayerBan;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public interface IDatabase {
    void connect();
    void disconnect();
    void savePlayer(IPlayer player);
    IPlayer loadPlayer(UUID uuid);
    Map<String, Integer> getLeaderboard();
    PlayerMute getMute(UUID uuid);

    void saveBan(UUID uuid, PlayerBan ban);

    void saveMute(UUID uuid, PlayerMute mute);

    PlayerBan getBan(UUID uuid);


    void deleteData(UUID playerId);

    void removeBan(UUID uniqueId);

    void removeMute(UUID uniqueId);
}
