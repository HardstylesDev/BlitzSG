package me.hardstyles.blitz.command;


import com.google.common.collect.ImmutableList;
import lombok.Getter;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
public abstract class SubCommand {
    private final String name, permission, usage;
    private final List<String> aliases;

    public SubCommand(String name, List<String> aliases, String permission, String usage) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.usage = usage;
    }

    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            return ImmutableList.of();
        } else {
            return onTabComplete(sender, args);
        }
    }

    public void execute(CommandSender sender, String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(ChatUtil.color("&cYou don't have permission to perform this command."));
        } else {
            onExecute(sender, args);
        }
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract void onExecute(CommandSender sender, String[] args);
}