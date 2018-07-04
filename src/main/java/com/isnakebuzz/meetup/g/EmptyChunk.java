package com.isnakebuzz.meetup.g;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class EmptyChunk extends ChunkGenerator {

    private static final EmptyChunk instance = new EmptyChunk();

    private final byte[] buf = new byte[0x10000];

    public static EmptyChunk getInstance() {
        return EmptyChunk.instance;
    }

    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        return buf;
    }
}
