package com.isnakebuzz.meetup.EventsManager.VoteEvents;

import com.isnakebuzz.meetup.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class VEventTemplate implements Listener {

    private Main plugin;

    public VEventTemplate(Main plugin) {
        this.plugin = plugin;
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
