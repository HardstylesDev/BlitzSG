package me.hardstyles.blitz.punishments.commands.punishment;

import com.google.common.collect.ImmutableList;
import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.menu.MenuContainer;
import me.hardstyles.blitz.menu.MenuItem;
import me.hardstyles.blitz.player.IPlayer;
import me.hardstyles.blitz.command.Command;
import me.hardstyles.blitz.command.SubCommand;
import me.hardstyles.blitz.punishments.punishtype.PlayerBan;
import me.hardstyles.blitz.punishments.punishtype.PlayerMute;
import me.hardstyles.blitz.punishments.punishtype.PunishType;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PunishmentsCommand extends Command {
    private final List<SubCommand> subcommands = new ArrayList<>();

    public PunishmentsCommand() {
        super("punishments", ImmutableList.of("punishment", "punish"), null);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return ImmutableList.copyOf(Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new));
        } else if (args.length == 2) {
            return ImmutableList.of("mutes", "bans");
        }
        return ImmutableList.of();
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.color("&cYou must be a player to use this command!"));
            return;
        }

        IPlayer p = BlitzSG.getInstance().getIPlayerManager().getPlayer(((Player) sender).getUniqueId());
        if (!p.getRank().isStaff()) {
            sender.sendMessage(ChatUtil.color("&cYou do not have permission to use this command!"));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatUtil.color("&cUsage: /punishments <player> [<type>]"));
            return;
        }

        OfflinePlayer target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            target = Bukkit.getOfflinePlayer(args[0]).getPlayer();
        }
        if (target == null) {
            sender.sendMessage(ChatUtil.color("&cThat player does not exist!"));
            return;
        }

       openMenu((Player) sender, target);


    }

    private void openMenu(Player sender, OfflinePlayer target){
        MenuContainer gui = new MenuContainer("§8Punishments", 2);
        ArrayList<PunishType> mutes = BlitzSG.getInstance().getDb().getMutes(target.getUniqueId());
        ArrayList<PunishType> bans = BlitzSG.getInstance().getDb().getBans(target.getUniqueId());

        // first, we just put 1 item for bans and 1 for mutes

        ItemStack banIcon = new ItemBuilder(Material.GOLD_AXE).lore(ChatColor.RED + "Bans").make();
        ItemStack muteIcon = new ItemBuilder(Material.GOLD_AXE).lore(ChatColor.RED + "Mutes").make();
        final UUID uuid = target.getUniqueId();

        MenuItem banItem = new MenuItem(banIcon, event -> {
            openSection((Player) sender, gui, bans, uuid);
        });

        MenuItem muteItem = new MenuItem(muteIcon, event -> {
            openSection((Player) sender, gui, mutes, uuid);
        });

        ItemStack skull = new ItemBuilder(Material.SKULL_ITEM).name(ChatColor.RED + target.getName()).durability(3).skullOwner(target.getName()).make();
        MenuItem skullItem = new MenuItem(skull, event -> {
            sender.sendMessage(ChatUtil.color("&cThis feature is not yet implemented!"));
        });

        gui.setItem(2, 0, banItem);

        gui.setItem(4, 0, skullItem);

        gui.setItem(6, 0, muteItem);

        gui.show((HumanEntity) sender);
    }


    private void openSection(Player sender, MenuContainer prev, ArrayList<PunishType> list, UUID uuid) {
        String title = list.isEmpty() ? "No Punishments" : list.get(0).getNamePlural();

        MenuContainer muteMenu = new MenuContainer("§8" + title, 2);
        int index = 0;
        for (PunishType punishment : list) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add("&7Punished for &c" + punishment.getReason());
            lore.add("&7Active: " + (punishment.isActive() ? "&aYes" : "&cNo"));
            lore.add("&7Punished &c" + ChatUtil.formatPastDate(punishment.getStartTime()) + "&7 ago");
            long duration = punishment.getEndTime() - punishment.getStartTime() + System.currentTimeMillis();
            lore.add("&7Length: &c" + ChatUtil.formatDate(duration));
            lore.add("&7" + punishment.getPastTense() + " by &a" + punishment.getExecutor());
            System.out.println("index: " + index + " punishment: " + punishment.getReason() + " lore: " + lore);
            ItemStack item = new ItemBuilder(Material.GOLD_AXE).name("&c" + punishment.getReason()).lores(lore).make();

            MenuItem menuItem = new MenuItem(item, event2 -> {
                BlitzSG.getInstance().getDb().remove(uuid, punishment);
                sender.sendMessage(ChatUtil.color("&aSuccessfully removed punishment!"));
                openMenu(sender, Bukkit.getOfflinePlayer(uuid));
            });
            muteMenu.setItem(index, 0, menuItem);
            index++;
        }


        muteMenu.setItem(muteMenu.getBottomLeft(), new MenuItem(new ItemBuilder(Material.ARROW).name(ChatColor.GREEN + "Back").make(), event2 -> {
            prev.show((HumanEntity) sender);
        }));

        muteMenu.show((HumanEntity) sender);
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


}