package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;

public class WarpCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		p.sendMessage(p.getWorld().getName());
		if(BlitzSG.getInstance().getArenaManager().getRandomArena() != null) {
			p.teleport(BlitzSG.getInstance().getArenaManager().getRandomArena().getCenter());
			p.sendMessage(p.getWorld().getName());
		}
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/bsg warp <arena>";
	}

	@Override
	public String getPermission() {
		return "bsg.admin";
	}

}
