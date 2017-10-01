package com.isnakebuzz.meetup.d;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.e.API;
import com.isnakebuzz.meetup.i.NMS_1_7_R3;
import com.isnakebuzz.meetup.i.NMS_1_8_R3;
import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder.EnumWorldBorderAction;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class Border {
    
    public static int walls;
    
    public static void teleport(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 1;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static void buildWalls(final int current, final Material type, final int altura, final World world) {
        final Location location = new Location(world, 0.0, 59.0, 0.0);
        if ("1.8.8".equals(API.getVersion())){
            for (Player all : Bukkit.getOnlinePlayers()){
            if (ProtocolSupportAPI.getProtocolVersion(all) == ProtocolVersion.MINECRAFT_1_8 ||ProtocolSupportAPI.getProtocolVersion(all) == ProtocolVersion.MINECRAFT_FUTURE){
                    if (!API.NoBorder.contains(all)){
                        setWorldBoder18(all);
                    }
                }
            }
        }
        for (int i = altura; i < altura + altura; ++i) {
            for (int j = location.getBlockX() - current-1; j <= location.getBlockX() + current; ++j) {
                for (int k = 58; k <= 58; ++k) {
                    for (int l = location.getBlockZ() - current-1; l <= location.getBlockZ() + current; ++l) {
                        if (j == location.getBlockX() - current-1 || j == location.getBlockX() + current || l == location.getBlockZ() - current-1 || l == location.getBlockZ() + current) {
                            final Location location2 = new Location(world, (double)j, (double)k, (double)l);
                            location2.setY((double)world.getHighestBlockYAt(location2));
                            location2.getBlock().setType(type);
                        }
                    }
                }
            }
        }
    }
    
    public static void setWorldBoder18(Player p){
        WorldBorder wb = new WorldBorder();
        wb.setSize(walls*2);
        final World world = Bukkit.getWorld(Main.world);
        Location l = world.getSpawnLocation();
        wb.setCenter(l.getX(),l.getZ());
        PacketPlayOutWorldBorder ppowb = new PacketPlayOutWorldBorder(wb, EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppowb);
    }
    
    public static void setWorldBoder182(Player p, int size){
        WorldBorder wb = new WorldBorder();
        wb.setSize(size*2);
        final World world = Bukkit.getWorld(Main.world);
        Location l = world.getSpawnLocation();
        wb.setCenter(l.getX(),l.getZ());
        PacketPlayOutWorldBorder ppowb = new PacketPlayOutWorldBorder(wb, EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppowb);
    }
    
    public static void checkBorder(Player paramPlayer){
        int i = Border.walls;
        World localWorld = paramPlayer.getWorld();
        if (localWorld.getEnvironment().equals(World.Environment.NETHER)) {
            return;
        }
        if (paramPlayer.getLocation().getBlockX() > i){

            paramPlayer.teleport(new Location(localWorld, i - 2, paramPlayer.getLocation().getBlockY(), paramPlayer.getLocation().getBlockZ()));
            if (paramPlayer.getLocation().getBlockY() < localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ())){
                paramPlayer.teleport(new Location(localWorld, paramPlayer.getLocation().getBlockX(), localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ()) + 2, paramPlayer.getLocation().getBlockZ()));
            }
        }
        if (paramPlayer.getLocation().getBlockZ() > i){
            paramPlayer.teleport(new Location(localWorld, paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockY(), i - 2));
            if (paramPlayer.getLocation().getBlockY() < localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ())){
                paramPlayer.teleport(new Location(localWorld, paramPlayer.getLocation().getBlockX(), localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ()) + 2, paramPlayer.getLocation().getBlockZ()));
            }
        }
        if (paramPlayer.getLocation().getBlockX() < -i){
            paramPlayer.teleport(new Location(localWorld, -i + 2, paramPlayer.getLocation().getBlockY(), paramPlayer.getLocation().getBlockZ()));
            if (paramPlayer.getLocation().getBlockY() < localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ())){
                paramPlayer.teleport(new Location(localWorld, paramPlayer.getLocation().getBlockX(), localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ()) + 2, paramPlayer.getLocation().getBlockZ()));
            }
        }
        if (paramPlayer.getLocation().getBlockZ() < -i){
            paramPlayer.teleport(new Location(localWorld, paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockY(), -i + 2));
            if (paramPlayer.getLocation().getBlockY() < localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ())){
                paramPlayer.teleport(new Location(localWorld, paramPlayer.getLocation().getBlockX(), localWorld.getHighestBlockYAt(paramPlayer.getLocation().getBlockX(), paramPlayer.getLocation().getBlockZ()) + 2, paramPlayer.getLocation().getBlockZ()));
            }
        }
    }
    
}
