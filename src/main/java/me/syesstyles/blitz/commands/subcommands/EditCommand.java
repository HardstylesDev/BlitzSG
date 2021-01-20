package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.map.Map;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;

public class EditCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		if(args.length < 2) {
			p.sendMessage("§cUsage: /bsg edit <name>");
			return;
		}
		
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		for(Map a : BlitzSG.getInstance().getArenaManager().getArenas()) {
			BlitzSG.getInstance().getArenaManager().fixSpawns(a);
			if(a == null || a.getLobby().getWorld() == null) continue;


			if(a.getLobby().getWorld().getName().equalsIgnoreCase(args[1])) {
				System.out.println(a.getLobby().getWorld().getName() + " is being edited" );
				uhcPlayer.setEditedArena(a);
				p.teleport(a.getLobby());
				p.sendMessage("§eYou are now editing the arena §6\"" + args[1] + "\"§e.");
				return;
			}
		}
		p.sendMessage("§cThe specified arena does not exist, use /bsg arenas for a list of all arenas.");
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/bsg edit <arena>";
	}

	@Override
	public String getPermission() {
		return "bsg.admin";
	}
	
}
