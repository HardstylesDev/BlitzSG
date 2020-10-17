package org.example.SpigotServer.worldgeneration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.SpigotServer.SpigotServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Worlds {
    public static HashMap<Object, Boolean> worlds;
    static List<String> test;
    public static void addWorld(String g) {
        worlds.put(g, true);
        saveWorlds();
    }
    public static boolean exists(String s){
        if(worlds.containsKey(s))
            return true;
        return false;
    }
    public static void updateWorld(String w, boolean t){
        worlds.remove(w);
        worlds.put(w,t);
        saveWorlds();

    }
    public static boolean isLoaded(String w){
        return worlds.get(w);
    }
    public static Boolean getPrefix(String g) {
        return worlds.getOrDefault(g, false);
    }

    public static void loadWorlds() {
        File tempFile = new File(SpigotServer.plugin.getDataFolder() + "\\worlds.json");
        boolean exists = tempFile.exists();
        if (exists == false) {
            try {
                File file = new File(SpigotServer.plugin.getDataFolder() + "\\worlds.json");
                if (file.createNewFile()) {
                    System.out.println("New Guild Settings Created " + file.getName());
                } else {
                    System.out.println("Settings Already Exist");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Path path = Paths.get(SpigotServer.plugin.getDataFolder() + "\\worlds.json");
                Files.write(path, "{\"world\": true}".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(SpigotServer.plugin.getDataFolder() + "\\worlds.json");
        HashMap<Object, Boolean> newPrefixes = null;
        try {
            System.out.println(newPrefixes);
            newPrefixes = new Gson().fromJson(new FileReader(file), HashMap.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        worlds = newPrefixes;
    }

    public static void saveWorlds() {
        try {
            String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(worlds);
            Path path = Paths.get(SpigotServer.plugin.getDataFolder() + "\\worlds.json");
            Files.write(path, jsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}