package me.syesstyles.blitz.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

public class HorseCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean horseExists = false;
        for (Entity entity : Bukkit.getWorld("world").getEntities()) {
            if (entity instanceof Horse)
                horseExists = true;
        }
        if (horseExists) {
            sender.sendMessage(ChatColor.GREEN + "Our lobby horse still exists, no need to create a new one");
            return true;
        }

        World world = Bukkit.getWorld("world");
        Location location = new Location(world, -10.5, 106, 0.5);
        Horse horse = (Horse) Bukkit.getWorld("world").spawnEntity(location, EntityType.HORSE);
        horse.setVariant(Horse.Variant.HORSE);
        horse.setColor(Horse.Color.WHITE);
        horse.setStyle(Horse.Style.NONE);

        horse.setCustomName(ChatColor.WHITE + "Horsey 2.0 The Sequal");
        horse.setCustomNameVisible(true);
        horse.setTamed(true);
        horse.setAdult();
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        horse.setMaxHealth(30);
        horse.setJumpStrength(0.8);
        sender.sendMessage(ChatColor.GREEN + "Our beloved horsey has been teleported to the fountain!");
        return true;
    }
}
