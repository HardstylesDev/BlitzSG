package me.hardstyles.blitz.command.party.sub;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.party.Party;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PartyLeaveSubCommand extends SubCommand {
    public PartyLeaveSubCommand() {
        super("leave", ImmutableList.of(), null, "/party leave");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player s = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(s.getUniqueId());
        if (iPlayer.getParty() == null) {
            s.sendMessage(ChatUtil.color("&cYou are not in a party!"));
            return;
        }
        iPlayer.getParty().leave(s);
    }
}
