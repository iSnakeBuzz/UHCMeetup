package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EventWorld implements Listener {

    private Main plugin;

    public EventWorld(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void CancelSpawnMob(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (!e.getWorld().hasStorm()) e.setCancelled(true);
    }


    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
