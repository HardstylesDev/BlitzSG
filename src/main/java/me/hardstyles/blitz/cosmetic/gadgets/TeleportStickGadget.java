package me.hardstyles.blitz.cosmetic.gadgets;

import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.util.ChatUtil;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Set;

public class TeleportStickGadget extends Gadget {
    public TeleportStickGadget() {
        super("Teleport Stick", new ItemBuilder(Material.STICK).name("&aTeleport Stick &7(Right Click)").make(), "&7Right click to teleport!");
    }

    @Override
    public void onUse(Player player) {
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();
        Block block = player.getTargetBlock((Set<Material>) null, 50);
        if (block == null) {
            player.sendMessage(ChatUtil.color("&cYou must be looking at a block!"));
            return;
        }
        Location loc = block.getLocation();
        loc.setY(loc.getY() + 1);
        loc.setPitch(pitch);
        loc.setYaw(yaw);
        player.teleport(loc);
        player.playSound(player.getLocation(), "mob.endermen.portal", 1, 1);

    }
}
