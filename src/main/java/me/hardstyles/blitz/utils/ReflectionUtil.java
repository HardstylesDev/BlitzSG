package me.hardstyles.blitz.utils;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@UtilityClass
public class ReflectionUtil {
    private static final String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    private static final Class<?> packetPlayOutChat = getNMSClass("PacketPlayOutChat");

    public Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getCBClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void unregisterCommands(CommandMap map, String removing) {
        unregisterCommands(map, Collections.singleton(removing));
    }

    @SuppressWarnings("unchecked")
    public void unregisterCommands(CommandMap map, Collection<String> removing) {
        try {
            Field field = getField(map.getClass(), "knownCommands") != null ? getField(map.getClass(), "knownCommands")
                    : getField(map.getClass().getSuperclass(), "knownCommands");
            Map<String, Command> commands = (Map<String, Command>) field.get(map);

            for (String command : removing) {
                commands.remove(command);
            }

            field.set(map, commands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendActionBar(Player p, String message) {
        Method icbcMethod;
        Object icbc;
        Class<?> icbcClass = getNMSClass("IChatBaseComponent");
        Class<?> chatSerializerClass = null;
        for(Class<?> clazz : icbcClass.getClasses()) {
            if(clazz.getName().contains("ChatSerializer"))
                chatSerializerClass = clazz;
        }
        if(chatSerializerClass == null) return;

        try {
            icbcMethod = chatSerializerClass.getDeclaredMethod("a", String.class);
            icbc = icbcMethod.invoke(null, "{\"text\": \"" + message + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        Object packet = null;

        if(Integer.parseInt(version.split("_")[1]) < 11) {
            try {
                Constructor<?> constructor = packetPlayOutChat.getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
                packet = constructor.newInstance(icbc, (byte) 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class<?> chatMessageTypeClass = getNMSClass("ChatMessageType");
                Constructor<?> constructor = packetPlayOutChat.getConstructor(getNMSClass("IChatBaseComponent"), chatMessageTypeClass);
                Object chatMessageTypeValue = chatMessageTypeClass.getMethod("valueOf", String.class).invoke(null, "GAME_INFO");

                packet = constructor.newInstance(icbc, chatMessageTypeValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (packet == null) return;
        Class<?> player = getCBClass("entity.CraftPlayer");

        Object craftPlayer;
        try {
            craftPlayer = player.getMethod("getHandle").invoke(p);
            Field playerConnectionField = craftPlayer.getClass().getField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayer);

            Method sendPacketsMethod = playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"));
            sendPacketsMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPermissibleBase(Player player, PermissibleBase permissibleBase) {
        Class<?> craftHumanEntityClass = getCBClass("entity.CraftHumanEntity");
        Object craftHumanEntity = craftHumanEntityClass.cast(player);
        try {
            getField(craftHumanEntityClass, "perm").set(craftHumanEntity, permissibleBase);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public PermissibleBase getPermissibleBase(Player player) {
        Class<?> craftHumanEntityClass = getCBClass("entity.CraftHumanEntity");
        Object craftHumanEntity = craftHumanEntityClass.cast(player);

        try {
            return (PermissibleBase) getField(craftHumanEntityClass, "perm").get(craftHumanEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GameProfile getGameProfile(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object handle = player.getClass().getMethod("getHandle").invoke(player);

        return (GameProfile) handle.getClass()
                .getSuperclass()
                .getDeclaredMethod("getProfile")
                .invoke(handle);
    }

    public Property getTexturesProperty(GameProfile profile) {
        return profile.getProperties().get("textures").stream().findFirst().orElse(null);
    }
}