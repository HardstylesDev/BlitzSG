package me.hardstyles.blitz.cosmetic.wardrobe;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WardrobeCommand extends Command {
    public WardrobeCommand() {
        super("wardrobe", ImmutableList.of("wdb"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        IPlayer iPlayer = BlitzSG.getInstance().getIPlayerManager().getPlayer(player.getUniqueId());
        if (iPlayer.getRank().getPosition() > 2) {
            if (args.length < 1) {
                if (!(iPlayer.getGame() == null)) {
                    sender.sendMessage(ChatUtil.color("&cYou cannot use this command while in a game!"));
                    return;
                }
                new WardrobeMenu().open(player);
            } else {
                sender.sendMessage(ChatUtil.color("&cUsage: /wardrobe"));
            }
        } else {
            sender.sendMessage(ChatUtil.color("&cYou must be a &aVIP&6+ &cor higher to use this command!"));
        }

    }
}
