package me.syesstyles.blitz.utils.nickname;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.UUID;

public class Nickname {
    public void setNick(Player p, String s) {

        boolean test = setPlayerSkin(p, s);
        if (test) refresh(p);

        setPlayerNameTag(p, s);
        BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());

        p.setPlayerListName(uhcPlayer.getRank().getPrefix() + p.getName() + BlitzSG.getInstance().getEloManager().getEloLevel(uhcPlayer.getElo()).getPrefix()
                + " [" + uhcPlayer.getElo() + "]");
    }

    public void setPlayerNameTag(Player player, String name) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(player);
            boolean gameProfileExists = false;
            try {
                Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            try {
                Class.forName("com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            for (Player p : Bukkit.getOnlinePlayers())
                if (!p.equals(player))
                    p.hidePlayer(player);
            if (!gameProfileExists) {
                Field nameField = entityPlayer.getClass().getSuperclass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(entityPlayer, name);

                Field uuidField = entityPlayer.getClass().getSuperclass().getDeclaredField("id");
                uuidField.setAccessible(true);
                uuidField.set(entityPlayer, UUID.randomUUID());
            } else {
                Object profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                Field ff = profile.getClass().getDeclaredField("name");
                ff.setAccessible(true);
                ff.set(profile, name);


                Field ffu = profile.getClass().getDeclaredField("id");
                ffu.setAccessible(true);
                ffu.set(profile, UUID.randomUUID());
            }
            if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
                for (Player p : players) {
                    p.hidePlayer(player);
                    p.showPlayer(player);
                }
            } else {
                Player[] players = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null));
                for (Player p : players) {
                    p.hidePlayer(player);
                    p.showPlayer(player);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public boolean setPlayerSkin(Player p, String arg) {
        OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(arg);
        if (!op.getName().equalsIgnoreCase(arg)) {
            return false;
        }
        p.sendMessage("Setting your nick to " + arg);
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.mineskin.org/generate/user/" + op.getUniqueId()).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                if(reply.contains("\"error\""))
                    return false;
                String value = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];

                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (!player.equals(p))
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) p).getHandle()));
                    CraftPlayer cp = (CraftPlayer) p;
                    GameProfile gameProfile = cp.getHandle().getProfile();

                    gameProfile.getProperties().removeAll("textures");
                    gameProfile.getProperties().put("textures", new Property("textures", value, signature));

                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) p).getHandle()));
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(p.getEntityId()));
                    if (!player.equals(p))
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer) p).getHandle()));
                    if (player.equals(p)) ;

                });
                return true;
            }
        } catch (IOException exception) {

            exception.printStackTrace();
            return false;
        }
        return false;
    }

    public void refresh(Player p) {
        final EntityPlayer ep = ((CraftPlayer) p).getHandle();
        final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
        final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
        final Location loc = p.getLocation().clone();
        ep.playerConnection.sendPacket(removeInfo);
        ep.playerConnection.sendPacket(addInfo);
        Location otherWorldLocation = null;
        if (p.getWorld().getName().equalsIgnoreCase("world"))
            for (World world : Bukkit.getWorlds()) {
                if (!world.getName().equalsIgnoreCase("world")) {
                    otherWorldLocation = world.getSpawnLocation();
                    break;
                }
            }
        else
            otherWorldLocation = Bukkit.getWorld("world").getSpawnLocation();
        p.teleport(otherWorldLocation);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(loc);
                ep.playerConnection.sendPacket(new PacketPlayOutRespawn(ep.dimension, ep.getWorld().getDifficulty(), ep.getWorld().getWorldData().getType(), ep.playerInteractManager.getGameMode()));
                p.updateInventory();
            }
        }.runTaskLater(BlitzSG.getInstance(), 2L);
    }
}


