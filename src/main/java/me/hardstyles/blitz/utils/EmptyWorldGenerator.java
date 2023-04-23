package me.hardstyles.blitz.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.Random;

public class EmptyWorldGenerator extends ChunkGenerator {
    @Override
    public byte[][] generateBlockSections(World world, Random random, int cx, int cz, ChunkGenerator.BiomeGrid biomes)
    {
        byte air = (byte) 0;
        byte[][] chunk = new byte[8][4096];
        for (int n = 0; n < 8; ++n)
            Arrays.fill(chunk[n], air);

        if (cx == 0 && cz == 0)
            chunk[4][0] = (byte) 1;

        return chunk;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 100, 0);
    }
}
