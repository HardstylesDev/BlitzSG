package me.hardstyles.blitz.command.world.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WorldUnloadSubCommand extends SubCommand {

    public WorldUnloadSubCommand() {
        super("unload", ImmutableList.of("disable"), "blitz.rank.worldunload", "/world unload <world>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(ChatUtil.color("&cUsage: /world new <name>"));
        }
        if (args.length == 2) {
            World world = Bukkit.getWorld(args[1]);
            if (world == null) {
                sender.sendMessage(ChatUtil.color("&cWorld is not loaded! Type /world load " + args[1]));
                return;
            }
            Bukkit.unloadWorld(world, false);
            sender.sendMessage(ChatUtil.color("&aWorld unloaded!"));
        }
    }
}