package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import sun.java2d.opengl.WGLSurfaceData;

import java.io.File;
import java.io.IOException;

public class WorldGen extends BukkitRunnable {

    private Main plugin;
    private String worldname;
    private World world;
    private boolean isGenerating = false;
    private int cx;
    private int cz;

    public WorldGen(Main plugin, String worldname) {
        this.plugin = plugin;
        this.worldname = worldname;
    }

    @Override
    public void run() {
        if (this.isGenerating) {
            return;
        }

        this.generateNewWorld();
    }

    private void generateNewWorld() {
        FileConfiguration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        plugin.getBorderManager().setBorder(config.getInt("Border.Default"), false, worldname);
        this.isGenerating = true;
        this.deleteDirectory(new File(worldname));
        WorldCreator worldCreator = new WorldCreator(worldname);
        worldCreator.generateStructures(false);

        try {
            this.world = plugin.getServer().createWorld(worldCreator);
        } catch (Exception e) {
            Bukkit.getLogger().info("World NPE when trying to generate map.");
            Bukkit.getServer().unloadWorld(this.world, false);
            this.deleteDirectory(new File(worldname));
            this.isGenerating = false;
            return;
        }

        int waterCount = 0;

        Bukkit.getLogger().info("Loaded a new world.");
        boolean flag = false;
        for (int i = -plugin.getBorderManager().getBorder(); i <= plugin.getBorderManager().getBorder(); ++i) {
            boolean isInvalid = false;
            for (int j = -plugin.getBorderManager().getBorder(); j <= plugin.getBorderManager().getBorder(); j++) {
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

            this.deleteDirectory(new File(this.worldname));

            this.isGenerating = false;
            return;
        } else {
            Bukkit.getLogger().info("Found a good seed (" + this.world.getSeed() + ").");
            this.cancel();
        }

        // Create Lock
        File lock = new File(this.worldname + "/gen.lock");
        try {
            lock.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
            return;
        }

        this.isGenerating = true;
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "worldborder shape square");
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "worldborder " + this.worldname + " set " + plugin.getBorderManager().getBorder() + " 0 0");
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "worldborder " + this.worldname + " fill " + plugin.getBorderManager().getBorder());
        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "worldborder fill confirm");

        final int n = plugin.getBorderManager().getBorder() / 16 + 1;
        this.cx = n;
        this.cz = n;
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0) + 10, 0);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setTime(6000);
        plugin.getLobbyManager().setWorldLobby(world.getSpawnLocation());
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

}
