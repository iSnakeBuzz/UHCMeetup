package com.isnakebuzz.meetup.d;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import net.badlion.worldborder.WorldFillerTaskCompleteEvent;

public class WorldGenerator extends BukkitRunnable implements Listener {

    private World world;

    private boolean isGenerating = false;

    public WorldGenerator() {
        // Fail-safe
        this.deleteDirectory(new File(Main.world));

        // Register listener
        Main.plugin.getServer().getPluginManager().registerEvents(this, Main.plugin);
    }

    @EventHandler
    public void onWorldFinishGeneration(WorldFillerTaskCompleteEvent event) {
        this.addGlassBorder();
    }

    @Override
    public void run() {
        // Don't let one start if one is generating already
        if (this.isGenerating) {
                return;
        }

        this.generateNewWorld();
    }

    private void generateNewWorld() {
        // Just to avoid any weird bukkit race conditions
        this.isGenerating = true;

        WorldCreator worldCreator = new WorldCreator(Main.world);

        // Another fail safe
        try {
                this.world = Main.plugin.getServer().createWorld(worldCreator);
        } catch (Exception e) {
                Bukkit.getLogger().info("World NPE when trying to generate map.");
                Bukkit.getServer().unloadWorld(this.world, false);

                this.deleteDirectory(new File(Main.world));

                this.isGenerating = false;
                return;
        }

        int waterCount = 0;

        Bukkit.getLogger().info("Loaded a new world.");
        boolean flag = false;
        for (int i = -Border.walls; i <= Border.walls; ++i) {
                boolean isInvalid = false;
                for (int j = -Border.walls; j <= Border.walls; j++) {
                        boolean isCenter = i >= -100 && i <= 100 && j >= -100 && j <= 100;
                        if (isCenter) {
                                Block block = this.world.getHighestBlockAt(i, j).getLocation().add(0, -1, 0).getBlock();
                                if (block.getType() == Material.STATIONARY_WATER || block.getType() == Material.WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
                                        ++waterCount;
                                }
                        }

                        if (waterCount >= 1000) {
                                Bukkit.getLogger().info("Invalid center, too much water/lava.");
                                isInvalid = true;
                                break;
                        }
                }

                if (isInvalid) {
                        flag = true;
                        Bukkit.getLogger().info("Invalid biome2");
                        break;
                }
        }
        
        if (flag) {
            Bukkit.getLogger().info("Failed to find a good seed (" + this.world.getSeed() + ").");
            Bukkit.getServer().unloadWorld(this.world, false);

            this.deleteDirectory(new File(Main.world));

            this.isGenerating = false;
            return;
        } else {
            Bukkit.getLogger().info("Found a good seed (" + this.world.getSeed() + ").");
            this.cancel();
        }

        // Create Lock
        File lock = new File(Main.world + "/gen.lock");
        try {
            lock.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
            return;
        }

        this.isGenerating = true;

        Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
        Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + Main.world + " set " + 125 + " 0 0");
        Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + Main.world + " fill 5000");
        Main.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb fill confirm");
        Border.buildWalls(Border.walls, Material.BEDROCK, 6, world);
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0) + 10, 0);
    }

	private boolean deleteDirectory(File path) {
            if (path.exists()) {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                            deleteDirectory(files[i]);
                    } else {
                            files[i].delete();
                    }
                }
            }
            return (path.delete());
	}

    private void addGlassBorder() {
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
                        for (int i = y; i < 200; i++) {
                            Block block = this.world.getBlockAt(x, i, z);

                            block.setType(Material.GLASS);
                            block.setData((byte) 0);
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
                                for (int i = y; i < 200; i++) {
                                    Block block = this.world.getBlockAt(x, i, z);

                                    block.setType(Material.GLASS);
                                    block.setData((byte) 0);
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
                            for (int i = y; i < 200; i++) {
                                Block block = this.world.getBlockAt(x, i, z);

                                block.setType(Material.GLASS);
                                block.setData((byte) 0);
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
                    for (int i = y; i < 200; i++) {
                        Block block = this.world.getBlockAt(x, i, z);

                        block.setType(Material.GLASS);
                        block.setData((byte) 0);
                    }
                }

                if (this.counter >= Border.walls) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 1L);
    }


}
