package me.syesstyles.blitz.commands.subcommands;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.arena.Arena;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenasCommand extends SubCommand {

    @Override
    public void runCommand(Player p, String[] args) {
        for (Arena a : BlitzSG.getInstance().getArenaManager().getArenas()) {
            BlitzSG.getInstance().getArenaManager().fixSpawns(a);
            p.sendMessage("§eArena: §6" + a.getName() + " lobby: " + a.getLobby().getX() + ", " + a.getLobby().getY() + ", " + a.getLobby().getZ() + ChatColor.GREEN + "Deathmatch: " + a.getDeathmatch().getX() + ", " + a.getDeathmatch().getY() + ", " + a.getDeathmatch().getZ());
        }
    }

    @Override
    public String getHelp() {
        return "§8\u2022 §f/bsg arenas";
    }

    @Override
    public String getPermission() {
        return "bsg.admin";
    }

}
