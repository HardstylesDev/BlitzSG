package me.hardstyles.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyAcceptSubCommand extends SubCommand {
    public PartyAcceptSubCommand() {
        super("accept", ImmutableList.of(), null, "/party accept");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getParty() != null) {
            player.sendMessage(ChatUtil.color("&cYou are already in a party!"));
            return;
        }
        if(iPlayer.getPartyInvite() == null){
            player.sendMessage(ChatUtil.color("&cYou have no party invites!"));
            return;
        }
        Party party = iPlayer.getPartyInvite();
        party.accept(player);
    }
}
