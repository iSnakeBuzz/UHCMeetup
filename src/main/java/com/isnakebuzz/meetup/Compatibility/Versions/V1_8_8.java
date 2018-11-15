package com.isnakebuzz.meetup.Compatibility.Versions;

import com.isnakebuzz.meetup.Compatibility.PluginVersion;
import net.minecraft.server.v1_7_R4.*;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;

public class V1_8_8 implements PluginVersion {

    private HashMap<Player, Integer> sitted = new HashMap<>();

    @Override
    public void biomeSwapper() {
        Field biomesField = null;
        try {
            biomesField = BiomeBase.class.getDeclaredField("biomes");
            biomesField.setAccessible(true);
            if ((biomesField.get(null) instanceof BiomeBase[])) {
                BiomeBase[] biomes = (BiomeBase[]) biomesField.get(null);
                biomes[BiomeBase.OCEAN.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.DESERT.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.EXTREME_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.FOREST.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.TAIGA.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.SWAMPLAND.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.RIVER.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.HELL.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.SKY.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.FROZEN_OCEAN.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.FROZEN_RIVER.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.ICE_PLAINS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.ICE_MOUNTAINS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.MUSHROOM_ISLAND.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.MUSHROOM_SHORE.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.BEACH.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.DESERT_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.FOREST_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.TAIGA_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.SMALL_MOUNTAINS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.JUNGLE.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.JUNGLE_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.JUNGLE_EDGE.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.STONE_BEACH.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.COLD_BEACH.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.BIRCH_FOREST.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.BIRCH_FOREST_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.ROOFED_FOREST.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.COLD_TAIGA.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.COLD_TAIGA_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.MEGA_TAIGA.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.MEGA_TAIGA_HILLS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.EXTREME_HILLS_PLUS.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.SAVANNA.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.SAVANNA_PLATEAU.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.MESA.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.MESA_PLATEAU_F.id] = BiomeBase.PLAINS;
                biomes[BiomeBase.MESA_PLATEAU.id] = BiomeBase.PLAINS;
                biomesField.set(null, biomes);
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException localException) {
        }
    }

    @Override
    public void sitPlayer(Player p) {
        Location l = p.getLocation();
        EntityHorse horse = new EntityHorse(((CraftWorld) l.getWorld()).getHandle());

        EntityBat pig = new EntityBat(((CraftWorld) l.getWorld()).getHandle());

        pig.setLocation(l.getX(), l.getY() + 0.5, l.getZ(), 0, 0);
        pig.setInvisible(true);
        pig.setHealth(6);

        horse.setLocation(l.getX(), l.getY() + 0.5, l.getZ(), 0, 0);
        horse.setInvisible(true);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(pig);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        sitted.put(p, pig.getId());

        PacketPlayOutAttachEntity sit = new PacketPlayOutAttachEntity(0, ((CraftPlayer) p).getHandle(), pig);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sit);
    }

    @Override
    public void unsitPlayer(Player p) {
        if (sitted.get(p) != null) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(sitted.get(p));
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
