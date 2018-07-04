package com.isnakebuzz.meetup.i;

import com.isnakebuzz.meetup.a.VersionHandler;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import net.minecraft.server.v1_7_R4.BiomeBase;
import net.minecraft.server.v1_7_R4.ChunkProviderServer;
import net.minecraft.server.v1_7_R4.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R4.EntityHorse;
import net.minecraft.server.v1_7_R4.EntityLightning;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IScoreboardCriteria;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_7_R4.Scoreboard;
import net.minecraft.server.v1_7_R4.ScoreboardObjective;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.EntityCreeper;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardScore;

import java.lang.reflect.*;
import org.bukkit.craftbukkit.v1_7_R4.*;
import net.minecraft.server.v1_7_R4.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;

public class NMS_1_7_R3 implements VersionHandler {
	
	private HashMap<Player, Integer> horses = new HashMap<>();
	
	@Override
	public void SitPlayer(Player p) {
        
		Location l = p.getLocation();
        EntityHorse horse = new EntityHorse(((CraftWorld)l.getWorld()).getHandle());
        
        EntityBat pig = new EntityBat(((CraftWorld)l.getWorld()).getHandle());
        
        pig.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
        pig.setInvisible(true);
        pig.setHealth(6);
        
        horse.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
        horse.setInvisible(true);        
        
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(pig);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        
        horses.put(p, pig.getId());
        
        PacketPlayOutAttachEntity sit = new PacketPlayOutAttachEntity(0, (net.minecraft.server.v1_7_R4.Entity)((CraftPlayer) p).getHandle(), pig);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(sit);
		
    }
	
	@Override
	public void UnSitPlayer(Player p) {
		if (horses.get(p) != null) {
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(horses.get(p));
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
    }
	
    @Override
    public void SwapBiomes() {
        Bukkit.getConsoleSender().sendMessage("§aInjectando biomas 1.7.10...");
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

	@Override
	public void setWorldBoder18(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorldBoder182(Player p, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendLighting(Player p, Player k) {
		Location kl = k.getLocation();
		Location pl = p.getLocation();
		pl.getWorld().strikeLightningEffect(pl);
	}
}
