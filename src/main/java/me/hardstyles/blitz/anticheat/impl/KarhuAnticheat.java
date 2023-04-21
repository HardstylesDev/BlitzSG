package me.hardstyles.blitz.anticheat.impl;

import me.hardstyles.blitz.anticheat.AntiCheatProvider;
import me.liwk.karhu.api.event.KarhuEvent;
import me.liwk.karhu.api.event.KarhuListener;
import me.liwk.karhu.api.event.impl.KarhuAlertEvent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class KarhuAnticheat implements AntiCheatProvider, KarhuListener {

    Map<Player, Long> whitelist = new HashMap<>();
    @Override
    public void whitelistPlayer(Player player, long duration) {
        whitelist.put(player, System.currentTimeMillis() + duration);
    }

    @Override
    public void unwhitelistPlayer(Player player) {
        whitelist.remove(player);
    }

    @Override
    public boolean isWhitelisted(Player player) {
        long now = System.currentTimeMillis();
        if (whitelist.containsKey(player)) {
            if (whitelist.get(player) > now) {
                return true;
            } else {
                whitelist.remove(player);
            }
        }
        return false;
    }

    @Override
    public void onEvent(KarhuEvent karhuEvent) {
        if(karhuEvent instanceof KarhuAlertEvent){
            KarhuAlertEvent alertEvent = (KarhuAlertEvent) karhuEvent;
            Player player = alertEvent.getPlayer();
            if(isWhitelisted(player)){
                alertEvent.cancel();
            }
        }
    }
}
