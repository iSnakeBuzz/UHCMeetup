package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class BorderManager {

    private int border;
    private Main plugin;

    public BorderManager(Main plugin) {
        this.plugin = plugin;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border, boolean command, String worldname) {
        this.border = border;
        if (command) {
            plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "wb " + worldname + " set " + border + " spawn");
        }
    }

    private void teleport(final Player p, int border, String world_name) {
        Random r = new Random();
        int x = r.nextInt(border);
        int z = -r.nextInt(border);
        int y = p.getWorld().getHighestBlockYAt(x, z) + 1;
        World world = Bukkit.getWorld(world_name);
        Location randomLoc = new Location(world, (double) x, (double) y, (double) z);
        p.teleport(randomLoc);
    }

    public void checkBorder(Player p) {
        int i = this.border;
        World localWorld = p.getWorld();
        if (localWorld.getEnvironment().equals(World.Environment.NETHER)) {
            return;
        }
        if (p.getLocation().getBlockX() > i) {
            p.teleport(new Location(localWorld, i - 2, p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
        if (p.getLocation().getBlockZ() > i) {
            p.teleport(new Location(localWorld, p.getLocation().getBlockX(), p.getLocation().getBlockY(), i - 2, p.getLocation().getYaw(), p.getLocation().getPitch()));
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
        if (p.getLocation().getBlockX() < -(i + 1)) {
            p.teleport(new Location(localWorld, -i + 2, p.getLocation().getBlockY(), p.getLocation().getBlockZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
        if (p.getLocation().getBlockZ() < -(i + 1)) {
            p.teleport(new Location(localWorld, p.getLocation().getBlockX(), p.getLocation().getBlockY(), -i + 2, p.getLocation().getYaw(), p.getLocation().getPitch()));
            int y = localWorld.getHighestBlockYAt(p.getLocation()) + 1;
            Location loc = new Location(localWorld, p.getLocation().getX(), y, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
            p.teleport(loc);
        }
    }

    public void teleportNormal(Player p, String world_name) {
        int i = this.border;
        World localWorld = p.getWorld();
        if (localWorld.getEnvironment().equals(World.Environment.NETHER)) {
            return;
        }
        teleport(p, i - 5, world_name);
    }

    public void teleportBorder(Player p, String world_name) {
        int i = this.border;
        World localWorld = p.getWorld();
        if (localWorld.getEnvironment().equals(World.Environment.NETHER)) {
            return;
        }
        if (p.getLocation().getBlockX() > i) {
            teleport(p, i - 5, world_name);
        }
        if (p.getLocation().getBlockZ() > i) {
            teleport(p, i - 5, world_name);
        }
        if (p.getLocation().getBlockX() < -(i + 1)) {
            teleport(p, i - 5, world_name);
        }
        if (p.getLocation().getBlockZ() < -(i + 1)) {
            teleport(p, i - 5, world_name);
        }
    }

    public void newBorderGenerator(int High, String worldname) {
        new BukkitRunnable() {

            private World world = Bukkit.getServer().getWorld(worldname);

            private int counter = -getBorder() - 1;
            private boolean phase1 = false;
            private boolean phase2 = false;
            private boolean phase3 = false;

            @Override
            public void run() {
                if (!this.phase1) {
                    int maxCounter = this.counter + 500;
                    int x = -getBorder() - 1;
                    for (int z = this.counter; z <= getBorder() && this.counter <= maxCounter; z++, this.counter++) {
                        Block highestBlock = this.world.getHighestBlockAt(x, z);

                        // Ignore non-solid blocks
                        while (!highestBlock.getType().isSolid() || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                            highestBlock = highestBlock.getRelative(0, -1, 0);
                        }

                        int y = highestBlock.getY() + 1;
                        int hight = highestBlock.getY() + High;
                        for (int i = y; i < hight; i++) {
                            Block block = this.world.getBlockAt(x, i, z);

                            block.setType(Material.BEDROCK);
                        }
                    }

                    if (this.counter >= getBorder()) {
                        this.counter = -getBorder() - 1;
                        this.phase1 = true;
                    }

                    return;
                }

                if (!this.phase2) {
                    int maxCounter = this.counter + 500;
                    int x = getBorder();
                    for (int z = this.counter; z <= getBorder() && this.counter <= maxCounter; z++, this.counter++) {
                        Block highestBlock = this.world.getHighestBlockAt(x, z);

                        // Ignore non-solid blocks
                        while (!highestBlock.getType().isSolid()
                                || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                            highestBlock = highestBlock.getRelative(0, -1, 0);
                        }

                        int y = highestBlock.getY() + 1;
                        int hight = highestBlock.getY() + High;
                        for (int i = y; i < hight; i++) {
                            Block block = this.world.getBlockAt(x, i, z);

                            block.setType(Material.BEDROCK);
                        }
                    }

                    if (this.counter >= getBorder()) {
                        this.counter = -getBorder() - 1;
                        this.phase2 = true;
                    }

                    return;
                }

                if (!this.phase3) {
                    int maxCounter = this.counter + 500;
                    int z = -getBorder() - 1;
                    for (int x = this.counter; x <= getBorder() && this.counter <= maxCounter; x++, this.counter++) {
                        if (x == getBorder() || x == -getBorder() - 1) {
                            continue;
                        }

                        Block highestBlock = this.world.getHighestBlockAt(x, z);

                        // Ignore non-solid blocks
                        while (!highestBlock.getType().isSolid()
                                || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                            highestBlock = highestBlock.getRelative(0, -1, 0);
                        }

                        int y = highestBlock.getY() + 1;
                        int hight = highestBlock.getY() + High;
                        for (int i = y; i < hight; i++) {
                            Block block = this.world.getBlockAt(x, i, z);

                            block.setType(Material.BEDROCK);
                        }
                    }

                    if (this.counter >= getBorder()) {
                        this.counter = -getBorder() - 1;
                        this.phase3 = true;
                    }

                    return;
                }


                int maxCounter = this.counter + 500;
                int z = getBorder();
                for (int x = this.counter; x <= getBorder() && this.counter <= maxCounter; x++, this.counter++) {
                    if (x == getBorder() || x == -getBorder() - 1) {
                        continue;
                    }

                    Block highestBlock = this.world.getHighestBlockAt(x, z);

                    // Ignore non-solid blocks
                    while (!highestBlock.getType().isSolid()
                            || highestBlock.getType() == Material.LEAVES || highestBlock.getType() == Material.LEAVES_2) {
                        highestBlock = highestBlock.getRelative(0, -1, 0);
                    }

                    int y = highestBlock.getY() + 1;
                    int hight = highestBlock.getY() + High;
                    for (int i = y; i < hight; i++) {
                        Block block = this.world.getBlockAt(x, i, z);

                        block.setType(Material.BEDROCK);
                    }
                }

                if (this.counter >= getBorder()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

}
