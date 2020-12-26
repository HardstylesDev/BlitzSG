package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.entity.Player;

public abstract class SubCommand {
	
	public abstract void runCommand(Player p, String[] args);
	
	public abstract String getHelp();
	
	public abstract String getPermission();

}
