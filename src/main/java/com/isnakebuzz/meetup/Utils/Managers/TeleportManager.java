package com.isnakebuzz.meetup.Utils.Managers;

import com.isnakebuzz.meetup.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TeleportManager {

    private Main plugin;
    private ArrayList<Location> locations;

    public TeleportManager(Main plugin) {
        this.plugin = plugin;
        this.locations = new ArrayList<>();
    }

    public Collection<Location> getLocations() {
        return locations;
    }

    public void teleport() {
        int i = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(locations.get(i));
            i++;
        }
    }

    public void loadLocations(String world_name) {
        Configuration config = plugin.getConfigUtils().getConfig(plugin, "Settings");
        for (int i = 0; i < config.getInt("GameOptions.MaxPlayers"); i++) {
            Random r = new Random();
            World world = Bukkit.getWorld(world_name);

            int radius = plugin.getBorderManager().getBorder() - 10;
            double d1 = r.nextDouble() * radius * 2.0D - radius;
            double d2 = r.nextDouble() * radius * 2.0D - radius;
            d1 = Math.round(d1) + 0.5D;
            d2 = Math.round(d2) + 0.5D;

            int y = world.getHighestBlockYAt((int) d1, (int) d2);
            Location loc = new Location(world, d1, (double) y, d2);

            if (isLocationValid(loc, locations, config.getDouble("TeleportOptions.MinDistance"))) {
                if (isLocationBlockValid(loc))
                    this.getLocations().add(loc);
                else
                    i--;
            } else {
                i--;
            }
        }
    }

    private boolean isLocationValid(Location location, Collection<Location> locations, Double d) {
        for (Location loc : locations) {
            if (Math.sqrt(NumberConversions.square(loc.getX() - location.getX()) + NumberConversions.square(loc.getZ() - location.getZ())) < d) {
                return false;
            }
        }
        return true;
    }

    private void updateEntities(Player tpedPlayer, List<Player> players, boolean visible) {
        // Hide or show every player to tpedPlayer
        // and hide or show tpedPlayer to every player.
        for (Player player : players) {
            if (visible) {
                tpedPlayer.showPlayer(player);
                player.showPlayer(tpedPlayer);
            } else {
                tpedPlayer.hidePlayer(player);
                player.hidePlayer(tpedPlayer);
            }
        }
    }

    public void fixTeleport(Player p) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                final List<Player> nearby = getPlayersWithin(p, 150);
                updateEntities(p, nearby, false);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        updateEntities(p, nearby, true);
                    }
                }, 1);
            }
        }, 5);
    }

    private List<Player> getPlayersWithin(Player player, int distance) {
        List<Player> res = new ArrayList<Player>();
        int d2 = distance * distance;
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p != player && p.getWorld() == player.getWorld()
                    && p.getLocation().distanceSquared(player.getLocation()) <= d2) {
                res.add(p);
            }
        }
        return res;
    }

    private boolean isLocationBlockValid(Location loc) {
        Material type = loc.getBlock().getRelative(0, -6, 0).getType();
        return !(type == Material.LAVA || type == Material.STATIONARY_LAVA || type == Material.WATER || type == Material.STATIONARY_WATER);
    }

}
