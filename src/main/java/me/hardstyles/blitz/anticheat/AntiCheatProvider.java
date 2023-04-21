package me.hardstyles.blitz.anticheat;

import org.bukkit.entity.Player;

public interface AntiCheatProvider {
    public void whitelistPlayer(Player player, long duration);
    public void unwhitelistPlayer(Player player);

    public boolean isWhitelisted(Player player);


}
