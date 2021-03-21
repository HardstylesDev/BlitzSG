package me.syesstyles.blitz.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class VanillaCommands {

    public void removed() throws Exception {
        SimplePluginManager simplePluginManager = (SimplePluginManager) Bukkit.getServer()
                .getPluginManager();

        Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
        commandMapField.setAccessible(true);

        SimpleCommandMap simpleCommandMap = (SimpleCommandMap) commandMapField.get(simplePluginManager);

        for (Command command : simpleCommandMap.getCommands()) {
            System.out.println(command.getName() + " " + command.getDescription());

        }

        simpleCommandMap.getCommand("acban").unregister(simpleCommandMap);
        //simpleCommandMap.clearCommands();
    }

    public void removeCommands() throws Exception {
        Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
        SimpleCommandMap commandMap = (SimpleCommandMap) result;
        Object map = getPrivateField(commandMap, "knownCommands"); // Line 270
        @SuppressWarnings("unchecked")
        HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
        ArrayList<String> toUnregister = new ArrayList<>();
        knownCommands.forEach((s, command) -> {
            if (removeCommands(s)) {
                toUnregister.add(s);
            }
        });
        for (String s : toUnregister) {
            knownCommands.remove(s);
        }
    }


    private Object getPrivateField(Object object, String field) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field); // Line 283
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    private boolean removeCommands(String s) {
        switch (s) {
            case "stop":
                return false;
            case "plugman":
                return false;
            case "bsg":
                return false;
            case "fw":
                return false;
            case "horsey":
                return false;
            case "unban":
                return false;
            case "acban":
                return false;
            case "nick":
                return false;
            case "unnick":
                return false;
            case "tag":
                return false;
            case "op":
                return false;
            case "fly":
                return false;
            case "gamemode":
                return false;
            default:
                return true;
        }
    }
}
