package me.syesstyles.blitz.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.syesstyles.blitz.commands.subcommands.AddSpawnCommand;
import me.syesstyles.blitz.commands.subcommands.ArenasCommand;
import me.syesstyles.blitz.commands.subcommands.CreateCommand;
import me.syesstyles.blitz.commands.subcommands.DeleteCommand;
import me.syesstyles.blitz.commands.subcommands.EditCommand;
import me.syesstyles.blitz.commands.subcommands.JoinCommand;
import me.syesstyles.blitz.commands.subcommands.LeaveCommand;
import me.syesstyles.blitz.commands.subcommands.SaveCommand;
import me.syesstyles.blitz.commands.subcommands.SetLobbyCommand;
import me.syesstyles.blitz.commands.subcommands.SubCommand;
import me.syesstyles.blitz.commands.subcommands.WandCommand;
import me.syesstyles.blitz.commands.subcommands.WarpCommand;

public class CommandHandler implements CommandExecutor {
	
	private HashMap<String, SubCommand> commands;
	
	public CommandHandler() {
		this.commands = new HashMap<String, SubCommand>();
		registerCommands();
	}
	
	public void registerCommands() {
		commands.put("create", new CreateCommand());
		commands.put("save", new SaveCommand());
		commands.put("addspawn", new AddSpawnCommand());
		commands.put("delete", new DeleteCommand());
		commands.put("edit", new EditCommand());
		commands.put("wand", new WandCommand());
		commands.put("join", new JoinCommand());
		commands.put("leave", new LeaveCommand());
		commands.put("arenas", new ArenasCommand());
		commands.put("warp", new WarpCommand());
		commands.put("setlobby", new SetLobbyCommand());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		if(args.length == 0 || (args.length >= 1 && args[0].equalsIgnoreCase("help"))) {
			sendHelpMenu(sender);
			return true;
		}
		for(String str : commands.keySet())
			if(args[0].equalsIgnoreCase(str)) {
				commands.get(str).runCommand((Player) sender, args);
				break;
			}
		return true;
	}
	
	private void sendHelpMenu(CommandSender sender) {
		sender.sendMessage("§7§m------------------------------");
		sender.sendMessage("               §6>> §e§lSpeedUHC §6<<      ");
		sender.sendMessage("");
		for(SubCommand sc : this.commands.values())
			if(sender.hasPermission(sc.getPermission()))
				sender.sendMessage(sc.getHelp());
		sender.sendMessage("§7§m------------------------------");
	}

}
