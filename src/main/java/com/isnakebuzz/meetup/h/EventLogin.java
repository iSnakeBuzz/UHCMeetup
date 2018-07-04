package com.isnakebuzz.meetup.h;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventLogin implements Listener {

    private Main plugin;

    public EventLogin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void LoginEvent(PlayerLoginEvent e){
        if (Main.getStates.state == Main.getStates.LOADING){
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cLoading Game");
        } else if (Main.getStates.state != Main.getStates.LOBBY){
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cIn Game");
        }
    }

    private String c(String c) {
        return ChatColor.translateAlternateColorCodes('&', c);
    }
}
