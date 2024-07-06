package me.hardstyles.blitz.nickname;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.hardstyles.blitz.BlitzSG;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

public class SkinManager {
    public SkinManager() {
    }

    HashMap<String, Skin> cache = new HashMap<>();

    public Skin getSkin(String name){
        if(cache.containsKey(name)){
            return cache.get(name);
        }
        Skin skin = Skin.fromName(name);
        cache.put(name, skin);
        return skin;
    }

    public void setSkin(Player player, Skin skin) {
        for (Player onlinePlayer : BlitzSG.getInstance().getServer().getOnlinePlayers()) {
            ((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer) player).getHandle()));
        }
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        GameProfile gameProfile = entityPlayer.getProfile();
        gameProfile.getProperties().clear();
        gameProfile.getProperties().put("textures", new Property("textures", skin.getValue(), skin.getSignature()));

        for (Player onlinePlayer : BlitzSG.getInstance().getServer().getOnlinePlayers()) {
            ((CraftPlayer) onlinePlayer).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle()));
            if(onlinePlayer.canSee(player) && !onlinePlayer.equals(player)) {
                onlinePlayer.hidePlayer(player);
                BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> onlinePlayer.showPlayer(player), 5);
            }
        }
    }
}
