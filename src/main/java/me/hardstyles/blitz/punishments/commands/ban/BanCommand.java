package me.hardstyles.blitz.punishments.commands.ban;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.punishments.PlayerBan;
import me.hardstyles.blitz.punishments.PlayerMute;
import me.hardstyles.blitz.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BanCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public BanCommand() {
        super("ban", ImmutableList.of(), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        } else if (args.length == 2) {
            return ImmutableList.of("1h", "2h", "6h", "12h", "1d", "7d", "14d", "30d", "90d", "180d", "365d");
        } else if (args.length > 2) {
            return ImmutableList.of("Unfair Advantage", "Extreme Chat Behaviour", "Advertising", "Scamming", "Other");
        }
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        String executor = "Console";
        if (sender instanceof Player) {
            IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
            if (!(p.getRank().canBan())) {
                sender.sendMessage(ChatUtil.color("&cYou do not have permission to use this command!"));
                return;
            }
            executor = ((Player) sender).getName();
        }

        if (args.length == 0) {
            help(sender);
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtil.color("&cThat player is not online!"));
            return;
        }
        if (args.length == 1) {
            help(sender);
            return;
        }
        String duration = args[1];
        long futureTime = calculateFutureTime(duration);
        if (futureTime == -1) {
            sender.sendMessage(ChatUtil.color("&cInvalid duration!"));
            return;
        }

        String reason = "No reason specified.";
        if (args.length > 2) {
            reason = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
        }

        String finalReason = reason;
        String finalExecutor = executor;
        PlayerBan ban = new PlayerBan(futureTime, finalReason, finalExecutor);
        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
            try {
               BlitzSG.getInstance().getDb().saveBan(target.getUniqueId(), ban);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        target.kickPlayer(ChatUtil.color("&cYou have been banned for " + duration + " for " + reason + ""));
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.color("&cUsage: /ban <player> <duration> [<reason>]"));
    }

    private List<SubCommand> getAvailableSubs(CommandSender sender) {
        return subcommands.stream()
                .filter(c -> sender.hasPermission(c.getPermission()))
                .collect(Collectors.toList());
    }

    private SubCommand getSubCommand(String name) {
        String a = name.toLowerCase();
        return subcommands.stream()
                .filter(sub -> (sub.getName().equals(a) || sub.getAliases().contains(a)))
                .findFirst().orElse(null);
    }


    public long calculateFutureTime(String durationStr) {
        long currentTimeMillis = System.currentTimeMillis();
        long durationInMillis = 0;
        try {
            int duration = Integer.parseInt(durationStr.substring(0, durationStr.length() - 1));
            char unit = durationStr.charAt(durationStr.length() - 1);
            switch (unit) {
                case 's':
                    durationInMillis = duration * 1000L;
                    break;
                case 'm':
                    durationInMillis = duration * 60L * 1000L;
                    break;
                case 'h':
                    durationInMillis = duration * 60L * 60L * 1000L;
                    break;
                case 'd':
                    durationInMillis = duration * 24L * 60L * 60L * 1000L;
                    break;
                default:
                    return -1;
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return -1;
        }
        return currentTimeMillis + durationInMillis;
    }
}