package me.syesstyles.blitz.commands.subcommands;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.blitzsgplayer.BlitzSGPlayer;
import org.bukkit.entity.Player;

public class setDeathmatchDistanceCommand extends SubCommand {

    @Override
    public void runCommand(Player p, String[] args) {
        BlitzSGPlayer blitzSGPlayer = BlitzSG.getInstance().getBlitzSGPlayerManager().getBsgPlayer(p.getUniqueId());
        try {
            blitzSGPlayer.getEditedArena().setDeathmatchDistance(Integer.parseInt(args[1]));
            p.sendMessage("§eSuccesfully set the new deathmatch distance!");
        } catch (Exception e) {
            p.sendMessage("§cfailed to set distance! " + e.getLocalizedMessage());
        }
    }

    @Override
    public String getHelp() {
        return "§8\u2022 §f/bsg setdeathmatchdistance";
    }

    @Override
    public String getPermission() {
        return "bsg.admin";
    }

}
