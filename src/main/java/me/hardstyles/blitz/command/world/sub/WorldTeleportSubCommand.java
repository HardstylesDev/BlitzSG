package me.hardstyles.blitz.command.world.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldTeleportSubCommand extends SubCommand {

    public WorldTeleportSubCommand() {
        super("tp", ImmutableList.of("teleport"), "blitz.rank.worldlist", "/world tp <worldName>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        if (args.length == 1) {
            sender.sendMessage(ChatUtil.color("&cUsage: /world tp <worldName>"));
            return;
        }
        String worldName = args[1];
        if (Bukkit.getWorld(worldName) == null) {
            sender.sendMessage(ChatUtil.color("&cWorld not found."));
            return;
        }
        s.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
    }
}