package org.example.SpigotServer.commands.building;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.example.SpigotServer.utils.SkullNBT;

import java.util.Arrays;

public class BuildToolsCommand
        implements CommandExecutor, Listener

{
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        Player p = (Player) sender;
        String redoItem = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTYzMzlmZjJlNTM0MmJhMThiZGM0OGE5OWNjYTY1ZDEyM2NlNzgxZDg3ODI3MmY5ZDk2NGVhZDNiOGFkMzcwIn19fQ==";
        String undoItem = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjg0ZjU5NzEzMWJiZTI1ZGMwNThhZjg4OGNiMjk4MzFmNzk1OTliYzY3Yzk1YzgwMjkyNWNlNGFmYmEzMzJmYyJ9fX0=";
        ItemStack redoItemItem = SkullNBT.getCustomTextureHead(redoItem);
        ItemStack undoItemItem = SkullNBT.getCustomTextureHead(undoItem);

        ItemMeta redoItemItemMeta = redoItemItem.getItemMeta();
        redoItemItemMeta.setDisplayName(ChatColor.GREEN + "WorldEdit Redo Last Operation");
        redoItemItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "RIGHT-CLICK to redo 1x", ChatColor.GRAY + "RIGHT-CLICK + SNEAK to redo 10x"));
        redoItemItem.setItemMeta(redoItemItemMeta);

        ItemMeta undoItemItemMeta = undoItemItem.getItemMeta();
        undoItemItemMeta.setDisplayName(ChatColor.RED + "WorldEdit Undo Last Operation");
        undoItemItemMeta.setLore(Arrays.asList(ChatColor.GRAY + "RIGHT-CLICK to undo 1x", ChatColor.GRAY + "RIGHT-CLICK + SNEAK to undo 10x"));
        undoItemItem.setItemMeta(undoItemItemMeta);


        ItemStack gunpowder = new ItemStack(Material.SULPHUR, 1);
        ItemStack arrow = new ItemStack(Material.ARROW, 1);

        ItemStack axe = new ItemStack(Material.WOOD_AXE, 1);
        ItemStack barrier = new ItemStack(Material.BARRIER, 1);

        ItemMeta gunpowderMeta = gunpowder.getItemMeta();
        gunpowderMeta.setDisplayName(ChatColor.YELLOW + "Voxel Generate Tool");
        gunpowder.setItemMeta(gunpowderMeta);

        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.setDisplayName(ChatColor.YELLOW + "Voxel Replace Tool");
        arrow.setItemMeta(arrowMeta);

        ItemMeta axeMeta = axe.getItemMeta();
        axeMeta.setDisplayName(ChatColor.YELLOW + "World Edit Wand");
        axe.setItemMeta(axeMeta);

        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName(ChatColor.GOLD + "Voxel Undo Tool");
        barrierMeta.setLore(Arrays.asList(ChatColor.GRAY + "RIGHT-CLICK to undo 1x", ChatColor.GRAY + "RIGHT-CLICK + SNEAK to undo 10x"));
        barrier.setItemMeta(barrierMeta);

        p.getInventory().setItem(0, axe);
        p.getInventory().setItem(3, gunpowder);
        p.getInventory().setItem(4, barrier);
        p.getInventory().setItem(5, arrow);
        p.getInventory().setItem(7, undoItemItem);
        p.getInventory().setItem(8, redoItemItem);
        return true;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event)
    {
        if(!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        Player p = event.getPlayer();
        if (p.getItemInHand().getType() == Material.BARRIER && p.getItemInHand().getItemMeta().getDisplayName().contains("Voxel Undo Tool"))
        {
            event.setCancelled(true);
            if(p.isSneaking()){
                p.chat("/u 10");
            }else{
                p.chat("/u");
            }
        }
        else if (p.getItemInHand().getType() == Material.SKULL_ITEM && p.getItemInHand().getItemMeta().getDisplayName().contains("Redo Last Operation"))
        {
            event.setCancelled(true);
            if(p.isSneaking()){
                p.chat("//redo 10");
            }else{
                p.chat("//redo");
            }
        }
        else if (p.getItemInHand().getType() == Material.SKULL_ITEM && p.getItemInHand().getItemMeta().getDisplayName().contains("Undo Last Operation"))
        {
            event.setCancelled(true);
            if(p.isSneaking()){
                p.chat("//undo 10");
            }else{
                p.chat("//undo");
            }
        }

    }
}

//getCustomTextureHead