
/*
 a = (String)name
 b = (String)displayname
 c = (String)prefix
 d = (String)suffix
 e = (Enum)EnumTagVisibility
 f = (int)EnumChatColor id
 h = (int)join/quit/ (0 or 2)
 i = (int)whether to allow friendly fire / packetdata
  */
package me.syesstyles.blitz.utils.nametag;

import me.syesstyles.blitz.rank.Rank;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Random;

/**
 * @author Shawckz
 * http://shawckz.com
 */
public class Nametag {
    private Rank rank;

    public Nametag() {

    }

    public void setNametag(Player p, String prefix, boolean defaultRank) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "h", 0);
        setField(packet, "b", randomString(10));
        setField(packet, "a", randomString(10));
        setField(packet, "c", ChatColor.WHITE + "");
        setField(packet, "e", "always");
        setField(packet, "i", 1);
        addPlayer(packet, p);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            sendToPlayer(packet, onlinePlayer);
        }

    }

    private PacketPlayOutScoreboardTeam packet;

    public Rank getRequiredRank() {
        return rank;
    }

    /**
     * Construct the team name, display name and prefix (prefix will appear on player's name tag and in the tab list)
     */
    private String randomString(final int length) {
        Random r = new Random(); // perhaps make it a class variable so you don't make a new one every time
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (r.nextInt((int) (Character.MAX_VALUE)));
            sb.append(c);
        }
        return sb.toString();
    }


    /**
     * Send it to all players, only players who have been added to the team will get their name changed.
     */


    private void setField(PacketPlayOutScoreboardTeam packet, String field, Object value) {
        try {
            Field f = packet.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(packet, value);
            f.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addPlayer(PacketPlayOutScoreboardTeam packet, Player pl) {
        try {
            Field f = packet.getClass().getDeclaredField("g");
            f.setAccessible(true);
            ((Collection) f.get(packet)).add(pl.getName());
        } catch (Exception ignored) {
        }
    }

    public void sendToPlayer(PacketPlayOutScoreboardTeam packet, Player pl) {
        try {
            ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
            pl.sendMessage("Failed to send packet");
        }
    }
}