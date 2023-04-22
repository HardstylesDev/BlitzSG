package me.hardstyles.blitz.cosmetic.cosmetics.taunt;

import me.hardstyles.blitz.BlitzSG;
import me.hardstyles.blitz.cosmetic.Taunt;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class BatDudeTaunt extends Taunt {
    public BatDudeTaunt() {
        super("batdude", "Bat Dude", "While taunting, 4 noisy bats fly around your head.", BlitzSG.getInstance().getRankManager().getRankByName("VIP+"), new ItemStack(Material.MONSTER_EGG, 1, (byte)65));
    }


    public void run(Player player) {
        villagerShenanigans(player.getLocation());
        for (int t = 0; t < 3; ++t) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getWorld().getPlayers().forEach(player1 -> player1.playSound(player.getLocation(), Sound.BAT_IDLE, (float) Math.random(), (float) Math.random()));
                }
            }.runTaskLater(BlitzSG.getInstance(), t * 8);
        }
    }


    private void villagerShenanigans(Location location) {
        ArrayList<Entity> l = new ArrayList<>();
        for (int t = 0; t < 3; ++t) {
            Entity bat = location.getWorld().spawnEntity(location.clone().add((Math.random() * 2) - 1, 2, (Math.random() * 2) - 1), EntityType.BAT);
            this.addNBT((CraftEntity) bat, "{Attributes:[{Name:generic.movementSpeed,Base:0}],Invulnerable:1,Silent:1}");

            bat.setVelocity(new Vector((Math.random() * 2) - 1, 0, (Math.random() * 2) - 1));
            l.add(bat);
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(BlitzSG.getInstance(), () -> {
            for (Entity entity : l) {
                entity.remove();
            }
        }, 30);

        for (int t = 0; t < 6; ++t) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(BlitzSG.getInstance(), () -> {
                for (Entity entity : l) {
                    entity.setVelocity(new Vector((Math.random() * 1) - .5, 0, (Math.random() * 1) - .5));
                }
            }, t * 5);
        }

    }

    private void addNBT(CraftEntity e, String value) {
        net.minecraft.server.v1_8_R3.Entity nms = (e).getHandle();
        NBTTagCompound nbt = new NBTTagCompound();
        nms.c(nbt);
        try {
            NBTTagCompound nbtv = MojangsonParser.parse(value);
            nbt.a(nbtv);
            nms.f(nbt);
        } catch (MojangsonParseException ex) {
            //boo hoo
        }
    }
}
