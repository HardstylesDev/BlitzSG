package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;

public class JoinCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		if(BlitzSG.getInstance().getGameManager().getAvailableGame() == null) {
			p.sendMessage("§cCouldn't find any available games");
			return;
		}
		BlitzSG.getInstance().getGameManager().getAvailableGame().addPlayer(p);
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc join";
	}

	@Override
	public String getPermission() {
		return "speeduhc.default";
	}

}
