package com.isnakebuzz.meetup.g;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LobbyManager {

    private Main plugin;
    private List<Player> PlayersInLobby;
    private Location worldLobby;

    public LobbyManager(Main plugin) {
        this.plugin = plugin;
        this.PlayersInLobby = new ArrayList<>();
    }

    public List<Player> getPlayersInLobby() {
        return PlayersInLobby;
    }

    public void addSpawnLocation(Player p) throws IOException {
        FileConfiguration config = plugin.getConfigUtils().getConfig(plugin, "Utils/SpawnLocs");
        int i = 1;
        try {
            Set<String> keys = config.getConfigurationSection("Spawns").getKeys(false);
            i = keys.size() + 1;
        } catch (Exception ex) {
        }
        config.set("Spawns." + i, LocToString(p.getLocation()));
        config.save(plugin.getConfigUtils().getFile(plugin, "Utils/SpawnLocs"));
        p.sendMessage(c("&b➠&e You has been setted &aLobby#" + i));
        p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
    }

    public void removeSpawnLocation(Player p) throws IOException {
        FileConfiguration config = plugin.getConfigUtils().getConfig(plugin, "Utils/SpawnLocs");
        int i = 1;
        try {
            Set<String> keys = config.getConfigurationSection("Spawns").getKeys(false);
            i = keys.size();
        } catch (Exception ex) {
        }
        config.set("Spawns." + i, null);
        config.save(plugin.getConfigUtils().getFile(plugin, "Utils/SpawnLocs"));
        p.sendMessage(c("&b➠&e You has been removed &aLobby#" + i));
        p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1, 0);
    }

    public Location getWorldLobby(){
        return worldLobby;
    }

    public void setWorldLobby(Location worldLobby) {
        this.worldLobby = worldLobby;
    }

    public Location getLobby() {
        FileConfiguration config = plugin.getConfigUtils().getConfig(plugin, "Utils/SpawnLocs");
        Set<String> keys = config.getConfigurationSection("Spawns").getKeys(false);
        List<String> spawns = new ArrayList<>();
        for (String toList : keys) {
            spawns.add(toList);
        }
        int spawnID = new SecureRandom().nextInt(spawns.size());
        return StringToLoc(config.getString("Spawns." + spawns.get(spawnID)));
    }

    public String LocToString(Location l) {
        return String
                .valueOf(new StringBuilder(String.valueOf(l.getWorld().getName())).append(":").append(l.getBlockX())
                        .toString())
                + ":" + String.valueOf(l.getBlockY()) + ":" + String.valueOf(l.getBlockZ()) + ":"
                + String.valueOf(l.getYaw()) + ":" + String.valueOf(l.getPitch());
    }

    public Location StringToLoc(String s) {
        Location l = null;
        try {
            World world = Bukkit.getWorld(s.split(":")[0]);
            Double x = Double.parseDouble(s.split(":")[1]);
            Double y = Double.parseDouble(s.split(":")[2]);
            Double z = Double.parseDouble(s.split(":")[3]);
            Float p = Float.parseFloat(s.split(":")[4]);
            Float y2 = Float.parseFloat(s.split(":")[5]);

            return l = new Location(world, x + 0.5, y, z + 0.5, p, y2);
        } catch (Exception ex) {
        }
        return l;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }

}
