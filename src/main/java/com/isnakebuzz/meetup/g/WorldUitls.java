package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;

public class WorldUitls {

    private Main plugin;

    public WorldUitls(Main plugin) {
        this.plugin = plugin;
    }

    public void swapBiomes() {
        // Swap all biomes with other biomes
        Bukkit.getServer().setBiomeBase(Biome.OCEAN, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.RIVER, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.BEACH, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.JUNGLE, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.JUNGLE_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.JUNGLE_EDGE, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.DEEP_OCEAN, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.SAVANNA_PLATEAU, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.DESERT, Biome.PLAINS, 0);

        // Weird sub-biomes
        Bukkit.getServer().setBiomeBase(Biome.JUNGLE, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.JUNGLE_EDGE, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.SAVANNA, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.SAVANNA_PLATEAU, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.DESERT_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.DESERT_MOUNTAINS, Biome.PLAINS, 0);

        // LIMITED threshold biomes
        Bukkit.getServer().setBiomeBase(Biome.STONE_BEACH, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.FOREST, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.FOREST_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.BIRCH_FOREST, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.BIRCH_FOREST_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.BIRCH_FOREST_HILLS, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.BIRCH_FOREST_HILLS_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.BIRCH_FOREST_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.TAIGA, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.TAIGA, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.TAIGA_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.TAIGA_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.ICE_PLAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.ICE_PLAINS, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.ICE_PLAINS_SPIKES, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MEGA_SPRUCE_TAIGA, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MEGA_SPRUCE_TAIGA_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MEGA_TAIGA, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MEGA_TAIGA, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.MEGA_TAIGA_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.COLD_BEACH, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.COLD_TAIGA, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.COLD_TAIGA, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.COLD_TAIGA_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.COLD_TAIGA_MOUNTAINS, Biome.PLAINS, 0);

        // DISALLOWED threshold biomes
        Bukkit.getServer().setBiomeBase(Biome.FLOWER_FOREST, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.SUNFLOWER_PLAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.ROOFED_FOREST_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.ROOFED_FOREST, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MESA, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MESA, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.MESA_PLATEAU, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MESA_PLATEAU, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.MESA_BRYCE, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MESA_PLATEAU_FOREST, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MESA_PLATEAU_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.MESA_PLATEAU_FOREST_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.EXTREME_HILLS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.EXTREME_HILLS, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.EXTREME_HILLS_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.EXTREME_HILLS_PLUS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.EXTREME_HILLS_PLUS, Biome.PLAINS, 128);
        Bukkit.getServer().setBiomeBase(Biome.EXTREME_HILLS_PLUS_MOUNTAINS, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.FROZEN_OCEAN, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.FROZEN_RIVER, Biome.PLAINS, 0);
        Bukkit.getServer().setBiomeBase(Biome.ICE_MOUNTAINS, Biome.PLAINS, 0);
    }
}
