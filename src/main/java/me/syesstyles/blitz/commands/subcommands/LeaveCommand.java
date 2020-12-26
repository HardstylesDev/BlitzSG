package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;

public class LeaveCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		if(uhcPlayer.isInGame())
			uhcPlayer.getGame().removePlayer(p);
	}

	@Override
	public String getHelp() {
		return "�8\u2022 �f/speeduhc leave";
	}

	@Override
	public String getPermission() {
		return "speeduhc.default";
	}

}
