package me.syesstyles.blitz.commands.subcommands;

import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;

public class SetLobbyCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		blitzSGPlayer.getEditedArena().setLobby(p.getLocation());
		p.sendMessage("§eSuccesfully set the new lobby location!");
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc setlobby";
	}

	@Override
	public String getPermission() {
		return "bsg.admin";
	}

}
