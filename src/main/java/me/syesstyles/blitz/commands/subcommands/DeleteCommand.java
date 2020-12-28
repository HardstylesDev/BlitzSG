package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.entity.Player;

public class DeleteCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc delete <arena>";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
