package me.syesstyles.blitz.command;

import com.google.common.collect.ImmutableList;

import lombok.Getter;
import me.syesstyles.blitz.utils.ChatUtil;
import me.syesstyles.blitz.utils.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Command extends org.bukkit.command.Command {
    private static final List<String> registeredCommands = new ArrayList<>();
    String name, permission;
    List<String> aliases;

    public Command(String name, List<String> aliases, String permission) {
        super(name);
        setAliases(aliases);

        this.name = name;
        this.aliases = aliases;
        this.permission = permission;

        try {
            CommandMap map = (CommandMap) ReflectionUtil.getField(Bukkit.getServer().getClass(), "commandMap").get(Bukkit.getServer());
            ReflectionUtil.unregisterCommands(map, getName());
            ReflectionUtil.unregisterCommands(map, getAliases());
            map.register(getName(), "bloodcore", this);
            registeredCommands.add(name);
            registeredCommands.addAll(aliases);
            Bukkit.getConsoleSender().sendMessage(ChatUtil.color("&aLoaded command: &f" + name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(ChatUtil.color("&cYou don't have permission to perform this command."));
        } else {
            onExecute(sender, args);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            return ImmutableList.of();
        }

        List<String> completion = onTabComplete(sender, args);
        return completion == null ? ImmutableList.of() : completion;
    }

    public static List<String> getRegisteredCommands() {
        return registeredCommands;
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract void onExecute(CommandSender sender, String[] args);

}