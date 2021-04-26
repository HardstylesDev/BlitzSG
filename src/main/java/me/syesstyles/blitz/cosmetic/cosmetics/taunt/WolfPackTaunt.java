package me.syesstyles.blitz.cosmetic.cosmetics.taunt;

import me.syesstyles.blitz.BlitzSG;
import me.syesstyles.blitz.cosmetic.Taunt;
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
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class WolfPackTaunt extends Taunt {
    public WolfPackTaunt() {
        super("wolfpack", "Wolf Pack", "While taunting, 2 wolfs sit by your side while you howl like a wolf.", BlitzSG.getInstance().getRankManager().getRankByName("VIP+"), new ItemStack(Material.BONE, 1));
    }


    public void go(Player player) {
        villagerShenanigans(player.getLocation());
        player.getWorld().getPlayers().forEach(player1 -> player1.playSound(player.getLocation(), Sound.WOLF_HOWL, (float) 1, (float) 1));

    }


    private void villagerShenanigans(Location location) {
        ArrayList<Entity> l = new ArrayList<>();
        for (int t = 0; t < 2; ++t) {
            Entity wolf = location.getWorld().spawnEntity(location.clone().add((Math.random() * 2) - 1, 0, (Math.random() * 2) - 1), EntityType.WOLF);
            this.addNBT((CraftEntity) wolf, "{Attributes:[{Name:generic.movementSpeed,Base:0}],Invulnerable:1,Silent:1,NoAI:1}");
            wolf.setVelocity(new Vector((Math.random() * 2) - 1, 0, (Math.random() * 2) - 1));
            ((Wolf)wolf).setTamed(true);

            ((Wolf) wolf).setSitting(true);
            l.add(wolf);
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(BlitzSG.getInstance(), () -> {
            for (Entity entity : l) {
                entity.remove();
            }
        }, 30);
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
