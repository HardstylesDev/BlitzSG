package me.syesstyles.blitz.commands.subcommands;

import me.syesstyles.blitz.utils.VoidGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.map.Map;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;

import java.io.File;

public class CreateCommand extends SubCommand {

	@Override
	public void runCommand(Player p, String[] args) {
		if(args.length < 2) {
			p.sendMessage("§cUsage: /bsg create <name>");
			return;
		}
		/*if(ArenaUtils.getCornerLoc1(p) == null || ArenaUtils.getCornerLoc2(p) == null) {
			p.sendMessage("§cPlease set both corners before creating an arena");
			return;
		}*/
		
		BlitzSGPlayer uhcPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
		for(Map a : BlitzSG.getInstance().getArenaManager().getArenas()) {
			if(a.getName().equalsIgnoreCase(args[1])) {
				uhcPlayer.setEditedArena(a);
				p.teleport(new Location(a.getArenaWorld(), 0, 100, 0));
				p.sendMessage("§eYou are now editing the arena §6\"" + args[1] + "\"§e.");
				return;
			}
		}
		
		//WorldCreator wc = new WorldCreator(BlitzSG.getInstance().getDataFolder() + "/worlds/" + args[1]);
//
		//wc.type(WorldType.FLAT);
		//wc.generatorSettings("2;0;1;");
//
		//wc.createWorld();
		//
		//

		BlitzSG.getInstance().getArenaManager().copyFileStructure(new File("arenas/" + args[1].toLowerCase()), new File("worlds/" + args[1].toLowerCase()));
		new WorldCreator(args[1].toLowerCase()).generator(new VoidGenerator()).createWorld();

		World w = Bukkit.getWorld(args[1].toLowerCase());

		//w.setAutoSave(false);
        //w.setTicksPerAnimalSpawns(1);
        //w.setTicksPerMonsterSpawns(1);
        //w.setGameRuleValue("doMobSpawning", "false");
        //w.setGameRuleValue("mobGriefing", "false");
        //w.setGameRuleValue("doFireTick", "false");
        //w.setGameRuleValue("showDeathMessages", "false");
		
		p.teleport(new Location(w, 0, 100, 0));
		uhcPlayer.setEditedArena(new Map(p.getWorld(), args[1]));
		p.sendMessage("§eSuccessfully created the world for arena §6\"" + args[1] + "\"§e.");
		p.sendMessage("§eUse §6/bsg save §eto save the arena.");
		
		//new Arena(ArenaUtils.getCornerLoc1(p), ArenaUtils.getCornerLoc2(p), args[1]);
		//p.sendMessage("§eSuccessfully created the arena §6\"" + args[1] + "\"");
		
		//TODO - SkywarsReloaded arena creation system
		//Create world and let player setup arena in said world
		//Save world
		//Dupe world every time a new map needs to be used
	}

	@Override
	public String getHelp() {
		return "§8\u2022 §f/bsg create <arena>";
	}

	@Override
	public String getPermission() {
		return "bsg.admin";
	}

}
