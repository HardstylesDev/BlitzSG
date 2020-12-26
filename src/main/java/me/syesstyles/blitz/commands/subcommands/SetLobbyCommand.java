package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;

public class SetLobbyCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		BlitzSG.getInstance().getConfig().set("Lobby.Spawn", p.getLocation().getWorld().getName());
		BlitzSG.getInstance().getConfig().set("Lobby.X", p.getLocation().getBlockX());
		BlitzSG.getInstance().getConfig().set("Lobby.Y", p.getLocation().getBlockY());
		BlitzSG.getInstance().getConfig().set("Lobby.Z", p.getLocation().getBlockZ());
		BlitzSG.getInstance().saveConfig();
		p.sendMessage("�eSuccesfully set the new lobby location!");
	}

	@Override
	public String getHelp() {
		return "�8\u2022 �f/speeduhc setlobby";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
