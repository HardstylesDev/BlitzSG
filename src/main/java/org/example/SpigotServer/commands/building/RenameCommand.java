package org.example.SpigotServer.commands.building;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.SpigotServer.utils.sendColor;

public class RenameCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        Player p = (Player) sender;


        if(args.length == 0){
            p.sendMessage(sendColor.format("&8[&3Noctas&8] &cProvide a name!"));
            return true;
        }

        String message = "";
        for (String part : args) {
            if (message != "") message += " ";
            message += part;
        }
        ItemStack tool = new ItemStack(p.getItemInHand());
        ItemMeta toolMeta = tool.getItemMeta();
        toolMeta.setDisplayName(sendColor.format(message));
        tool.setItemMeta(toolMeta);
        p.setItemInHand(tool);
        return true;

    }

}
