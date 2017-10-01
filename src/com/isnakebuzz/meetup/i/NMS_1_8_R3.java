package com.isnakebuzz.meetup.i;

import com.isnakebuzz.meetup.a.VersionHandler;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.Bukkit;

public class NMS_1_8_R3 implements VersionHandler{

    @Override
    public void SwapBiomes() {
        Bukkit.getConsoleSender().sendMessage("§aInjectando biomas 1.8.8...");
        Field biomesField = null;
        try{
          biomesField = BiomeBase.class.getDeclaredField("biomes");
          biomesField.setAccessible(true);
          if ((biomesField.get(null) instanceof BiomeBase[])){
              
            BiomeBase[] biomes = (BiomeBase[])biomesField.get(null);
            biomes[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.OCEAN.id] = BiomeBase.DESERT;
            biomes[BiomeBase.JUNGLE.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.ICE_PLAINS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.JUNGLE_EDGE.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.JUNGLE_HILLS.id] = BiomeBase.DESERT;
            biomes[BiomeBase.COLD_TAIGA.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.ICE_MOUNTAINS.id] = BiomeBase.DESERT;
            biomes[BiomeBase.FROZEN_RIVER.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.EXTREME_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.EXTREME_HILLS_PLUS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.SWAMPLAND.id] = BiomeBase.DESERT;
            biomes[BiomeBase.TAIGA_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.TAIGA.id] = BiomeBase.DESERT;
            biomes[BiomeBase.RIVER.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.SWAMPLAND.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.BEACH.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.ROOFED_FOREST.id] = BiomeBase.DESERT;
            biomes[BiomeBase.COLD_TAIGA_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.COLD_TAIGA.id] = BiomeBase.DESERT;
            biomes[BiomeBase.BIRCH_FOREST.id] = BiomeBase.DESERT;
            biomes[BiomeBase.BIRCH_FOREST_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.FOREST_HILLS.id] = BiomeBase.DESERT;
            biomes[BiomeBase.SAVANNA.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.SAVANNA_PLATEAU.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.FOREST.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.FOREST_HILLS.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.MEGA_TAIGA.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.MEGA_TAIGA_HILLS.id] = BiomeBase.PLAINS;            
            biomes[BiomeBase.MUSHROOM_ISLAND.id] = BiomeBase.PLAINS;
            biomes[BiomeBase.MUSHROOM_SHORE.id] = BiomeBase.PLAINS;
            
            biomesField.set(null, biomes);
          }
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException localException) {
        }
        Bukkit.getConsoleSender().sendMessage("§aBiomas injectados correctamente");
    }
    
}
