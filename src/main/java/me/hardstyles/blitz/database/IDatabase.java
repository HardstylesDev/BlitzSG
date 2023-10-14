package me.hardstyles.blitz.database;

import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.punishments.punishtype.PlayerMute;
import me.hardstyles.blitz.punishments.punishtype.PlayerBan;
import me.hardstyles.blitz.punishments.punishtype.PunishType;

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

    ArrayList<PunishType> getMutes(UUID uuid);
    ArrayList<PunishType> getBans(UUID uuid);

    void saveBan(UUID uuid, PlayerBan ban);

    void saveMute(UUID uuid, PlayerMute mute);

    void revokeBan(UUID uuid);

    PlayerBan getBan(UUID uuid);


    void deleteData(UUID playerId);

    void removeBan(UUID uniqueId);

    void removeMute(UUID uniqueId);

    void revokeMute(UUID uniqueId);

    void remove(UUID id, PunishType punishment);
}
