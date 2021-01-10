package me.syesstyles.blitz.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.arena.ArenaUtils;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;

public class SaveCommand extends SubCommand{

	@Override
	public void runCommand(Player p, String[] args) {
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		if(!uhcPlayer.isEditingArena()) {
			p.sendMessage("§cYou are currently not editing an arena.");
			return;
		}
		if(uhcPlayer.getEditedArena().getSpawns().size() < 2) {
			p.sendMessage("§cYou must add at least 2 spawns in order to save the arena.");
			return;
		}
		if(uhcPlayer.getEditedArena().getLobby() == null) {
			p.sendMessage("§cPlease specify the lobby.");
			return;
		}


		p.sendMessage("§eSuccessfully saved arena §6" + uhcPlayer.getEditedArena().getName() + "§e!");
		p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 100.5, 0.5, 90, 0));  //todo change back


		Bukkit.unloadWorld(p.getWorld(), true);
		uhcPlayer.setEditedArena(null);

		ArenaUtils.saveArenas();
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/bsg save";
	}

	@Override
	public String getPermission() {
		return "bsg.admin";
	}

}
