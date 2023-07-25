package me.hardstyles.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyInviteSubCommand extends SubCommand {
    public PartyInviteSubCommand() {
        super("invite", ImmutableList.of(), null, "/party invite <player>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        } else {
            return ImmutableList.of();
        }
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(s.getUniqueId());
        if (iPlayer.getParty() == null) {
            s.sendMessage(ChatUtil.color("&9Party > &eYou have created a party!"));
            iPlayer.setParty(new Party(s.getUniqueId()));
        }
        Party party = iPlayer.getParty();
        if(party.getOwner() != iPlayer.getUuid()){
            s.sendMessage(ChatUtil.color("&cYou are not the owner of this party!"));
            return;
        }
        if(args.length < 2){
            s.sendMessage(ChatUtil.color("&cUsage: /party invite <s>"));
            return;
        }

        Player target = BlitzSG.getInstance().getServer().getPlayer(args[1]);
        if (target == null) {
            s.sendMessage(ChatUtil.color("&cThat s is not online!"));
            return;
        }
        IPlayer iTarget = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
        if (iTarget.getParty() != null) {
            s.sendMessage(ChatUtil.color("&cThat player is already in a party!"));
            return;
        }

        iPlayer.getParty().invite(target);
    }
}
