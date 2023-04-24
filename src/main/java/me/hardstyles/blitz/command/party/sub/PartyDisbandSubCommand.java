package me.hardstyles.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyDisbandSubCommand extends SubCommand {
    public PartyDisbandSubCommand() {
        super("disband", ImmutableList.of(), null, "/party disband");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getParty() == null) {
            player.sendMessage(ChatUtil.color("&cYou are not in a party!"));
            return;
        }
        Party party = iPlayer.getParty();
        if(party.getOwner() != iPlayer.getUuid()){
            player.sendMessage(ChatUtil.color("&cYou are not the owner of this party!"));
            return;
        }
        party.disband();
    }
}
