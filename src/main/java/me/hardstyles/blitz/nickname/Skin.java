package me.hardstyles.blitz.nickname;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Getter
public class Skin {
    private String value;
    private String signature;

    public Skin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    public static Skin fromName(String arg) {
        OfflinePlayer op = Bukkit.getServer().getOfflinePlayer(arg);
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.mineskin.org/generate/user/" + op.getUniqueId()).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                if (reply.contains("\"error\"")) {
                    return null;

                }
                String value = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];


                return new Skin(value, signature);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}

