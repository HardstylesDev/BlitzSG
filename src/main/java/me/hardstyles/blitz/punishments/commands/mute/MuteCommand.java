package me.hardstyles.blitz.punishments.commands.mute;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.punishments.PlayerMute;
import me.hardstyles.blitz.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MuteCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public MuteCommand() {
        super("mute", ImmutableList.of(), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        } else if (args.length == 2) {
            return ImmutableList.of("1h", "6h", "1d", "7d", "14d", "30d");
        } else if (args.length > 2) {
            return ImmutableList.of("Verbal Abuse", "Flooding Chat", "Advertising", "Toxic Behaviour", "Other");
        }
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        String executor = "Console";
        if (sender instanceof Player) {
            IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
            if (!(p.getRank().isStaff())) {
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
        Bukkit.getScheduler().runTaskAsynchronously(BlitzSG.getInstance(), () -> {
            try (Connection connection = BlitzSG.getInstance().getDb().getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO `mutes` (`uuid`, `reason`, `expires`, `executor`) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, target.getUniqueId().toString());
                statement.setString(2, finalReason);
                statement.setLong(3, futureTime);
                statement.setString(4, finalExecutor);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(target.getUniqueId());
        p.setMute(new PlayerMute(futureTime, finalReason));

        target.sendMessage(ChatUtil.color("&7&m-------------------------------"));
        target.sendMessage(ChatUtil.color("&cYou have been muted for " + duration + " for " + reason + "."));
        target.sendMessage(ChatUtil.color("&7&m-------------------------------"));

    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatUtil.color("&cUsage: /mute <player> <duration> [<reason>]"));
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