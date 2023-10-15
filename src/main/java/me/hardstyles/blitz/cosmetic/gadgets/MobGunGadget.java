package me.hardstyles.blitz.cosmetic.gadgets;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Gadget;
import me.hardstyles.blitz.util.ItemBuilder;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.HashSet;

public class MobGunGadget extends Gadget {

    public MobGunGadget() {
        super("Mob Gun", new ItemBuilder(Material.BLAZE_ROD).name("&aMob Gun &7(Right Click)").make(), "&7Right click to teleport!");
        setCooldown(10000);
    }

    @Override
    public void onUse(Player player) {
        Location loc = player.getLocation();
        String[] mobs = {"PIG", "COW", "SHEEP", "CHICKEN"};
        Entity entity = loc.getWorld().spawnEntity(loc, EntityType.valueOf(mobs[(int) (Math.random() * mobs.length)]));
        entity.setVelocity(loc.getDirection().multiply(2));
        BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
            Location pos = entity.getLocation();
            Firework firework = (Firework) pos.getWorld().spawnEntity(pos, EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(0);
            fireworkMeta.addEffect(FireworkEffect.builder().withColor(org.bukkit.Color.RED).with(FireworkEffect.Type.STAR).build());
            firework.setFireworkMeta(fireworkMeta);
            BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
                firework.detonate();
            }, 1);





            HashSet<Item> items = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                Location pos2 = pos.clone();
                pos2.setX(pos2.getX() + Math.random() * 2 - 1);
                pos2.setY(pos2.getY() + Math.random() * 2 - 1);
                pos2.setZ(pos2.getZ() + Math.random() * 2 - 1);
                items.add(pos2.getWorld().dropItemNaturally(pos2, new ItemBuilder(Material.BONE).make()));
                items.add(pos2.getWorld().dropItemNaturally(pos2, new ItemBuilder(Material.ROTTEN_FLESH).make()));
                items.add(pos2.getWorld().dropItemNaturally(pos2, new ItemBuilder(Material.REDSTONE).make()));
            }
            items.forEach(item -> item.setPickupDelay(100000));
            BlitzSG.getInstance().getServer().getScheduler().runTaskLater(BlitzSG.getInstance(), () -> {
                items.forEach(Entity::remove);
            }, 30);
            entity.remove();
        }, 30);
    }
}
