package me.hardstyles.blitz.punishments.commands.ban;

import com.google.common.collect.ImmutableList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.util.ChatUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UnbanCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public UnbanCommand() {
        super("unban", ImmutableList.of(), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
            if (!(p.getRank().canBan())) {
                sender.sendMessage(ChatUtil.color("&cYou do not have permission to use this command!"));
                return;
            }
        }


        if (args.length == 0) {
            help(sender);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtil.color("&cThat player could not be found!"));
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
            try {
                MongoDatabase database = BlitzSG.getInstance().getDb().getDatabase();
                MongoCollection<Document> collection = database.getCollection("bans");

                Document query = new Document("uuid", target.getUniqueId().toString());
                collection.deleteOne(query);

                sender.sendMessage(ChatUtil.color("&aSuccessfully unbanned " + target.getName() + "!"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.color("&cUsage: /unban <player>"));
    }
}