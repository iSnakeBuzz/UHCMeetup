package com.isnakebuzz.meetup.c;

import com.isnakebuzz.meetup.a.Main;
import com.isnakebuzz.meetup.b.States;
import com.isnakebuzz.meetup.d.Border;
import com.isnakebuzz.meetup.e.API;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListeners implements Listener{
    
    private final Main plugin;
    
    public GameListeners (Main plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void NoLeave(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Border.checkBorder(p);
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (States.state == States.STARTING){
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                Location loc = event.getFrom();
                event.getPlayer().teleport(loc.setDirection(event.getTo().getDirection()));
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void CommandMenuSecretInGame(final PlayerCommandPreprocessEvent e) {
        String cmd;
        final Player p = e.getPlayer();
        cmd = e.getMessage();
        if (cmd.equals("/mlg") && States.state == States.FINISH) {
            Bukkit.broadcastMessage("§aEmpezo el desafio §c§lMLG");
            API.MLGe = true;
            API.MLG.add(p);
            e.setCancelled(true);
        }
    }
    public void init(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
