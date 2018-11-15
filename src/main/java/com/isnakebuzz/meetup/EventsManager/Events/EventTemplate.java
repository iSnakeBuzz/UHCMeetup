package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class EventTemplate implements Listener {

    private Main plugin;

    public EventTemplate(Main plugin) {
        this.plugin = plugin;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
