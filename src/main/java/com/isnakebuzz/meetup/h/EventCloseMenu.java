package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class EventCloseMenu implements Listener {

    private Main plugin;

    public EventCloseMenu(Main plugin) {
        this.plugin = plugin;
    }



    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
