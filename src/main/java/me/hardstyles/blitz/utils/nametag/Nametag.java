package me.hardstyles.blitz.utils.nametag;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.hardstyles.blitz.rank.Rank;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

public class Nametag {

    private PacketPlayOutScoreboardTeam packet;
    private Rank rank;

    public Nametag() {
        packet = new PacketPlayOutScoreboardTeam();
        setField("h", 0);
        setField("b", randomString(10));
        setField("a", randomString(10));
        setField("e", "always");
        setField("i", 1);
    }

    public Nametag setPrefix(String prefix) {
        setField("b", randomString(10));
        setField("a", randomString(10));
        setField("c", prefix);
        return this;
    }

    public Nametag setSuffix(String suffix) {
        setField("b", randomString(10));
        setField("a", randomString(10));
        setField("d", suffix);
        return this;
    }

    public Nametag setRank(Rank rank) {
        this.rank = rank;
        return this;
    }

    public Rank getRequiredRank() {
        return rank;
    }

    public void apply(Player player) {
        addPlayer(player);
        sendPacketToAllPlayers();
    }

    private String randomString(final int length) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (r.nextInt((int) (Character.MAX_VALUE)));
            sb.append(c);
        }
        return sb.toString();
    }

    private void setField(String field, Object value) {
        try {
            Field f = packet.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(packet, value);
            f.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addPlayer(Player player) {
        try {
            Field f = packet.getClass().getDeclaredField("g");
            f.setAccessible(true);
            ((Collection<String>) f.get(packet)).add(player.getName());
        } catch (Exception ignored) {
        }
    }



    private void sendPacketToAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player);
        }
    }

    public void sendPacket(Player player) {
        try {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
            player.sendMessage("Failed to send packet");
        }
    }
}