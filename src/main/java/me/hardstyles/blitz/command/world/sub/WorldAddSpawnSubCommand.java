package me.hardstyles.blitz.command.world.sub;


import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WorldAddSpawnSubCommand extends SubCommand {

    public WorldAddSpawnSubCommand() {
        super("addspawn", ImmutableList.of("addspawnpoint"), "blitz.rank.addspawn", "/world addspawn <name>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        if (args.length == 2) {
            String name = args[1];
            FileConfiguration fc = new YamlConfiguration();
            try {
                fc.load(new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + name + ".yml"));
                int currentSpawns = fc.getConfigurationSection("Spawns").getKeys(false).size();
                fc.set("Spawns." + currentSpawns + ".X", (int) s.getLocation().getX());
                fc.set("Spawns." + currentSpawns + ".Y", (int) s.getLocation().getY());
                fc.set("Spawns." + currentSpawns + ".Z", (int) s.getLocation().getZ());
                fc.save(new File(BlitzSG.getInstance().getDataFolder() + "/arenas/" + name + ".yml"));
            } catch (IOException | InvalidConfigurationException e) {
                s.sendMessage(ChatUtil.color("&cError: &7Could not find the world you are in!"));
            }
        }
    }
}