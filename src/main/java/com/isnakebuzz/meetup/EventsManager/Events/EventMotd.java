package com.isnakebuzz.meetup.EventsManager.Events;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.Enums.GameStates;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class EventMotd implements Listener {

    private Main plugin;

    public EventMotd(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void MotdChange(ServerListPingEvent e) {
        Configuration lang = plugin.getConfig("Lang");
        e.setMotd(c(lang.getString("Motd." + plugin.getArenaManager().getGameStates().toString())));
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
