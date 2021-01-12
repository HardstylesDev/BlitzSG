package me.syesstyles.blitz.commands.subcommands;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import org.bukkit.entity.Player;

public class setDeathmatchCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		blitzSGPlayer.getEditedArena().setDeathmatch(p.getLocation());
		p.sendMessage("§eSuccesfully set the new deathmatch location!");
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/bsg setdeathmatch";
	}

	@Override
	public String getPermission() {
		return "bsg.admin";
	}

}
