package me.hardstyles.blitz.command.world.sub;


import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WorldLocateSubCommand extends SubCommand {

    public WorldLocateSubCommand() {
        super("locate", ImmutableList.of("find"), "blitz.rank.find", "/world find");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        if (args.length == 1) {
            FileConfiguration fc = new YamlConfiguration();
            try {
                fc.load(new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + s.getWorld().getName().toLowerCase() + ".yml"));
            } catch (IOException | InvalidConfigurationException e) {
                s.sendMessage(ChatUtil.color("&cError: &7Could not find the world you are in!"));
            }
            s.teleport(new Location(s.getWorld(), fc.getInt("Lobby.X"), fc.getInt("Lobby.Y"), fc.getInt("Lobby.Z")));
            s.sendMessage(ChatUtil.color("&aTeleported to the lobby!"));
        }
    }
}