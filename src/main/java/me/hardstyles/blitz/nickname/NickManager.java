package me.hardstyles.blitz.nickname;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class NickManager {
    public NickManager() {

    }


    public void setNick(Player player, String name, String skinName) {
        setNameTag(player, name);
        if(skinName == null){
            skinName = name;
        }

        SkinManager skinManager = BlitzSG.getInstance().getSkinManager();
        Skin skin = skinManager.getSkin(skinName);
        skinManager.setSkin(player, skin);

        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        iPlayer.setNickName(name);

        BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
            player.performCommand("lobby");
        }, 15);

    }

    public void resetNick(Player player) {
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        player.sendMessage("§aYour nickname has been reset to  " + iPlayer.getUsername());
        setNick(player, iPlayer.getUsername(), iPlayer.getUsername());
        iPlayer.setNickName(null);
    }



    private void setNameTag(Player player, String name) {
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
            if (!gameProfileExists) {
                Field nameField = entityPlayer.getClass().getSuperclass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(entityPlayer, name);
            } else {
                Object profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                Field ff = profile.getClass().getDeclaredField("name");
                ff.setAccessible(true);
                ff.set(profile, name);
            }

            refreshPlayer(player);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void refreshPlayer(Player player) {
        Location loc = player.getLocation();
        loc.setYaw(player.getLocation().getYaw());
        loc.setPitch(player.getLocation().getPitch());
        EntityPlayer ep = ((CraftPlayer) player).getHandle();


        new BukkitRunnable() {
            @Override
            public void run() {
                ep.playerConnection.sendPacket(new PacketPlayOutRespawn(ep.getWorld().worldProvider.getDimension(), ep.getWorld().getDifficulty(), ep.getWorld().worldData.getType(), WorldSettings.EnumGamemode.getById(player.getGameMode().getValue())));
                player.teleport(loc);
                player.updateInventory();
            }
        }.runTaskLater(BlitzSG.getInstance(), 5);
    }


}
