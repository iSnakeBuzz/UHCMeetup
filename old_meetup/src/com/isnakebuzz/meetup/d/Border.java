package com.isnakebuzz.meetup.d;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.e.API;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class Border {
    
    public static int walls = 125;
    
    public static void teleport(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 1;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static void teleportFIRST(final Player p, int locs) {
        final Random r = new Random();
        final int x = r.nextInt(locs);
        final int z = -r.nextInt(locs);
        final int y = p.getWorld().getHighestBlockYAt(x, z) + 1;
        final World world = Bukkit.getWorld(Main.world);
        final Location randomLoc = new Location(world, (double)x, (double)y, (double)z);
        p.teleport(randomLoc);
    }
    
    public static void checkBorder(Player p){
        int i = Border.walls;
        World localWorld = p.getWorld();
        if (localWorld.getEnvironment().equals(World.Environment.NETHER)) {
            return;
        }
        if (p.getLocation().getBlockX() > i){
            p.teleport(new Location(localWorld, i - 2, p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
        if (p.getLocation().getBlockZ() > i){
            p.teleport(new Location(localWorld, p.getLocation().getBlockX(), p.getLocation().getBlockY(), i - 2, p.getLocation().getYaw(), p.getLocation().getPitch()));
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
        if (p.getLocation().getBlockX() < -(i+1)){
            p.teleport(new Location(localWorld, -i + 2, p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
        if (p.getLocation().getBlockZ() < -(i+1)){
            p.teleport(new Location(localWorld, p.getLocation().getBlockX(), p.getLocation().getBlockY(), -i + 2, p.getLocation().getYaw(), p.getLocation().getPitch()));            
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
    }
    
    public static void randomTpOnBorder(Player p){
        int i = Border.walls;
        World localWorld = p.getWorld();
        if (localWorld.getEnvironment().equals(World.Environment.NETHER)) {
            return;
        }
        if (p.getLocation().getBlockX() > i){
            Border.teleport(p, i-5);
        }
        if (p.getLocation().getBlockZ() > i){
        	Border.teleport(p, i-5);
        }
        if (p.getLocation().getBlockX() < -(i+1)){
        	Border.teleport(p, i-5);
        }
        if (p.getLocation().getBlockZ() < -(i+1)){
        	Border.teleport(p, i-5);
        }
    }
    
    
    public static void buildWalls(final int current, final Material type, final int altura, final World world) {
        final Location location = new Location(world, 0.0, 59.0, 0.0);
        if ("1.8.8".equals(API.getVersion())){
            for (Player all : Bukkit.getOnlinePlayers()){
            if (ProtocolSupportAPI.getProtocolVersion(all) == ProtocolVersion.MINECRAFT_1_8 ||ProtocolSupportAPI.getProtocolVersion(all) == ProtocolVersion.MINECRAFT_FUTURE){
                    if (!API.NoBorder.contains(all)){
                        Main.plugin.versionHandler.setWorldBoder18(all);
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
                            
                            if (location2.getBlock().getType() == Material.LEAVES 
                            	|| location2.getBlock().getType() == Material.LEAVES_2 
                            	|| location2.getBlock().getType() == Material.WATER
                                || location2.getBlock().getType() == Material.LAVA ) {
                            	
                                location2.getBlock().setType(type);
                            }else{
                                location2.getBlock().setType(type);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void newBorderGenerator(int altura) {
        new BukkitRunnable() {

            private World world = Bukkit.getServer().getWorld(Main.world);

            private int counter = -Border.walls - 1;
            private boolean phase1 = false;
            private boolean phase2 = false;
            private boolean phase3 = false;

			@Override
            public void run() {
                if (!this.phase1) {
                    int maxCounter = this.counter + 500;
                    int x = -Border.walls - 1;
                    for (int z = this.counter; z <= Border.walls && this.counter <= maxCounter; z++, this.counter++) {
                        Block highestBlock = this.world.getHighestBlockAt(x, z);

                        // Ignore non-solid blocks
                        while (!highestBlock.getType().isSolid() || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                            highestBlock = highestBlock.getRelative(0, -1, 0);
                        }

                        int y = highestBlock.getY() + 1;
                        int hight = highestBlock.getY() + altura;
                        for (int i = y; i < hight; i++) {
                            Block block = this.world.getBlockAt(x, i, z);

                            block.setType(Material.BEDROCK);
                        }
                    }

                    if (this.counter >= Border.walls) {
                        this.counter = -Border.walls - 1;
                        this.phase1 = true;
                    }

                    return;
                }

                if (!this.phase2) {
                        int maxCounter = this.counter + 500;
                        int x = Border.walls;
                        for (int z = this.counter; z <= Border.walls && this.counter <= maxCounter; z++, this.counter++) {
                                Block highestBlock = this.world.getHighestBlockAt(x, z);

                                // Ignore non-solid blocks
                                while (!highestBlock.getType().isSolid()
                                                || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                                    highestBlock = highestBlock.getRelative(0, -1, 0);
                                }

                                int y = highestBlock.getY() + 1;
                                int hight = highestBlock.getY() + altura;
                                for (int i = y; i < hight; i++) {
                                    Block block = this.world.getBlockAt(x, i, z);

                                    block.setType(Material.BEDROCK);
                                }
                        }

                        if (this.counter >= Border.walls) {
                            this.counter = -Border.walls - 1;
                            this.phase2 = true;
                        }

                        return;
                }

                if (!this.phase3) {
                        int maxCounter = this.counter + 500;
                        int z = -Border.walls - 1;
                        for (int x = this.counter; x <= Border.walls && this.counter <= maxCounter; x++, this.counter++) {
                            if (x == Border.walls || x == -Border.walls - 1) {
                                continue;
                            }

                            Block highestBlock = this.world.getHighestBlockAt(x, z);

                            // Ignore non-solid blocks
                            while (!highestBlock.getType().isSolid()
                                            || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                                highestBlock = highestBlock.getRelative(0, -1, 0);
                            }

                            int y = highestBlock.getY() + 1;
                            int hight = highestBlock.getY() + altura;
                            for (int i = y; i < hight; i++) {
                                Block block = this.world.getBlockAt(x, i, z);

                                block.setType(Material.BEDROCK);
                            }
                    }

                    if (this.counter >= Border.walls) {
                        this.counter = -Border.walls - 1;
                        this.phase3 = true;
                    }

                    return;
                }


                int maxCounter = this.counter + 500;
                int z = Border.walls;
                for (int x = this.counter; x <= Border.walls && this.counter <= maxCounter; x++, this.counter++) {
                    if (x == Border.walls || x == -Border.walls - 1) {
                        continue;
                    }

                    Block highestBlock = this.world.getHighestBlockAt(x, z);

                    // Ignore non-solid blocks
                    while (!highestBlock.getType().isSolid()
                                    || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                        highestBlock = highestBlock.getRelative(0, -1, 0);
                    }

                    int y = highestBlock.getY() + 1;
                    int hight = highestBlock.getY() + altura;
                    for (int i = y; i < hight; i++) {
                        Block block = this.world.getBlockAt(x, i, z);

                        block.setType(Material.BEDROCK);
                    }
                }

                if (this.counter >= Border.walls) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 1L);
    }
    
    @SuppressWarnings("deprecation")
	public static void updateBlocks(Player p) {
		World w = Bukkit.getWorld(Main.world);
		if (w != null) {
			for (Block b : getWallBlocks2(w, p)) {
				if (distanceSquared(p.getLocation().getZ(), p.getLocation().getX(), b.getLocation().getZ(), b.getLocation().getX()) <= 10) {
					if (b.getType() == Material.AIR || b.getType() == Material.STATIONARY_WATER || b.getType() == Material.WATER || b.getType() == Material.STATIONARY_LAVA
							|| b.getType() == Material.LAVA) {
						p.sendBlockChange(b.getLocation(), Material.STAINED_GLASS, (byte)14);
					}
				}
			}
			
			for (Block b : getWallsToRemove(w, p)) {
				if (distanceSquared(p.getLocation().getZ(), p.getLocation().getX(), b.getLocation().getZ(), b.getLocation().getX()) <= 10) {
				}else {
					if (distanceSquared(p.getLocation().getZ(), p.getLocation().getX(), b.getLocation().getZ(), b.getLocation().getX()) >= 11
							&& distanceSquared(p.getLocation().getZ(), p.getLocation().getX(), b.getLocation().getZ(), b.getLocation().getX()) <= 16) {
						if (b.getType() == Material.AIR || b.getType() == Material.STATIONARY_WATER || b.getType() == Material.WATER || b.getType() == Material.STATIONARY_LAVA
								|| b.getType() == Material.LAVA) {
							p.sendBlockChange(b.getLocation(), Material.AIR, (byte)0);
						}
					}
					
					int test1 = (int) p.getLocation().getY() + 5;
					int test2 = (int) p.getLocation().getY() - 5;
					
					if (distanceYY(p.getLocation().getY(), b.getY()) > test2 && distanceYY(p.getLocation().getY(), b.getY()) < test1) {
						
					}
				}
			}
			
		}
		
	}
    
    static int minX = (-walls)-1;
    static int maxX = walls;
	
    static int minZ = (-walls)-1;
    static int maxZ = walls;
	
	public static double distanceSquared(final double px, final double pz, final double n3, final double n4) {
        final double n5 = Math.max(px, n3) - Math.min(px, n3);
        final double n6 = Math.max(pz, n4) - Math.min(pz, n4);
        double test = n5 + n6;
        return test;
    }
	
	public static double distanceYY(final double y1,final double y2) {
        final double n5 = Math.max(y1, y2) - Math.min(y1, y2);
        return n5;
    }
	
	public static Set<Block> getWallsToRemove(final World world, Player p) {
        final HashSet<Block> set = new HashSet<Block>();
        
        int minY = (int)p.getLocation().getY() - 15;
        int maxY = (int)p.getLocation().getY() + 15;
        
        int minX = (-walls)-1;
        int maxX = walls;
    	
        int minZ = (-walls)-1;
        int maxZ = walls;
        
        
        for (int i = minX; i <= maxX; ++i) {
            for (int j = minY; j <= maxY; ++j) {
                set.add(world.getBlockAt(i, j, minZ));
                set.add(world.getBlockAt(i, j, maxZ));
            }
        }
        for (int k = minZ; k <= maxZ; ++k) {
            for (int l = minY; l <= maxY; ++l) {
                set.add(world.getBlockAt(minX, l, k));
                set.add(world.getBlockAt(maxX, l, k));
            }
        }
        return set;
    }
	
	public static Set<Block> getWallBlocks2(final World world, Player p) {
        final HashSet<Block> set = new HashSet<Block>();
        
        int minY = (int)p.getLocation().getY() - 3;
        int maxY = (int)p.getLocation().getY() + 3;
        
        int minX = (-walls)-1;
        int maxX = walls;
    	
        int minZ = (-walls)-1;
        int maxZ = walls;
        
        
        for (int i = minX; i <= maxX; ++i) {
            for (int j = minY; j <= maxY; ++j) {
                set.add(world.getBlockAt(i, j, minZ));
                set.add(world.getBlockAt(i, j, maxZ));
            }
        }
        for (int k = minZ; k <= maxZ; ++k) {
            for (int l = minY; l <= maxY; ++l) {
                set.add(world.getBlockAt(minX, l, k));
                set.add(world.getBlockAt(maxX, l, k));
            }
        }
        return set;
    }
    
    @SuppressWarnings("deprecation")
	public static void sendBlockChange(final Player player, final Block block, final Material material, final byte b) {
        player.sendBlockChange(block.getLocation(), material, b);
    }
    
}
