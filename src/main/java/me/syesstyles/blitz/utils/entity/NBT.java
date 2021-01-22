package me.syesstyles.blitz.utils.entity;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class NBT {

    public void addNBT(CraftEntity e, String value) {
        Entity nms = (e).getHandle();
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
