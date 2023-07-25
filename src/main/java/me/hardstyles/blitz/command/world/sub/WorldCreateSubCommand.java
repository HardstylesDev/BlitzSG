package me.hardstyles.blitz.command.world.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.EmptyWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WorldCreateSubCommand extends SubCommand {

    public WorldCreateSubCommand() {
        super("create", ImmutableList.of("new"), "blitz.rank.worldnew", "/world new <world>");
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
            if (world != null) {
                sender.sendMessage(ChatUtil.color("&cWorld already exists! Type /world tp " + args[1]));
                return;
            }
            new WorldCreator(args[1]).generator(new EmptyWorldGenerator()).createWorld();
            sender.sendMessage(ChatUtil.color("&aWorld created! Type /world tp " + args[1]));
        }
    }
}