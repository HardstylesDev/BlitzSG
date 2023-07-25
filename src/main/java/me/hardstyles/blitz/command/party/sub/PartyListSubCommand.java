package me.hardstyles.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PartyListSubCommand extends SubCommand {
    public PartyListSubCommand() {
        super("list", ImmutableList.of(), null, "/party list");
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
        player.sendMessage(ChatUtil.color("&aParty Owner:"));
        OfflinePlayer owner = Bukkit.getOfflinePlayer(party.getOwner());
        if(owner.isOnline()){
            IPlayer iOwner = BlitzSG.getInstance().getIPlayerManager().getPlayer(owner.getUniqueId());
            player.sendMessage(ChatUtil.color("&7 - " + iOwner.getRank().getPrefix() + owner.getName() + " &a●"));
        } else {
            player.sendMessage(ChatUtil.color("&7 - " + owner.getName() + " &c●"));
        }
        player.sendMessage(ChatUtil.color("&aParty Members:"));
        for (UUID member : party.getMembers()) {
            OfflinePlayer partyMember = Bukkit.getOfflinePlayer(member);
            if(partyMember.isOnline()){
                IPlayer iPartyMember = BlitzSG.getInstance().getIPlayerManager().getPlayer(partyMember.getUniqueId());
                player.sendMessage(ChatUtil.color("&7- " + iPartyMember.getRank().getPrefix() + partyMember.getName() + " &a●"));
            } else {
                player.sendMessage(ChatUtil.color("&7- " + partyMember.getName() + " &c●"));
            }
        }
    }
}
