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
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getSpeedUHCPlayerManager().getUhcPlayer(p.getUniqueId());
		if(!uhcPlayer.isEditingArena()) {
			p.sendMessage("�cYou are currently not editing an arena.");
			return;
		}
		if(uhcPlayer.getEditedArena().getSpawns().size() < 2) {
			p.sendMessage("�cYou must add at least 2 spawns in order to save the arena.");
			return;
		}
		if(ArenaUtils.getCornerLoc1(p) == null || ArenaUtils.getCornerLoc2(p) == null) {
			if(uhcPlayer.getEditedArena().getArenaMinCorner() == null && uhcPlayer.getEditedArena().getArenaMaxCorner() == null) {
				p.sendMessage("�cPlease set both corners before saving the arena");
				return;
			}
		}
		if(ArenaUtils.getCornerLoc1(p) != null || ArenaUtils.getCornerLoc2(p) != null) {
			uhcPlayer.getEditedArena().setArenaWorld(p.getWorld());
			
			double x1 = Math.min(ArenaUtils.getCornerLoc1(p).getBlockX(), ArenaUtils.getCornerLoc2(p).getBlockX());
			double y1 = Math.min(ArenaUtils.getCornerLoc1(p).getBlockY(), ArenaUtils.getCornerLoc2(p).getBlockY());
			double z1 = Math.min(ArenaUtils.getCornerLoc1(p).getBlockZ(), ArenaUtils.getCornerLoc2(p).getBlockZ());
			double x2 = Math.max(ArenaUtils.getCornerLoc1(p).getBlockX(), ArenaUtils.getCornerLoc2(p).getBlockX());
			double y2 = Math.max(ArenaUtils.getCornerLoc1(p).getBlockY(), ArenaUtils.getCornerLoc2(p).getBlockY());
			double z2 = Math.max(ArenaUtils.getCornerLoc1(p).getBlockZ(), ArenaUtils.getCornerLoc2(p).getBlockZ());
			Location arenaMinCorner = new Location(uhcPlayer.getEditedArena().getArenaWorld(), x1, y1, z1);
			Location arenaMaxCorner = new Location(uhcPlayer.getEditedArena().getArenaWorld(), x2, y2, z2);
			
			uhcPlayer.getEditedArena().setArenaMinCorner(arenaMinCorner);
			uhcPlayer.getEditedArena().setArenaMaxCorner(arenaMaxCorner);
		}
		p.sendMessage("�eSuccessfully saved arena �6" + uhcPlayer.getEditedArena().getName() + "�e!");
		p.teleport(BlitzSG.lobbySpawn);
		
		Bukkit.unloadWorld(p.getWorld(), true);
		uhcPlayer.setEditedArena(null);
	}

	@Override
	public String getHelp() {
		return "�8\u2022 �f/speeduhc save";
	}

	@Override
	public String getPermission() {
		return "speeduhc.admin";
	}

}
