package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.arena.Arena;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;

public class EditCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		if(args.length < 2) {
			p.sendMessage("§cUsage: /suhc edit <name>");
			return;
		}
		
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		for(Arena a : BlitzSG.getInstance().getArenaManager().getArenas()) {
			if(a.getName().equalsIgnoreCase(args[1])) {
				uhcPlayer.setEditedArena(a);
				p.teleport(new Location(a.getArenaWorld(), 0, 100, 0));
				p.sendMessage("§eYou are now editing the arena §6\"" + args[1] + "\"§e.");
				return;
			}
		}
		p.sendMessage("§cThe specified arena does not exist, use /suhc arenas for a list of all arenas.");
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/speeduhc edit <arena>";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}
	
}
