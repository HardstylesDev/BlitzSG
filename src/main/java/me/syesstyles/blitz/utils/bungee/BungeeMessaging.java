package me.syesstyles.blitz.utils.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.syesstyles.blitz.BlitzSG;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class BungeeMessaging implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GameStatus")) {

            String subChannel = in.readUTF();
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            try {
                String somedata = msgin.readUTF(); // Read the data in the same way you wrote it
                System.out.println("Received: " + somedata);
            } catch (IOException e){
            }

            if (BlitzSG.getInstance().getGameManager().getRunningGames().size() > 0) {

            }

        }
    }
}
